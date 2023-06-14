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
public class fakturmanager {
    Connection conn = null;
    Statement st = null;
    String url = "jdbc:mysql://localhost:3308/ecashier";
    String user = "root";
    String pass = "";
    public fakturmanager(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            st = conn.createStatement();
        }catch (Exception e) {
            e.printStackTrace();
        }
    } 
    public List getfakturr(){
        ResultSet rs = null;
        List fakturr = new ArrayList();
        try {
            rs = st.executeQuery("select * from faktur");
            while (rs.next()) {
            faktur s = new faktur();
                s.setkode_faktur(rs.getString("kode_faktur"));
                s.settanggal(rs.getString("tanggal"));
                s.settotal(rs.getString("total"));
                s.setjumlah_bayar(rs.getString("jumlah_bayar"));
                s.setkembalian(rs.getString("kembalian"));
                fakturr.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fakturr;
    }
    public int Insert(faktur s){
        int result = 0;
        try{
            result = st.executeUpdate("insert into faktur (kode_faktur) values('" +
            s.getkode_faktur()+ "')");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public int Delete(faktur s){
        int result = 0;
        try{
            result = st.executeUpdate("delete from faktur where kode_faktur ='" +s.getkode_faktur()+ "'");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public int Update(faktur f){
        int result = 0;
        try{
            result = st.executeUpdate("update faktur set tanggal='" + f.gettanggal()+"', total='" + f.gettotal() +"',jumlah_bayar='" + f.getjumlah_bayar() +"',kembalian='" + f.getkembalian() +"', layanan='" + f.getlayanan() +"' where kode_faktur='"+f.getkode_faktur()+"'");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public int Updatelayanan(faktur f){
        int result = 0;
        try{
            result = st.executeUpdate("update faktur set layanan='" + f.getlayanan() +"' where kode_faktur='"+f.getkode_faktur()+"'");
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
