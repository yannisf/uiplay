package fraglab.library.it.container;

import fraglab.library.it.container.embedded.EmbeddedServer;
import fraglab.library.it.container.embedded.EmbeddedTomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.UUID;

public abstract class AbstractRestIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractRestIntegrationTest.class);
    private static final EmbeddedServer EMBEDDED_SERVER = new EmbeddedTomcat();
    private RestTemplate restTemplate = new RestTemplate();

    {
        restTemplate.setErrorHandler(new RestErrorHandler());
    }

    @BeforeClass
    public void startServer() throws Exception {
        LOG.info("Embedded server: [{}]", EMBEDDED_SERVER.serverName());
        LOG.info("Embedded server port: [{}]", EMBEDDED_SERVER.PORT);
        EMBEDDED_SERVER.start();
    }

    @AfterClass
    public void stopServer() throws Exception {
        EMBEDDED_SERVER.stop();
    }

    String getRandomString() {
        return UUID.randomUUID().toString();
    }

}
