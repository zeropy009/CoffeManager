/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Model.Table;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author zero
 */
public interface TableDAO {
    Table getTableByID(int id);
    ArrayList<Table> getAllTable();
    boolean addTable(String tableName);
    boolean updateTable(Table table);
    boolean deleteTable(int id);
    default Table getTableInfor(ResultSet rs) throws SQLException {
        return new Table(rs.getInt("ID"),
                        rs.getString("TABLE_NAME"),
                        rs.getBoolean("STATUS"));
    }
}
