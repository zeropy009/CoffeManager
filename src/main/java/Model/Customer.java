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
    private int tier_id;

    public Customer() {
    }

    public Customer(int id, String name, String phone, String email, int tier_id) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tier_id = tier_id;
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

    public int getTier_id() {
        return tier_id;
    }

    public void setTier_id(int tier_id) {
        this.tier_id = tier_id;
    }
}
