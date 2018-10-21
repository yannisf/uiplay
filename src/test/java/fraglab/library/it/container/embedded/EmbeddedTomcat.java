package fraglab.library.it.container.embedded;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class EmbeddedTomcat implements EmbeddedServer {

    private static final Logger LOG = LoggerFactory.getLogger(EmbeddedTomcat.class);

    private Tomcat tomcat;
    private static final String CONTEXT_PATH = "";

    @Override
    public String serverName() {
        return "TOMCAT";
    }

    @Override
    public void start() throws Exception {
        tomcat = new Tomcat();
        tomcat.setPort(PORT);
        String webAppDirPath = new File(WEBAPP_DIR).getAbsolutePath();
        StandardContext context = (StandardContext) tomcat.addWebapp(CONTEXT_PATH, webAppDirPath);
        StandardJarScanner jarScanner = (StandardJarScanner) context.getJarScanner();
        jarScanner.setScanManifest(false);
        tomcat.start();
    }

    @Override
    public void stop() throws Exception {
        tomcat.stop();
        Optional<Path> tomcatWorkDir = Files.list(FileSystems.getDefault().getPath("."))
                .filter(p -> p.toFile().getName().contains(String.format("tomcat.%s", PORT)))
                .findFirst();
        if (tomcatWorkDir.isPresent()) {
            LOG.info("Removing tomcat work directory [{}]", tomcatWorkDir.get());
            FileSystemUtils.deleteRecursively(tomcatWorkDir.get());
        }

    }

}
