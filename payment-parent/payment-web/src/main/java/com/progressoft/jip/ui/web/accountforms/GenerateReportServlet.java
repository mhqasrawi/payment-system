package com.progressoft.jip.ui.web.accountforms;

import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.report.core.ReportGenerator;
import com.progressoft.jip.payment.report.core.ReportManager;
import com.progressoft.jip.payment.report.core.ReportSettingsSpi;
import com.progressoft.jip.payment.report.impl.ReportNodeProviderImpl;
import com.progressoft.jip.payment.report.impl.ReportSettingsImpl;
import com.progressoft.jip.payment.transcription.Transcription;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@WebServlet(urlPatterns = "/generateReport")
public class GenerateReportServlet extends HttpServlet {
    private ImplementationProvider implementationProvider;
    private boolean parametersValid = true;

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
        initAttributes(req);
        req.getRequestDispatcher("/WEB-INF/views/base.jsp").forward(req, resp);
    }

    private void initAttributes(HttpServletRequest req) {
        req.setAttribute("extensions", getSupportedExtensions());
        req.setAttribute("transcribers", getSupportedTranscribers());
        req.setAttribute("pageContent", "/WEB-INF/views/generate-report.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        parametersValid = true;
        ReportSettingsSpi settings = getReportSettings(req, resp);
        if (!parametersValid) {
            return;
        }
        ReportManager manager = implementationProvider.getImplementation(ReportManager.class);
        manager.generateReport(settings);
        resp.sendRedirect("/generateReport?success=true");
    }

    private ReportSettingsSpi getReportSettings(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReportSettingsImpl settings = new ReportSettingsImpl();
        String fileNameParam = req.getParameter("file-name");
        String extensionParam = req.getParameter("extension");
        String transcriberClassParam = req.getParameter("transcriber").split(" ")[1];
        String fileDirectoryParam = req.getParameter("file-directory");
        try {
            settings.setFileExtention(extensionParam);
            settings.setFileName(fileNameParam);
            Class<? extends Transcription> clazz = (Class<? extends Transcription>)
                    Class.forName(transcriberClassParam);
            settings.setPath(validatePath(req, resp, fileDirectoryParam));
            settings.setTranscriberClass(clazz);
            settings.setPayments(getPayments());
            settings.setReportNodeProviderClass(ReportNodeProviderImpl.class);
        } catch (ClassNotFoundException | InvalidPathException e) {
            redirectAndSetError(req, resp, e);
            return null;
        }
        return settings;
    }

    private Path validatePath(HttpServletRequest req, HttpServletResponse resp, String fileDirectoryParam) throws ServletException, IOException {
        Path path = Paths.get(fileDirectoryParam);
        if (!Files.exists(path)) {
            redirectAndSetError(req, resp, new IllegalArgumentException());
            return null;
        }
        return path;
    }

    private Iterable<PaymentDTO> getPayments() {
        //TODO replace by database payment records
        ReportTestCases cases = new ReportTestCases();
        return cases.getMockPayments();
    }

    private void redirectAndSetError(HttpServletRequest req, HttpServletResponse resp, Exception e) throws ServletException, IOException {
        parametersValid = false;
        req.setAttribute("error", "Incorrect file path");
        initAttributes(req);
        RequestDispatcher disp = req.getRequestDispatcher("/WEB-INF/views/base.jsp");
        disp.forward(req, resp);
    }
}