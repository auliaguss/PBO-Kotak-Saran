package kotaksaran;

import java.sql.*;
import javax.swing.JOptionPane;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AmbilData extends DatabaseConnection{
    public AmbilData(){
        ambilKoneksi("localhost", "3306", "root", "", "kotak_saran");
    }
    public AmbilData(String l, String p, String r, String pass, String db){
        ambilKoneksi(l, p, r, pass, db);
    }
    public void ambilKoneksi(String l, String p, String r, String pass, String db){
        koneksi = DatabaseConnection.getKoneksi(l, p, r, pass, db);
    }
    public int daftar(String iNis,String iNama, String iKelas,String iEmail,String iSandi){
        try{
            Statement stm = koneksi.createStatement();
            String query = "INSERT INTO data_login(nis, nama, kelas, sandi, email) "+"VALUES('"+iNis+"', '"+iNama+"', '"+iKelas+"', '"+iSandi+"', '"+iEmail+"')";
            System.out.println(query);
            int berhasil = stm.executeUpdate(query);
            if(berhasil == 1){
                display(berhasil, "Anda Berhasil Daftar");
                return 1;
            }
            else{
                display(berhasil, "Terjadi Kesalahan");
                return 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    public int login(String niss, String sandii){
        try{
            pstm = koneksi.prepareStatement("select * from data_login where nis='" + niss + "' AND sandi = '"+sandii+"'");
            ResultSet rs = pstm.executeQuery();
            int i = 0;
            if (rs.next()) {
                i++;
            }
            if (i < 1) {
                display(0, "Username Atau Password Salah!");
                return 0;
            }
            else {
                display(1, "Anda Berhasil Login!");
                return 1;
            }
        }catch(SQLException e){
            e.printStackTrace();
            display(1, "Terjadi Kesalahan");
            return 0;
        }
    }
    
    public int tambahSaran(String nis, String judul, String isi){
        Date tgl = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Statement stm = koneksi.createStatement();
            String query = "INSERT INTO saran(nis, judul, isi, tanggal) "+"VALUES('"+nis+"', '"+judul+"', '"+isi+"', '"+df.format(tgl)+"')";
            System.out.println(query);
            int berhasil = stm.executeUpdate(query);
            if(berhasil == 1){
                display(berhasil, "Saran Anda Telah Direkam!");
                return 1;
            }
            else{
                display(berhasil, "Terjadi Kesalahan");
                return 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    public void klikSaran(String id){
        String detail="";
        int berhasil = -3;
        try{
            Statement st = koneksi.createStatement();
            String query = "SELECT * FROM saran WHERE id='"+id+"'";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                detail = rs.getString("isi");
                berhasil = 1;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        if(berhasil>-3){
            detailIsi(detail);
        }
    }
    void detailIsi(String isi){
        JOptionPane.showMessageDialog(null, isi, "Detail Isi Saran", 1);
    }
    public int hapusSaran(String idPencet){
        try{
            Statement st = koneksi.createStatement();
            String query = "DELETE FROM saran WHERE id="+idPencet;
            int berhasil = st.executeUpdate(query);
            if(berhasil==1){
                display(1, "Data Berhasil Dihapus!");
                return 1;
            }
            else{
                display(0, "Data Gagal Dihapus");
                return 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    public int editDataLogin(String nis, String nama, String kelas, String sandi, String email){
        try{
            stm = koneksi.createStatement();
            String query = "UPDATE data_login SET nama = '"+nama+"', kelas = '"+kelas+"', sandi = '"+sandi+"', email = '"+email+"' WHERE nis = '"+nis+"'";
            System.out.println(query);
            int berhasil = stm.executeUpdate(query);
            if(berhasil <1){
                display(0, "Data Gagal Di Update!");
                return 0;
            }
            else{
                display(1, "Data Berhasil Dipervarui!");
                return 1;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    
    public void display(int i, String teks){
        JOptionPane.showMessageDialog(null, teks, "Perhatian", i);;
    }
}
