/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Impl;

import Common.DBConnection;
import Common.UserSession;
import DAO.InvoiceDAO;
import Model.Invoice;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public class InvoiceImpl implements InvoiceDAO {

    @Override
    public Invoice getInvoiceById(int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT i.*, c.[NAME] CUSTOMER_NAME, t.[TABLE_NAME]");
        query.append(" FROM INVOICE i");
        query.append(" LEFT JOIN [CUSTOMER] c");
        query.append(" ON i.CUSTOMER_ID = c.ID");
        query.append(" LEFT JOIN [TABLE] t");
        query.append(" ON i.TABLE_ID = t.ID");
        query.append(" WHERE i.ID = ? AND i.DELETED = 0");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

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
        StringBuilder query = new StringBuilder();
        query.append("SELECT i.*, c.[NAME] CUSTOMER_NAME, t.[TABLE_NAME]");
        query.append(" FROM INVOICE i");
        query.append(" LEFT JOIN [CUSTOMER] c");
        query.append(" ON i.CUSTOMER_ID = c.ID");
        query.append(" LEFT JOIN [TABLE] t");
        query.append(" ON i.TABLE_ID = t.ID");
        query.append(" WHERE i.DELETED = 0");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {

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
    public ArrayList<Invoice> getAllInvoices(int id, Timestamp fromDate, Timestamp toDate){
        ArrayList<Invoice> resList = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT i.*, c.[NAME] CUSTOMER_NAME, t.[TABLE_NAME]");
        query.append(" FROM INVOICE i");
        query.append(" LEFT JOIN [CUSTOMER] c");
        query.append(" ON i.CUSTOMER_ID = c.ID");
        query.append(" LEFT JOIN [TABLE] t");
        query.append(" ON i.TABLE_ID = t.ID");
        query.append(" WHERE i.DELETED = 0");
        if (id != 0) {
            query.append(" AND i.ID = ?");
        }
        if (fromDate != null) {
            query.append(" AND i.[DATE] >= ?");
        }
        if (toDate != null) {
            query.append(" AND i.[DATE] < ?");
        }
        
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            int index = 1;
            if (id != 0) {
                stmt.setInt(index, id);
                index++;
            }
            if (fromDate != null) {
                stmt.setTimestamp(index, fromDate);
                index++;
            }
            if (toDate != null) {
                stmt.setTimestamp(index, toDate);
            }
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
