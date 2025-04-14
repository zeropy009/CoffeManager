/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author zero
 */
public class AppConfig {
    public static final DBConfig DB;
    public static final String DEFAULT_PASSWORD;

    //Tự động khởi chạy 1 lần duy nhất khi AppConfig được gọi lần đầu tiên
    static {
        Properties properties = loadProperties();
        DB = loadDBConfig(properties);
        DEFAULT_PASSWORD = loadDefaultPassword(properties);
    }
    
    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Không tìm thấy file config.properties trong classpath!");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    // Load DB config from properties
    private static DBConfig loadDBConfig(Properties properties) {
        return new DBConfig(
            properties.getProperty("db.driverClassName", ""),
            properties.getProperty("db.url", ""),
            properties.getProperty("db.port", ""),
            properties.getProperty("db.name", ""),
            properties.getProperty("db.encrypt", ""),
            properties.getProperty("db.trustservercertificate", ""),
            properties.getProperty("db.username", ""),
            properties.getProperty("db.password", "")
        );
    }

    // Load default password
    private static String loadDefaultPassword(Properties properties) {
        return properties.getProperty("default.password", "123456");
    }
    
    //chặn việc tạo Object khác
    private AppConfig() {}
}
