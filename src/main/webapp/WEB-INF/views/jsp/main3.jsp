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
		function myFunction() {
		    var x = document.getElementById("addToPlaylist");
		    if (x.style.display === "none") {
		        x.style.display = "block";
		    } else {
		        x.style.display = "none";
		    }
		}
		</script>
	</head>
	<body>
		<jsp:include page="headerLogged.jsp"></jsp:include>
		
		<table class="sortingTable">
			<tr>
				<td class="sortTd">Sort by: |</td>
				<td> <a class="sortingLink" href="index">Explore |</a> </td>
				<td> <a class="sortingLink" href="index=likes">Likes |</a> </td>
				<td> <a class="sortingLink" href="index=recent">UploadDate |</a> </td>
			</tr>
		</table>
		
<%-- 		<jsp:include page="main2.jsp"></jsp:include> --%>
		<jsp:include page="song_table.jsp"></jsp:include>
		
	</body>
</html>