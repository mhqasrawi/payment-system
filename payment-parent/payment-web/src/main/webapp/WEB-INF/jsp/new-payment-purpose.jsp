<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Create New Payment Purpose</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="<c:url value="/static/bootstrap/css/bootstrap.min.css"/>"
	rel="stylesheet">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link href="<c:url value="/static/main.css"/>" rel="stylesheet">
<link rel="stylesheet"
	href="http://www.w3schools.com/lib/w3-theme-black.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
html, body, h1, h2, h3, h4, h5, h6 {
	font-family: "Roboto", sans-serif;
	color: DarkSlateBlue;
	
}

.w3-sidenav a, .w3-sidenav h4 {
	padding: 12px;
}

.w3-navbar li a {
	padding-top: 12px;
	padding-bottom: 12px;
}

label.padding2 {
	font-style: normal;
	font-weight: lighter;
	color: rgb(30, 205, 151);
}
</style>
</head>

<body>
	<div class="w3-top">
		<ul class="w3-navbar w3-theme w3-top w3-left-align w3-large">
			<li><a>Payment System</a></li>
		</ul>
	</div>
	<div class="container" style="margin-top: 40px">
		<h2>Create New Payment Purpose</h2>
		<form action="${request.getAttribute("
			javax.servlet.forward.request_uri")}" method="post">
			<div class="form-group">
				<label class="padding2">Short Code:</label> <input type="text"
					class="form-control" name="shortCode">
			</div>
			<div class="form-group">
				<label class="padding2">Description:</label> <input type="text"
					class="form-control" name="description">
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
	</div>

</body>
</html>
