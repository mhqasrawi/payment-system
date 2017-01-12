<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>main page</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css"> -->
<!-- <link rel="stylesheet" -->
<!-- 	href="http://www.w3schools.com/lib/w3-theme-black.css"> -->
<!-- <link rel="stylesheet" -->
<!-- 	href="https://fonts.googleapis.com/css?family=Roboto"> -->
<!-- <link rel="stylesheet" -->
<!-- 	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
<link href="<c:url value="/static/bootstrap/css/bootstrap.min.css"/>"
	rel="stylesheet">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link href="<c:url value="/static/main.css"/>" rel="stylesheet">
<link rel="stylesheet"
	href="http://www.w3schools.com/lib/w3-theme-black.css">
<style>
.enjoy-css {
	-webkit-box-sizing: content-box;
	-moz-box-sizing: content-box;
	box-sizing: content-box;
	width: 300px;
	height: 70px;
	cursor: pointer;
	margin: 0 auto;
	border: 2px solid rgb(0, 230, 172);
	-webkit-border-radius: 30px;
	border-radius: 30px;
	padding-top: 16px;
	padding-bottom: 0px;
	font-family: Verdana, sans-serif;
	font-size: 20px;
	padding:10px;
	line-height: 1.5;
	color: rgb(0, 230, 172);
	text-align: center;
	-o-text-overflow: clip;
	text-overflow: clip;
	letter-spacing: 1px;
	background: rgba(0, 0, 0, 0);
	-webkit-transition: background-color 0.3s cubic-bezier(0, 0, 0, 0),
		color 0.3s cubic-bezier(0, 0, 0, 0), width 0.3s
		cubic-bezier(0, 0, 0, 0), border-width 0.3s cubic-bezier(0, 0, 0, 0),
		border-color 0.3s cubic-bezier(0, 0, 0, 0);
	-moz-transition: background-color 0.3s cubic-bezier(0, 0, 0, 0), color
		0.3s cubic-bezier(0, 0, 0, 0), width 0.3s cubic-bezier(0, 0, 0, 0),
		border-width 0.3s cubic-bezier(0, 0, 0, 0), border-color 0.3s
		cubic-bezier(0, 0, 0, 0);
	-o-transition: background-color 0.3s cubic-bezier(0, 0, 0, 0), color
		0.3s cubic-bezier(0, 0, 0, 0), width 0.3s cubic-bezier(0, 0, 0, 0),
		border-width 0.3s cubic-bezier(0, 0, 0, 0), border-color 0.3s
		cubic-bezier(0, 0, 0, 0);
	transition: background-color 0.3s cubic-bezier(0, 0, 0, 0), color 0.3s
		cubic-bezier(0, 0, 0, 0), width 0.3s cubic-bezier(0, 0, 0, 0),
		border-width 0.3s cubic-bezier(0, 0, 0, 0), border-color 0.3s
		cubic-bezier(0, 0, 0, 0);
}

html, body, h1, h2, h3, h4, h5, h6 {
	font-family: "Roboto", sans-serif
}

.w3-sidenav a, .w3-sidenav h4 {
	padding: 12px;
}

.w3-navbar li a {
	padding-top: 12px;
	padding-bottom: 12px;
}

 .enjoy-css:hover { 
 	color: rgba(255, 255, 255, 1); 
 	background: rgb(30, 205, 151); 
} 

.enjoy-css:active {
	border: 2px solid rgba(33, 224, 163, 1);
	background: rgba(33, 224, 163, 1);
	-webkit-transition: none;
	-moz-transition: none;
	-o-transition: none;
	transition: none;
}
</style>
</head>
<body>
	<div class="w3-top">
		<ul class="w3-navbar w3-theme w3-top w3-left-align w3-large">
			<li><a>Payment System</a></li>
		</ul>
	</div>
	<div style="color: green; max-width: 400px">
		<button type="button"
			onclick="window.location.href='/newAccount'"
			class="enjoy-css" style="margin-top: 60%; margin-left: 125%" >Create New
			Account</button>
	</div>
	<div  style="color: green; max-width: 400px">
		<button type="button"
			onclick="window.location.href='http://localhost:8080/pikup_account'"
			class="enjoy-css" style="margin-top: 30px;margin-left: 125%;  ">Choose Account</button>
	</div>
</body>
</html>