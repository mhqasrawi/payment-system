package com.progressoft.jip.ui.web.accountforms;

import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.report.core.ReportGenerator;
import com.progressoft.jip.payment.report.core.ReportManager;
import com.progressoft.jip.payment.report.impl.ReportSettingsImpl;
import com.progressoft.jip.payment.transcription.EnglishTranscription;
import com.progressoft.jip.payment.transcription.Transcription;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;

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

    private LinkedList<Transcription> getSupportedTranscribers() {
        ServiceLoader<Transcription> load = ServiceLoader.load(Transcription.class);
        LinkedList<Transcription> transcribers = new LinkedList<>();
        Iterator<Transcription> it = load.iterator();
        while (it.hasNext()) {
            transcribers.add(it.next());
        }
        return transcribers;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("extensions", getSupportedExtensions());
        req.setAttribute("transcribers", getSupportedTranscribers());
        req.setAttribute("pageContent", "/WEB-INF/views/generate-report.jsp");
        req.getRequestDispatcher("/WEB-INF/views/base.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Exception> error = new HashMap<>();
        ReportSettingsImpl settings = new ReportSettingsImpl();
//        PaymentDAO paymentDao = implementationProvider.getImplementation(PaymentDAO.class);

//        settings.setPayments(paymentDao.getAll());

        try {
            settings.setFileExtention(req.getParameter("extension"));
            settings.setFileName(req.getParameter("file-name"));
            settings.setPath(Paths.get(req.getParameter("file-directory")));
            Class<? extends Transcription> clazz = (Class<? extends Transcription>) Class.forName(req.getParameter("transcriber"));
            settings.setTranscriberClass(clazz);
        } catch (ClassNotFoundException | InvalidPathException e) {
            error.put("Incorrect file path", e);
            req.setAttribute("error", error);
            RequestDispatcher disp = req.getRequestDispatcher("/WEB-INF/views/base.jsp");
            disp.forward(req, resp);
            return;
        }
        ReportManager manager = implementationProvider.getImplementation(ReportManager.class);
        manager.generateReport(settings);
        resp.sendRedirect("/generateReport");
    }


}
