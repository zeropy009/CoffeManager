/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public interface UserDAO {
    User Login(String userName, String passWord);
    User getUserByUserName(String userName);
    ArrayList<User> getAllUsers();
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(String userName);
    default User getUserInfor(ResultSet rs) throws SQLException {
        return new User(rs.getString("USER_NAME"),
                        rs.getString("PASS_WORD"),
                        rs.getInt("ROLE_ID"),
                        rs.getString("FULL_NAME"),
                        rs.getBoolean("SEX"),
                        rs.getString("ADDRESS"),
                        rs.getInt("YEAR_OF_BIRTH"),
                        rs.getString("PHONE"),
                        rs.getString("EMAIL"),
                        rs.getInt("SALARY"));
    }
}
