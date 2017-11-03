<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		 <link rel="stylesheet" type="text/css" href="<c:url value="css/style.css"/>" />
		<title>SISound</title>
				
		
	</head>
	
	<body>
		<jsp:include page="headerLogged.jsp"></jsp:include>
	
		<c:out value="Your follower's recent uploads"></c:out>
	
		<jsp:include page="main2.jsp"></jsp:include>
	</body>
</html>	