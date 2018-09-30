package fraglab.library.it.container;

import com.fasterxml.jackson.databind.ObjectMapper;
import fraglab.library.Author;
import fraglab.library.Book;
import fraglab.library.it.container.embedded.EmbeddedServer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.testng.annotations.Test;
import sun.reflect.generics.tree.VoidDescriptor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorRestApiTest extends AbstractRestIntegrationTest {

    private static final String API_BASE = String.format("http://localhost:%s/api", EmbeddedServer.PORT);
    private static final String AUTHOR = "author";
    private static final String AUTHOR_URL = String.format("%s/%s", API_BASE, AUTHOR);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testPost() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String authorName = getRandomString();
        Author author = new Author(authorName);
        String jsonAuthor = MAPPER.writeValueAsString(author);
        HttpEntity<String> entity = new HttpEntity<>(jsonAuthor, headers);
        ResponseEntity<Author> exchange = restTemplate.exchange(AUTHOR_URL, HttpMethod.POST, entity, Author.class);

        Author postAuthor = exchange.getBody();
        Long id = Objects.requireNonNull(postAuthor).getId();
        Author getAuthor = restTemplate.getForObject(String.format("%s/%s", AUTHOR_URL, id), Author.class);
        assertThat(Objects.requireNonNull(getAuthor).getName()).isEqualTo(authorName);

        String bookTitle = getRandomString();
        Book book = new Book(bookTitle);
        String jsonBook = MAPPER.writeValueAsString(book);
        entity = new HttpEntity<>(jsonBook, headers);
        restTemplate.exchange(String.format("%s/%s", AUTHOR_URL, id), HttpMethod.POST, entity, Void.class);

        ResponseEntity<List<Book>> response = restTemplate.exchange(
                String.format("%s/%s/books", AUTHOR_URL, id),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<Book>>(){});
        List<Book> books = response.getBody();

        assertThat(books).hasSize(1);
        assertThat(Objects.requireNonNull(books).get(0).getTitle()).isEqualTo(bookTitle);
    }

}
