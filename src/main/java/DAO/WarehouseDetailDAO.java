/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Model.WarehouseDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zero
 */
public interface WarehouseDetailDAO {
    WarehouseDetail getWarehouseDetailByID(int id);
    ArrayList<WarehouseDetail> getAllWarehouseDetails();
    ArrayList<WarehouseDetail> getAllWarehouseDetailsByWarehouseId(int warehouseId);
    boolean addWarehouseDetail(WarehouseDetail warehouseDetail);
    int addWarehouseDetails(List<WarehouseDetail> warehouseDetailList);
    boolean updateWarehouseDetail(WarehouseDetail warehouseDetail);
    boolean deleteWarehouseDetail(int id);
    default WarehouseDetail getWarehouseDetailInfor(ResultSet rs) throws SQLException {
        return new WarehouseDetail(rs.getInt("ID"),
                        rs.getInt("WAREHOUSE_ID"),
                        rs.getString("PRODUCT_NAME"),
                        rs.getInt("QUANTITY"),
                        rs.getInt("PRICE"),
                        rs.getInt("AMOUNT"));
    }
}
