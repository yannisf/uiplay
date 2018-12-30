package fraglab.library;

import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import fraglab.library.valueobject.PagedValue;

import java.util.List;

public interface AuthorService {
    Author find(Long id);

    AuthorValue findValue(Long id);

    PagedValue<AuthorValue> findPageValue(int page, int pageSize, String sort, String filter);

    List<AuthorValue> findAllValues();

    Author save(Author author);

    void delete(Long authorId);

    Author findWithBooks(Long id);

    List<BookValue> findBooksValues(Long id);

    void addBook(Long authorId, Book book);

    BookValue addBookValue(Long authorId, BookValue bookValue);

    void deleteBook(Long authorId, Long bookId);

    AuthorValue saveValue(AuthorValue authorValue);

    List<AuthorValue> findTop10ByNameContainingIgnoreCase(String query);

    void reorderAuthorBooks(Long authorId, Long...bookIds);
}
