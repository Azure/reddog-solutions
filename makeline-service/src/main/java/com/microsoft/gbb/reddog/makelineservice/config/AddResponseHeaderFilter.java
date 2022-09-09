package com.microsoft.gbb.reddog.makelineservice.config;

import io.opentelemetry.api.trace.Span;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Add response header filter to add OTEL headers to the response.
 */
@WebFilter("*")
public class AddResponseHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("x-trace-id", Span.current().getSpanContext().getTraceId());
        chain.doFilter(request, response);
    }
}