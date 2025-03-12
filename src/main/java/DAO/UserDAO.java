/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.User;
import java.util.List;

/**
 *
 * @author zero
 */
public interface UserDAO {
    User getUserById(int id);
    List<User> getAllUsers();
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
}
