<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Hello Spring MySQL</title>
</head>
<body>
	<h1>Hello Spring Glusterfs!</h1>
    <p>tenant : ${tenant}, id : ${id}, password : ${pass}, authurl : ${url}</p>
    <br>
	<h4>Glusterfs File Upload</h4>

	<h4>File</h4>
	<form action="swifttest" method="post" enctype="multipart/form-data">
	<input id="attchFile" name="attchFile" type="file"/>
	<input type="submit" value="upload"/>
	</form>
</body>
</html>
