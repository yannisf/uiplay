package fraglab.library.it.container.embedded;

import java.util.concurrent.ThreadLocalRandom;

public interface EmbeddedServer {

    Integer PORT = ThreadLocalRandom.current().nextInt(50000, 55000);

    String WEBAPP_DIR = "src/main/webapp/";

    String serverName();

    void start() throws Exception;

    void stop() throws Exception;

}
