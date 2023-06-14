/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ValFreed
 */
public class koneksi {
    private Connection koneksi;

    public Connection getKoneksi() {
        return koneksi;
    }

    public void koneksiDatabase() { //<-- untuk koneksi ke database
        // Cek Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //<-- nama driver untuk koneksi ke MySQL

            // Cek Database
            try {
                String url = "jdbc:mysql://localhost:3308/ecashier";
                String user = "root";
                String pass = "";
                koneksi = DriverManager.getConnection(url, user, pass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
}
