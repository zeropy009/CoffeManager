/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author zero
 */
public class ConnectDB {

    public static Connection con;

    public static Connection getCon() {
        return con;
    }

    public static void setCon(Connection con) {
        ConnectDB.con = con;
    }

    public static Connection getConnection() throws Exception {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        Class.forName(driver);
        String databaseName = "CAFFE_MANAGER";
        //String url = String.format("jdbc:sqlserver://zerohost.ddns.net:8211;databaseName=%s;encrypt=true;trustServerCertificate=true", databaseName);
        String url = String.format("jdbc:sqlserver://localhost:1433;databaseName=%s;encrypt=true;trustServerCertificate=true", databaseName);
        con = DriverManager.getConnection(url, "sa", "Admin123");
        return con;
    }

   

    public static void close() {
        try {
            con.close();
        } catch (Exception e) {
        }
    }

    public static ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            Statement stm = con.createStatement();
            rs = stm.executeQuery(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi nè", "Thông báo", 2);

        }
        return rs;
    }

    public static int executeUpdate(String sql) {
        int i = -1;
        try {

            Statement stm = con.createStatement();
            i = stm.executeUpdate(sql);
        } catch (Exception e) {
        }
        return i;
    }
}
