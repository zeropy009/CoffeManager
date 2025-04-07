/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Impl;

import Common.DBConnection;
import Common.Untils;
import Common.UserSession;
import DAO.InvoiceDAO;
import Model.Invoice;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public class InvoiceImpl implements InvoiceDAO {

    @Override
    public Invoice getInvoiceById(int id) {
        String query = "SELECT * FROM INVOICE WHERE ID = ? AND DELETED = 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return getInvoiceInfor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Invoice> getAllInvoices() {
        ArrayList<Invoice> resList = new ArrayList<>();
        String query = "SELECT * FROM INVOICE WHERE DELETED = 0";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resList.add(getInvoiceInfor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    @Override
    public boolean addInvoice(Invoice invoice) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO INVOICE (DATE, TOTAL_AMOUNT, USER_NAME, CUSTOMER_ID, DISCOUNT_PERCENTAGE, TABLE_ID, CREATED_BY, LAST_UPDATE_BY) VALUES");
        query.append("(?, ?, ?, ?, ?, ?, ?, ?)");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            
            stmt.setTimestamp(1, invoice.getDate());
            stmt.setInt(2, invoice.getTotalAmount());
            stmt.setString(3, invoice.getUserName());
            stmt.setInt(4, invoice.getCustomerId());
            stmt.setDouble(5, invoice.getDiscountPercentage());
            stmt.setInt(6, invoice.getTableId());
            stmt.setString(7, UserSession.getInstance().getUsername());
            stmt.setString(8, UserSession.getInstance().getUsername());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateInvoice(Invoice invoice) {
        String query = "UPDATE INVOICE SET DATE = ?, TOTAL_AMOUNT = ?, USER_NAME = ?, CUSTOMER_ID = ?, DISCOUNT_PERCENTAGE = ?, TABLE_ID = ?, LAST_UPDATE_BY = ? WHERE ID = ?";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, invoice.getDate());
            stmt.setInt(2, invoice.getTotalAmount());
            stmt.setString(3, invoice.getUserName());
            stmt.setInt(4, invoice.getCustomerId());
            stmt.setDouble(5, invoice.getDiscountPercentage());
            stmt.setInt(6, invoice.getTableId());
            stmt.setString(7, UserSession.getInstance().getUsername());
            stmt.setInt(8, invoice.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteInvoice(int id) {
        String query = "UPDATE INVOICE SET LAST_UPDATE_BY = ?, DELETED = 1 WHERE ID = ?";
    
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
