/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Connection.DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "CreateNewAccount", urlPatterns = {"/CreateNewAccount"})
public class CreateNewAccount extends HttpServlet {

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
        
        
        boolean check=false;
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        DatabaseConnection db = new DatabaseConnection();
        db.dbconnection();
        try
        {
            String sql="insert into users values('"+firstname+"' , '"+lastname+"' , '"+username+"' , '"+password+"')";
            
            int i = db.getUpdate(sql);
            
            if(i==1)
                check = true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateNewAccount</title>");  
            out.println("</head>");
            out.println("<body>");
            
            if(check)
            {
                out.println("<h1><center>New Account Successfully Create</center></h1>");
                response.sendRedirect("/NymbleClientLogin/index.jsp");
            }
            else
            {
                out.println("<h1><center>Account Not Created<br/>User Allready Exist</center></h1>");
            }
            out.println("</body>");
            out.println("</html>");
            
        } finally {            
            out.close();
        }
    }

    
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
