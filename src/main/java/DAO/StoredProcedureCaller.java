/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zero
 */
public interface StoredProcedureCaller {
    List<Map<String, Object>> callStoredProcedure(String procedureName, List<Object> params) throws SQLException;
}
