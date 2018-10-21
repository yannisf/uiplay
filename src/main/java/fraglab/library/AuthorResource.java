package fraglab.library;

import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import fraglab.library.valueobject.PagedValue;

import java.util.List;

public interface AuthorResource {

    List<AuthorValue> findAllAuthors();

    List<AuthorValue> findByName(String query);

    PagedValue<AuthorValue> pageAllAuthors(Integer pageNumber, Integer pageSize, String sort, String filter);

    AuthorValue findAuthor(Long authorId);

    AuthorValue saveAuthor(AuthorValue authorValue);

    void deleteAuthor(Long authorId);

    void saveBook(Long authorId, BookValue bookValue);

    void deleteBook(Long authorId, Long bookId);

    List<BookValue> getAuthorBooks(Long authorId);

}
