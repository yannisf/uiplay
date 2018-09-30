package fraglab.library.it.container;

import fraglab.library.it.container.embedded.EmbeddedJetty;
import fraglab.library.it.container.embedded.EmbeddedServer;
import fraglab.library.it.container.embedded.EmbeddedTomcat;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.UUID;

public abstract class AbstractRestIntegrationTest {

    private static final EmbeddedServer EMBEDDED_SERVER = new EmbeddedTomcat();
    protected RestTemplate restTemplate = new RestTemplate();

    {
        restTemplate.setErrorHandler(new RestErrorHandler());
    }

    @BeforeClass
    public void startServer() throws Exception {
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
