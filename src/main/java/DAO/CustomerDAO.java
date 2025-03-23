/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Model.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author DZUNG
 */
public interface CustomerDAO {
    List<Customer> getlistCustomer();
    boolean getinfoCustomer(Customer customer);
    boolean deleteCustomer(string ID);
    boolean addCustomer(String ID, String NAME, String PHONE, String EMAIL, String TIER_ID, String CREATED_BY, String CREATED_AT, String LAST_UPDATE_BY, String LAST_UPDATE_AT, String DELETED);
    boolean editCustomer(String ID, String NAME, String PHONE, String EMAIL, String TIER_ID, String DELETED);
    boolean searchCustomer(String ID);
    default Customer getinfoCustomer(ResultSet rs) throws SQLException {
        return new Customer(rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getString("PHONE"),
                        rs.getInt("EMAIL"),
                        rs.getString("TIER_ID"),
                        rs.getBoolean("CREATED_BY"),
                        rs.getString("CREATED_AT"),
                        rs.getInt("LAST_UPDATED_BY"),
                        rs.getString("LAST_UPDATED_AT"),
                        rs.getString("DELETED"));
    }
}
