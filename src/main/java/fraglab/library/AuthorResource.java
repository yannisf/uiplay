package fraglab.library;

import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import fraglab.library.valueobject.PagedValue;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * <p>AuthorResource interface.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
public interface AuthorResource {

    /**
     * <p>findAllAuthors.</p>
     *
     * @return a {@link java.util.List} object.
     */
    List<AuthorValue> findAllAuthors();

    /**
     * <p>findByName.</p>
     *
     * @param query a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    List<AuthorValue> findByName(String query);

    /**
     * <p>pageAllAuthors.</p>
     *
     * @param pageNumber a {@link java.lang.Integer} object.
     * @param pageSize   a {@link java.lang.Integer} object.
     * @param sort       a {@link java.lang.String} object.
     * @param filter     a {@link java.lang.String} object.
     * @return a {@link fraglab.library.valueobject.PagedValue} object.
     */
    PagedValue<AuthorValue> pageAllAuthors(Integer pageNumber, Integer pageSize, String sort, String filter);

    /**
     * <p>findAuthor.</p>
     *
     * @param authorId a {@link java.lang.Long} object.
     * @return a {@link fraglab.library.valueobject.AuthorValue} object.
     */
    AuthorValue findAuthor(Long authorId);

    /**
     * <p>saveAuthor.</p>
     *
     * @param authorValue a {@link fraglab.library.valueobject.AuthorValue} object.
     * @param bindingResult
     * @return a {@link fraglab.library.valueobject.AuthorValue} object.
     */
    AuthorValue saveAuthor(AuthorValue authorValue, BindingResult bindingResult);

    /**
     * <p>deleteAuthor.</p>
     *
     * @param authorId a {@link java.lang.Long} object.
     */
    void deleteAuthor(Long authorId);

    /**
     * <p>saveBook.</p>
     *
     * @param authorId a {@link java.lang.Long} object.
     * @param bookValue a {@link fraglab.library.valueobject.BookValue} object.
     */
    void saveBook(Long authorId, BookValue bookValue);

    /**
     * <p>deleteBook.</p>
     *
     * @param authorId a {@link java.lang.Long} object.
     * @param bookId a {@link java.lang.Long} object.
     */
    void deleteBook(Long authorId, Long bookId);

    /**
     * <p>getAuthorBooks.</p>
     *
     * @param authorId a {@link java.lang.Long} object.
     * @return a {@link java.util.List} object.
     */
    List<BookValue> getAuthorBooks(Long authorId);

}
