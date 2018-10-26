package fraglab;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * <p>ApplicationInitializer class.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
public class ApplicationInitializer implements WebApplicationInitializer {

    private static final String API_CONTEXT = "/api/*";

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.setConfigLocation("fraglab");
        container.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        registration.setLoadOnStartup(1);
        registration.addMapping(API_CONTEXT);
    }

}
