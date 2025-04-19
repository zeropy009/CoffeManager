/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Common.Untils;
import java.sql.Timestamp;

/**
 *
 * @author daomi
 */
public class Invoice extends BaseModel {
    private int id;
    private Timestamp date;
    private int totalAmount;
    private String userName;
    private int customerId;
    private double discountPercentage;
    private int tableId;
    private String customerName;
    private String tableName;
    
    public Invoice() {
    }
    
    public Invoice(int id, Timestamp date, int totalAmount, String userName, int customerId, double discountPercentage, int tableId, String customerName, String tableName) {
        this.id = id;
        this.date = date;
        this.totalAmount = totalAmount;
        this.userName = userName;
        this.customerId = customerId;
        this.discountPercentage = discountPercentage;
        this.tableId = tableId;
        this.customerName = customerName;
        this.tableName = tableName;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    @Override
    public String toString() {
        return date.toLocalDateTime().format(Untils.fd);
    }
}
