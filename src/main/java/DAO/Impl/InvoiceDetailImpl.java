/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Impl;

import Common.DBConnection;
import Common.UserSession;
import DAO.InvoiceDetailDAO;
import Model.InvoiceDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public class InvoiceDetailImpl implements InvoiceDetailDAO {

    @Override
    public Model.InvoiceDetail getInvoiceDetailByID(int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT d.*, b.[NAME] BEVERAGES_NAME");
        query.append(" FROM INVOICE_DETAIL d");
        query.append(" LEFT JOIN BEVERAGES b");
        query.append(" ON d.BEVERAGES_ID = b.ID");
        query.append(" WHERE d.ID = ? AND d.DELETED = 0");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return getInvoiceDetailInfor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<InvoiceDetail> getAllInvoiceDetails() {
        ArrayList<InvoiceDetail> resList = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT d.*, b.[NAME] BEVERAGES_NAME");
        query.append(" FROM INVOICE_DETAIL d");
        query.append(" LEFT JOIN BEVERAGES b");
        query.append(" ON d.BEVERAGES_ID = b.ID");
        query.append(" WHERE d.DELETED = 0");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resList.add(getInvoiceDetailInfor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    @Override
    public ArrayList<InvoiceDetail> getAllInvoiceDetailsByInvoiceId(int invoiceId) {
        ArrayList<InvoiceDetail> resList = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT d.*, b.[NAME] BEVERAGES_NAME");
        query.append(" FROM INVOICE_DETAIL d");
        query.append(" LEFT JOIN BEVERAGES b");
        query.append(" ON d.BEVERAGES_ID = b.ID");
        query.append(" WHERE d.DELETED = 0 AND d.INVOICE_ID = ?");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            stmt.setInt(1, invoiceId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resList.add(getInvoiceDetailInfor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    @Override
    public boolean addInvoiceDetail(InvoiceDetail invoiceDetail) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO INVOICE_DETAIL (INVOICE_ID, BEVERAGES_ID, QUANTITY, PRICE, AMOUNT, CREATED_BY, LAST_UPDATE_BY) VALUES");
        query.append("(?, ?, ?, ?, ?, ?, ?, ?)");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            
            stmt.setInt(1, invoiceDetail.getInvoiceId());
            stmt.setInt(2, invoiceDetail.getBeveragesId());
            stmt.setInt(3, invoiceDetail.getQuantity());
            stmt.setInt(4, invoiceDetail.getPrice());
            stmt.setInt(5, invoiceDetail.getAmount());
            stmt.setString(6, UserSession.getInstance().getUsername());
            stmt.setString(7, UserSession.getInstance().getUsername());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateInvoiceDetail(InvoiceDetail invoiceDetail) {
        String query = "UPDATE INVOICE_DETAIL SET INVOICE_ID = ?, BEVERAGES_ID = ?, QUANTITY = ?, PRICE = ?, AMOUNT = ?, LAST_UPDATE_BY = ? WHERE ID = ?";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, invoiceDetail.getInvoiceId());
            stmt.setInt(2, invoiceDetail.getBeveragesId());
            stmt.setInt(3, invoiceDetail.getQuantity());
            stmt.setInt(4, invoiceDetail.getPrice());
            stmt.setInt(5, invoiceDetail.getAmount());
            stmt.setString(6, UserSession.getInstance().getUsername());
            stmt.setInt(7, invoiceDetail.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteInvoiceDetail(int id) {
        String query = "UPDATE INVOICE_DETAIL SET LAST_UPDATE_BY = ?, DELETED = 1 WHERE ID = ?";
    
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
