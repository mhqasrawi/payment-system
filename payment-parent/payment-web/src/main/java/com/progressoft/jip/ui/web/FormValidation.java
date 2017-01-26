package com.progressoft.jip.ui.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


public class FormValidation extends HttpServlet {

    private static final long serialVersionUID = 3083395460587763944L;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        String bodyContent = "";
        String line;
        while ((line = reader.readLine()) != null) {
            bodyContent = bodyContent.concat(line);
        }
        String[] parameters = bodyContent.split("&");
        if (parameters.length < 2)
            return;
    }
}
