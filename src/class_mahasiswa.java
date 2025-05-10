import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class class_mahasiswa {
    Connection con = null;
    
    public List get_data(String str){
        List<String> assetList = new ArrayList<String>();
        try{
            con = new class_koneksi().getConSQL();
            PreparedStatement pr = con.prepareStatement("SELECT * FROM tb_mahasiswa WHERE nim = ?");
            pr.setString(1,str);
            
            ResultSet rs = pr.executeQuery();
            rs.next();
            
            String nim = rs.getString("nim").trim();
            String nama = rs.getString("nama").trim();
            String jenis_kelamin = rs.getString("jenis_kelamin").trim();
            String fakultas = rs.getString("fakultas").trim();
            String jurusan = rs.getString("jurusan").trim();
            String semester = rs.getString("semester").trim();
            
            String[] dataField = {nim, nama, jenis_kelamin, fakultas, jurusan, semester};
            Collections.addAll(assetList, dataField);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return assetList;
    }
    
    public List tampil_data(){
        List<String> assetList = new ArrayList<String>();
        try{
            con = new class_koneksi().getConSQL();
            PreparedStatement pr = con.prepareStatement("SELECT * FROM tb_mahasiswa ORDER BY nama ASC");
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                String nim = rs.getString("nim").trim();
                String nama = rs.getString("nama").trim();
                String jenis_kelamin = rs.getString("jenis_kelamin").trim();
                String fakultas = rs.getString("fakultas").trim();
                String jurusan = rs.getString("jurusan").trim();
                String semester = rs.getString("semester").trim();
                
                String[] dataField = {nim, nama, jenis_kelamin, fakultas, jurusan, semester};
                Collections.addAll(assetList, dataField);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return assetList;
    }
    
    public List cari_data(String str){
        List<String> assetList = new ArrayList<String>();
        try{
            con = new class_koneksi().getConSQL();
            PreparedStatement pr = con.prepareStatement("SELECT * FROM tb_mahasiswa WHERE nama LIKE ? ORDER BY nama ASC");
            pr.setString(1,str + "%");
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                String nim = rs.getString("nim").trim();
                String nama = rs.getString("nama").trim();
                String jenis_kelamin = rs.getString("jenis_kelamin").trim();
                String fakultas = rs.getString("fakultas").trim();
                String jurusan = rs.getString("jurusan").trim();
                String semester = rs.getString("semester").trim();
                
                String[] dataField = {nim, nama, jenis_kelamin, fakultas, jurusan, semester};
                Collections.addAll(assetList, dataField);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return assetList;
    }
    
    public String tambah_data(String nim, String nama, String jenis_kelamin, String fakultas, String jurusan, String semester){
        String result = "";
        try{
            con = new class_koneksi().getConSQL();
            String str = "INSERT INTO tb_mahasiswa(nim, nama, jenis_kelamin, fakultas, jurusan, semester) VALUES(?,?,?,?,?,?)";
            PreparedStatement pr = con.prepareStatement(str);
            
            pr.setString(1,nim);
            pr.setString(2,nama);
            pr.setString(3,jenis_kelamin);
            pr.setString(4,fakultas);
            pr.setString(5,jurusan);
            pr.setString(6,semester);
            
            int i = pr.executeUpdate();
            if(i!=0){
                result = "Data Berhasil Disimpan";
            }
            else{
                result = "Data Gagal Disimpan";
            }
            
            con.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return result;
    }
    
    public String ubah_data(String nim, String nama, String jenis_kelamin, String fakultas, String jurusan, String semester){
        String result = "";
        try{
            con = new class_koneksi().getConSQL();
            String str = "UPDATE tb_mahasiswa SET nama=?, jenis_kelamin=?, fakultas=?, jurusan=?, semester=? WHERE nim=?";
            PreparedStatement pr = con.prepareStatement(str);
            
            pr.setString(1,nama);
            pr.setString(2,jenis_kelamin);
            pr.setString(3,fakultas);
            pr.setString(4,jurusan);
            pr.setString(5,semester);
            pr.setString(6,nim);
            
            int i = pr.executeUpdate();
            if(i!=0){
                result = "Data Berhasil Di Ubah";
            }
            else{
                result = "Data Gagal Di Ubah";
            }
            con.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return result;
    }
    
    public String hapus_data(String nim){
        String result = "";
        try{
            con = new class_koneksi().getConSQL();
            String str = "DELETE tb_mahasiswa WHERE nim=?";
            PreparedStatement pr = con.prepareStatement(str);
            
            pr.setString(1,nim);
            
            int i = pr.executeUpdate();
            if(i!=0){
                result = "Data Berhasil Di Hapus";
            }
            else{
                result = "Data Gagal Di Hapus";
            }
            con.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return result;
    }
    
    public String Regex(String s){
        String result;
        if(s.matches("(?i)[a-z]+([,.\\s]+[a-z]+)*")){
            result = "true";
        }
        else{
            result = "false";
        }
        
        return result;
    }
}