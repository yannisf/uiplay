package fraglab.library;

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

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@Transactional
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapperService authorMapperService;

    public Author find(Long id) {
        return authorRepository.findById(id).orElseThrow(IllegalStateException::new);
    }

    public AuthorValue findValue(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(IllegalStateException::new);
        return authorMapperService.toValue(author);
    }

    public PagedValue<AuthorValue> findPageValue(int page, int pageSize, String sort) {
        PageRequest pageable = createPageRequest(page, pageSize, sort);
        Page<Author> authorPage = authorRepository.findAll(pageable);
        List<AuthorValue> authorValues = authorPage.getContent().stream()
                .map(authorMapperService::toValue)
                .collect(toList());

        return PagedValue.of(authorPage.getTotalElements(), authorPage.getTotalPages(), authorValues);
    }

    private PageRequest createPageRequest(int page, int pageSize, String sort) {
        PageRequest pageable;
        if (StringUtils.equalsAnyIgnoreCase(sort, ASC.name(), DESC.name())) {
            pageable = PageRequest.of(page, pageSize, new Sort(Sort.Direction.fromString(sort.toUpperCase()), "name"));
        } else {
            pageable = PageRequest.of(page, pageSize);
        }
        return pageable;
    }

    @Cacheable({"authors"})
    public List<AuthorValue> findAllValues() {
        return authorRepository.findAll().stream()
                .map(authorMapperService::toValue)
                .collect(toList());
    }

    @CacheEvict(value = "authors", allEntries = true)
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @CacheEvict(value = "authors", allEntries = true)
    public void delete(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    public Author findWithBooks(Long id) {
        return authorRepository.getById(id);
    }

    public List<BookValue> findBooksValues(Long id) {
        return authorRepository.getById(id).getBooks().stream()
                .map(authorMapperService::toBookValue)
                .collect(toList());
    }

    public void addBook(Long authorId, Book book) {
        Author author = find(authorId);
        author.addBook(book);
    }

    public void addBookValue(Long authorId, BookValue bookValue) {
        Author author = find(authorId);
        if (bookValue.getId() != null) {
            Book book = author.getBooks().stream()
                    .filter(b -> b.getId().equals(bookValue.getId()))
                    .findFirst().orElseThrow(IllegalStateException::new);
            authorMapperService.toBookEntity(bookValue, book);
        } else {
            Book book = authorMapperService.toBookEntity(bookValue);
            author.addBook(book);
        }
    }

    public void deleteBook(Long authorId, Long bookId) {
        Author author = find(authorId);
        author.getBooks().removeIf(b -> b.getId().equals(bookId));
    }


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
}
