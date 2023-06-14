/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.db;

/**
 *
 * @author ValFreed
 */
public class transaksi {
    private String no_antrian;
    private String kuantitas;
    private String kode_faktur;
    private String kode_barang;
    public transaksi(){
        
    }
    public String getno_antrian(){
        return no_antrian;
    }
    public void setno_antrian(String no_antrian){
        this.no_antrian = no_antrian;
    }
    public String getkuantitas(){
        return kuantitas;
    }
    public void setkuantitas(String kuantitas){
        this.kuantitas = kuantitas;
    }
    public String getkode_faktur(){
        return kode_faktur;
    }
    public void setkode_faktur(String kode_faktur){
        this.kode_faktur = kode_faktur;
    }
    public String getkode_barang(){
        return kode_barang;
    }
    public void setkode_barang(String kode_barang){
        this.kode_barang = kode_barang;
    }
}
