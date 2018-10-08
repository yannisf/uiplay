package fraglab.library;

import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

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

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public List<AuthorValue> findAllValues() {
        return authorRepository.findAll().stream()
                .map(authorMapperService::toValue)
                .collect(toList());
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public void delete(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    public Author findWithBooks(Long id) {
        return authorRepository.getById(id);
    }

    public AuthorValue findValueWithBooksValues(Long id) {
        return authorMapperService.toValue(authorRepository.getById(id));
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
