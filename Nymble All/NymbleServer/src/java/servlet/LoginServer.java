/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Inbo
 */
public class LoginServer extends HttpServlet {
    
    int count;
    public static String ipAddress;
    String date;
    //static HashMap<String, Integer> hm = new HashMap<String, Integer>();

    public static String status="NO";
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        count = 0;
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServer</title>");
            out.println("</head>");
            out.println("<body>");

            //String ipAddress = request.getHeader("X-FORWARDED-FOR");
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
            out.println("<h2>"+ipAddress+"</h2>");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
            getDate();
//            out.println("ipAddress:" + ipAddress);
            
            DatabaseUtility du = new DatabaseUtility();
            int c = du.getCount(ipAddress, date);
            if (c == 0) {
                count = 0;
                du.insert(ipAddress, count, date);
            } else {
                count = c;
            }

            //delete
            du.delete(date);
            
            if (count < 3) {
                if (du.authenticateLogin(username, password)) {
                    SuccessfullServlet.value = "success";
                    status = du.checkStatus(username, password);
                    request.getSession().setAttribute("ipAddress", ipAddress);
                    
                    response.sendRedirect("/NymbleServer/SuccessfullServlet");
                } else {
                    SuccessfullServlet.value = "failed";
                    out.println("chance else " + count);
                    count += 1;
                    du.update(ipAddress, count, date);
                    if (count == 3) {
                        SuccessfullServlet.value = "block";
                        
                        du.block(ipAddress, date);
                        response.sendRedirect("/NymbleServer/SuccessfullServlet");
                    } else {
                        response.sendRedirect("/NymbleServer/SuccessfullServlet");
                    }
                }
//                if (count >= 3) {
//                    out.println("chance if" + count);
//                    SuccessfullServlet.value = "block";
////                    response.sendRedirect("/NymbleServer/SuccessfullServlet");
//                }
            } else {
                //out.println("chance if" + count);
                SuccessfullServlet.value = "block";
                response.sendRedirect("/NymbleServer/SuccessfullServlet");
            }

//            out.println("count== " + count);
            //out.println("<h1>Servlet LoginServer at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
        } finally {
            out.close();
        }
    }
    
    public void getDate() {
        
//        Date today = new Date();
//        Timestamp currentTimestamp=new Timestamp(today.getTime());
//        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date1 = new java.util.Date();
        date = dateFormat.format(date1);
//        System.out.println("Current Date : " + date);
    }

//    public void blacklisted() {
//        try {
//            DatabaseConnection db = new DatabaseConnection();
//            db.dbconnection();
//
//            int i = db.getUpdate("insert into blacklist values('" + ipAddress + "','server2','Blocked','" + date + "')");
//
//            //ResultSet rs = db.getResultSet("select * from blacklist where ipaddress='" + ipAddress + "'");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    //            if (hm.containsKey(ipAddress)) {
//                int getcount = Integer.parseInt(hm.get(ipAddress).toString());
//                if (getcount < 3) {
//                    if (username.equals("user1") && password.equals("user1")) {
//                        SuccessfullServlet.value = "success";
//                        response.sendRedirect("/NymbleServer/SuccessfullServlet");
//                    } else {
//                        SuccessfullServlet.value = "failed";
//                        count += 1;
//                        //hm.remove(ipAddress);
//                        hm.put(ipAddress, count);
//                        response.sendRedirect("/NymbleServer/SuccessfullServlet");
//                    }
//                }
//                if (count >= 3) {
//                    SuccessfullServlet.value = "block";
//                    response.sendRedirect("/NymbleServer/SuccessfullServlet");
//                }
//
//            } else {
//                hm.put(ipAddress, (count += 1));
//                //int getcount = Integer.parseInt(hm.get(ipAddress).toString());
//                //if (getcount <= 3) {
//                if (username.equals("user1") && password.equals("user1")) {
//                    SuccessfullServlet.value = "success";
//                    response.sendRedirect("/NymbleServer/SuccessfullServlet");
//                }
//                if (!username.equals("user1") && !password.equals("user1")) {
//                    SuccessfullServlet.value = "failed";
//                    count += 1;
//                    //hm.remove(ipAddress);
//                    hm.put(ipAddress, count);
//                    response.sendRedirect("/NymbleServer/SuccessfullServlet");
//                }
//                //}
////                if (count > 3) {
////                    SuccessfullServlet.value = "failed";
////                    response.sendRedirect("/NymbleServer/SuccessfullServlet");
////                }
//            }
//            DatabaseConnection db = new DatabaseConnection();
//            db.dbconnection();
//            ResultSet rs = db.getResultSet("select * from dummy where ipaddress='" + ipAddress + "'");
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
