/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Impl;

import Common.DBConnection;
import Common.Untils;
import DAO.CustomerDAO;
import Model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author DZUNG
 */
public class CustomerDAO implements CustomerDAO{
    public static ArrayList<Customer> getlistCustomer() {
        ArrayList<Customer> info = new ArrayList<>();
        try {
            ConnectDB.getConnection();
            String sql = "select * from CUSTOMER";
            ResultSet rs = ConnectDB.executeQuery(sql);
            while (rs.next()) {
                ds.add(new Customer(rs.getString("ID").trim(),
                        rs.getString("NAME".trim()),
                        rs.getString("PHONE").trim(),
                        rs.getString("EMAIL").trim(),
                        rs.getString("TIER_ID").trim(),
                        rs.getString("CREATED_BY").trim()),
                        rs.getString("CREATED_AT").trim(),
                        rs.getString("LAST_UPDATED_BY").trim(),
                        rs.getString("LAST_UPDATED_AT").trim(),
                        rs.getString("DELETED").trim());
            }
        } catch (Exception e) {
        }
        ConnectDB.close();
        return info;
    }

    public static ArrayList<Customer> getinfoCustomer(String Tier_ID) {
        ArrayList<Customer> info = new ArrayList<>();
        for (Customer ct : getlistCustomer()) {
            if (ct.getCustomerTier().trim().toUpperCase().equals(Tier_ID.trim().toUpperCase())) {
                info.add(ct);
            }
        }
        return info;
    }

    public static Customer getinfoCustomer(String Tier_ID, String ID) {
        Customer ct1 = null;
        for (Customer ct : getlistCustomer()) {
            if (ct.getTier_ID().trim().toUpperCase().equals(Tier_ID.trim().toUpperCase())
                    && ct.getID().trim().toUpperCase().equals(ID.trim().toUpperCase())) {
                ct1 = ct;
                break;
            }
        }
        return ct1;
    }

    public static int deleteCustomer(String ID) {
        int i = -1;
        String sql = "delete Customer where ID='" + Customer + "'";
        try {
            ConnectDB.getConnection();
            i = ConnectDB.executeUpdate(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error", "Warning", 1);
        }
        ConnectDB.close();
        return i;
    }

    public static int addCustomer(String ID, String NAME, String PHONE, String EMAIL, String TIER_ID, String CREATED_BY, String CREATED_AT, String LAST_UPDATE_BY, String LAST_UPDATE_AT, String DELETED) {
        int i = -1;
        String sql = "insert into CUSTOMER values(?,?,?,?,?,?,?,?,?,?)";
        try {
            ConnectDB.getConnection();
            PreparedStatement pre = ConnectDB.con.prepareStatement(sql);
            pre.setString(1, ID);
            pre.setString(2, NAME);
            pre.setString(3, PHONE);
            pre.setString(4, EMAIL);
            pre.setString(5, TIER_ID);
            pre.setString(6, CREATED_BY);
            pre.setString(7, CREATED_AT);
            pre.setString(8, LAST_UPDATE_BY);
            pre.setString(9, LAST_UPDATE_AT);
            pre.setString(10, DELETED);
            i = pre.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error", "Warning", 1);

        }
        ConnectDB.close();
        return i;
    }

    public static int editCustomer(String ID, String NAME, String PHONE, String EMAIL, String TIER_ID, String DELETED) {
        int i = -1;
        String sql = "update CUSTOMER set ID=?,NAME=?,PHONE=?,EMAIL=?,TIER_ID=?,DELETED=? where ID=?";
        try {
            ConnectDB.getConnection();
            PreparedStatement pre = ConnectDB.con.prepareStatement(sql);
            pre.setString(6, ID);
            pre.setString(1, NAME);
            pre.setString(2, PHONE);
            pre.setString(3, EMAIL);
            pre.setString(4, TIER_ID);
            pre.setString(5, DELETED);
            i = pre.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error", "Warning", 1);

        }
        ConnectDB.close();
        return i;
    }

    public static ResultSet searchCustomer(String ID) {
        String sql = "select * from CUSTOMER,CUSTOMER_TIER where CUSTOMER.TIER_ID=CUSTOMER_TIER.ID and ID=?";
        ResultSet rs = null;
        try {
            ConnectDB.getConnection();
            PreparedStatement pre = ConnectDB.getCon().prepareStatement(sql);
            pre.setString(1, ID);
            rs = pre.executeQuery();
        } catch (Exception ex) {

        }
        return rs;
    }
}
