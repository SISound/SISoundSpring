<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>SISound</title>
		<link rel="stylesheet" type="text/css" href = "<c:url value="css/create_playlist.css" />" />
		<link rel="stylesheet" type="text/css" href = "//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" />
				
	</head>
	<body>
		<jsp:include page="headerLogged.jsp"></jsp:include>
		 	<div class="wrapper" >
		  	  <form class="form-signin" action = "createPlaylist${songId }" method = POST>       
			      <h2 class="form-signin-heading">Creating new playlist</h2>
			      <input type="text" class="form-control" name="title" placeholder="Playlist title" required="" autofocus="" />
			      <label class="checkbox">
		          	<input type="checkbox" name="private"> Private 
				  </label>
				  <button class="btn btn-lg btn-primary btn-block" type="submit">Create</button>   
			  </form>
			</div>
	</body>
</html>