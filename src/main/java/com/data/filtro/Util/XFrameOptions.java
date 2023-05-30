package com.data.filtro.Util;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class XFrameOptions implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        response.setHeader("Content-Security-Policy", "script-src 'self' https://code.jquery.com " +
                "https://kit.fontawesome.com " +
                "https://unpkg.com " + "https://www.google.com/recaptcha/ " + "https://www.gstatic.com/recaptcha/ " +
                "https://cdn.jsdelivr.net ; " +
                "frame-src 'self' https://www.google.com/ ;"
                + "style-src 'self' https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css 'unsafe-inline' " +
                "https://fonts.googleapis.com/ 'unsafe-inline';");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Expect-CT", "max-age=86400");
//        String newContentDisposition = "attachment; filename=f.txt";
//        response.setHeader("content-disposition", newContentDisposition);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void destroy() {
    }
}
