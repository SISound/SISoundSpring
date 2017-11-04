<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title> SISound </title>
		<link  rel="stylesheet" type="text/css" href="<c:url value="https://www.cratemayne.com/css/foundation.css" />">
		<link  rel="stylesheet" type="text/css" href="<c:url value="https://www.cratemayne.com/css/styles.css" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="css/playlist.css" />" />
		<script type="text/javascript" src=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js" />"></script>
	</head>
<body>

	<script src="<c:url value="js/playlist.js" />"  type ="text/javascript"></script>
	<jsp:include page="headerLogged.jsp"></jsp:include>
	
	
		<h3 class="heading-medium"> Playlist: ${ playlist.title }</h3>
		
		<c:if test="${fn:length(playlist.songs) eq 0}">
  				<h3 class="heading-medium"> No songs yet in the playlist</h3>
		</c:if>
		
		<c:if  test="${not empty playlist.songs}">
  				<section class="row top text-center">
					<div class="eight columns offset-by-two">
					   <div id="ap-mix" style="display:block;position:relative;width:100%;height:auto;margin:0px auto 0px;">
					        <ul class="ap-audios" style="display:none;">
					        
					        <c:set var="count" value="0" scope="page" />
		
					        <c:forEach var="entry" items="${ playlist.songs }">
								<c:set var="count" value="${count + 1}" scope="page"/>
					       		 <li data-artist="${ entry.value.user.username }" data-title="${ entry.value.title }" data-album="" data-info="&nbsp;${ count }." data-image="" data-duration="">
					            	 <div class="ap-source" data-src="getSong${ entry.value.url }" data-type="audio/mpeg" />
					          	</li>
							</c:forEach>
							
					        </ul>
					      </div>
				      </div>
				</section>
		</c:if>
		
		

</body>
</html>