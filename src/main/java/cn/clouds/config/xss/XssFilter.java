package cn.clouds.config.xss;

import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author clouds
 * @version 1.0
 */
@Configuration
public class XssFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        HttpServletResponse xssResponse = (HttpServletResponse) servletResponse;
        xssResponse.setHeader("X-XSS-Protection", "1; mode=block");
        xssResponse.setHeader("X-Frame-Options", "SAMEORIGIN");
        xssResponse.setHeader("Strict-Transport-Security", "max-age=31536; includeSubDomains");
        xssResponse.setHeader("Content-Security-Policy", "default-src 'self ");
        xssResponse.setHeader("X-Content-Type-Options", "nosniff");
        filterChain.doFilter(xssRequest, xssResponse);
    }

    @Override
    public void destroy() {

    }
}
