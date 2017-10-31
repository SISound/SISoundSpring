<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="<c:url value="css/style.css"/>" />
	<title>SISound</title>
	
	<script type="text/javascript">
		
		function followUnfollow(value) {
			var button = document.getElementById("followButton");
			var title = button.innerHTML;
			var followed=value;
			
			if(title=="Follow"){
				followUser(followed);
			}
			else{
				unfollowUser(followed);
			}
		}
	
		function followUser(followed){
			var request = new XMLHttpRequest();
			var fwd=followed;
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById("followButton");
					button.innerHTML = "Unfollow";
					button.style.background='rgba(92,168,214,0.9)';
				}
				else
					if (this.readyState == 4 && this.status == 401) {
						alert("Sorry, you must log in to like this video!");
					}
			}
			request.open("post", "followUser?followed="+followed, true);
			request.send();
		}
		
		function unfollowUser(followed) {
			var request = new XMLHttpRequest();
			var fwd=followed;
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById("followButton");
					button.innerHTML = "Follow";
					button.style.background='white';
				}
				else
					if (this.readyState == 4 && this.status == 401) {
						alert("Sorry, you must log in to like this video!");
					}
			}
			request.open("post", "unfollowUser?followed="+followed, true);
			request.send();
		}
		
		function likeSong(value){
			var songId=value;
			var request=new XMLHttpRequest();
			request.onreadystatechange = function(){
				if(this.readyState ==4 && this.status == 200){
					var button = document.getElementById("likeButton");
					button.innerHTML="&#10084Liked";
					button.style.color='rgba(92,168,214,0.9)';
					
					var button=document.getElementById("dislikeButton");
					button.innerHTML="&#128078Dislike";
					button.style.color='#6D6D6F';
				}
				else{
					if(this.readyState == 4 && this.status == 401){
						alert("Sorry, you must log in to like this song!")
					}
				}
			}
			request.open("post", "likeSong?songId=" + songId, true);
			request.send();
		}
		
		function dislikeSong(value){
			var songId=value;
			var request=new XMLHttpRequest();
			request.onreadystatechange = function(){
				if(this.readyState ==4 && this.status == 200){
					var button = document.getElementById("dislikeButton");
					button.innerHTML="&#128078Disliked";
					button.style.color='rgba(92,168,214,0.9)';
					
					var button=document.getElementById("likeButton");
					button.innerHTML="&#10084Like";
					button.style.color='#6D6D6F';
				}
				else{
					if(this.readyState == 4 && this.status == 401){
						alert("Sorry, you must log in to like this song!")
					}
				}
			}
			request.open("post", "dislikeSong?songId=" + songId, true);
			request.send();
		}
		
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
				<td><a class="sortingLink" href="sortSongs/likes">Likes </a>|</td>
				<td><a class="sortingLink" href="sortSongs/date">UploadDate </a>|</td>
			</tr>
		</table>
		
		<table>
			<c:forEach items="${sortedSongs}" var="song">
				<tr>
					<nav class="archive-links">
					  <ol>
					    <li>
					      <div href="userProfile" class="${song.user.username}">
					      	<c:if test="${song.user.profilPicture != null}">
								<div class="main-image"><img src="${song.user.profilPicture}" alt="Camera" style="width:60px;height:60px;">
						          <img src="http://www.meetchaos.com/resources/images/camera.png" class="imtip" />
						        </div>
				 			</c:if>
					        <c:if test="${song.user.profilPicture == null}">
								<div class="main-image"><img src="img/defaultProfile.png" alt="Camera" style="width:60px;height:60px;">
						          <img src="http://www.meetchaos.com/resources/images/camera.png" class="imtip" />
						        </div>
				 			</c:if>
					        <a class="link-title" href="songPage"><c:out value="${ song.title }"></c:out></a>
					        <a class="link-excerpt" href="profile${song.user.username }"><c:out value="${ song.user.username }"></c:out></a>
					        <c:if test="${ song.user.username != sessionUser.username}">
					        		<button class="followButton" id="followButton" value="${ song.user.username }" onclick="followUnfollow(this.value)">Follow</button>
					        </c:if>
					        <button class="addToPlaylist" id="addingButton" value="${ song.id }" onclick="addToPlaylist(this.value)">&#8801 Add to playlist</button>
					        <button class="actionButton" id="shareButton" value="${ song.id }" onclick="shareSong(this.value)">&#10609Share</button>
					        <button class="actionButton" id="commentButton" value="${ song.id }" onclick="commentSong(this.value)">&#128172Comment</button>
					        <button class="actionButton" id="dislikeButton" value="${ song.id }" onclick="dislikeSong(this.value)">&#128078Dislike</button>
					        <button class="actionButton" id="likeButton" value="${ song.id }" onclick="likeSong(this.value)">&#10084Like</button>				
					      </div>
					      <audio controls class="myPlayer">
							  <source src="getSong${song.url}" type="audio/mpeg">
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