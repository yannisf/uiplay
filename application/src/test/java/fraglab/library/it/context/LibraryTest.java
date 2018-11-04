package fraglab.library.it.context;

import fraglab.library.Author;
import fraglab.library.Book;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class LibraryTest extends AbstractContextIntegrationTest {

    @Test
    public void authorCrud() {
        String authorName = getRandomString();
        Author author = new Author(authorName);
        Author savedAuthor = authorService.save(author);
        Author fetchedAuthor = authorService.find(savedAuthor.getId());
        assertThat(fetchedAuthor.getName()).isEqualTo(authorName);

        String updatedAuthorName = getRandomString();
        fetchedAuthor.setName(updatedAuthorName);
        Author updatedAuthor = authorService.save(fetchedAuthor);
        Author fetchedUpdatedAuthor = authorService.find(updatedAuthor.getId());
        assertThat(fetchedUpdatedAuthor.getName()).isEqualTo(updatedAuthorName);

        authorService.delete(updatedAuthor.getId());
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> authorService.find(updatedAuthor.getId()));
    }

    @Test
    public void bookCrud() {
        String authorName = getRandomString();
        Author author = new Author(authorName);
        author = authorService.save(author);

        String title1 = getRandomString();
        Book book1 = new Book(title1);
        author.addBook(book1);
        author = authorService.save(author);

        assertThat(author.getBooks()).hasSize(1);
        assertThat(author.getBooks().get(0).getTitle()).isEqualTo(title1);

        Long book1Id = author.getBooks().get(0).getId();
        book1 = bookRepository.findById(book1Id).orElseThrow(IllegalStateException::new);
        assertThat(book1.getTitle()).isEqualTo(title1);

        String title2 = getRandomString();
        Book book2 = new Book(title2);
        authorService.addBook(author.getId(), book2);
        author = authorRepository.getById(author.getId());

        assertThat(author.getBooks()).hasSize(2);
        assertThat(author.getBooks().get(1).getTitle()).isEqualTo(title2);

        authorService.deleteBook(author.getId(), author.getBooks().get(0).getId());
        author = authorService.findWithBooks(author.getId());
        assertThat(author.getBooks()).hasSize(1);
        assertThat(author.getBooks().get(0).getTitle()).isEqualTo(title2);

        String updatedTitle = getRandomString();
        author.getBooks().get(0).setTitle(updatedTitle);
        author = authorService.save(author);

        author = authorRepository.getById(author.getId());
        Book book = author.getBooks().get(0);
        assertThat(book.getTitle()).isEqualTo(updatedTitle);

        authorService.delete(author.getId());
        assertThat(bookRepository.findById(book.getId()).isPresent()).isFalse();
    }

}
