<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<c:url value="css/style.css"/>" />

	</head>
	<body>

		
		<table>
			<c:forEach items="${ songsToShow }" var="songsToShow">
				<tr>
					<nav class="archive-links">
					  <ol>
					    <li>
					      <div href="userProfile" class="${ songsToShow.user.username }">
					      	
					      	<c:if test="${ songsToShow.user.profilPicture != null}">
								<class="main-image"><img src="getPic${ songsToShow.user.username }" class="main-image" alt="Camera" style="width:60px;height:60px;">
				 			</c:if>
					        <c:if test="${songsToShow.user.profilPicture == null}">
								<img src="img/defaultProfile.png" class="main-image" alt="Camera" style="width:60px;height:60px;">
				 			</c:if>
					        
					        <a class="link-title" href="track=${ songsToShow.id }"><c:out value="${ songsToShow.title }"></c:out></a>
					        <a class="link-excerpt" href="profile${ songsToShow.user.username }"><c:out value="${ songsToShow.user.username }"></c:out></a>
					        
					        <c:if test="${ songsToShow.user.username != sessionUser.username || !sessionUser.followedIds[songsToShow.user.userID] }">
					        	<form method = POST action="follow?user=${songsToShow.user.userID}&name=${songsToShow.user.username}">
					        		<button class="followButton" >Follow</button>
					        	</form>
					        </c:if>
					        
					        <c:if test="${ sessionUser.followedIds[songsToShow.user.userID] }">
					        	<form method = POST action="unfollow?user=${songsToShow.user.userID}&name=${songsToShow.user.username}">
					        		<button class="unfollowButton" >Unfollow</button>
					        	</form>
					        </c:if>
					        
					        	<form action="index">
							       	<button class="addToPlaylist" >&#8801 Add to playlist</button>
<%-- 					      		<button class="actionButton" id="shareButton" value="${ songsToShow.id }" >&#10609Share</button> --%>
								</form>
								
								<div class="likeDiv">
									<c:if test="${ sessionUser.likedSongs[songsToShow.id] }">
					        			<form method = POST action="unlikesong?song=${ songsToShow.id }">
					       					<button class="actionButtonClicked" >&#10084Liked</button>
					        			</form>
									</c:if>
									<c:if test="${ !sessionUser.likedSongs[songsToShow.id] }">
										<form method = POST action="likesong?song=${ songsToShow.id }">
						      				<button class="actionButton" >&#10084Like</button>
										</form>
									</c:if>	
									
									<c:if test="${ sessionUser.dislikedSongs[songsToShow.id] }">
					        			<form method = POST action="undislikesong?song=${ songsToShow.id }">
					       					<button class="actionButtonClicked" >&#128078Disliked</button>
					      				</form>
									</c:if>
									<c:if test="${ !sessionUser.dislikedSongs[songsToShow.id] }">
<%-- 									&?page=${requestScope['javax.servlet.forward.request_uri']} --%>
										<form method = POST action="dislikesong?song=${ songsToShow.id }">
					      					<button class="actionButton" >&#128078Dislike</button>
										</form>
									</c:if>	
									<form action="">
 					        			<button class="actionButton" >&#128172Comment</button>
					        		</form>
								</div>
					        		
					      </div>
					      
					      <audio controls class="myPlayer">
							  <source src="getSong${songsToShow.url}" type="audio/mpeg">
						  </audio>	
					    </li>
					  </ol>
					</nav>
				</tr>
				<br><br><br><br><br><br><br>
			</c:forEach>
		</table>
				
		
	</body>
</html>