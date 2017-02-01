package com.progressoft.jip.ui.web;

import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.dependency.SpringImplementationProvider;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTOImpl;
import com.progressoft.jip.payment.purpose.dao.impl.PaymentPurposeDAO;
import com.progressoft.jip.payment.usecase.NewPaymentPurposeUseCase;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/new-payment-purpose")
public class NewPaymentPurposeServlet extends HttpServlet {
    private static final String APP_CONTEXT_LOCATION = "app.context.location";

    private ImplementationProvider implementationProvider;

    @Override
    public void init() throws ServletException {
        super.init();
        implementationProvider = (ImplementationProvider) getServletContext().getAttribute(ImplementationProvider.DEPENDENCY_PROVIDER);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageContent", "/WEB-INF/jsp/new-payment-purpose.jsp");
        req.getRequestDispatcher("/WEB-INF/views/base.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaymentPurposeDTOImpl paymentPurposeDTO = new PaymentPurposeDTOImpl();
        paymentPurposeDTO.setShortCode(req.getParameter("shortCode"));
        paymentPurposeDTO.setDescription(req.getParameter("description"));

        NewPaymentPurposeUseCase newPaymentPurposeUseCase = implementationProvider.getImplementation(NewPaymentPurposeUseCase.class);
        newPaymentPurposeUseCase.process(paymentPurposeDTO);
        resp.sendRedirect(req.getContextPath() + "/new-payment-purpose");
    }

}
