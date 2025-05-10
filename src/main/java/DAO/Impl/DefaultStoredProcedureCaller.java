/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Impl;

import Common.DBConnection;
import DAO.StoredProcedureCaller;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zero
 */
public class DefaultStoredProcedureCaller implements StoredProcedureCaller {
    
    @Override
    public List<Map<String, Object>> callStoredProcedure(String procedureName, List<Object> params) {

        try (Connection connection = DBConnection.getConnection(); 
             CallableStatement stmt = connection.prepareCall(buildProcedureCall(procedureName, params))) {

            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    stmt.setObject(i + 1, params.get(i));
                }
            }

            boolean hasResultSet = stmt.execute();
            if (hasResultSet) {
                try (ResultSet rs = stmt.getResultSet()) {
                    return convertToListOfMaps(rs);
                }
            } else {
                return Collections.emptyList();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private String buildProcedureCall(String procedureName, List<Object> params) {
        StringBuilder call = new StringBuilder("{call ");
        call.append(procedureName);

        if (params != null && !params.isEmpty()) {
            String placeholders = String.join(",", Collections.nCopies(params.size(), "?"));
            call.append("(").append(placeholders).append(")");
        }

        call.append("}");
        return call.toString();
    }

    private List<Map<String, Object>> convertToListOfMaps(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        while (rs.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String column = meta.getColumnLabel(i);
                row.put(column, rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }
}
