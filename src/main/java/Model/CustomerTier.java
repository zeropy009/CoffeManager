/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author zero
 */
public class CustomerTier {
    private int id;
    private String name;
    private double minPurchase;
    private double discountPercantage;

    public CustomerTier() {
    }

    public CustomerTier(int id, String name, double minPurchase, double discountPercantage) {
        this.id = id;
        this.name = name;
        this.minPurchase = minPurchase;
        this.discountPercantage = discountPercantage;
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

    public double getMinPurchase() {
        return minPurchase;
    }

    public void setMinPurchase(double minPurchase) {
        this.minPurchase = minPurchase;
    }

    public double getDiscountPercantage() {
        return discountPercantage;
    }

    public void setDiscountPercantage(double discountPercantage) {
        this.discountPercantage = discountPercantage;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
