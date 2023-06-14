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
public class transaksimanager {
    Connection conn = null;
    Statement st = null;
    String url = "jdbc:mysql://localhost:3308/ecashier";
    String user = "root";
    String pass = "";
    public transaksimanager(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            st = conn.createStatement();
        }catch (Exception e) {
            e.printStackTrace();
        }
    } 
    public List gettransaksii(){
        ResultSet rs = null;
        List transaksii = new ArrayList();
        try {
            rs = st.executeQuery("select * from transaksi");
            while (rs.next()) {
            transaksi s = new transaksi();
                s.setno_antrian(rs.getString("no_antrian"));
                s.setkuantitas(rs.getString("kuantitas"));
                s.setkode_faktur(rs.getString("kode_faktur"));
                s.setkode_barang(rs.getString("kode_barang"));
                transaksii.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transaksii;
    }
    public int Insert(transaksi s){
        int result = 0;
        try{
            result = st.executeUpdate("insert into transaksi values('" +s.getno_antrian()+ "','" + s.getkuantitas() + "','" + s.getkode_faktur() + "','" + s.getkode_barang() + "')");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public int Delete(transaksi s){
        int result = 0;
        try{
            result = st.executeUpdate("delete from transaksi where kode_faktur ='" +s.getkode_faktur()+ "'");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public int Update(transaksi s){
        int result = 0;
        try{
            result = st.executeUpdate("update into transaksi set no_antrian='" +
            s.getno_antrian()+ "', kuantitas='" + s.getkuantitas() + "',kode_faktur='" + s.getkode_faktur() +"',kode_barang='" + s.getkode_barang() +"'");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public void closeConnection() {
        try {
            conn.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
