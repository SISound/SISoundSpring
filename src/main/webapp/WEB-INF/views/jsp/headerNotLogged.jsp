<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SISound</title>
 <link rel="stylesheet" type="text/css" href="<c:url value="css/header.css" />" />
<%--  <link rel="stylesheet" type="text/css" href = "<c:url value="css/logReg.css" />" /> --%>
</head>
<body>
	<nav class = "nav">
		
		<a href="index">
 			<img class = "logoHeader"src="<c:url value="img/logo.png"/>">
		</a>
		
<!-- 		<input id="input" type="text" placeholder="Search" class="header-input desktop-only" /> -->
		<a class = "headerText" href="loginPage">Login</a>
		<a class = "headerText" href="index">Home</a>
	</nav>
	
	
  <div class="fullscreen-bg"></div>
</body>
</html>