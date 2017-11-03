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
		
		<table class="sortingTable">
			<tr>
				<td class="sortTd">Sort by: |</td>
				<td> <a class="sortingLink" href="index">Default |</a> </td>
				<td> <a class="sortingLink" href="index=likes">Likes |</a> </td>
				<td> <a class="sortingLink" href="index=recent">UploadDate |</a> </td>
			</tr>
		</table>
		
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
					        <c:if test="${ songsToShow.user.username != sessionUser.username}">
					        		<button class="followButton" id="followButton" value="${ songsToShow.user.username }" onclick="followUnfollow(this.value)">Follow</button>
					        </c:if>
					        <button class="addToPlaylist" id="addingButton" value="${ songsToShow.id }" onclick="addToPlaylist(this.value)">&#8801 Add to playlist</button>
					        <button class="actionButton" id="shareButton" value="${ songsToShow.id }" onclick="shareSong(this.value)">&#10609Share</button>
					        <button class="actionButton" id="commentButton" value="${ songsToShow.id }" onclick="commentSong(this.value)">&#128172Comment</button>
					        <button class="actionButton" id="dislikeButton" value="${ songsToShow.id }" onclick="dislikeSong(this.value)">&#128078Dislike</button>
					        <button class="actionButton" id="likeButton" value="${ songsToShow.id }" onclick="likeSong(this.value)">&#10084Like</button>				
					      </div>
					      <audio controls class="myPlayer">
							  <source src="getSong${songsToShow.url}" type="audio/mpeg">
						  </audio>	
					    </li>
					  </ol>
					</nav>
				</tr>
				<br/><br><br><br><br><br><br>
			</c:forEach>
		</table>
				
		
	</body>
</html>