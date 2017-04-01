<%-- 
    Document   : upload
    Created on : Nov 19, 2012, 7:06:10 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import = "java.io.File, java.lang.Exception, java.text.SimpleDateFormat " %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        
        
        <h1 align="center">List of NYMBLE SERVER 2 Service user can upload, download but Not to Delete the Service</h1>
        
        
        <TABLE height="20px" width="100%" bgcolor="#eeeeee" bordercolor="black" border="1" align="center">
            <tr style="color:blue; background-color: lightblue">
                <th width="40%">Name</th>
                <th width="7%">Size</th>
                <th width="7%">Type</th>
                <th width="15%">Date</th>
                <th width="15%"></th>
                <th width="15%"></th>
            </tr>
            <%
                //ServletContext application = getServletConfig().getServletContext();
                  File f = new File(application.getRealPath(request.getRequestURI())).getParentFile();
                  String path = "";
                  while (f != null && !f.exists()) {
                      f = f.getParentFile();
                  }
                  if (f != null) {
                      path = f.getAbsolutePath();
                  }
                  path = f.getAbsolutePath();
                  System.out.println("Path is : "+path);
                File directory = new File(path+"/uploadFiles");
 
            //get all the files from a directory
            File[] fList = directory.listFiles();
            String[] name = new String[fList.length];
            int count = 0;
 
        for (File file : fList){
            if (file.isFile()){
                
                name[count] = file.getName();
                count++;
                %>
                
                
                <tr>
                    <td ><%= file.getName() %></td>
                    <%
                    long filesize = file.length();
                    long fileSizeInKB = filesize / 1024;
                    %>
                    <td ><%= fileSizeInKB %> KB</td>
                    <%
                        String fileTypeName = file.toString().substring(file.toString().lastIndexOf("."), file.toString().length());
                    %>
                    <td><%= fileTypeName %></td>
                    
                    <%
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        
                    %>
                    <td><%= sdf.format(file.lastModified()) %></td>
                    
                    <td><a href="Download.jsp?filename=<%= file.getName()%>"><center>Download</center></a></td>
                    <td><a href="DeleteFile.jsp?filename=<%= file.getName()%>"><center>Delete</center></a></td>
            </tr>
        <%    
            }
        }
        %>

        </table>


      <br/>  
      
        
        
      <br/>
        <form name="uploadFile" method="POST" action="MyJSP.jsp"     
 enctype="multipart/form-data">
            <center><input type="file" name="myfile" size="50" />&nbsp;
     <input type="submit" value="Upload" /></center>
 </form>
      <%}%>
    </body>
</html>
