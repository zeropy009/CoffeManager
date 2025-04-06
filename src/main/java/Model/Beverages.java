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
    

    public Beverages() {
    }

    public Beverages(int id, String name, int price, int baveragesCategooryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.baveragesCategoryId = baveragesCategooryId;
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

    public int getBaveragesCategooryId() {
        return baveragesCategoryId;
    }

    public void setBaveragesCategooryId(int baveragesCategooryId) {
        this.baveragesCategoryId = baveragesCategooryId;
    }
    
    
}

	