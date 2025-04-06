/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Beverages;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public interface BeveregesDAO {
    Beverages getBeveragesByID(int id);
    ArrayList<Beverages> getAllBeverages();
    boolean addBevereges(Beverages beverages);
    boolean updateBevereges(Beverages bevereges);
    boolean deleteBevereges(int id);
    default Beverages getBeveragesInfor(ResultSet rs) throws SQLException {
        return new Beverages(rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("PRICE"),
                        rs.getInt("BEVERAGES_CATEGORY_ID"));
    }
}
