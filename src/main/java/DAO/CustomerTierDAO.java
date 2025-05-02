/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Model.CustomerTier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public interface CustomerTierDAO {
    CustomerTier getCustomerTierById(int id);
    ArrayList<CustomerTier> getAllCustomerTier();
    boolean addCustomerTier(CustomerTier customerTier);
    boolean updateCustomerTier(CustomerTier customerTier);
    boolean deleteCustomerTier(int id);
    default CustomerTier getCustomerTierInfor(ResultSet rs) throws SQLException {
        return new CustomerTier(rs.getInt("ID"),
                                rs.getString("NAME"),
                                rs.getDouble("MIN_PURCHASE"),
                                rs.getDouble("DISCOUNT_PERCENTAGE"));
    }
}
