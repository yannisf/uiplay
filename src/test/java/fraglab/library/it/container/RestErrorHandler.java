package fraglab.library.it.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestErrorHandler implements ResponseErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            LOG.error("Client error (4xx)");
            throw new RuntimeException("Client error");
        } else if (response.getStatusCode().is5xxServerError()) {
            LOG.error("Server error (5xx)");
            throw new RuntimeException("Server error");
        }
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

}