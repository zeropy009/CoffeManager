/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Model.BeveragesCategory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public interface BeveragesCategoryDAO {
    BeveragesCategory getBeveragesCategoryByID(int id);
    ArrayList<BeveragesCategory> getAllBeveragesCategory();
    boolean addBeveragesCategory(BeveragesCategory beveragesCategory);
    boolean updateBeveragesCategory(BeveragesCategory beveragesCategory);
    boolean deleteBeveragesCategory(int id);
    default BeveragesCategory getBeveragesCategoryInfor(ResultSet rs) throws SQLException {
        return new BeveragesCategory(rs.getInt("ID"), rs.getString("NAME"));
    }
}
