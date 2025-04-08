/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Common;

import Enums.Roles;

/**
 *
 * @author zero
 */
public class UserSession {
    private static UserSession instance;
    private String userName;
    private String fullName;
    private Roles role;

    private UserSession(String userName, String fullName, Roles role) {
        this.userName = userName;
        this.fullName = fullName;
        this.role = role;
    }

    public static void createSession(String username, String fullName, Roles role) {
        instance = new UserSession(username, fullName, role);
    }

    public static UserSession getInstance() {
        return instance;
    }

    public static void clearSession() {
        instance = null; // Đăng xuất
    }

    public String getUsername() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public Roles getRole() {
        return role;
    }
}