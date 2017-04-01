

<%-- 
    Document   : MyJSP
    Created on : Nov 16, 2012, 8:06:46 PM
    Author     : admin
--%>

<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.File"%>


<%
    System.out.println("Content Type ="+request.getContentType());
    System.out.println("Cookies" + request.getCookies());

    DiskFileUpload fu = new DiskFileUpload();
    // If file size exceeds, a FileUploadException will be thrown
    fu.setSizeMax(100000000);

    List fileItems = fu.parseRequest(request);
    Iterator itr = fileItems.iterator();

    while(itr.hasNext()) {
      FileItem fi = (FileItem)itr.next();

      //Check if not form field so as to only handle the file inputs
      //else condition handles the submit button input
      if(!fi.isFormField()) {
        System.out.println("\nNAME: "+fi.getName());
        System.out.println("SIZE: "+fi.getSize());
        //System.out.println(fi.getOutputStream().toString());
        
         File f = new File(application.getRealPath(request.getRequestURI())).getParentFile();
                  String path = "";
                  while (f != null && !f.exists()) {
                      f = f.getParentFile();
                  }
                  if (f != null) {
                      path = f.getAbsolutePath();
                  }
                  path = f.getAbsolutePath();
        File fNew= new File(path+"/uploadFiles/", fi.getName());

        System.out.println(fNew.getAbsolutePath());
        fi.write(fNew);
      }
      else {
        System.out.println("Field ="+fi.getFieldName());
      }
    }
    
    response.sendRedirect("upload.jsp");
 %>











