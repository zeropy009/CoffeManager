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
    private String ID;
    private String NAME;
    private String PHONE;
    private String EMAIL;
    private String TIER_ID;
    private String CREATED_BY;
    private String CREATED_AT;
    private String LAST_UPDATED_BY;
    private String LAST_UPDATED_AT;
    private String DELETED;

    public Customer() {
    }

    public Customer(String ID, String NAME, String PHONE, String EMAIL, String TIER_ID, String CREATED_BY, String CREATED_AT, String LAST_UPDATED_BY, String LAST_UPDATED_AT, String DELETED) {
        this.ID = ID;
        this.NAME = NAME;
        this.PHONE = PHONE;
        this.EMAIL = EMAIL;
        this.TIER_ID = TIER_ID;
        this.CREATED_BY = CREATED_BY;
        this.CREATED_AT = CREATED_AT;
        this.LAST_UPDATED_BY = LAST_UPDATED_BY;
        this.LAST_UPDATED_AT = LAST_UPDATED_AT;
        this.DELETED = DELETED;
    }

    public String getID() {
        return ID;
    }

    public void setTIER_ID(String TIER_ID) {
        this.TIER_ID = TIER_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getCREATED_BY() {
        return CREATED_BY;
    }

    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY;
    }

    public String getCREATED_AT() {
        return CREATED_AT;
    }

    public void setCREATED_AT(String CREATED_AT) {
        this.CREATED_AT = CREATED_AT;
    }

    public String getLAST_UPDATED_BY() {
        return LAST_UPDATED_BY;
    }

    public void setLAST_UPDATED_BY(String LAST_UPDATED_BY) {
        this.LAST_UPDATED_BY = LAST_UPDATED_BY;
    }
    
    public String getLAST_UPDATED_AT() {
        return LAST_UPDATED_AT;
    }

    public void setLAST_UPDATED_AT(String LAST_UPDATED_AT) {
        this.LAST_UPDATED_AT = LAST_UPDATED_AT;
    }
    
    public String getDELETED() {
        return DELETED;
    }

    public void setDELETED(String DELETED) {
        this.DELETED = DELETED;
    }
    @Override
    public String toString() {
        return ID+"_"+"_"+NAME;
    }
}
