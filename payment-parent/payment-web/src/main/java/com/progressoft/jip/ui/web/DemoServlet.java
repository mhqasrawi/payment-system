package com.progressoft.jip.ui.web;

import com.progressoft.jip.configuration.Configuration;
import com.progressoft.jip.dependency.ImplementationProvider;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/demo")
public class DemoServlet extends HttpServlet {

    private static final long serialVersionUID = 1039210706996087499L;
    private ImplementationProvider implementationProvider;

    @Override
    public void init(ServletConfig config) throws ServletException {
        implementationProvider = (ImplementationProvider) config.getServletContext()
                .getAttribute(ImplementationProvider.DEPENDENCY_PROVIDER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Configuration configuration = implementationProvider.getImplementation(Configuration.class);
        System.out.println("user name " + configuration.getProperty("db.username"));
        System.out.println("url " + configuration.getProperty("db.url"));
        req.getRequestDispatcher("/WEB-INF/jsp/sample.jsp").forward(req, resp);
    }
}
