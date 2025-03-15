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
    private String username;
    private int role;

    private UserSession(String username, int role) {
        this.username = username;
        this.role = role;
    }

    public static void createSession(String username, int role) {
        instance = new UserSession(username, role);
    }

    public static UserSession getInstance() {
        return instance;
    }

    public static void clearSession() {
        instance = null; // Đăng xuất
    }

    public String getUsername() {
        return username;
    }

    public int getRole() {
        return role;
    }
}