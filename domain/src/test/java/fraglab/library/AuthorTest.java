package fraglab.library;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorTest {

    @Test
    public void testAddBook() {
        Author author = new Author();
        Book book = new Book();
        Author authorWithBook = author.addBook(book);

        assertThat(author.getBooks()).hasSize(1);
        assertThat(author).isSameAs(authorWithBook);
        assertThat(book.getAuthor()).isSameAs(author);
    }

}
