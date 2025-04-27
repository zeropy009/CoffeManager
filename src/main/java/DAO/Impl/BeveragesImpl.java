/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Impl;

import Common.DBConnection;
import Common.UserSession;
import Model.Beverages;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DAO.BeveragesDAO;

/**
 *
 * @author zero
 */
public class BeveragesImpl implements BeveragesDAO{

    @Override
    public Beverages getBeveragesByID(int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT b.*, bc.[NAME] AS BEVERAGES_CATEGORY_NAME");
        query.append(" FROM BEVERAGES b");
        query.append(" LEFT JOIN BEVERAGES_CATEGORY bc");
        query.append("   ON b.BEVERAGES_CATEGORY_ID = bc.ID");
        query.append(" WHERE b.ID = ? AND b.DELETED = 0");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return getBeveragesInfor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Beverages> getAllBeverages() {
        ArrayList<Beverages> resList = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT b.*, bc.[NAME] AS BEVERAGES_CATEGORY_NAME");
        query.append(" FROM BEVERAGES b");
        query.append(" LEFT JOIN BEVERAGES_CATEGORY bc");
        query.append("   ON b.BEVERAGES_CATEGORY_ID = bc.ID");
        query.append(" WHERE b.DELETED = 0");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resList.add(getBeveragesInfor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    @Override
    public boolean addBeverages(Beverages beverages) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO BEVERAGES (NAME, PRICE, BEVERAGES_CATEGORY_ID, CREATED_BY, LAST_UPDATE_BY) VALUES");
        query.append("(?, ?, ?, ?, ?)");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            
            stmt.setString(1, beverages.getName());
            stmt.setInt(2, beverages.getPrice());
            stmt.setInt(3, beverages.getBaveragesCategoryId());
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
    public boolean updateBeverages(Beverages bevereges) {
        String query = "UPDATE BEVERAGES SET NAME = ?, PRICE = ?, BEVERAGES_CATEGORY_ID = ?, LAST_UPDATE_BY = ? WHERE ID = ?";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bevereges.getName());
            stmt.setInt(2, bevereges.getPrice());
            stmt.setInt(3, bevereges.getBaveragesCategoryId());
            stmt.setString(4, UserSession.getInstance().getUsername());
            stmt.setInt(5, bevereges.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBeverages(int id) {
        String query = "UPDATE BEVERAGES SET LAST_UPDATE_BY = ?, DELETED = 1 WHERE ID = ?";
    
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
