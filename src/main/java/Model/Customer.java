/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author DZUNG
 */
public class Customer extends BaseModel {
    private int id;
    private String name;
    private String phone;
    private String email;
    private int tierId;
    private String tierName;
    private double discountPercentage;

    public Customer() {
    }

    public Customer(int id, String name, String phone, String email, int tierId, String tierName, double discountPercentage) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tierId = tierId;
        this.tierName = tierName;
        this.discountPercentage = discountPercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTierId() {
        return tierId;
    }

    public void setTierId(int tierId) {
        this.tierId = tierId;
    }

    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
