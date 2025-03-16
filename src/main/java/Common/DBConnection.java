/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Common;

import Config.AppConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author zero
 */
public class DBConnection {
    private static HikariDataSource dataSource;

    static {
        try {
            String jdbcUrl = String.format("%s:%s;databaseName=%s;trustServerCertificate=%s;",
                    AppConfig.DB.url, AppConfig.DB.port, AppConfig.DB.name, AppConfig.DB.trustServerCertificate);
            
            HikariConfig config = new HikariConfig();

            config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(AppConfig.DB.username);
            config.setPassword(AppConfig.DB.password);

            config.setMaximumPoolSize(10); // Giới hạn tối đa 10 connection
            config.setMinimumIdle(2); // Số lượng connection giữ sẵn
            config.setIdleTimeout(30000); // Đóng connection nếu không dùng sau 30s
            config.setMaxLifetime(600000); // Mỗi connection tối đa tồn tại 10 phút

            dataSource = new HikariDataSource(config);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    private DBConnection(){};
}
