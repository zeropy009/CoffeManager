/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Impl;

import Common.DBConnection;
import Common.UserSession;
import DAO.WarehouseDetailDAO;
import Model.WarehouseDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author zero
 */
public class WarehouseDetailImpl implements WarehouseDetailDAO {

    @Override
    public WarehouseDetail getWarehouseDetailByID(int id) {
        String query = "SELECT * FROM WAREHOUSE_DETAIL WHERE ID = ? AND DELETED = 0";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return getWarehouseDetailInfor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<WarehouseDetail> getAllWarehouseDetails() {
        ArrayList<WarehouseDetail> resList = new ArrayList<>();
        String query = "SELECT * FROM WAREHOUSE_DETAIL WHERE DELETED = 0";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resList.add(getWarehouseDetailInfor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }
    
    @Override
    public ArrayList<WarehouseDetail> getAllWarehouseDetailsByWarehouseId(int warehouseId) {
        ArrayList<WarehouseDetail> resList = new ArrayList<>();
        String query = "SELECT * FROM WAREHOUSE_DETAIL WHERE DELETED = 0 AND WAREHOUSE_ID = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, warehouseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resList.add(getWarehouseDetailInfor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    @Override
    public boolean addWarehouseDetail(WarehouseDetail warehouseDetail) {
        if (warehouseDetail.getWarehouseId() != 0) {
            String query = "INSERT INTO WAREHOUSE_DETAIL (WAREHOUSE_ID, PRODUCT_NAME, QUANTITY, PRICE, AMOUNT, CREATED_BY, LAST_UPDATE_BY) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, warehouseDetail.getWarehouseId());
                stmt.setString(2, warehouseDetail.getProductName());
                stmt.setInt(3, warehouseDetail.getQuantity());
                stmt.setInt(4, warehouseDetail.getPrice());
                stmt.setInt(5, warehouseDetail.getAmount());
                stmt.setString(6, UserSession.getInstance().getUsername());
                stmt.setString(7, UserSession.getInstance().getUsername());

                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        else {
            String insertSql = "INSERT INTO WAREHOUSE (INPUT_DATE, USER_NAME, TOTAL_AMOUNT, CREATED_BY, LAST_UPDATE_BY) OUTPUT INSERTED.ID VALUES (?, ?, ?, ?, ?)"; 
            String query = "INSERT INTO WAREHOUSE_DETAIL (WAREHOUSE_ID, PRODUCT_NAME, QUANTITY, PRICE, AMOUNT, CREATED_BY, LAST_UPDATE_BY) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            try (Connection conn = DBConnection.getConnection()) {
                conn.setAutoCommit(false);
                
                try (
                        PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                        PreparedStatement stmt = conn.prepareStatement(query)   
                    ) {
                    insertStmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                    insertStmt.setString(2, UserSession.getInstance().getUsername());
                    insertStmt.setInt(3, warehouseDetail.getAmount());
                    insertStmt.setString(4, UserSession.getInstance().getUsername());
                    insertStmt.setString(5, UserSession.getInstance().getUsername());

                    ResultSet rs = insertStmt.executeQuery();
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        warehouseDetail.setWarehouseId(id);
                        stmt.setInt(1, warehouseDetail.getWarehouseId());
                        stmt.setString(2, warehouseDetail.getProductName());
                        stmt.setInt(3, warehouseDetail.getQuantity());
                        stmt.setInt(4, warehouseDetail.getPrice());
                        stmt.setInt(5, warehouseDetail.getAmount());
                        stmt.setString(6, UserSession.getInstance().getUsername());
                        stmt.setString(7, UserSession.getInstance().getUsername());

                        if (stmt.executeUpdate() > 0) {
                            conn.commit();
                            return true;
                        }
                    } else {
                        conn.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    
    @Override
    public boolean addWarehouseDetails(List<WarehouseDetail> warehouseDetailList) {
        if (warehouseDetailList.get(0).getWarehouseId() != 0) {
            String query = "INSERT INTO WAREHOUSE_DETAIL (WAREHOUSE_ID, PRODUCT_NAME, QUANTITY, PRICE, AMOUNT, CREATED_BY, LAST_UPDATE_BY) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String username = UserSession.getInstance().getUsername();
            
            try (Connection conn = DBConnection.getConnection()) {
                conn.setAutoCommit(false);
                
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    for (WarehouseDetail detail : warehouseDetailList) {
                        stmt.setInt(1, detail.getWarehouseId());
                        stmt.setString(2, detail.getProductName());
                        stmt.setInt(3, detail.getQuantity());
                        stmt.setInt(4, detail.getPrice());
                        stmt.setInt(5, detail.getAmount());
                        stmt.setString(6, username);
                        stmt.setString(7, username);

                        stmt.addBatch();
                    }
                    int[] batchResults = stmt.executeBatch();
                    
                    if (Arrays.stream(batchResults).sum() > 0) {
                        conn.commit();
                        return true;
                    } else {
                        conn.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            String insertSql = "INSERT INTO WAREHOUSE (INPUT_DATE, USER_NAME, TOTAL_AMOUNT, CREATED_BY, LAST_UPDATE_BY) OUTPUT INSERTED.ID VALUES (?, ?, ?, ?, ?)";
            String query = "INSERT INTO WAREHOUSE_DETAIL (WAREHOUSE_ID, PRODUCT_NAME, QUANTITY, PRICE, AMOUNT, CREATED_BY, LAST_UPDATE_BY) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            try (Connection conn = DBConnection.getConnection()) {
                conn.setAutoCommit(false);
                
                 try (
                        PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                        PreparedStatement stmt = conn.prepareStatement(query)   
                    ) {
                    
                    int totalAmount = warehouseDetailList.stream()
                                             .mapToInt(WarehouseDetail::getAmount)
                                             .sum();
                    String username = UserSession.getInstance().getUsername();
                    
                    insertStmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                    insertStmt.setString(2, UserSession.getInstance().getUsername());
                    insertStmt.setInt(3, totalAmount);
                    insertStmt.setString(4, username);
                    insertStmt.setString(5, username);

                    ResultSet rs = insertStmt.executeQuery();
                    if (rs.next()) {
                        int warehouseId  = rs.getInt(1);
                        for (WarehouseDetail detail : warehouseDetailList) {
                            stmt.setInt(1, warehouseId);
                            stmt.setString(2, detail.getProductName());
                            stmt.setInt(3, detail.getQuantity());
                            stmt.setInt(4, detail.getPrice());
                            stmt.setInt(5, detail.getAmount());
                            stmt.setString(6, username);
                            stmt.setString(7, username);

                            stmt.addBatch();
                        }

                        int[] batchResults = stmt.executeBatch();
                        conn.commit();

                        int insertedCount = Arrays.stream(batchResults).sum();
                        if (insertedCount == warehouseDetailList.size()) {
                            conn.commit();
                            return true;
                        } else {
                            conn.rollback();
                            return false;
                        }
                    } else {
                        conn.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean updateWarehouseDetail(WarehouseDetail warehouseDetail) {
        String query = "UPDATE WAREHOUSE_DETAIL SET WAREHOUSE_ID = ?, PRODUCT_NAME = ?, QUANTITY = ?, PRICE = ?, AMOUNT = ?, LAST_UPDATE_BY = ? WHERE ID = ?";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, warehouseDetail.getWarehouseId());
            stmt.setString(2, warehouseDetail.getProductName());
            stmt.setInt(3, warehouseDetail.getQuantity());
            stmt.setInt(4, warehouseDetail.getPrice());
            stmt.setInt(5, warehouseDetail.getAmount());
            stmt.setString(6, UserSession.getInstance().getUsername());
            stmt.setInt(7, warehouseDetail.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteWarehouseDetail(int id) {
        String query = "UPDATE WAREHOUSE_DETAIL SET LAST_UPDATE_BY = ?, DELETED = 1 WHERE ID = ?";
    
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, UserSession.getInstance().getUsername());
            stmt.setInt(2, id);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }   
}