<%-- 
    Document   : DeleteFile
    Created on : Nov 19, 2012, 5:51:25 PM
    Author     : admin
--%>

<%@page import="javax.swing.JOptionPane"%>
<%@page import="servlet.LoginServer"%>
<%@page import="servlet.DatabaseUtility"%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import ="java.io.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%
        if(session.getAttribute("Block")!=null)
        {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        
        }
        else
         {
        %>
        
         <%
          File f1 = new File(application.getRealPath(request.getRequestURI())).getParentFile();
                  String path = "";
                  while (f1 != null && !f1.exists()) {
                      f1 = f1.getParentFile();
                  }
                  if (f1 != null) {
                      path = f1.getAbsolutePath();
                  }
                  path = f1.getAbsolutePath();
                  
                        String fileName = request.getParameter("filename");
			File f = new File((path+"/uploadFiles"),fileName);
                        
                       
                            
                                if(LoginServer.status.equals("YES"))
                                {
                                    f.delete();
                                    response.sendRedirect("upload.jsp");
                                }
                                else
                                {
                                    DatabaseUtility du = new DatabaseUtility();
                                    String ip = session.getAttribute("ipAddress").toString();
                                    String mac = du.getMacAddress(ip);
                                    session.setAttribute("Block", "Block");
                                    du.blacklist(ip,mac);
                                    response.sendRedirect("index.jsp");
                                }
                                
        %>
          <%    
            }
       
        %>
    </body>
</html>
