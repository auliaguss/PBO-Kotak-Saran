package kotaksaran;

import java.sql.*;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    Connection koneksi=null;
    Statement stm;
    PreparedStatement pstm;
    public static Connection getKoneksi(String host, String port, String username, String password, String db){
        String konString="jdbc:mysql://"+host+":"+port+"/"+db;
        
        Connection koneksi=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            koneksi=DriverManager.getConnection(konString, username, password);
            System.out.println("Koneksi Berhasil");
        }
        catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tida Bisa Koneksi");
            koneksi=null;
        }
        return koneksi;
    }
}
