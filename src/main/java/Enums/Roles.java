/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enums;

/**
 *
 * @author zero
 */
public enum Roles {
    MANAGER(1),
    STAFF(2);
    
    private final int code;

    private Roles(int code) {
        this.code = code;
    }
    
    public int getCode(){
        return code;
    }
    
    public static Roles fromCode(int code) {
        for (Roles r : Roles.values()) {
            if (r.code == code) return r;
        }
        throw new IllegalArgumentException("Mã vai trò không hợp lệ: " + code);
    }
    
    @Override
    public String toString() {
        return code == 1 ? "Quản lý" : "Nhân viên";
    }
}
