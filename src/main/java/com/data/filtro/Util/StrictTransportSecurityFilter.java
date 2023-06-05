package com.data.filtro.Util;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class StrictTransportSecurityFilter implements Filter {
    private static final String STRICT_TRANSPORT_SECURITY_HEADER_NAME = "Strict-Transport-Security";
    private static final String STRICT_TRANSPORT_SECURITY_HEADER_VALUE = "max-age=31536000; includeSubDomains";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(STRICT_TRANSPORT_SECURITY_HEADER_NAME, STRICT_TRANSPORT_SECURITY_HEADER_VALUE);
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
