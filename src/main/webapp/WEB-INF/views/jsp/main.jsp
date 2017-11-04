<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		 <link rel="stylesheet" type="text/css" href="<c:url value="css/style.css"/>" />
		<title>SISound</title>
				
		<script type="text/javascript">

		//LIKE AND UNLIKE A SONG
		function handleLike(id){
			var button = document.getElementById(id);
			var value=button.value;
			var title = button.innerHTML;
			if(title =="Like"){
				likeSong(value, id);
			}
			else{
				unlikeSong(value, id);
			}
		}
	
		function likeSong(value, id) {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById(id);
					button.innerHTML = "Unlike";
					document.getElementById("likeCount").value++;
					if(document.getElementById("dislikeCount").value>0){
						document.getElementById("dislikeCount").value--;
					}
					var button = document.getElementById("dislikeButton");
					button.innerHTML = "Dislike";
				}
				else
				if (this.readyState == 4 && this.status == 401) {
					alert("Sorry, you must log in to like this song!");
				}
					
			}
			request.open("post", "restLikeSong?songId=" + value, true);
			request.send();
		}
		
		function unlikeSong(value, id) {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById(id);
					button.innerHTML = "Like";
					if(document.getElementById("likeCount").value>0){
						document.getElementById("likeCount").value--;
					}
				}
				else
					if (this.readyState == 4 && this.status == 401) {
						alert("Sorry, you must log in to unlike this song!");
					}
			}
			request.open("post", "restUnlikeSong?songId=" + value, true);
			request.send();
		}
		
		//DISABLE PLAYING MORE THAN 1 SONGS
		document.addEventListener('play', function(e){
		    var audios = document.getElementsByTagName('audio');
		    for(var i = 0, len = audios.length; i < len;i++){
		        if(audios[i] != e.target){
		            audios[i].pause();
		        }
		    }
		}, true);
		
		
		</script>
	</head>
	
	<body id="mainBody">
				
		<jsp:include page="headerLogged.jsp"></jsp:include>
		
		<table class="sortingTable">
			<tr>
				<td class="sortTd">Sort by: |</td>
				<td><button  id="likesButton" class="sortingLink" onclick="sortByLikes()">Likes </a>|</td>
				<td><button  id="dateButton" class="sortingLink" onclick="sortByDate()">UploadDate </a>|</td>
				<td><button  id="defaultButton" class="sortingLink" onclick="defaultOrder()">Default </a>|</td>
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
					        
<%-- 					        <c:if test="${ songsToShow.user.username != sessionUser.username || !sessionUser.followedIds[songsToShow.user.userID] }"> --%>
<%-- 					        	<form method = POST action="follow?user=${songsToShow.user.userID}&name=${songsToShow.user.username}"> --%>
<!-- 					        		<button class="followButton" >Follow</button> -->
<!-- 					        	</form> -->
<%-- 					        </c:if> --%>
					        
<%-- 					        <c:if test="${ sessionUser.followedIds[songsToShow.user.userID] }"> --%>
<%-- 					        	<form method = POST action="unfollow?user=${songsToShow.user.userID}&name=${songsToShow.user.username}"> --%>
<!-- 					        		<button class="unfollowButton" >Unfollow</button> -->
<!-- 					        	</form> -->
<%-- 					        </c:if> --%>
					        
					        <c:if test="${ sessionUser.likedSongs[songsToShow.id] }">
								<button id="${ songsToShow.id }" value="${songsToShow.id }" onclick="handleLike(${ songsToShow.id })">Unlike</button>
							</c:if>
							<c:if test="${ !sessionUser.likedSongs[songsToShow.id] }">
								<button id="${songsToShow.id }" value="${songsToShow.id }" onclick="handleLike(${ songsToShow.id })">Like</button>
							</c:if>
							
							<input id="likeCount" type="number" min="0" onkeydown="return false" value="${songsToShow.likesCount }">
					        
<!-- 					        	<form action="index"> -->
<!-- 							       	<button class="addToPlaylist" >&#8801 Add to playlist</button> -->
<%-- <%-- 					      		<button class="actionButton" id="shareButton" value="${ songsToShow.id }" >&#10609Share</button> --%> --%>
<!-- 								</form> -->
								
<!-- 								<div class="likeDiv"> -->
<%-- 									<c:if test="${ sessionUser.likedSongs[songsToShow.id] }"> --%>
<%-- 					        			<form method = POST action="unlikesong?song=${ songsToShow.id }"> --%>
<!-- 					       					<button class="actionButtonClicked" >&#10084Liked</button> -->
<!-- 					        			</form> -->
<%-- 									</c:if> --%>
<%-- 									<c:if test="${ !sessionUser.likedSongs[songsToShow.id] }"> --%>
<%-- 										<form method = POST action="likesong?song=${ songsToShow.id }"> --%>
<!-- 						      				<button class="actionButton" >&#10084Like</button> -->
<!-- 										</form> -->
<%-- 									</c:if>	 --%>
									
<%-- 									<c:if test="${ sessionUser.dislikedSongs[songsToShow.id] }"> --%>
<%-- 					        			<form method = POST action="undislikesong?song=${ songsToShow.id }"> --%>
<!-- 					       					<button class="actionButtonClicked" >&#128078Disliked</button> -->
<!-- 					      				</form> -->
<%-- 									</c:if> --%>
<%-- 									<c:if test="${ !sessionUser.dislikedSongs[songsToShow.id] }"> --%>
<%-- <%-- 									&?page=${requestScope['javax.servlet.forward.request_uri']} --%> --%>
<%-- 										<form method = POST action="dislikesong?song=${ songsToShow.id }"> --%>
<!-- 					      					<button class="actionButton" >&#128078Dislike</button> -->
<!-- 										</form> -->
<%-- 									</c:if>	 --%>
<!-- 									<form action=""> -->
<!--  					        			<button class="actionButton" >&#128172Comment</button> -->
<!-- 					        		</form> -->
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