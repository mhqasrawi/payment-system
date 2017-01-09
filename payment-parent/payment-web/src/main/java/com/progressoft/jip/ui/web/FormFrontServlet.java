package com.progressoft.jip.ui.web;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.progressoft.jip.actions.forms.NewAccountForm;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.web.form.FormError;
import com.progressoft.jip.ui.web.form.WebFormController;
import com.progressoft.jip.ui.web.form.manger.DynamicProxySingleWebFormManger;
import com.progressoft.jip.ui.web.form.parameter.StreamParameterParser;
import com.progressoft.jip.ui.webrendering.form.FormRenderer;
import com.progressoft.jip.ui.webrendering.form.impl.FormHtmlRenderer;

@WebServlet(urlPatterns = "/form-validation")
public class FormFrontServlet extends HttpServlet {

	private static final long serialVersionUID = -5154813441486624006L;

	private static final Logger logger = LoggerFactory.getLogger(FormFrontServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		WebFormController webFormController = (WebFormController) session.getAttribute("current_form_controller");
		if (Objects.isNull(webFormController)) {
			logger.error("Web Form Controller Equal Null , Req : " + req);
			return;
		}
		Iterable<FormError> errors = webFormController.process(req.getInputStream());
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(errors);
		System.out.println(json);
		resp.getWriter().println(json);
	}
	
}
