<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
 
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	
<!-- 		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> -->
	<script type="text/javascript" src=" <c:url value="https://code.jquery.com/jquery-3.2.1.min.js" />"></script>

<!-- https://code.jquery.com/jquery-3.2.1.min.js -->
			<link  rel="stylesheet" type="text/css" href="<c:url value="https://p.typekit.net/p.gif?s=1&k=yyw5dsj&app=typekit&ht=tk&h=www.flagstar.com&f=6846.6848.6849.6851.6852&a=1922000&sl=195&fl=122&js=1.14.8&_=1458594264430" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="css/profile.css" />" />
<%-- 		<script src="<c:url value="/js/profile.js" />" type ="text/javascript"></script> --%>
<%-- 		<script type="text/javascript" src="${pageContext.request.contextPath}/js/profile.js"></script> --%>

		<title>SISound</title>
		
		<script type="text/javascript">
			
			function handleFollow() {
				var button=document.getElementById("followButton");
				var value=button.value;
				var title=button.innerHTML;
				
				if(title=="Follow"){
					followUser(value);
				}
				else{
					unfollowUser(value);
				}
			}
			
			function followUser(value) {
				var request = new XMLHttpRequest();
				request.onreadystatechange = function() {
					//when response is received
					if (this.readyState == 4 && this.status == 200) {
						var button = document.getElementById("followButton");
						button.innerHTML = "Unfollow";
					}
					else
					if (this.readyState == 4 && this.status == 401) {
						alert("Sorry, you must log in to follow this user!");
					}
						
				}
				request.open("post", "restFollowUser?userId=" + value, true);
				request.send();
			}
			
			function unfollowUser(value) {
				var request = new XMLHttpRequest();
				request.onreadystatechange = function() {
					//when response is received
					if (this.readyState == 4 && this.status == 200) {
						var button = document.getElementById("followButton");
						button.innerHTML = "Follow";
					}
					else
					if (this.readyState == 4 && this.status == 401) {
						alert("Sorry, you must log in to unfollow this user!");
					}
						
				}
				request.open("post", "restUnfollowUser?userId=" + value, true);
				request.send();
			}
		</script>
				
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
<%-- 		<c:if test="${ modelUser.coverPhoto == null}"> --%>
<%-- 			<img class="cover-pic" src="<c:url value="img/cover.jpg"/>">					 --%>
<%-- 		</c:if> --%>
		<c:if test="${ modelUser.coverPhoto != null}">
			<img class="cover-pic" alt="profilePic" src="getCover${ modelUser.username }">
		</c:if>
		<br>
		<div class="container">
			<div class="profile-pic">
				<div class="avatar">
					<c:if test="${ modelUser.profilPicture == null}">
						<img class="avatar" src="<c:url value="img/defaultProfile.png"/>">					
					</c:if>
					<c:if test="${ modelUser.profilPicture != null}">
						<img class="avatar" alt="profilePic" src="getPic${ modelUser.username }">
					</c:if>
				</div>
				<c:if test="${ modelUser.username != sessionUser.username}">
					<c:if test="${ sessionUser.followedIds[modelUser.userID]}">
<!-- 						<a href="#" class="button button-primary mt-20">Follow</a> -->
						<button class="followButton" id="followButton" value="${modelUser.userID }" onclick="handleFollow()">Unfollow</button>
					</c:if>
					<c:if test="${!sessionUser.followedIds[modelUser.userID]}">
<!-- 						<a href="#" class="button button-primary mt-20">Follow</a> -->
						<button class="followButton" id="followButton" value="${modelUser.userID }" onclick="handleFollow()">Follow</button>
					</c:if>						
				</c:if>
				<c:if test="${ modelUser.username == sessionUser.username}">					
					<a href="editProfile" class="button button-primary mt-20">Edit Profile</a>
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
		<div class="container tab-group">
			<div class="tab">
			  <button class="tablinks" onclick="openTab(event, 'Songs')" id="defaultOpen">Songs</button>
			  <button class="tablinks" onclick="openTab(event, 'Playlists')">Playlists</button>
			</div>
		</div>
		
			<c:if test="${fn:length(modelUser.songs) eq 0}">
  				<h3 class="heading-small tabcontent" id = "Songs"> No songs yet </h3>
			</c:if>
			
			<div id="Songs" class="tabcontent">
			  <c:forEach items="${ modelUser.songs}" var="song">
			  		<a class="heading-small" href="track=${ song.id }"><c:out value="${ song.title }"></c:out></a><br>
			  		
			  </c:forEach>
			</div>
					
			<c:if test="${fn:length(modelUser.playlists) eq 0}">
  				<h3 class="heading-small tabcontent" id = "Playlists"> No playlist yet </h3>
			</c:if>
		</div>
		<div id="Playlists" class="tabcontent">
		  <c:forEach items="${ modelUser.playlists}" var="playlist">
		  		<c:if test="${ playlist.isPrivate && modelUser.username == sessionUser.username}">
		  			<a class="heading-small" href="playlist?id=${ playlist.id }"><c:out value="${ playlist.title }"></c:out></a><br>	
		  		</c:if>
		  		<c:if test="${ !playlist.isPrivate }">
		  			<a class="heading-small" href="playlist?id=${ playlist.id }"><c:out value="${ playlist.title }"></c:out></a><br>	
		  		</c:if>

		  </c:forEach>
		</div>

	
	</div>
<!--   <div class="fullscreen-bg"></div> -->

	
		<script src="<c:url value="js/profile.js" />"  type ="text/javascript"></script>
<%-- 		<jsp:include page="footer.jsp"></jsp:include> --%>
		
		
</body>
</html>