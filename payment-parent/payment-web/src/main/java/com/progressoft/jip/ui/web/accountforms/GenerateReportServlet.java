package com.progressoft.jip.ui.web.accountforms;

import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.report.core.ReportGenerator;
import com.progressoft.jip.payment.report.core.ReportManager;
import com.progressoft.jip.payment.report.impl.ReportSettingsImpl;
import com.progressoft.jip.payment.transcription.EnglishTranscription;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ServiceLoader;

@WebServlet(urlPatterns = "/generateReport")
public class GenerateReportServlet extends HttpServlet {
    private ImplementationProvider implementationProvider;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.implementationProvider = (ImplementationProvider) config.getServletContext()
                .getAttribute(ImplementationProvider.DEPENDENCY_PROVIDER);
    }

    private LinkedList<String> getSupportedExtensions() {
        ServiceLoader<ReportGenerator> load = ServiceLoader.load(ReportGenerator.class);
        LinkedList<String> extensions = new LinkedList<>();
        Iterator<ReportGenerator> it = load.iterator();
        while (it.hasNext()) {
            extensions.add(it.next().getSupportedFileExtension());
        }
        return extensions;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("extensions", getSupportedExtensions());
        req.setAttribute("pageContent", "/WEB-INF/views/generate-report.jsp");
        req.getRequestDispatcher("/WEB-INF/views/base.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
//		out.println("<h1>" + ((PaymentMenuContext) req.getSession().getAttribute("PAYMENT_MENU_CONTEXT")).getCurrentAccount().getAccountNumber() + "</h1>");
//		out.println("<h1>" +  + "</h1>");
        ReportSettingsImpl settings = new ReportSettingsImpl();
        PaymentDAO paymentDao = implementationProvider.getImplementation(PaymentDAO.class);

        settings.setPayments(paymentDao.getAll());
        settings.setFileExtention(req.getParameter("extension"));
        settings.setFileName("test");
        settings.setPath(Paths.get("C:/Users/u625/Desktop"));
        settings.setTranscriberClass(EnglishTranscription.class);
        ReportManager manager = implementationProvider.getImplementation(ReportManager.class);
        manager.generateReport(settings);
//		resp.sendRedirect("/generateReport");
    }


}
