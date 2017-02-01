package com.progressoft.jip.session;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by u612 on 1/31/2017.
 */
@WebFilter(urlPatterns = "/*")
public class SesssionValidationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        if ("/pikup_account".equalsIgnoreCase(requestURI) || "/paymentSystem".equalsIgnoreCase(requestURI) ||"/".equalsIgnoreCase(requestURI)) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = req.getSession(false);
        if (Objects.isNull(session)) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect(req.getContextPath() + "/pikup_account");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
