/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Model.InvoiceDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public interface InvoiceDetailDAO {
    InvoiceDetail getInvoiceDetailByID(int id);
    ArrayList<InvoiceDetail> getAllInvoiceDetails();
    ArrayList<InvoiceDetail> getAllInvoiceDetailsByInvoiceId(int invoiceId);
    boolean addInvoiceDetail(InvoiceDetail invoiceDetail);
    boolean updateInvoiceDetail(InvoiceDetail invoiceDetail);
    boolean deleteInvoiceDetail(int id);
    default InvoiceDetail getInvoiceDetailInfor(ResultSet rs) throws SQLException {
        return new InvoiceDetail(rs.getInt("ID"),
                        rs.getInt("INVOICE_ID"),
                        rs.getInt("BEVERAGES_ID"),
                        rs.getInt("QUANTITY"),
                        rs.getInt("PRICE"),
                        rs.getInt("AMOUNT"));
    }
}
