<%-- 
    Document   : OpenFile
    Created on : Nov 19, 2012, 5:07:15 PM
    Author     : admin
--%>

<%@page import="java.awt.Desktop"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import ="java.io.* , java.net.*" %>

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
                        String fileName = request.getParameter("filename");
                        
                     /*
			<a href="<%= fileName >" target="_child">
                        <h1>Hello World! <%= fileName ></h1>
                        </a>
                       */  
                        
                        
                         //File f = new File(application.getRealPath("/uploadFiles"),fileName);
                        
                        File f1 = new File(application.getRealPath(request.getRequestURI())).getParentFile();
                  String path = "";
                  while (f1 != null && !f1.exists()) {
                      f1 = f1.getParentFile();
                  }
                  if (f1 != null) {
                      path = f1.getAbsolutePath();
                  }
                  path = f1.getAbsolutePath();
			response.sendRedirect("upload.jsp");
                       Desktop.getDesktop().open(new File((path+"/uploadFiles/"+fileName)));
                       
        %>
        
         <%    
            }
       
        %>

        
    </body>
</html>
