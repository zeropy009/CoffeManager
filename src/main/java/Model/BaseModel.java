/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author zero
 */
public class BaseModel {
    protected String lastUpdatedUser;
    protected String deletedUser;
    protected boolean delete;

    public BaseModel() {
    }

    public BaseModel(String lastUpdatedUser, String deletedUser, boolean delete) {
        this.lastUpdatedUser = lastUpdatedUser;
        this.deletedUser = deletedUser;
        this.delete = delete;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getDeletedUser() {
        return deletedUser;
    }

    public void setDeletedUser(String deletedUser) {
        this.deletedUser = deletedUser;
    }

    public boolean isIsDeleted() {
        return delete;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.delete = isDeleted;
    }
}
