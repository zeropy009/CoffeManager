/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enums;

/**
 *
 * @author zero
 */
public enum Sex {
    FEMALE(false),
    MALE(true);
    
    private final boolean sex;
    
    private Sex(boolean sex){
        this.sex = sex;
    }
    
    public boolean getSex(){
        return this.sex;
    }
    
    public static Sex fromSex(boolean sex) {
        for (Sex r : Sex.values()) {
            if (r.sex == sex) return r;
        }
        throw new IllegalArgumentException("Giá trị giới tính không hợp lệ: " + sex);
    }
    
    @Override
    public String toString() {
        return sex ? "Nam" : "Nữ";
    }
}
