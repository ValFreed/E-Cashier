/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ValFreed
 */
public class loginmanager {
    public Connection conn = null;
    public Statement st = null;
    String url = "jdbc:mysql://localhost:3308/ecashier";
    String user = "root";
    String pass = "";
    public void loginmanager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            st = conn.createStatement();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int tambah(login s){
        int result = 0;
        try{
            result = st.executeUpdate("insert into login (username,password) values('"+s.getusername()+"','"+s.getpassword()+"')");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
