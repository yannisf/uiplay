package fraglab.library.it.container;

import fraglab.library.AuthorResource;
import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorRestApiTest extends AbstractRestIntegrationTest {

    private AuthorResource client = new AuthorResourceClient();

    @Test
    public void testPost() {
        String authorName = getRandomString();
        AuthorValue authorValue = new AuthorValue(authorName);
        AuthorValue postAuthor = client.saveAuthor(authorValue);
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

}
