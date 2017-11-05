<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	
		<script type="text/javascript" src=" <c:url value="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" />"></script>
		<script type="text/javascript" src=" <c:url value="https://www.grandvincent-marion.fr/_codepen/jquery.fancybox.pack.js" />"></script>
		<script type="text/javascript" src=" <c:url value="https://www.grandvincent-marion.fr/_codepen/mediaelement-and-player.min.js" />"></script>
		<link rel="stylesheet" type="text/css" href="<c:url value="css/musicPlayer.css" />" />

		<link  rel="stylesheet" type="text/css" href="<c:url value="https://p.typekit.net/p.gif?s=1&k=yyw5dsj&app=typekit&ht=tk&h=www.flagstar.com&f=6846.6848.6849.6851.6852&a=1922000&sl=195&fl=122&js=1.14.8&_=1458594264430" />">
		<link  rel="stylesheet" type="text/css" href="<c:url value="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="css/profile.css" />" />
		
		<title>SISound</title>

		<script type="text/javascript">
	
		//LIKE AND UNLIKE A SONG
		function handleLike(){
			var button = document.getElementById("likeButton");
			var value=button.value;
			var title = button.innerHTML;
			if(title =="Like"){
				likeSong(value);
			}
			else{
				unlikeSong(value);
			}
		}
	
		function likeSong(value) {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById("likeButton");
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
		
		function unlikeSong(value) {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById("likeButton");
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
		
		//DISLIKE AND UNDISLIKE A SONG
		function handleDislike() {
			var button=document.getElementById("dislikeButton");
			var value=button.value;
			var title=button.innerHTML;
			if(title=="Dislike"){
				dislikeSong(value);
			}
			else{
				undislikeSong(value);
			}
		}
		
		function dislikeSong(value) {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById("dislikeButton");
					button.innerHTML = "Undislike";
					document.getElementById("dislikeCount").value++;
					if(document.getElementById("likeCount").value>0){
						document.getElementById("likeCount").value--;
					}
					var button=document.getElementById("likeButton");
					button.innerHTML="Like";
				}
				else
				if (this.readyState == 4 && this.status == 401) {
					alert("Sorry, you must log in to dislike this song!");
				}
					
			}
			request.open("post", "restDislikeSong?songId=" + value, true);
			request.send();
		}
		
		function undislikeSong(value){
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById("dislikeButton");
					button.innerHTML = "Dislike";
					if(document.getElementById("dislikeCount").value>0){
						document.getElementById("dislikeCount").value--;
					}
				}
				else
				if (this.readyState == 4 && this.status == 401) {
					alert("Sorry, you must log in to undislike this song!");
				}
					
			}
			request.open("post", "restUndislikeSong?songId=" + value, true);
			request.send();
		}
		
		//ADDING TO PLAYLIST
		function showDiv() {
		    var x = document.getElementById("plContainer");
		    alert("are");
		    if (x.style.display === "none") {
		        x.style.display = "block";
		    }
		}
		
		function closeDiv(){
			var x = document.getElementById("plContainer");
		    if (x.style.display === "block") {
		        x.style.display = "none";
		    }
		}
		
		function addToPlaylist(id, value){
			var playlistId=id;
			var songId=value;
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					var button = document.getElementById(playlistId);
					button.innerHTML = "Added";
				}
				else
				if (this.readyState == 4 && this.status == 401) {
					alert("Sorry, you must log in to add this song to playlist!");
				}
					
			}
			request.open("post", "restAddToPlaylist?playlistId=" + playlistId + "&songId=" + value, true);
			request.send();
		}
	</script>
	
	</head>
<body>
	<script src="<c:url value="js/musicPlayer.js" />"  type ="text/javascript"></script>
	<jsp:include page="headerLogged.jsp"></jsp:include>
	
	<br>
	<br>
	<br>
	<br>
	
<div class = profileASD>

	<div class="contain">
		<div class="container">
		
			<div class="music-player">
				<div class="cover">
