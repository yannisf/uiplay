package fraglab.library.it.container.embedded;

import fraglab.ApplicationInitializer;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.ClassInheritanceHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.springframework.web.WebApplicationInitializer;

import java.util.concurrent.ConcurrentHashMap;

public class EmbeddedJetty implements EmbeddedServer {

    private Server server;

    @Override
    public String serverName() {
        return "JETTY";
    }

    @Override
    public void start() throws Exception {
        server = new Server(PORT);
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setResourceBase(WEBAPP_DIR);
        context.setParentLoaderPriority(true);
        context.setConfigurations(new Configuration[]{
                new WebXmlConfiguration(),
                new AnnotationConfiguration() {
                    @Override
                    public void preConfigure(WebAppContext context) {
                        ClassInheritanceMap map = new ClassInheritanceMap();
                        ConcurrentHashMap.KeySetView<String, Boolean> keySet = ConcurrentHashMap.newKeySet();
                        keySet.add(ApplicationInitializer.class.getName());
//                        keySet.add(SpringSecurityInitializer.class.getName());
                        map.put(WebApplicationInitializer.class.getName(), keySet);
                        context.setAttribute(CLASS_INHERITANCE_MAP, map);
                        _classInheritanceHandler = new ClassInheritanceHandler(map);
                    }
                }
        });
        server.setHandler(context);
        server.start();
    }

    @Override
    public void stop() throws Exception {
        if (server != null) {
            server.stop();
            server.join();
            server.destroy();
            server = null;
        }
    }

}