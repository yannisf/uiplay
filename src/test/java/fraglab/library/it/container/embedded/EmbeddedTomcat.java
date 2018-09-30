package fraglab.library.it.container.embedded;

import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class EmbeddedTomcat implements EmbeddedServer {

    private Tomcat tomcat;
    private static final String CONTEXT_PATH = "";
    private static final String WEBAPP_DIR = "src/main/webapp/";

    @Override
    public void start() throws Exception {
        tomcat = new Tomcat();
        tomcat.setPort(PORT);
        String webAppDirPath = new File(WEBAPP_DIR).getAbsolutePath();
        tomcat.addWebapp(CONTEXT_PATH, webAppDirPath);
        tomcat.start();
    }

    @Override
    public void stop() throws Exception {
        tomcat.stop();
    }

}
