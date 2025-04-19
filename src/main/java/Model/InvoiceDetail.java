/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author daomi
 */
public class InvoiceDetail extends BaseModel{
    private int id;
    private int invoiceId;
    private int beveragesId;
    private int quantity;
    private int price;
    private int amount;
    private String beveragesName;
    
    public InvoiceDetail() {
    }

    public InvoiceDetail(int id, int invoiceId, int beveragesId, int quantity, int price, int amount, String beveragesName) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.beveragesId = beveragesId;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.beveragesName = beveragesName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getBeveragesId() {
        return beveragesId;
    }

    public void setBeveragesId(int beveragesId) {
        this.beveragesId = beveragesId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBeveragesName() {
        return beveragesName;
    }
    
    @Override
    public String toString() {
        return beveragesName;
    }
}
    
