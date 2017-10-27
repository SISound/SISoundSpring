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
		
		<input id="input" type="text" placeholder="Search" class="header-input desktop-only" />
<!-- 		<a class = headerText href="loginPage">Profile</a> -->
		<a class = headerText href="index">Home</a>
	</nav>
	
	<div class="dropdown">
		<button class="dropbtn">
			  	<c:if test="${user.profilPicture != null}">
					<img class="profilePic" alt="profilePic" src="${user.profilPicture }">
					<span class="profileButton"><c:out value="${ user.username }"></c:out></span>
				</c:if>
				<c:if test="${user.profilPicture == null}">
					<img class="profilePic" alt="defaultProfilPic" src="img/defaultProfile.png">
					<span class="profileButton"><c:out value="${ user.username }"></c:out></span>
				</c:if>
			  </button>
			  <div class="dropdown-content">
			    <a href="profile">Profile</a>
			    <a href="upload">Upload</a>
			    <form action="logout" method="post">
			    	<div id="signOutDiv"><input id="signOut" type="submit" value="Sign Out">
			    	</div>
			    </form>
			  </div>
		</div>
	
	
  <div class="fullscreen-bg"></div>
</body>
</html>