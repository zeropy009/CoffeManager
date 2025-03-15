/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author zero
 */
public class BaseModel {
    protected String createdUser;
    protected Date createdTime;
    protected String lastUpdatedUser;
    protected Date lastUpdatedTime;
    protected boolean delete;

    public BaseModel() {
    }
    
    public BaseModel(String createdUser, Date createdTime, String lastUpdatedUser, Date lastUpdatedTime, boolean delete) {
        this.createdUser = createdUser;
        this.createdTime = createdTime;
        this.lastUpdatedUser = lastUpdatedUser;
        this.lastUpdatedTime = lastUpdatedTime;
        this.delete = delete;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
    
    public boolean getDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
