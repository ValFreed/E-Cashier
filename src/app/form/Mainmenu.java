/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package app.form;

import app.db.barang;
import app.db.barangmanager;
import app.db.faktur;
import app.db.fakturmanager;
import app.db.koneksi;

import app.db.transaksi;
import app.db.transaksimanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 *
 * @author ValFreed
 */
public class Mainmenu extends javax.swing.JFrame {
    
    public DefaultTableModel tabel; //Default tabel modelnya saya kasih nama 'tabel'
    public koneksi db = new koneksi();
    private strukpembayaran sp = new strukpembayaran();
    /**
     * Creates new form Mainmenu
     */
    public Mainmenu(){
        initComponents();
        comboboxtransaksi();
        autokodebarang();
        autokodefaktur();
        autoantrian();
        tabeltransaksi();
        tanggal();
        
    }
    
    public void comboboxtransaksi(){
        db.koneksiDatabase();
        try {
            String query = "SELECT * FROM barang";
            Statement st = db.getKoneksi().createStatement();
            ResultSet rs = st.executeQuery(query);
            pilihnamatransaksi.addItem("-Pilih");
            while (rs.next()) {                
                Object[] ob = new Object[3];
                ob[0] = rs.getString(1);
                
                pilihnamatransaksi.addItem((String) ob[0]);
            }
            st.close();
            rs.close();
            
        } catch (SQLException e) {
        }
    }
    
