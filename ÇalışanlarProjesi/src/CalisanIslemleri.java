
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CalisanIslemleri {

    private Connection con=null;
    
    private Statement statement=null;
    
    private PreparedStatement preparedStatement=null;
    
    
    public CalisanIslemleri(){
        
        String url="jdbc:mysql://"+Database.host+":"+Database.port +"/"+Database.db_ismi+"?useUnicode=true&characterencoding=utf8";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver bulunamadı");
        }
        
        
        try {
            con=DriverManager.getConnection(url,Database.kullanici_adi,Database.parola);
            System.out.println("Bağlantı başarılı");
        } catch (SQLException ex) {
            System.out.println("Bağlantı başarısız");
        }
        
        
    }

    public boolean girisYap(String kullanici_adi, String parola) {
        
        String sorgu="Select * From adminler where username=? and password=?";
        
        try {
            preparedStatement=con.prepareStatement(sorgu);
            
            preparedStatement.setString(1,kullanici_adi );
            preparedStatement.setString(2,parola);
            
            ResultSet rs= preparedStatement.executeQuery();
            
            // O KULLANICI ADI VE PAROLAM YOKSA rs.next() false dönecek.
            
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            
            // try ın içinde değer dönmezse burda extrada ne dönceğini söylüyoruz.
            return false;
        }
        
    }
    
    public ArrayList<Calisan> calisanlari_getir(){
        
        // VERİTABANIMDAN HER BİLGİ ALDIĞIMDA BURAYA YAZILIR.
        ArrayList<Calisan> cikti=new ArrayList<Calisan>();
        
        
                
        try {
            statement=con.createStatement();
            String sorgu="Select * From calisanlar";
            
            ResultSet rs=statement.executeQuery(sorgu);
            
            while(rs.next()){
                
                int id=rs.getInt("id");
                String ad=rs.getString("ad");
                String soyad=rs.getString("soyad");
                String departman=rs.getString("departman");
                String maas=rs.getString("maas");
                
                cikti.add(new Calisan(id,ad,soyad,departman,maas));
            }
            return cikti;
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public void calisanekle(String ad,String soyad,String departman,String maas){
        
        String sorgu="Insert into calisanlar (ad,soyad,departman,maas) values(?,?,?,?)";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 

    public void calisanguncelle(int id,String ad, String soyad, String departman, String maas) {
        
        String sorgu="Update calisanlar set ad = ? , soyad = ? , departman = ? , maas = ? where id = ?";
        
        try {
            preparedStatement=con.prepareStatement(sorgu);
            
            
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            preparedStatement.setInt(5, id);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void calisansil(int id){
        
        String sorgu="Delete From calisanlar where id = ?";
        
        try {
            preparedStatement=con.prepareStatement(sorgu);
            
            preparedStatement.setInt(1, id);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            
        }
    }
     
}
