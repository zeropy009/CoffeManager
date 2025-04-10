/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Impl;

import Common.DBConnection;
import Common.UserSession;
import DAO.WarehouseDAO;
import Model.Warehouse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public class WarehouseImpl implements WarehouseDAO {

    @Override
    public Warehouse getWarehouseByID(int id) {
        String query = "SELECT * FROM WAREHOUSE WHERE ID = ? AND DELETED = 0";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return getWarehouseInfor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Warehouse> getAllWarehouses() {
        ArrayList<Warehouse> resList = new ArrayList<>();
        String query = "SELECT * FROM WAREHOUSE WHERE DELETED = 0";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resList.add(getWarehouseInfor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    @Override
    public boolean addWarehouse(Warehouse warehouse) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO WAREHOUSE (INPUT_DATE, USER_NAME, TOTAL_AMOUNT, CREATED_BY, LAST_UPDATE_BY) VALUES");
        query.append("(?, ?, ?, ?, ?)");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            
            stmt.setTimestamp(1, warehouse.getInputDate());
            stmt.setString(2, UserSession.getInstance().getUsername());
            stmt.setInt(3, warehouse.getTotalAmount());
            stmt.setString(4, UserSession.getInstance().getUsername());
            stmt.setString(5, UserSession.getInstance().getUsername());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateWarehouse(Warehouse warehouse) {
        String query = "UPDATE WAREHOUSE SET INPUT_DATE = ?, USER_NAME = ?, TOTAL_AMOUNT = ?, LAST_UPDATE_BY = ? WHERE ID = ?";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, warehouse.getInputDate());
            stmt.setString(2, warehouse.getUserName());
            stmt.setInt(3, warehouse.getTotalAmount());
            stmt.setString(4, UserSession.getInstance().getUsername());
            stmt.setInt(5, warehouse.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteWarehouse(int id) {
        String query = "UPDATE WAREHOUSE SET LAST_UPDATE_BY = ?, DELETED = 1 WHERE ID = ?";
    
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