    public void tampil(){
        db.koneksiDatabase();
        tampilhargatransaksi.setEditable(false);
        try {
            String query = "select * from barang where kode_barang='"+pilihnamatransaksi.getSelectedItem()+"'";
            Statement stt = db.getKoneksi().createStatement();
            ResultSet rss = stt.executeQuery(query);
            while (rss.next()) {                
                Object[] ob = new Object[3];
                ob[0] = rss.getString(3);
                ob[1] = rss.getString(2);
                
                tampilhargatransaksi.setText((String) ob[0]);    
                tampilnamatransaksi.setText((String) ob[1]);
            }
            
            stt.close();
            rss.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void autokodebarang() {
        try {
            String sql = "SELECT * FROM barang order by kode_barang desc";
            Statement stt = db.getKoneksi().createStatement();
            ResultSet rss = stt.executeQuery(sql);
            if (rss.next()) {
                String NoJual = rss.getString("kode_barang").substring(1);
                String AN = "" + (Integer.parseInt(NoJual) + 1);
                String Nol = "";

                if(AN.length()==1)
                    {Nol = "000";
                }else if(AN.length()==2){
                    Nol = "00";
                }else if(AN.length()==3){
                    Nol = "0";
                }else if(AN.length()==4){
                    Nol = "";
                }
                tampilkode_barang.setText("B" + Nol + AN);//sesuaikan dengan variable namenya
            } else {
                tampilkode_barang.setText("B0001");//sesuaikan dengan variable namenya
            }
            rss.close();
            stt.close();
        } catch (Exception e) {
            e.printStackTrace();//penanganan masalah
        }
    }
    
    public void autokodefaktur() {
        try {
            String sql = "SELECT * FROM faktur order by kode_faktur desc";
            Statement stt = db.getKoneksi().createStatement();
            ResultSet rss = stt.executeQuery(sql);
            if (rss.next()) {
                String NoJual = rss.getString("kode_faktur").substring(1);
                String AN = "" + (Integer.parseInt(NoJual) + 1);
                String Nol = "";

                if(AN.length()==1)
                    {Nol = "000";
                }else if(AN.length()==2){
                    Nol = "00";
                }else if(AN.length()==3){
                    Nol = "0";
                }else if(AN.length()==4){
                    Nol = "";
                }
                tampilkodetransaksi.setText("F" + Nol + AN);//sesuaikan dengan variable namenya
            } else {
                tampilkodetransaksi.setText("F0001");//sesuaikan dengan variable namenya
            }
            rss.close();
            stt.close();
        } catch (Exception e) {
            e.printStackTrace();//penanganan masalah
        }
    }
    
    public void autoantrian() {
        try {
            String sql = "SELECT * FROM transaksi order by no_antrian desc";
            Statement stt = db.getKoneksi().createStatement();
            ResultSet rss = stt.executeQuery(sql);
            if (rss.next()) {
                String NoJual = rss.getString("no_antrian").substring(1);
                String AN = "" + (Integer.parseInt(NoJual) + 1);
                String Nol = "";

                if(AN.length()==1)
                    {Nol = "000";
                }else if(AN.length()==2){
                    Nol = "00";
                }else if(AN.length()==3){
                    Nol = "0";
                }else if(AN.length()==4){
                    Nol = "";
                }
                antrian.setText(Nol + AN);//sesuaikan dengan variable namenya
            } else {
                antrian.setText("0001");//sesuaikan dengan variable namenya
            }
            rss.close();
            stt.close();
        } catch (Exception e) {
            e.printStackTrace();//penanganan masalah
        }
    }
    
    public void tabeltransaksi(){
        db.koneksiDatabase();
        tabel = new DefaultTableModel();
        tabeltransaksi.setModel(tabel);
        tabel.addColumn("No.");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Kuantitas");
        int total = 0;
        int harga = 0;
        int no = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/ecashier","root","");
            Statement smt = conn.createStatement();
            ResultSet rs = smt.executeQuery("Select * from transaksi t, barang b where t.kode_barang = b.kode_barang and kode_faktur = '"+tampilkodetransaksi.getText()+"'");
            while(rs.next()){
                String jumlah = rs.getString("kuantitas");
                String hrg = rs.getString("harga");
                total = total + (Integer.parseInt(hrg) * Integer.parseInt(jumlah));
                no = no + 1;
                tabel.addRow(new Object[]{no ,rs.getString("nama_barang"),rs.getString("kuantitas")});
                tampiltotal.setText(String.valueOf(total));
            }
            tabeltransaksi.setModel(tabel);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void tabellaporan(){
        db.koneksiDatabase();
        tabel = new DefaultTableModel();
        tabellaporan.setModel(tabel);
        tabel.addColumn("No.");
        tabel.addColumn("Kode Faktur");
        tabel.addColumn("Total");
        int total = 0;
        int no = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/ecashier","root","");
            Statement smt = conn.createStatement();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
            ResultSet rs = smt.executeQuery("Select * from faktur where faktur.tanggal between '"+ft.format(tanggalawal.getDate())+"' and '"+ft.format(tanggalakhir.getDate())+"'");
            while(rs.next()){
                String jumlah = rs.getString("total");
                total = total + (Integer.parseInt(jumlah));
                no = no + 1;
                tabel.addRow(new Object[]{no ,rs.getString("kode_faktur"),rs.getString("total")});
                tampiltotalpenjualan.setText(String.valueOf(total));
            }
            tabellaporan.setModel(tabel);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void tabellist(){
        db.koneksiDatabase();
        tabel = new DefaultTableModel();
        tabellist.setModel(tabel);
        tabel.addColumn("No. Antrian");
        tabel.addColumn("Kode Faktur");
        tabel.addColumn("Total");
        tabel.addColumn("Layanan");
        int total = 0;
        int no = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/ecashier","root","");
            Statement smt = conn.createStatement();
            Date hariini = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
            ResultSet rs = smt.executeQuery("Select * from transaksi t, faktur f where f.tanggal = '"+ft.format(hariini)+"'");
            while(rs.next()){
                
                tabel.addRow(new Object[]{rs.getString("no_antrian") ,rs.getString("kode_faktur"),rs.getString("total"),rs.getString("layanan")});
                
            }
            tabellist.setModel(tabel);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void tanggal(){
        Date hariini = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        tampiltanggal.setText(ft.format(hariini));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        Managebarang = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tambahnamabarang = new javax.swing.JTextField();
        tambahharga = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tambahbarang = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        tampilkode_barang = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelmanagebarang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        editbarangbaru = new javax.swing.JTextField();
        tampilhargaedit = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        edithargabaru = new javax.swing.JTextField();
        editbarang = new javax.swing.JButton();
        tampilnamaedit = new javax.swing.JTextField();
        tampilkodeedit = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tampilhargahapus = new javax.swing.JTextField();
        hapusbarang = new javax.swing.JButton();
        tampilnamahapus = new javax.swing.JTextField();
        Transaksi = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        pilihnamatransaksi = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        tampilhargatransaksi = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        btttambahtransaksi = new javax.swing.JButton();
        hehe = new javax.swing.JLabel();
        tampilkodetransaksi = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        antrian = new javax.swing.JTextField();
        jumlah = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        tampilnamatransaksi = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabeltransaksi = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        tampiltotal = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        bayar = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        tampilkembalian = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        hitung = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        tampiltanggal = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        diskon = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        Laporan = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        lihatpenjualan = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabellaporan = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        tampiltotalpenjualan = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        tanggalawal = new com.toedter.calendar.JDateChooser();
        jLabel36 = new javax.swing.JLabel();
        tanggalakhir = new com.toedter.calendar.JDateChooser();
        List = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabellist = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        sudah = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        refreshlist = new javax.swing.JButton();
        tampilfakturlist = new javax.swing.JTextField();
        belum = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 204, 255));

        Managebarang.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Manage Barang");

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jLabel5.setText("Rp.");

        tambahbarang.setText("Tambah Barang");
        tambahbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahbarangActionPerformed(evt);
            }
        });

        jLabel6.setText("Nama Barang");

        jLabel7.setText("Harga");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tambah Barang");

        jLabel33.setText("Kode Barang");

        tampilkode_barang.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tambahbarang, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tampilkode_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(46, 46, 46)
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tambahharga, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                            .addComponent(tambahnamabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(5, 5, 5)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(tambahbarang))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tampilkode_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel33)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(tambahnamabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(tambahharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addContainerGap(69, Short.MAX_VALUE)))
        );

        tabelmanagebarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tabelmanagebarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelmanagebarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelmanagebarang);

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Edit Barang");

        jLabel8.setText("Nama Barang");

        jLabel9.setText("Harga");

        jLabel4.setText("Menjadi");

        tampilhargaedit.setEnabled(false);

        jLabel10.setText("Rp.");

        jLabel11.setText("Menjadi");

        editbarang.setText("Edit Barang");
        editbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editbarangActionPerformed(evt);
            }
        });

        tampilnamaedit.setEnabled(false);

        tampilkodeedit.setEnabled(false);

        jLabel37.setText("Kode Barang");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(editbarang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel37))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tampilkodeedit)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tampilhargaedit, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tampilnamaedit))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editbarangbaru, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edithargabaru)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tampilkodeedit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel4)
                    .addComponent(editbarangbaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tampilnamaedit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(tampilhargaedit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(edithargabaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(editbarang)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Hapus Barang");

        jLabel13.setText("Nama Barang");

        jLabel14.setText("Harga");

        jLabel15.setText("Rp.");

        tampilhargahapus.setEnabled(false);

        hapusbarang.setText("Hapus Barang");
        hapusbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusbarangActionPerformed(evt);
            }
        });

        tampilnamahapus.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hapusbarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tampilhargahapus, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))
                            .addComponent(tampilnamahapus))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(tampilnamahapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(tampilhargahapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hapusbarang)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ManagebarangLayout = new javax.swing.GroupLayout(Managebarang);
        Managebarang.setLayout(ManagebarangLayout);
        ManagebarangLayout.setHorizontalGroup(
            ManagebarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManagebarangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ManagebarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ManagebarangLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        ManagebarangLayout.setVerticalGroup(
            ManagebarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManagebarangLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Barang", Managebarang);

        Transaksi.setBackground(new java.awt.Color(153, 255, 204));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Transaksi");

        jLabel17.setText("Nama Barang");

        pilihnamatransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihnamatransaksiActionPerformed(evt);
            }
        });

        jLabel18.setText("Harga");

        jLabel19.setText("Rp.");

        tampilhargatransaksi.setEnabled(false);

        jLabel20.setText("Jumlah");

        btttambahtransaksi.setText("Tambahkan");
        btttambahtransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btttambahtransaksiActionPerformed(evt);
            }
        });

        hehe.setText("Kode Faktur");

        tampilkodetransaksi.setEnabled(false);

        jLabel34.setText("Antrian Ke:");

        antrian.setEnabled(false);

        jLabel35.setText("=");

        tampilnamatransaksi.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tampilhargatransaksi))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btttambahtransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(pilihnamatransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tampilnamatransaksi))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(hehe)
                        .addGap(18, 18, 18)
                        .addComponent(tampilkodetransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(antrian)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hehe)
                    .addComponent(tampilkodetransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(antrian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(pilihnamatransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(tampilnamatransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(tampilhargatransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(btttambahtransaksi)
                    .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tabeltransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane2.setViewportView(tabeltransaksi);

        jLabel21.setText("Total harga");

        tampiltotal.setBackground(new java.awt.Color(255, 255, 51));
        tampiltotal.setEnabled(false);

        jLabel22.setText("Rp.");

        jLabel23.setText("Bayar");

        jLabel24.setText("Rp.");

        jLabel25.setText("Kembalian");

        tampilkembalian.setEnabled(false);

        jLabel26.setText("Rp.");

        hitung.setText("Hitung");
        hitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hitungActionPerformed(evt);
            }
        });

        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        refresh.setText("Refresh Tabel");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        tampiltanggal.setEnabled(false);

        jLabel39.setText("Diskon");

        diskon.setText("0");

        jLabel40.setText("%");

        javax.swing.GroupLayout TransaksiLayout = new javax.swing.GroupLayout(Transaksi);
        Transaksi.setLayout(TransaksiLayout);
        TransaksiLayout.setHorizontalGroup(
            TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TransaksiLayout.createSequentialGroup()
                        .addGap(0, 243, Short.MAX_VALUE)
                        .addComponent(hitung))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TransaksiLayout.createSequentialGroup()
                        .addComponent(tampiltanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refresh))
                    .addGroup(TransaksiLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tampiltotal))
                    .addGroup(TransaksiLayout.createSequentialGroup()
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(91, 91, 91)))
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bayar)
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addComponent(diskon, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(TransaksiLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(clear))
                            .addComponent(tampilkembalian))))
                .addContainerGap())
        );
        TransaksiLayout.setVerticalGroup(
            TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refresh)
                    .addComponent(tampiltanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tampiltotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(diskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(hitung)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(tampilkembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clear)
                .addGap(26, 26, 26))
        );

        jTabbedPane1.addTab("Transaksi", Transaksi);

        Laporan.setBackground(new java.awt.Color(255, 204, 204));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setText("Laporan Penjualan");

        jLabel28.setText("Pilih Tanggal");

        lihatpenjualan.setText("Lihat Penjualan");
        lihatpenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lihatpenjualanActionPerformed(evt);
            }
        });

        tabellaporan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tabellaporan.setEnabled(false);
        jScrollPane3.setViewportView(tabellaporan);

        jLabel29.setText("Total Penjualan");

        tampiltotalpenjualan.setEnabled(false);

        jLabel30.setText("Rp.");

        tanggalawal.setDateFormatString("dd/MM/yyyy");

        jLabel36.setText("Sampai:");

        tanggalakhir.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tanggalakhir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tanggalawal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lihatpenjualan))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(30, 30, 30)
                        .addComponent(tampiltotalpenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29)
                            .addComponent(jLabel36))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalawal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalakhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lihatpenjualan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tampiltotalpenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout LaporanLayout = new javax.swing.GroupLayout(Laporan);
        Laporan.setLayout(LaporanLayout);
        LaporanLayout.setHorizontalGroup(
            LaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(LaporanLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        LaporanLayout.setVerticalGroup(
            LaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Laporan", Laporan);

        List.setBackground(new java.awt.Color(204, 255, 204));

        tabellist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabellist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabellistMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabellist);

        jLabel31.setText("Pelanggan yang Sudah Dilayani:");

        sudah.setText("Sudah");
        sudah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sudahActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setText("List Pelanggan");

        refreshlist.setText("Refresh Tabel");
        refreshlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshlistActionPerformed(evt);
            }
        });

        belum.setText("Belum");
        belum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                belumActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ListLayout = new javax.swing.GroupLayout(List);
        List.setLayout(ListLayout);
        ListLayout.setHorizontalGroup(
            ListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ListLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(refreshlist))
                    .addGroup(ListLayout.createSequentialGroup()
                        .addGroup(ListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)
                            .addGroup(ListLayout.createSequentialGroup()
                                .addComponent(tampilfakturlist, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(sudah, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(belum, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ListLayout.setVerticalGroup(
            ListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshlist)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tampilfakturlist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sudah)
                    .addComponent(belum))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("List", List);

        jToolBar1.setRollover(true);

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/E_Casher-removebg-preview-removebg-preview.png"))); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 153, 153));
        jButton1.setText("LOGOUT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(66, 66, 66)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(66, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(392, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(192, 192, 192))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(229, 229, 229)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(229, Short.MAX_VALUE)))
        );

        jToolBar1.add(jPanel1);

        jTabbedPane1.addTab("LogOut", jToolBar1);

        getContentPane().add(jTabbedPane1, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void belumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_belumActionPerformed
        // TODO add your handling code here:
        fakturmanager fakturmgr = new fakturmanager();
        faktur f = new faktur();
        f.setkode_faktur(tampilfakturlist.getText());
        f.setlayanan("Belum");
        if (fakturmgr.Updatelayanan(f) > 0) {
            JOptionPane.showMessageDialog(this, "Berhasil", "Informasi",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal","Informasi",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_belumActionPerformed

    private void refreshlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshlistActionPerformed
        // TODO add your handling code here:
        tabellist();
    }//GEN-LAST:event_refreshlistActionPerformed

    private void sudahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sudahActionPerformed
        // TODO add your handling code here:
        fakturmanager fakturmgr = new fakturmanager();
        faktur f = new faktur();
        f.setkode_faktur(tampilfakturlist.getText());
        f.setlayanan("Sudah");
        if (fakturmgr.Updatelayanan(f) > 0) {
            JOptionPane.showMessageDialog(this, "Berhasil", "Informasi",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal","Informasi",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_sudahActionPerformed

    private void tabellistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabellistMouseClicked
        // TODO add your handling code here:
        int baris = tabellist.rowAtPoint(evt.getPoint());
        String kode_faktur = tabellist.getValueAt(baris, 1).toString();
        tampilfakturlist.setText(kode_faktur);
    }//GEN-LAST:event_tabellistMouseClicked

    private void lihatpenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihatpenjualanActionPerformed
        // TODO add your handling code here:
        tabellaporan();
    }//GEN-LAST:event_lihatpenjualanActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        // TODO add your handling code here:
        tabeltransaksi();
    }//GEN-LAST:event_refreshActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new Mainmenu().setVisible(true);
    }//GEN-LAST:event_clearActionPerformed

    private void hitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hitungActionPerformed
        // TODO add your handling code here:
        String total = tampiltotal.getText();
        String bayarr = bayar.getText();
        int dis = Integer.parseInt(diskon.getText());
        fakturmanager fakturmgr = new fakturmanager();
        faktur f = new faktur();
        int hasil = Integer.parseInt(total)*dis/100;
        int hasildiskon = Integer.parseInt(total) - hasil;
        int kembalian = Integer.parseInt(bayarr) - hasildiskon;
        tampilkembalian.setText(String.valueOf(kembalian));
        
        f.setkode_faktur(tampilkodetransaksi.getText());
        f.settanggal(tampiltanggal.getText());
        f.settotal(total);
        f.setjumlah_bayar(bayarr);
        f.setkembalian(String.valueOf(kembalian));
        f.setlayanan("Belum");
        if (fakturmgr.Update(f) > 0) {
            JOptionPane.showMessageDialog(this, "Berhasil", "Informasi",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal","Informasi",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_hitungActionPerformed

    private void btttambahtransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btttambahtransaksiActionPerformed
        // TODO add your handling code here:
        fakturmanager fakturmgr = new fakturmanager();
        faktur f = new faktur();
        transaksimanager transaksimgr = new transaksimanager();
        transaksi s = new transaksi();
        f.setkode_faktur(tampilkodetransaksi.getText());
        if (fakturmgr.Insert(f) > 0) {
            s.setno_antrian(antrian.getText());
            s.setkuantitas(jumlah.getText());
            s.setkode_faktur(tampilkodetransaksi.getText());
            s.setkode_barang(pilihnamatransaksi.getSelectedItem().toString());
            if (transaksimgr.Insert(s) > 0) {
                JOptionPane.showMessageDialog(this, "Berhasil", "Informasi",JOptionPane.INFORMATION_MESSAGE);
                jumlah.setText("");
                tampilnamatransaksi.setText("");
                tampilhargatransaksi.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal","Informasi",JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            s.setno_antrian(antrian.getText());
            s.setkuantitas(jumlah.getText());
            s.setkode_faktur(tampilkodetransaksi.getText());
            s.setkode_barang(pilihnamatransaksi.getSelectedItem().toString());
            if (transaksimgr.Insert(s) > 0) {
                JOptionPane.showMessageDialog(this, "Berhasil", "Informasi",JOptionPane.INFORMATION_MESSAGE);
                jumlah.setText("");
                tampilnamatransaksi.setText("");
                tampilhargatransaksi.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal","Informasi",JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }//GEN-LAST:event_btttambahtransaksiActionPerformed

    private void pilihnamatransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihnamatransaksiActionPerformed
        // TODO add your handling code here:
        tampil();
    }//GEN-LAST:event_pilihnamatransaksiActionPerformed

    private void hapusbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusbarangActionPerformed
        // TODO add your handling code here:
        int opsi = JOptionPane.showConfirmDialog(null, "apa iya anda mau menghapus barang ini?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if(opsi == JOptionPane.YES_OPTION){
            barangmanager barangmgr = new barangmanager();
            barang s = new barang();
            s.setkode_barang(tampilkodeedit.getText());
            if (barangmgr.Delete(s) > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus", "Information",JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Data gagal dihapus", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "gagal", "Information",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_hapusbarangActionPerformed

    private void editbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbarangActionPerformed
        // TODO add your handling code here:
        barangmanager barangmgr = new barangmanager();
        barang s = new barang();
        if(editbarangbaru.getText().equals(null)){     //ERROR GAN
            s.setkode_barang(tampilkodeedit.getText());
            s.setnama_barang(tampilnamaedit.getText());
            s.setharga(edithargabaru.getText());

            if (barangmgr.Update(s) > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate", "Information",JOptionPane.INFORMATION_MESSAGE);
                editbarangbaru.setText("");
                edithargabaru.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Data gagal diupdate", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(edithargabaru.getText().equals(null)){    //ERROR GAN
            s.setkode_barang(tampilkodeedit.getText());
            s.setnama_barang(editbarangbaru.getText());
            s.setharga(tampilhargaedit.getText());

            if (barangmgr.Update(s) > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate", "Information",JOptionPane.INFORMATION_MESSAGE);
                editbarangbaru.setText("");
                edithargabaru.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Data gagal diupdate", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            s.setkode_barang(tampilkodeedit.getText());
            s.setnama_barang(editbarangbaru.getText());
            s.setharga(edithargabaru.getText());

            if (barangmgr.Update(s) > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate", "Information",JOptionPane.INFORMATION_MESSAGE);
                editbarangbaru.setText("");
                edithargabaru.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Data gagal diupdate", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_editbarangActionPerformed

    private void tabelmanagebarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelmanagebarangMouseClicked
        // TODO add your handling code here:
        tabel = new DefaultTableModel();
        tabelmanagebarang.setModel(tabel);
        tabel.addColumn("Kode Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Harga");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/ecashier","root","");
            Statement smt = conn.createStatement();
            ResultSet rs = smt.executeQuery("Select * from barang");
            while(rs.next()){
                tabel.addRow(new Object[]{rs.getString("kode_barang"),rs.getString("nama_barang"),rs.getString("harga")});
            }
            tabelmanagebarang.setModel(tabel);
        }catch(Exception e){
            e.printStackTrace();
        }

        int baris = tabelmanagebarang.rowAtPoint(evt.getPoint());
        String kode_barang = tabelmanagebarang.getValueAt(baris, 0).toString();
        tampilkodeedit.setText(kode_barang);
        String nama_barang = tabelmanagebarang.getValueAt(baris, 1).toString();
        tampilnamahapus.setText(nama_barang);
        tampilnamaedit.setText(nama_barang);
        String harga = tabelmanagebarang.getValueAt(baris,2).toString();
        tampilhargahapus.setText(harga);
        tampilhargaedit.setText(harga);
    }//GEN-LAST:event_tabelmanagebarangMouseClicked

    private void tambahbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahbarangActionPerformed
        // TODO add your handling code here:

        barangmanager barangmgr = new barangmanager();
        barang s = new barang();
        if(tambahnamabarang.getText().equals(null) && tambahharga.getText().equals(null)){
            JOptionPane.showMessageDialog(this, "YANG BENER?!", "Informasi",JOptionPane.INFORMATION_MESSAGE);
        }else{
            s.setkode_barang(tampilkode_barang.getText());
            s.setnama_barang(tambahnamabarang.getText());
            s.setharga(tambahharga.getText());
            if (barangmgr.Insert(s) > 0) {
                JOptionPane.showMessageDialog(this, "Barang berhasil disimpan", "Informasi",JOptionPane.INFORMATION_MESSAGE);
                tampilkode_barang.setText("");
                tambahnamabarang.setText("");
                tambahharga.setText("");
                this.dispose();
                new Mainmenu().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Barang gagal disimpan","Informasi",JOptionPane.INFORMATION_MESSAGE);
                tampilkode_barang.setText("");
                tambahnamabarang.setText("");
                tambahharga.setText("");
            }
        }
    }//GEN-LAST:event_tambahbarangActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int opsi = JOptionPane.showConfirmDialog(null, "yakin mau log out?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if(opsi == JOptionPane.YES_OPTION){
            this.dispose();
            new Awal().setVisible(true);
        }else{
            this.dispose();
            new Mainmenu().setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mainmenu().setVisible(true);
                new koneksi().koneksiDatabase();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Laporan;
    private javax.swing.JPanel List;
    private javax.swing.JPanel Managebarang;
    private javax.swing.JPanel Transaksi;
    private javax.swing.JTextField antrian;
    private javax.swing.JTextField bayar;
    private javax.swing.JButton belum;
    private javax.swing.JButton btttambahtransaksi;
    private javax.swing.JButton clear;
    private javax.swing.JTextField diskon;
    private javax.swing.JButton editbarang;
    private javax.swing.JTextField editbarangbaru;
    private javax.swing.JTextField edithargabaru;
    private javax.swing.JButton hapusbarang;
    private javax.swing.JLabel hehe;
    private javax.swing.JButton hitung;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jumlah;
    private javax.swing.JButton lihatpenjualan;
    private javax.swing.JComboBox<String> pilihnamatransaksi;
    private javax.swing.JButton refresh;
    private javax.swing.JButton refreshlist;
    private javax.swing.JButton sudah;
    private javax.swing.JTable tabellaporan;
    private javax.swing.JTable tabellist;
    private javax.swing.JTable tabelmanagebarang;
    private javax.swing.JTable tabeltransaksi;
    private javax.swing.JButton tambahbarang;
    private javax.swing.JTextField tambahharga;
    private javax.swing.JTextField tambahnamabarang;
    private javax.swing.JTextField tampilfakturlist;
    private javax.swing.JTextField tampilhargaedit;
    private javax.swing.JTextField tampilhargahapus;
    private javax.swing.JTextField tampilhargatransaksi;
    private javax.swing.JTextField tampilkembalian;
    private javax.swing.JTextField tampilkode_barang;
    private javax.swing.JTextField tampilkodeedit;
    private javax.swing.JTextField tampilkodetransaksi;
    private javax.swing.JTextField tampilnamaedit;
    private javax.swing.JTextField tampilnamahapus;
    private javax.swing.JTextField tampilnamatransaksi;
    private javax.swing.JTextField tampiltanggal;
    private javax.swing.JTextField tampiltotal;
    private javax.swing.JTextField tampiltotalpenjualan;
    private com.toedter.calendar.JDateChooser tanggalakhir;
    private com.toedter.calendar.JDateChooser tanggalawal;
    // End of variables declaration//GEN-END:variables
}
