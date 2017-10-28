<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	
<!-- 		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> -->
<!-- https://code.jquery.com/jquery-3.2.1.min.js -->
		<script src=" https://code.jquery.com/jquery-3.2.1.min.js"></script>
		<link href="https://p.typekit.net/p.gif?s=1&k=yyw5dsj&app=typekit&ht=tk&h=www.flagstar.com&f=6846.6848.6849.6851.6852&a=1922000&sl=195&fl=122&js=1.14.8&_=1458594264430">
		<link rel="stylesheet" type="text/css" href="<c:url value="css/profile.css" />" />
<%-- 		<script src="<c:url value="/js/profile.js" />" type ="text/javascript"></script> --%>
<%-- 		<script type="text/javascript" src="${pageContext.request.contextPath}/js/profile.js"></script> --%>

		<title>SISound</title>
	</head>
<body>
<%-- 	<c:set value="<%= session.getAttribute("user") %>" var="user"></c:set> --%>
	
	<jsp:include page="headerLogged.jsp"></jsp:include>
	
	<br>
	<br>
	<br>
	<br>
	
<div class = profileASD>
	<div class="banner">
		<div class="container">
			<div class="profile-pic">
				<div class="avatar">
					<c:if test="${ modelUser.profilPicture == null}">
						<img class="avatar" src="<c:url value="img/defaultProfile.png"/>">					
					</c:if>
					<c:if test="${ modelUser.profilPicture != null}">
						<img class="avatar" src="<c:url value="${ modelUser.profilPicture }"/>">
					</c:if>
				</div>
				<c:if test="${ modelUser.username != sessionUser.username}">					
					<a href="#" class="button button-primary mt-20">Follow</a>
				</c:if>
				<c:if test="${ modelUser.username == sessionUser.username}">					
					<a href="#" class="button button-primary mt-20">Edit Profile</a>
				</c:if>
			</div>
			<div class="bio">
				<h1 class="heading-medium"> <c:out value="${ modelUser.username }"></c:out> </h1>
				<h3 class="heading-small"> <c:out value="${ modelUser.name }"></c:out>  </h3>
				<h5 class="heading-small"> <c:out value="${ modelUser.adress }"></c:out> </h5>
				<p class="body-small"> <c:out value="${ modelUser.bio }"></c:out> </p>
			</div>
		</div>
	</div>
	<div class="section-about">
		<div class="container">
			<div class="tab-group">
				<a href="#tab-contact" class="tab tab-active heading-small tab-label">Songs</a>
				<a href="#tab-about" class="tab heading-small tab-label">Playlists</a>
			</div>
		</div>
		<div class="container">
			<div class="tab-items">
				<div class="tab-content" id="tab-contact">
				
				</div>
				<div class="tab-content" id="tab-about">
				asdgsdagsd<br>
				asdgsdagsd<br>
				asdgsdagsd<br>
				asdgsdagsd<br>
				asdgsdagsd<br>
				asdgsdagsd<br>
				asdgsdagsd<br>
				
				</div>
			</div>
		</div>
	</div>


	</div>
<!--   <div class="fullscreen-bg"></div> -->

	
		<script src="<c:url value="js/profile.js" />"  type ="text/javascript"></script>
</body>
</html>