/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Connection.DatabaseConnection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Inbo
 */
public class DatabaseUtility {
    
    DatabaseConnection db;
    
    public DatabaseUtility() {
        db = new DatabaseConnection();
        db.dbconnection();
    }
    
    public boolean authenticateLogin(String username, String password)
    {
        boolean isValid = false;
        String sql="select * from users where username='"+username+"' AND password='"+password+"'";
        
        try{
            
        ResultSet rs = db.getResultSet(sql);
        
        if(rs.next())
        {
            isValid = true;
        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
                
        finally{
        return isValid;
        }
    }
    public int getCount(String ip, String date) {
        int c = 0;
        try {
            ResultSet rs = db.getResultSet("select count from dummy where ipaddress='" + ip + "' and date='" + date + "%'");
            while (rs.next()) {
                c = rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }
    
    public void insert(String ip, int c, String date) {
        try {
            db.getUpdate("insert into dummy values('" + ip + "'," + c + ",'" + date + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(String ip, int c, String date) {
        try {
            db.getUpdate("update dummy set count='" + c + "' where ipaddress='" + ip + "' and date='" + date + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void block(String ip, String date) {
        try {
            db.getUpdate("insert into blacklist values('" + ip + "','server2','Blocked','" + date + "')");
            System.err.println("inserted Succesfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delete(String date) {
        try {
            db.getUpdate("delete from dummy where date !='" + date + "'");            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String checkStatus(String n, String p)
    {
        if(n.equals("admin") && p.equals("admin"))
            return "YES";
        else
            return "NO";
    }
    
    
    public void blacklist(String ip,String mac)
    {
        String sql ="DELETE FROM blacklist WHERE ipAddress ='"+ip+"' AND MACAddress='"+mac+"'";
        //JOptionPane.showMessageDialog(null, "Enter in black list IP "+ip+" MAC "+mac);
        System.out.println(sql);
        db.getUpdate(sql);
        //JOptionPane.showMessageDialog(null, "Delete from blacklist");
        
        sql="INSERT INTO blacklist VALUES('"+ip+"' , "+"'server2', '"+getDate()+"23:59:59' , '"+mac+"')";
        
        System.out.println(sql);
        db.getUpdate(sql);
        //JOptionPane.showMessageDialog(null, "Inserted in blacklist");
        System.out.println("Done2");
    }
    
    
    
    public String getMacAddress(String ip)
    {
        String mac="";
        try
        {
            ResultSet rs = db.getResultSet("SELECT MACAddress FROM keyippair WHERE ipaddress='"+ip+"'");
            while(rs.next())
            {
                mac = rs.getString("MACAddress");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return mac;
    }
    
    
    public String getDate() {
        String date;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        java.util.Date date1 = new java.util.Date();
        date = dateFormat.format(date1);
        
        return date;
//        System.out.println("Current Date : " + date);
    }
    
    public static void main(String args[]) {
        DatabaseUtility d = new DatabaseUtility();
        d.blacklist("192.168.1.3", "53-4E-45-48-41-4C-2D-50-43");
    }
}
