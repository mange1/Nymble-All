<%-- 
    Document   : newAccount
    Created on : Nov 15, 2012, 4:21:26 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Account</title>
        
        
<script type="text/javascript" charset="utf-8">

function tologin(){

var first_name=document.getElementById("firstname").value;
var last_name=document.getElementById("lastname").value;
var username=document.getElementById("username").value;
var pwd=document.getElementById("password").value;


            if(first_name=="")
            {
                document.getElementById("firstname").focus();
                document.getElementById("firstname").placeholder="Enter First Name";
            }
            else if(last_name=="")
            {
                document.getElementById("lastname").focus();
                document.getElementById("lastname").placeholder="Enter Last Name";
            }
            else if(username == ""){
		document.getElementById("username").focus();
		document.getElementById("username").placeholder="Please provide uername";
            }
            else if(pwd == "")
            {
		document.getElementById("password").focus();
		document.getElementById("password").placeholder="Please provide password";
            }
            else
            {
		document.getElementById('adminlogin').submit();
            }
}
</script>        
        
        
        
<style type="text/css">
#hed{
	height:10px;
}
#header1{
	height:100px;
	background:#275DE3;
	text-align:center;
}
.center{
	display: inline-block;
    padding: 35px 15px;
    vertical-align: middle;
    width: 460px;
	font:30px Viner Hand ITC;
	color:#fff;
}
#hed1{
	height:20px;
}
.col2{
	width:450px;
	
}
.login{
	font-size:15px;
	width:550px;
	color:#fff;
	background:#3081c8;
	border-radius:4px;
	-moz-border-radius:4px;
	-webkit-border-radius:4px;
	behavior:url(js/PIE.htc);
}
.label1{
	padding:5px 12px 5px 5px;
	margin-bottom:2px;
	text-align:right;
	font:15px Arial, Helvetica, sans-serif;
	font-weight:bold;
}
.input1 {
	float:left;
	border:1px solid #dcdcdc;
	background:#fff;
	padding:5px;
	margin-bottom:2px;
	font:13px Arial, Helvetica, sans-serif;
}
.button1 {
	float:left;
	display:inline-block;
	font-size:13px;
	color:#fff;
	background:#f09e2f;
	line-height:30px;
	padding:0 13px;
	border-radius:4px;
	-moz-border-radius:4px;
	-webkit-border-radius:4px;
	behavior:url(js/PIE.htc);
	position:relative;
	font-weight:600;
	text-decoration:none;
	text-transform:uppercase
}
.button1:hover {background:#8cbd20}
</style>
    </head>
     <div>
            <div>
                <div>
                    <h1><a href="index.jsp" id="logo">Create New Account</a></h1>
                    
                </div>
               <br /><br /><br /><br />
                <section id="content">
			<table width="100%"><tr><td align="center">
				<div class="login">
					<form id="adminlogin" align="center" method="get" action="http://192.168.0.101:8080/NymbleServer/CreateNewAccount">
					
					<table   cellspacing="0" cellpadding="0" border="1">
							
							
							<tr >
								<td>
								</td>
							<td style="padding:20px">
							<table width= "100%">
							<tr>
								<td>
								</td>
							</tr>
							<tr>
								<td style="padding:0px 0px 5px 0px" colspan="2"><label class="error" ></label></td>
							</tr>
                                                        <tr>	
								<td class="label1" >First Name : </td> <td><input type="text" class="input1" id="firstname" 
								name="firstname" placeholder="First Name" value=""/></td>
							</tr>
							<tr>
								
							</tr>
                                                        <tr>	
								<td class="label1" >Last Name : </td> <td><input type="text" class="input1" id="lastname" 
								name="lastname" placeholder="Last Name" value=""/></td>
							</tr>
							<tr>
								
							</tr>
                                                        
							<tr>	
								<td class="label1" >Username : </td> <td><input type="text" class="input1" id="username" 
								name="username" placeholder="Username" value=""/></td>
							</tr>
							<tr>
								
							</tr>
							
							<tr>
								<td class="label1">Password : </td> <td><input type="password" class="input1" id="password" name="password" placeholder="Password" value=""/></td>
							</tr>
							<tr>
								<td style="padding-top:5px"></td>
								<td style="padding-top:5px" ><a href="#" class="button1" onclick="tologin()">Create Account</a></td>
							</tr>
							</table>
							</td>
							</tr>
						</table>
				</form>
				</div>
				</td></tr></table>
			</section>
               
            </div>
        </div>
        
</html>
