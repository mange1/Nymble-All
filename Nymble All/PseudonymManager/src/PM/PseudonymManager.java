package PM;

import Connection.DatabaseConnection;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Inbo
 */
public class PseudonymManager implements Runnable {

    Socket socket;
    ServerSocket serverSocket;
    InetAddress inetAddresss;
    String ClientIp;
    String ClientName;
    
    static int key;
    String name;
    String macName;
    DatabaseConnection db;
    ResultSet rs;
    String date = "";


    public PseudonymManager() {
        try {
            System.out.println("Pseudonym Manager Started...");
            System.out.println("Start Connecting Client");
            serverSocket = new ServerSocket(2828);

            Socket sock = serverSocket.accept();
            System.out.println("Client Connected");

            
            Runnable runnable = new PseudonymManager(sock);
            Thread thread = new Thread(runnable);
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PseudonymManager(Socket s) {
        socket = s;
    }

    public void setPseudonym() {

        String ip = ClientIp;

        String k = ip.substring(ip.lastIndexOf(".") + 1, ip.length());

        String nm = "";
        char c[] = k.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (c[i] != '0') {
                nm = nm + (char) (65 + Integer.parseInt(String.valueOf(c[i])));
            }
        }
        System.err.println("name== " + (nm + k));

        name = (nm + k);
        key = Integer.parseInt(k);

        NymbleManagertest.NMkey = key;
        System.err.println("key== " + (k));
    }

    public void write() {
        FileWriter fstream = null;
        try {
            
            
            db = new DatabaseConnection();
            db.dbconnection();
            boolean present = false;
            rs = db.getResultSet("select * from pseudonym where PMkey = '" + key + "' AND MACAddress = '" + macName + " '");
            while (rs.next()) {
                present = true;
            }

            if (!present) {

                int i = db.getUpdate("insert into pseudonym values ('" + name + "','" + key + "' , '" + macName + "')");
                if (i == 1) {
                    System.err.println("inserted Successfully!!!");
                }
                
                i = db.getUpdate("insert into keyippair values ('" + name + "','" + ClientIp + "' , '" + macName + "')");
                if (i == 1) {
                    System.err.println("inserted Successfully!!!");
                }
            }

           


        } catch (Exception ex) {
            Logger.getLogger(PseudonymManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String h[]) {
        new PseudonymManager();

    }

    public void getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        java.util.Date date1 = new java.util.Date();
        date = dateFormat.format(date1);
        System.out.println("Current Date : " + date);
    }

    public void run() {
        try {
            inetAddresss = socket.getInetAddress();
            InputStream is = socket.getInputStream();
            byte[] pcname;
            while (true) {
                if (is.available() > 0) {
                    byte buff[] = new byte[is.available()];
                    is.read(buff, 0, is.available());
                    pcname = buff;
                    break;
                }
            }

            byte[] pc = pcname;
            System.out.println(new String(pc));

            StringBuilder sb = new StringBuilder();

            for (int x = 0; x < pc.length; x++) {
                sb.append(String.format("%02X%s", pc[x], (x < pc.length - 1) ? "-" : ""));
                //sb.append(String.format("%s", (i<mac.length) ? "-" : ""));
            }
            System.out.println(sb.toString());
            macName = sb.toString();

            ClientName = inetAddresss.getHostName();

            ClientIp = inetAddresss.getHostAddress();

            System.out.println("inetAddress : " + inetAddresss);
            System.out.println("Client Name : " + ClientName);
            System.out.println("Client MAC Name : " + macName);
            System.out.println("Client IP : " + ClientIp);

            setPseudonym();

            write();
             System.err.println("completed all my work");

            new NymbleManagertest();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
