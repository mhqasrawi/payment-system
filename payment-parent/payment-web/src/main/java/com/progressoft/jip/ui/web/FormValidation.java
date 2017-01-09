package com.progressoft.jip.ui.web;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FormValidation extends HttpServlet {

	private static final long serialVersionUID = 3083395460587763944L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader reader = req.getReader();
		String bodyContent = "";
		String line = null;
		while ((line = reader.readLine()) != null) {
			bodyContent =bodyContent.concat(line);
		}
		String[] parameters = bodyContent.split("&");
		if (parameters.length < 2)
			return;

		String propertyName = parameters[0];
		String propertyValue = parameters[1];
		System.out.println(String.format("Parameter Name %s   Value  %s", propertyName, propertyValue));
	}
}
