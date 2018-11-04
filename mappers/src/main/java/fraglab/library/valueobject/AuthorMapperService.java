package fraglab.library.valueobject;

import com.github.dozermapper.core.Mapper;
import fraglab.library.Author;
import fraglab.library.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>AuthorMapperService class.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
@Service
public class AuthorMapperService {

    @Autowired
    private Mapper mapper;

    /**
     * <p>toValue.</p>
     *
     * @param author a {@link fraglab.library.Author} object.
     * @return a {@link fraglab.library.valueobject.AuthorValue} object.
     */
    public AuthorValue toValue(Author author) {
        return mapper.map(author, AuthorValue.class);
    }

    /**
     * <p>toEntity.</p>
     *
     * @param authorValue a {@link fraglab.library.valueobject.AuthorValue} object.
     * @return a {@link fraglab.library.Author} object.
     */
    public Author toEntity(AuthorValue authorValue) {
        return mapper.map(authorValue, Author.class);
    }

    /**
     * <p>toEntity.</p>
     *
     * @param authorValue a {@link fraglab.library.valueobject.AuthorValue} object.
     * @param entity      a {@link fraglab.library.Author} object.
     * @return a {@link fraglab.library.Author} object.
     */
    public Author toEntity(AuthorValue authorValue, Author entity) {
        mapper.map(authorValue, entity);
        return entity;
    }

    /**
     * <p>toBookEntity.</p>
     *
     * @param bookValue a {@link fraglab.library.valueobject.BookValue} object.
     * @return a {@link fraglab.library.Book} object.
     */
    public Book toBookEntity(BookValue bookValue) {
        return mapper.map(bookValue, Book.class);
    }

    /**
     * <p>toBookEntity.</p>
     *
     * @param bookValue a {@link fraglab.library.valueobject.BookValue} object.
     * @param book      a {@link fraglab.library.Book} object.
     * @return a {@link fraglab.library.Book} object.
     */
    public Book toBookEntity(BookValue bookValue, Book book) {
        mapper.map(bookValue, book);
        return book;
    }

    /**
     * <p>toBookValue.</p>
     *
     * @param book a {@link fraglab.library.Book} object.
     * @return a {@link fraglab.library.valueobject.BookValue} object.
     */
    public BookValue toBookValue(Book book) {
        return mapper.map(book, BookValue.class);
    }
}
