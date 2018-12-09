package fraglab.library.it.container;

import fraglab.library.AuthorResource;
import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import fraglab.library.valueobject.PagedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthorRestApiTest extends AbstractTestNGSpringContextTests {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorRestApiTest.class);
    private AuthorResource client = new AuthorResourceClient();

    @Test
    public void addRemoveAuthorAndBooks() {
        String authorName = getRandomString();
        AuthorValue authorValue = new AuthorValue(authorName);
        AuthorValue postAuthor = client.saveAuthor(authorValue, null);
        Long authorId = postAuthor.getId();
        AuthorValue getAuthor = client.findAuthor(authorId);
        assertThat(getAuthor.getName()).isEqualTo(authorName);

        String book1Title = getRandomString();
        BookValue book1 = new BookValue(book1Title);
        client.saveBook(authorId, book1);
        List<BookValue> books = client.getAuthorBooks(authorId);
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo(book1Title);

        String book2Title = getRandomString();
        BookValue book2 = new BookValue(book2Title);
        client.saveBook(authorId, book2);
        books = client.getAuthorBooks(authorId);
        assertThat(books).hasSize(2);
        assertThat(books.get(1).getTitle()).isEqualTo(book2Title);

        client.deleteBook(authorId, books.get(0).getId());
        books = client.getAuthorBooks(authorId);
        assertThat(books).hasSize(1);

        String book2UpdatedTitle = getRandomString();
        book2 = books.get(0);
        book2.setTitle(book2UpdatedTitle);
        client.saveBook(authorId, book2);

        books = client.getAuthorBooks(authorId);
        assertThat(books.get(0).getTitle()).isEqualTo(book2UpdatedTitle);

        client.deleteAuthor(authorId);
        List<AuthorValue> authorValues = client.findAllAuthors();
        assertThat(authorValues).isEmpty();
    }

    @Test
    public void pageFilterSortAndQuery() {
        client.saveAuthor(new AuthorValue("aa"), null);
        client.saveAuthor(new AuthorValue("bb"), null);
        client.saveAuthor(new AuthorValue("cc"), null);
        client.saveAuthor(new AuthorValue("dd"), null);
        client.saveAuthor(new AuthorValue("ee"), null);
        client.saveAuthor(new AuthorValue("aaa"), null);

        PagedValue<AuthorValue> authorsPage = client.pageAllAuthors(0, null, null, null);
        assertThat(authorsPage.getTotalElements()).isEqualTo(6);
        assertThat(authorsPage.getTotalPages()).isEqualTo(1);
        assertThat(authorsPage.getValues()).hasSize(6);

        authorsPage = client.pageAllAuthors(0, 3, null, null);
        assertThat(authorsPage.getTotalElements()).isEqualTo(6);
        assertThat(authorsPage.getTotalPages()).isEqualTo(2);
        assertThat(authorsPage.getValues()).hasSize(3);
        assertThat(getAuthorNames(authorsPage)).containsExactlyInAnyOrder("aa", "bb", "cc");

        authorsPage = client.pageAllAuthors(1, 3, null, null);
        assertThat(authorsPage.getTotalElements()).isEqualTo(6);
        assertThat(authorsPage.getTotalPages()).isEqualTo(2);
        assertThat(authorsPage.getValues()).hasSize(3);
        assertThat(getAuthorNames(authorsPage)).containsExactlyInAnyOrder("dd", "ee", "aaa");

        authorsPage = client.pageAllAuthors(0, null, null, "a");
        assertThat(authorsPage.getTotalElements()).isEqualTo(2);
        assertThat(authorsPage.getTotalPages()).isEqualTo(1);
        assertThat(authorsPage.getValues()).hasSize(2);
        assertThat(getAuthorNames(authorsPage)).containsExactlyInAnyOrder("aa", "aaa");

        authorsPage = client.pageAllAuthors(0, null, "ASC", null);
        assertThat(getAuthorNames(authorsPage)).containsExactly("aa", "aaa", "bb", "cc", "dd", "ee");

        authorsPage = client.pageAllAuthors(0, null, "DESC", null);
        assertThat(getAuthorNames(authorsPage)).containsExactly("ee", "dd", "cc", "bb", "aaa", "aa");

        List<AuthorValue> authors = client.findByName("a");
        assertThat(authors).hasSize(2);
        assertThat(getAuthorNames(authors)).containsExactlyInAnyOrder("aa", "aaa");

        authors = client.findByName("b");
        assertThat(authors).hasSize(1);
        assertThat(getAuthorNames(authors)).containsExactlyInAnyOrder("bb");

        authors = client.findByName("z");
        assertThat(authors).isEmpty();

        authors = client.findByName(null);
        assertThat(authors).isEmpty();
    }

    private List<String> getAuthorNames(PagedValue<AuthorValue> authors) {
        return getAuthorNames(authors.getValues());
    }

    private List<String> getAuthorNames(List<AuthorValue> authors) {
        return authors.stream()
                .map(AuthorValue::getName)
                .collect(toList());
    }

    private String getRandomString() {
        return UUID.randomUUID().toString();
    }


}
