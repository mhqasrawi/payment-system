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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">
		<h2>Create New Payment Purpose</h2>
		<form action="${request.getAttribute("
			javax.servlet.forward.request_uri")}" method="post">
			<div class="form-group">
				<label>Short Code:</label> <input type="text" class="form-control"
					name="shortCode">
			</div>
			<div class="form-group">
				<label>Description:</label> <input type="text" class="form-control"
					name="description">
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
	</div>

</body>
</html>
