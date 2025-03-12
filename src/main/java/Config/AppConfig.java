/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author zero
 */
public class AppConfig {
    public static final DBConfig DB;

    static {
        DB = loadDBConfig();
    }

    private static DBConfig loadDBConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            return new DBConfig(
                properties.getProperty("db.url"),
                properties.getProperty("db.port"),
                properties.getProperty("db.name"),
                properties.getProperty("db.encrypt"),
                properties.getProperty("db.trustservercertificate"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));
        } catch (IOException e) {
            e.printStackTrace();
            return new DBConfig("", "", "", "", "", "", "");
        }
    }
    
    private AppConfig() {}
}