<!-- 					<img src="https://www.grandvincent-marion.fr/_codepen/kygo.png" alt=""> -->
					
					<c:if test="${ modelUser.profilPicture == null}">
						<img class="avatar" src="<c:url value="img/defaultProfile.png"/>">					
					</c:if>
					<c:if test="${ modelUser.profilPicture != null}">
						<img class="avatar" alt="profilePic" src="getPic${ modelUser.username }">
					</c:if>
					
				</div>
				<div class="titre">
					<h3><c:out value="${ modelUser.username }"></c:out></h3><br>
					<h1><c:out value="${ commentable.title }"></c:out></h1>
				</div>
				<div class="lecteur">
					<audio style="width: 100%;" class="fc-media fc-audio"><source src="getSong${commentable.url}" type="audio/mp3"/></audio>
				</div>
				
			</div>
			
			
			<div class="banner">
			
			<div class="song-info">
				<h1 class="heading-medium"> <c:out value="${ commentable.title }"></c:out> </h1><br>
				<h3 class="heading-smallTP"> Genre: <c:out value="${ commentable.genre }"></c:out>  </h3><br>
				<h5 class="heading-smallTP"> Uploaded: <c:out value="${ commentable.uploadDateOnly }"></c:out> </h5><br>
				<table class="actionsTable">
					<tr>
						<c:if test="${ sessionUser.likedSongs[commentable.id] }">
							<td><button id="likeButton" value="${commentable.id }" onclick="handleLike()">Unlike</button></td>
						</c:if>
						<c:if test="${ !sessionUser.likedSongs[commentable.id] }">
							<td><button id="likeButton" value="${commentable.id }" onclick="handleLike()">Like</button></td>
						</c:if>
<%-- 						<button style="background-color: green" id="likebutton" value="${modelSong.id }" onclick="handleLike()">Like</button> --%>
						<td class="counterTd"><input id="likeCount" type="number" min="0" onkeydown="return false" value="${commentable.likesCount }"></input></td>
						<c:if test="${sessionUser.dislikedSongs[commentable.id]}">
							<td><button id="dislikeButton" value="${commentable.id }" onclick="handleDislike()">Undislike</button></td>
						</c:if>
						<c:if test="${!sessionUser.dislikedSongs[commentable.id]}">
							<td><button id="dislikeButton" value="${commentable.id }" onclick="handleDislike()">Dislike</button></td>
						</c:if>
						<td class="counterTd"><input id="dislikeCount" type="number" min="0" onkeydown="return false" value="${commentable.dislikesCount }"></input></td>
						
						<c:if test="${sessionUser!=null }">
							<td><button id="addButton" value="${commentable.id }" onclick="showDiv()">Add to playlist</button></td>
						</c:if>
					</tr>
				</table>
			</div>
			</div>
			
		</div>
	</div>
		<div id="plContainer" style="display: none;">
			<div id="playlistsDiv">
				<table >
					<button id="closeButton" onclick="closeDiv()">X</button>
				  	<c:forEach items="${sessionUser.playlists }" var="playlist">
				 		<tr>
				  			<td class="addToTd">
				  				<a class="playlistLink" href="playlist=${playlist.id }"><c:out value="${playlist.title }"></c:out></a>
				  			</td>
				  			<td>
				  				<button class="addToPlaylist" id="${playlist.id }" value="${commentable.id }" onclick="addToPlaylist(this.id, this.value)">Add to playlist</button>
				  			</td class="addToTd">
				  		</tr>
				  	</c:forEach>
				  	<tr>
				  		<td class="addToTd">
				  			<a href="createPlaylistPage?songId=${commentable.id }" class="createPlaylist">Create new playlist</button>
				  		</td>
				  	</tr>
			  	</table>
			</div>
		</div>
	
		<jsp:include page="comments.jsp"></jsp:include>
	
	</div>
</body>
</html>