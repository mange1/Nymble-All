/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PM;

import Connection.DatabaseConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Inbo
 */
public class NymbleManagertest implements Runnable {

    ServerSocket serverSocket;
    Socket socket;
    String PseudoName;
    String ServerAddr;
    PrintWriter printWriter;
    String NimbleTicket;
    String[] serverURL = {"\\\\192.168.0.101\\First", "server2", "\\\\192.168.0.101\\Second", "http://192.168.0.101:8080/apple/apple.html"};
    Boolean valid = true;
    String serverAddr;
    static Thread thread;
    static int NMkey = 0;
    DatabaseConnection db;
    ResultSet rs;

    public NymbleManagertest() {
        try {
            serverSocket = new ServerSocket(9898);

            Socket sock = serverSocket.accept();
            System.out.println("Client Connected to NM");

            db = new DatabaseConnection();
            db.dbconnection();

            NymbleManagertest runnable = new NymbleManagertest(sock);
            thread = new Thread(runnable);
            thread.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NymbleManagertest(Socket s) {
        socket = s;
    }

    public static void main(String args[]) {
        new NymbleManagertest();
    }

    public void getKey() {
        System.err.println("in getkey");

        try {

            db = new DatabaseConnection();
            db.dbconnection();
//            System.out.println("key== " + NMkey);
            rs = db.getResultSet("select * from pseudonym where PMkey ='" + NMkey + "'");
            while (rs.next()) {
                PseudoName = rs.getString("pseudoname");
//                System.out.println("PPPPname== " + PseudoName);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    
    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {

                String str = br.readLine();
                System.out.println("Nymble str== " + str);
                if (str.contains("server")) {

                    Vector server = new Vector();
                    Vector dateV = new Vector();
                    //gets key
                    getKey();

                    //pseudoname server pairing
                    int index = str.indexOf("-");
                    serverAddr = str.substring(index + 1, str.length());
                    System.out.println("NM Address got server name " + serverAddr);

                    int ind = Integer.parseInt(serverAddr);
                    ServerAddr = serverURL[ind - 1];//Actual Location of Server

                    System.out.println("Server Add : "+ServerAddr);
                    if (!(PseudoName.equals("")) && !(ServerAddr.equals(""))) {

                        String IP = "";
                        String mac = "";



                        ResultSet rss = db.getResultSet("select * from keyippair where name='" + PseudoName + "'");
                        if (rss.next()) {
                            IP = rss.getString("ipaddress");
                            mac = rss.getString("MACAddress");
                        }
                        //JOptionPane.showMessageDialog(null, "Mac : "+mac+"Name : "+PseudoName);

                        String qry = "select * from blacklist where MACAddress='" + mac + "'";
                        ResultSet rf = db.getResultSet(qry);

                        System.out.println("Q : "+qry);
//                        Date today = new Date();
//                        Date myDate = new Date(today.getYear(), today.getMonth() - 1, today.getDay());
                        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date myDate = new java.util.Date();
                        Timestamp time = new Timestamp(myDate.getTime());

                        // JOptionPane.showMessageDialog(null, time);

                        while (rf.next()) {
                            //server.add(rs.getString("server"));
                            //dateV.add(rs.getString("date"));
                            System.out.println("Enter here");
                            if (ServerAddr.equals(rf.getString("Server"))) {

                                String regString = rf.getString("date");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                Date regDate = dateFormat.parse(regString);
                                Timestamp reg = new Timestamp(regDate.getTime());

                                System.out.println("s : "+ServerAddr+" D : "+regDate+" M : "+time);

                                if (reg.before(time)) {
                                    valid = true;
                                } else {
                                    valid = false;
                                }
                                 //JOptionPane.showMessageDialog(null, "Server : "+ServerAddr+"Date : "+regDate+" MyDate :"+time);

                            }

                        }

                        String head = "yes";
                        if (valid) {
                            head = "yes";
                        }
                        if (!valid) {
                            head = "no";
                        }


                        //Nymble Ticket Generation
                        NimbleTicket = head + "-" + ServerAddr; //name and server addr in ticket

                        printWriter = new PrintWriter(socket.getOutputStream());
                        printWriter.println(NimbleTicket);
                        printWriter.flush();
//                        System.out.println("Ticket Send to User " + NimbleTicket);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
