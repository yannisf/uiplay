package fraglab.library;

import fraglab.library.valueobject.AuthorMapperService;
import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import fraglab.library.valueobject.PagedValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * <p>AuthorService class.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorMapperService authorMapperService;

    /**
     * <p>find.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link fraglab.library.Author} object.
     */
    @Override
    public Author find(Long id) {
        return authorRepository.findById(id).orElseThrow(IllegalStateException::new);
    }

    /**
     * <p>findValue.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link fraglab.library.valueobject.AuthorValue} object.
     */
    @Override
    public AuthorValue findValue(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(IllegalStateException::new);
        return authorMapperService.toValue(author);
    }

    /**
     * <p>findPageValue.</p>
     *
     * @param page     a int.
     * @param pageSize a int.
     * @param sort     a {@link java.lang.String} object.
     * @param filter   a {@link java.lang.String} object.
     * @return a {@link fraglab.library.valueobject.PagedValue} object.
     */
    @Override
    public PagedValue<AuthorValue> findPageValue(int page, int pageSize, String sort, String filter) {
        PageRequest pageable = createPageRequest(page, pageSize, sort);
        Page<Author> authorPage;
        if (StringUtils.isNotBlank(filter)) {
            authorPage = authorRepository.findByNameContainingIgnoreCase(filter, pageable);
        } else {
            authorPage = authorRepository.findAll(pageable);
        }
        List<AuthorValue> authorValues = authorPage.getContent().stream()
                .map(authorMapperService::toValue)
                .collect(toList());

        return PagedValue.of(authorPage.getTotalElements(), authorPage.getTotalPages(), authorValues);
    }

    PageRequest createPageRequest(int page, int pageSize, String sort) {
        PageRequest pageable;
        if (StringUtils.equalsAnyIgnoreCase(sort, ASC.name(), DESC.name())) {
            pageable = PageRequest.of(page, pageSize, new Sort(Sort.Direction.fromString(sort.toUpperCase()), "name"));
        } else {
            pageable = PageRequest.of(page, pageSize);
        }
        return pageable;
    }

    /**
     * <p>findAllValues.</p>
     *
     * @return a {@link java.util.List} object.
     */
    @Override
    @Cacheable({"authors"})
    public List<AuthorValue> findAllValues() {
        return authorRepository.findAll().stream()
                .map(authorMapperService::toValue)
                .collect(toList());
    }

    /**
     * <p>save.</p>
     *
     * @param author a {@link fraglab.library.Author} object.
     * @return a {@link fraglab.library.Author} object.
     */
    @Override
    @CacheEvict(value = "authors", allEntries = true)
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    /**
     * <p>delete.</p>
     *
     * @param authorId a {@link java.lang.Long} object.
     */
    @Override
    @CacheEvict(value = "authors", allEntries = true)
    public void delete(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    /**
     * <p>findWithBooks.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link fraglab.library.Author} object.
     */
    @Override
    public Author findWithBooks(Long id) {
        return authorRepository.getById(id);
    }

    /**
     * <p>findBooksValues.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link java.util.List} object.
     */
    @Override
    public List<BookValue> findBooksValues(Long id) {
        return authorRepository.getById(id).getBooks().stream()
                .map(authorMapperService::toBookValue)
                .collect(toList());
    }

    /**
     * <p>addBook.</p>
     *
     * @param authorId a {@link java.lang.Long} object.
     * @param book     a {@link fraglab.library.Book} object.
     */
    @Override
    public void addBook(Long authorId, Book book) {
        Author author = find(authorId);
        author.addBook(book);
    }

    /**
     * <p>addBookValue.</p>
     *
     * @param authorId  a {@link java.lang.Long} object.
     * @param bookValue a {@link fraglab.library.valueobject.BookValue} object.
     */
    @Override
    public BookValue addBookValue(Long authorId, BookValue bookValue) {
        Author author = find(authorId);
        Book book = null;
        if (bookValue.getId() != null) {
            book = author.getBooks().stream()
                    .filter(b -> b.getId().equals(bookValue.getId()))
                    .findFirst().orElseThrow(IllegalStateException::new);
            authorMapperService.toBookEntity(bookValue, book);
        } else {
            book = authorMapperService.toBookEntity(bookValue);
            book.setAuthor(author);
            book = bookRepository.save(book);
            author.addBook(book);
        }
        return authorMapperService.toBookValue(book);
    }

    /**
     * <p>deleteBook.</p>
     *
     * @param authorId a {@link java.lang.Long} object.
     * @param bookId   a {@link java.lang.Long} object.
     */
    @Override
    public void deleteBook(Long authorId, Long bookId) {
        Author author = find(authorId);
        author.getBooks().removeIf(b -> b.getId().equals(bookId));
    }


    /**
     * <p>saveValue.</p>
     *
     * @param authorValue a {@link fraglab.library.valueobject.AuthorValue} object.
     * @return a {@link fraglab.library.valueobject.AuthorValue} object.
     */
    @Override
    @CacheEvict(value = "authors", allEntries = true)
    public AuthorValue saveValue(AuthorValue authorValue) {
        Author author;
        if (authorValue.getId() != null) {
            author = find(authorValue.getId());
            author = authorMapperService.toEntity(authorValue, author);
        } else {
            author = authorMapperService.toEntity(authorValue);
        }
        author = save(author);
        return authorMapperService.toValue(author);
    }

    /**
     * <p>findTop10ByNameContainingIgnoreCase.</p>
     *
     * @param query a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    @Override
    public List<AuthorValue> findTop10ByNameContainingIgnoreCase(String query) {
        if (StringUtils.isBlank(query)) {
            return Collections.emptyList();
        } else {
            return authorRepository.findTop10ByNameContainingIgnoreCase(query).stream()
                    .map(authorMapperService::toValue)
                    .collect(toList());
        }
    }

}
