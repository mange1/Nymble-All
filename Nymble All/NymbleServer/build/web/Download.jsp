<%-- 
    Document   : Download
    Created on : Nov 19, 2012, 3:53:06 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import ="java.io.*" %>


<%!

class Writer2Stream extends OutputStream {

		Writer out;

		Writer2Stream(Writer w) {
			super();
			out = w;
		}

		public void write(int i) throws IOException {
			out.write(i);
		}

		public void write(byte[] b) throws IOException {
			for (int i = 0; i < b.length; i++) {
				int n = b[i];
				//Convert byte to ubyte
				n = ((n >>> 4) & 0xF) * 16 + (n & 0xF);
				out.write(n);
			}
		}

		public void write(byte[] b, int off, int len) throws IOException {
			for (int i = off; i < off + len; i++) {
				int n = b[i];
				n = ((n >>> 4) & 0xF) * 16 + (n & 0xF);
				out.write(n);
			}
		}
	} //End of class Writer2Stream




static void copyStreamsWithoutClose(InputStream in, OutputStream out, byte[] buffer)
			throws IOException {
		int b;
		while ((b = in.read(buffer)) != -1)
			out.write(buffer, 0, b);
	}

%>



<!DOCTYPE html>
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
			
                        
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename=\"" + f.getName()
						+ "\"");
				response.setContentLength((int) f.length());
				BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(f));
				byte buffer[] = new byte[8 * 1024];
				out.clearBuffer();
                               OutputStream out_s = new Writer2Stream(out);
				copyStreamsWithoutClose(fileInput, out_s, buffer);
				fileInput.close();
				out_s.flush();

        %>
        <h1>Hello World! <%= fileName %></h1>
        
         
         <%    
            }
       
        %>
    </body>
</html>
