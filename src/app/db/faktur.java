/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.db;

/**
 *
 * @author ValFreed
 */
public class faktur {
    private String kode_faktur;
    private String tanggal;
    private String total;
    private String jumlah_bayar;
    private String kembalian;
    private String layanan;
    public faktur(){
        
    }
    public String getkode_faktur(){
        return kode_faktur;
    }
    public void setkode_faktur(String kode_faktur){
        this.kode_faktur = kode_faktur;
    }
    public String gettanggal(){
        return tanggal;
    }
    public void settanggal(String tanggal){
        this.tanggal = tanggal;
    }
    public String gettotal(){
        return total;
    }
    public void settotal(String total){
        this.total = total;
    }
    public String getjumlah_bayar(){
        return jumlah_bayar;
    }
    public void setjumlah_bayar(String jumlah_bayar){
        this.jumlah_bayar = jumlah_bayar;
    }
    public String getkembalian(){
        return kembalian;
    }
    public void setkembalian(String kembalian){
        this.kembalian = kembalian;
    }
    public String getlayanan(){
        return layanan;
    }
    public void setlayanan(String layanan){
        this.layanan = layanan;
    }
}
