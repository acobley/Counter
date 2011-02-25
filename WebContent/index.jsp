<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.stores.*" %>
   
    <%@ page import="java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    	<title>GlassFish JSP Page</title>
  </head>
  <body>

    <h1>Hello World!</h1>
 
    
    <h1>!00 Random Data</h1>
    
    <%
    List<String> Rd = (List<String>)request.getAttribute("Data");
    if (Rd !=null){
    Iterator<String> iterator;


    iterator = Rd.iterator();     
    while (iterator.hasNext()){
    	String md = (String)iterator.next();
    	
    	%>
 		<%=md %><br>
    	<% 
     
    }
    }
    %>
 
    
  </body>
</html> 
