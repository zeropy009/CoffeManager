/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Model.Invoice;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public interface InvoiceDAO {
    Invoice getInvoiceById(int id);
    Invoice getInvoiceByTableId(int tableId);
    ArrayList<Invoice> getAllInvoices();
    ArrayList<Invoice> getAllInvoices(int id, Timestamp fromDate, Timestamp toDate);
    boolean addInvoice(Invoice invoice);
    boolean updateInvoice(Invoice user);
    boolean deleteInvoice(int id);
    default Invoice getInvoiceInfor(ResultSet rs) throws SQLException {
        return new Invoice(rs.getInt("ID"),
                        rs.getTimestamp("DATE"),
                        rs.getInt("TOTAL_AMOUNT"),
                        rs.getString("USER_NAME"),
                        rs.getInt("CUSTOMER_ID"),
                        rs.getDouble("DISCOUNT_PERCENTAGE"),
                        rs.getInt("TABLE_ID"),
                        rs.getBoolean("PAYMENT_STATUS"),
                        rs.getString("CUSTOMER_NAME"),
                        rs.getString("TABLE_NAME"));
    }
}
