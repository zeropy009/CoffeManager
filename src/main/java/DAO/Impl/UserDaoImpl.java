/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Impl;

import Common.DBConnection;
import Common.Untils;
import Common.UserSession;
import DAO.UserDAO;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public class UserDaoImpl implements UserDAO {
    
    @Override
    public User Login(String userName, String passWord){
        String query = "SELECT * FROM [USER] WHERE USER_NAME = ? AND PASS_WORD = ? AND DELETED = 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userName);
            stmt.setString(2, Untils.hashMD5(passWord)); 
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = getUserInfor(rs);
                UserSession.createSession(user.getUserName(), user.getFullName(), user.getRole());
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByUserName(String userName) {
        String query = "SELECT * FROM [USER] WHERE USER_NAME = ? AND DELETED = 0";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return getUserInfor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> resList = new ArrayList<>();
        String query = "SELECT * FROM [USER] WHERE DELETED = 0";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resList.add(getUserInfor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    @Override
    public boolean addUser(User user) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO [USER]");
        query.append(" (USER_NAME,");
        query.append(" PASS_WORD,");
        query.append(" ROLE_ID,");
        query.append(" FULL_NAME,");
        query.append(" SEX,");
        query.append(" ADDRESS,");
        query.append(" YEAR_OF_BIRTH,");
        query.append(" PHONE,");
        query.append(" EMAIL,");
        query.append(" SALARY,");
        query.append(" CREATED_BY,");
        query.append(" LAST_UPDATE_BY) VALUES ");
        query.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            
            stmt.setString(1, user.getUserName());
            stmt.setString(2, Untils.hashMD5(user.getPassWord()));
            stmt.setInt(3, user.getRole());
            stmt.setString(4, user.getFullName());
            stmt.setBoolean(5, user.isSex());
            stmt.setString(6, user.getAddress());
            stmt.setInt(7, user.getYearOfBirth());
            stmt.setString(8, user.getPhone());
            stmt.setString(9, user.getEmail());
            stmt.setInt(10, user.getSalary());
            stmt.setString(11, UserSession.getInstance().getUsername());
            stmt.setString(12, UserSession.getInstance().getUsername());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE [USER] SET");
        query.append(" PASS_WORD = ?,");
        query.append(" ROLE_ID = ?,");
        query.append(" FULL_NAME = ?,");
        query.append(" SEX = ?,");
        query.append(" ADDRESS = ?,");
        query.append(" YEAR_OF_BIRTH = ?,");
        query.append(" PHONE = ?,");
        query.append(" EMAIL = ?,");
        query.append(" SALARY = ?,");
        query.append(" LAST_UPDATE_BY = ?");
        query.append(" WHERE [USER_NAME] = ?");
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            
            stmt.setString(1, Untils.hashMD5(user.getPassWord()));
            stmt.setInt(2, user.getRole());
            stmt.setString(3, user.getFullName());
            stmt.setBoolean(4, user.isSex());
            stmt.setString(5, user.getAddress());
            stmt.setInt(6, user.getYearOfBirth());
            stmt.setString(7, user.getPhone());
            stmt.setString(8, user.getEmail());
            stmt.setInt(9, user.getSalary());
            stmt.setString(10, UserSession.getInstance().getUsername());
            stmt.setString(11, user.getUserName());
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(String userName) {
        String query = "UPDATE [USER] SET LAST_UPDATE_BY = ?, DELETED = 1 WHERE [USER_NAME] = ?";
    
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, UserSession.getInstance().getUsername());
            stmt.setString(2, userName);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
