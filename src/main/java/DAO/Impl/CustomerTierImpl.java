/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Impl;

import Common.DBConnection;
import DAO.CustomerTierDAO;
import Model.CustomerTier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public class CustomerTierImpl implements CustomerTierDAO {

    @Override
    public CustomerTier getCustomerTierById(int id) {
        String query = "SELECT * FROM CUSTOMER_TIER WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return getCustomerTierInfor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<CustomerTier> getAllCustomerTier() {
        ArrayList<CustomerTier> resList = new ArrayList<>();
        String query = "SELECT * FROM CUSTOMER_TIER";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resList.add(getCustomerTierInfor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    @Override
    public boolean addCustomerTier(CustomerTier customerTier) {
        String query = "INSERT INTO [CUSTOMER_TIER] (NAME, MIN_PURCHASE, DISCOUNT_PERCENTAGE) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, customerTier.getName());
            stmt.setDouble(2, customerTier.getMinPurchase());
            stmt.setDouble(3, customerTier.getDiscountPercantage());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCustomerTier(CustomerTier customerTier) {
        String query = "UPDATE CUSTOMER_TIER SET NAME = ?, MIN_PURCHASE = ?, DISCOUNT_PERCENTAGE = ? WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, customerTier.getName());
            stmt.setDouble(2, customerTier.getMinPurchase());
            stmt.setDouble(3, customerTier.getDiscountPercantage());
            stmt.setInt(4, customerTier.getId());
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCustomerTier(int id) {
        String query = "DELETE CUSTOMER_TIER WHERE ID = ?";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
