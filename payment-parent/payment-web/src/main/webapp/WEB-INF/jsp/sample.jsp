<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page import="com.progressoft.jip.ui.web.FormDemo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Insert title here</title>
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
        <%
            FormDemo formDemo = new FormDemo();
            out.write(formDemo.getForm());
        %>

        <script type="text/javascript">
            $(document).ready(function () {
                $('form input').each(function (i, v) {
                    $(this).change(function () {
                        console.log(v);
                        console.log(v.name);
                        console.log(v.value);
                        var data = [];
                        data[v.name] = v.value;
                        console.log(data[v.name]);
                        $.ajax({
                            url: "./form-validation",
                            data: data,
                            dataType: 'json'
                        });
                    });
                });
            });
        </script>

    </body>
</html>