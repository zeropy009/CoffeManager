/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Common;

/**
 *
 * @author zero
 */
public class UserSession {
    private static UserSession instance;
    private String userName;
    private String fullName;
    private int role;

    private UserSession(String userName, String fullName, int role) {
        this.userName = userName;
        this.fullName = fullName;
        this.role = role;
    }

    public static void createSession(String username, String fullName, int role) {
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

    public int getRole() {
        return role;
    }
}