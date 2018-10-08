package fraglab.library.it.container;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fraglab.library.Author;
import fraglab.library.Book;
import fraglab.library.it.container.embedded.EmbeddedServer;
import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class AuthorRestApiTest extends AbstractRestIntegrationTest {

    private static final String API_BASE = String.format("http://localhost:%s/api", EmbeddedServer.PORT);
    private static final String AUTHOR = "author";
    private static final String AUTHOR_URL = String.format("%s/%s", API_BASE, AUTHOR);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testPost() throws IOException {
        String authorName = getRandomString();
        HttpEntity<String> authorEntity = createAuthorEntityFromName(authorName);
        AuthorValue postAuthor = postAuthor(authorEntity);
        Long authorId = postAuthor.getId();
        AuthorValue getAuthor = getAuthor(authorId);
        assertThat(getAuthor.getName()).isEqualTo(authorName);
        String bookTitle = getRandomString();
        HttpEntity<String> bookEntity = createBookEntityFromTitle(bookTitle);
        postBook(bookEntity, authorId);
        List<BookValue> books = getBooks(authorId);
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo(bookTitle);
        deleteBook(authorId, books.get(0).getId());
        List<BookValue> bookValues = getBooks(authorId);
        assertThat(bookValues).isEmpty();
        deleteAuthor(authorId);
        List<AuthorValue> authorValues = getAllAuthors();
        assertThat(authorValues).isEmpty();
    }

    private HttpEntity<String> createAuthorEntityFromName(String authorName) throws JsonProcessingException {
        AuthorValue authorValue = new AuthorValue(authorName);
        String jsonAuthor = MAPPER.writeValueAsString(authorValue);
        return new HttpEntity<>(jsonAuthor, basicHeaders());
    }

    private HttpEntity<String> createBookEntityFromTitle(String bookTitle) throws JsonProcessingException {
        Book book = new Book(bookTitle);
        String jsonBook = MAPPER.writeValueAsString(book);
        return new HttpEntity<>(jsonBook, basicHeaders());
    }

    private List<AuthorValue> getAllAuthors() {
        String format = String.format("%s", AUTHOR_URL);
        return restTemplate.exchange(format, GET, justHeadersEntity(), listAuthorValue()).getBody();
    }

    private List<BookValue> getBooks(Long authorId) {
        String url = String.format("%s/%s/book", AUTHOR_URL, authorId);
        return restTemplate.exchange(url, GET, justHeadersEntity(), listBookValue()).getBody();
    }

    private void postBook(HttpEntity<String> entity, Long id) {
        String format = String.format("%s/%s/book", AUTHOR_URL, id);
        restTemplate.exchange(format, POST, entity, Void.class);
    }

    private AuthorValue getAuthor(Long authorId) {
        String url = String.format("%s/%s", AUTHOR_URL, authorId);
        return restTemplate.getForObject(url, AuthorValue.class);
    }

    private AuthorValue postAuthor(HttpEntity<String> entity) {
        return restTemplate.exchange(AUTHOR_URL, POST, entity, AuthorValue.class).getBody();
    }

    private void deleteAuthor(Long id) {
        String url = String.format("%s/%s", AUTHOR_URL, id);
        restTemplate.exchange(url, DELETE, justHeadersEntity(), Void.class);
    }

    private void deleteBook(Long authorId, Long bookId) {
        String format = String.format("%s/%s/%s", AUTHOR_URL, authorId, bookId);
        restTemplate.exchange(format, DELETE, justHeadersEntity(), Void.class);
    }

    private HttpHeaders basicHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        return headers;
    }

    private HttpEntity<Object> justHeadersEntity() {
        return new HttpEntity<>(basicHeaders());
    }


    private ParameterizedTypeReference<List<AuthorValue>> listAuthorValue() {
        return new ParameterizedTypeReference<List<AuthorValue>>() {
        };
    }

    private ParameterizedTypeReference<List<BookValue>> listBookValue() {
        return new ParameterizedTypeReference<List<BookValue>>() {
        };
    }
}
