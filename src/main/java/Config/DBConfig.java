/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

/**
 *
 * @author zero
 */
public class DBConfig {
    public final String url;
    public final String port;
    public final String name;
    public final String encrypt;
    public final String trustServerCertificate;
    public final String username;
    public final String password;

    public DBConfig(String url, String port, String name, String encrypt, String trustServerCertificate, String username, String password) {
        this.url = url;
        this.port = port;
        this.name = name;
        this.encrypt = encrypt;
        this.trustServerCertificate = trustServerCertificate;
        this.username = username;
        this.password = password;
    }
}