<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.progressoft.jip.actions.forms.NewAccountForm"%>
<%@ page import="com.progressoft.jip.ui.web.form.WebFormController"%>
<%@ page import="com.progressoft.jip.payment.account.AccountDTO"%>
<%@ page
	import="com.progressoft.jip.ui.web.form.parameter.StreamParameterParser"%>
<%@ page
	import="com.progressoft.jip.ui.web.form.manger.DynamicProxySingleWebFormManger"%>
<%@ page
	import="com.progressoft.jip.ui.webrendering.form.impl.FormHtmlRenderer"%>
<%@ page
	import="com.progressoft.jip.ui.webrendering.form.impl.FormHtmlRenderer"%>
<%@ page import="com.progressoft.jip.ui.webrendering.form.FormRenderer"%>
<%@ page import="com.progressoft.jip.ui.form.Form"%>
<%@ page import="com.progressoft.jip.dependency.ImplementationProvider"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.js"></script>
</head>
<body>
	<%
		StreamParameterParser parameterParser = new StreamParameterParser();
		NewAccountForm form = new NewAccountForm();
		DynamicProxySingleWebFormManger singleWebFormManger = new DynamicProxySingleWebFormManger(form);
		WebFormController<AccountDTO> webFormController = new WebFormController<>(parameterParser,
				singleWebFormManger);
		webFormController.attachForm(
				(ImplementationProvider) session.getServletContext().getAttribute("dependency-provider"), form);
		session.putValue("current_form_controller", webFormController);
		FormRenderer formRenderer = new FormHtmlRenderer();
		String formString = formRenderer.renderToHtml(form);
		out.write(formString);
	%>

	<script type="text/javascript">
		$(document).ready(function() {
			$('form input').each(function(i, v) {
				$(this).change(function() {
					console.log(v);
					console.log(v.name);
					console.log(v.value);
					var data = new Object();
					data[v.name] = v.value;
					$.post("./form-validation", data, function(returned_data) {
						var returnedMessage = $.parseJSON(returned_data);
						if('null'!=returnedMessage.message){
							$('[name="'+parameterValueTuple.parameter+'""]').							
						}
					});
				});
			});
		});
	</script>

</body>
</html>