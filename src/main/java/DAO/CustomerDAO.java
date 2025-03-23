/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Model.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DZUNG
 */
public interface CustomerDAO {
    Customer getCustomerByID(int id);
    ArrayList<Customer> getAllCustomers();
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int id);
    default Customer getCustomerInfor(ResultSet rs) throws SQLException {
        return new Customer(rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("PHONE"),
                        rs.getString("EMAIL"),
                        rs.getInt("TIER_ID"));
    }
}
