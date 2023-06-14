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
public class barangmanager {
    public Connection conn = null;
    public Statement st = null;
    String url = "jdbc:mysql://localhost:3308/ecashier";
    String user = "root";
    String pass = "";
    public barangmanager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            st = conn.createStatement();
        }catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
    public List getbarangg(){
        ResultSet rs = null;
        List barangg = new ArrayList();
        try {
            rs = st.executeQuery("select * from barang");
            while (rs.next()) {
            barang s = new barang();
                s.setkode_barang(rs.getString("kode_barang"));
                s.setnama_barang(rs.getString("nama_barang"));
                s.setharga(rs.getString("harga"));
                barangg.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return barangg;
    }
    public int Insert(barang s){
        int result = 0;
        try{
            result = st.executeUpdate("insert into barang values('" +
            s.getkode_barang()+ "','" + s.getnama_barang() + "','" + s.getharga() +
            "')");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public int Delete(barang s){
        int result = 0;
        try{
            result = st.executeUpdate("delete from barang where kode_barang ='" +s.getkode_barang()+ "'");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public int Update(barang s){
        int result = 0;
        try{
            result = st.executeUpdate("update barang set nama_barang='" + s.getnama_barang() + "',harga='" + s.getharga() +"' where kode_barang='"+s.getkode_barang()+"'");
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
