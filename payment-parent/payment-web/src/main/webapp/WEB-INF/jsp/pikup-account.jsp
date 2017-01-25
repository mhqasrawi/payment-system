<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Pikup Account</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<form action="pikup_account" method="POST">

        <div class="form-group" style="margin-left: 30%; margin-top:10%;">
            <fieldset>
                <legend>Choose Account</legend>
            <label for="account-number"><b>Account Number</b></label>
            <input type="text" class="form-control" id="account-number" placeholder="Account Number"
                   name="account-number" style="width: 200px;"/>
<br/><br/>
            <button type="submit" class="btn btn-primary">Submit</button>
            </fieldset>

        </div>
</form>
</body>
</html>