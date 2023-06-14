/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.db;

/**
 *
 * @author ValFreed
 */
public class barang {
    private String kode_barang;
    private String nama_barang;
    private String harga;
    public barang(){
        
    }
    public String getkode_barang(){
        return kode_barang;
    }
    public void setkode_barang(String kode_barang){
        this.kode_barang = kode_barang;
    }
    public String getnama_barang(){
        return nama_barang;
    }
    public void setnama_barang(String nama_barang){
        this.nama_barang = nama_barang;
    }
    public String getharga(){
        return harga;
    }
    public void setharga(String harga){
        this.harga = harga;
    }
}
