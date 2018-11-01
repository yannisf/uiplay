package fraglab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", initParams = @WebInitParam(name = "appContext", value = "/app"))
public class RedirectFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(RedirectFilter.class);
    private String appContext;

    @Override
    public void init(FilterConfig filterConfig) {
        appContext = filterConfig.getInitParameter("appContext");
        LOG.info("Initialized HTML5 pushstate redirect filter using as UI application context [{}]", appContext);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (httpServletRequest.getRequestURI().startsWith(appContext)) {
            LOG.debug("Redirecting to application router");
            httpServletRequest.getRequestDispatcher("/index.html").forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
