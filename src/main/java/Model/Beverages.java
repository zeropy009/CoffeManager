/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author daomi
 */
public class Beverages extends BaseModel{
    private int id;
    private String name;
    private int price;
    private int baveragesCategoryId;
    private String baveragesCategoryName;
    

    public Beverages() {
    }

    public Beverages(int id, String name, int price, int baveragesCategoryId, String baveragesCategoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.baveragesCategoryId = baveragesCategoryId;
        this.baveragesCategoryName = baveragesCategoryName;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBaveragesCategoryId() {
        return baveragesCategoryId;
    }

    public void setBaveragesCategoryId(int baveragesCategoryId) {
        this.baveragesCategoryId = baveragesCategoryId;
    }

    public String getBaveragesCategoryName() {
        return baveragesCategoryName;
    }

    public void setBaveragesCategoryName(String baveragesCategoryName) {
        this.baveragesCategoryName = baveragesCategoryName;
    }
    
    @Override
    public String toString() {
        return name;
    }
}

	