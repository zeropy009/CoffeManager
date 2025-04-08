/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Impl;

import Common.DBConnection;
import Common.UserSession;
import DAO.BeveragesCategoryDAO;
import Model.BeveragesCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public class BeveragesCategoryImpl implements BeveragesCategoryDAO{

    @Override
    public BeveragesCategory getBeveragesCategoryByID(int id) {
         String query = "SELECT * FROM BEVERAGES_CATEGORY WHERE ID = ? AND DELETED = 0";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return getBeveragesCategoryInfor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<BeveragesCategory> getAllBeveragesCategory() {
        ArrayList<BeveragesCategory> resList = new ArrayList<>();
        String query = "SELECT * FROM BEVERAGES_CATEGORY WHERE DELETED = 0";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resList.add(getBeveragesCategoryInfor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    @Override
    public boolean addBeveragesCategory(BeveragesCategory beveragesCategory) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO BEVERAGES_CATEGORY (NAME, CREATED_BY, LAST_UPDATE_BY) VALUES");
        query.append("(?, ?, ?)");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            
            stmt.setString(1, beveragesCategory.getName());
            stmt.setString(2, UserSession.getInstance().getUsername());
            stmt.setString(3, UserSession.getInstance().getUsername());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBeveragesCategory(BeveragesCategory beveragesCategory) {
        String query = "UPDATE BEVERAGES_CATEGORY SET NAME = ?, LAST_UPDATE_BY = ? WHERE ID = ?";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, beveragesCategory.getName());
            stmt.setString(2, UserSession.getInstance().getUsername());
            stmt.setInt(3, beveragesCategory.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBeveragesCategory(int id) {
        String query = "UPDATE BEVERAGES_CATEGORY SET LAST_UPDATE_BY = ?, DELETED = 1 WHERE ID = ?";
    
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
