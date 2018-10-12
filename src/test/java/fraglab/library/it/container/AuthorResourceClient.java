package fraglab.library.it.container;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fraglab.library.AuthorResource;
import fraglab.library.it.container.embedded.EmbeddedServer;
import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import fraglab.library.valueobject.PagedValue;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class AuthorResourceClient implements AuthorResource {

    private RestTemplate restTemplate = new RestTemplate();

    private static final String API_BASE = String.format("http://localhost:%s/api", EmbeddedServer.PORT);
    private static final String AUTHOR_URL = String.format("%s/author", API_BASE);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final ParameterizedTypeReference<List<BookValue>> LIST_BOOK_VALUE =
            new ParameterizedTypeReference<List<BookValue>>() {
            };
    private static final ParameterizedTypeReference<List<AuthorValue>> LIST_AUTHOR_VALUE =
            new ParameterizedTypeReference<List<AuthorValue>>() {
            };

    @Override
    public List<AuthorValue> findAllAuthors() {
        String url = String.format("%s", AUTHOR_URL);
        return restTemplate.exchange(url, GET, justHeadersEmptyEntity(), LIST_AUTHOR_VALUE).getBody();
    }

    @Override
    public List<AuthorValue> findByName(String query) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagedValue<AuthorValue> pageAllAuthors(int pageNumber, int pageSize, String sort, String filter) {
        //TODO: Needs update implementation
        String url = String.format("%s/page/%s", AUTHOR_URL, pageNumber);
        return restTemplate.exchange(url, GET, justHeadersEmptyEntity(), PagedValue.class).getBody();
    }

    @Override
    public AuthorValue findAuthor(Long authorId) {
        String url = String.format("%s/%s", AUTHOR_URL, authorId);
        return restTemplate.getForObject(url, AuthorValue.class);
    }

    @Override
    public AuthorValue saveAuthor(AuthorValue author) {
        String authorJson = getAsJson(author);
        HttpEntity<String> authorEntity = new HttpEntity<>(authorJson, justHeaders());
        return restTemplate.exchange(AUTHOR_URL, POST, authorEntity, AuthorValue.class).getBody();

    }

    @Override
    public void deleteAuthor(Long authorId) {
        String url = String.format("%s/%s", AUTHOR_URL, authorId);
        restTemplate.exchange(url, DELETE, justHeadersEmptyEntity(), Void.class);
    }

    @Override
    public void saveBook(Long authorId, BookValue book) {
        String bookJson = getAsJson(book);
        HttpEntity<String> bookEntity = new HttpEntity<>(bookJson, justHeaders());
        String url = String.format("%s/%s/book", AUTHOR_URL, authorId);
        restTemplate.exchange(url, POST, bookEntity, Void.class);
    }

    @Override
    public void deleteBook(Long authorId, Long bookId) {
        String url = String.format("%s/%s/book/%s", AUTHOR_URL, authorId, bookId);
        restTemplate.exchange(url, DELETE, justHeadersEmptyEntity(), Void.class);
    }

    @Override
    public List<BookValue> getAuthorBooks(Long authorId) {
        String url = String.format("%s/%s/book", AUTHOR_URL, authorId);
        return restTemplate.exchange(url, GET, justHeadersEmptyEntity(), LIST_BOOK_VALUE).getBody();
    }

    private HttpHeaders justHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        return headers;
    }

    private HttpEntity<Object> justHeadersEmptyEntity() {
        return new HttpEntity<>(justHeaders());
    }

    private String getAsJson(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
