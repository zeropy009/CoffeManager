/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Model.Warehouse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public interface WarehouseDAO {
    Warehouse getWarehouseByID(int id);
    ArrayList<Warehouse> getAllWarehouses();
    boolean addWarehouse(Warehouse warehouse);
    boolean updateWarehouse(Warehouse warehouse);
    boolean deleteWarehouse(int id);
    default Warehouse getWarehouseInfor(ResultSet rs) throws SQLException {
        return new Warehouse(rs.getInt("ID"),
                        rs.getTimestamp("INPUT_DATE"),
                        rs.getString("USER_NAME"),
                        rs.getInt("TOTAL_AMOUNT"));
    }
}
