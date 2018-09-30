package fraglab.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author find(Long id) {
        return authorRepository.findById(id).orElseThrow(IllegalStateException::new);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
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

    public void addBook(Long authorId, Book book) {
        Author author = find(authorId);
        author.addBook(book);
    }

    public void deleteBook(Long authorId, Long bookId) {
        Author author = find(authorId);
        author.getBooks().removeIf(b -> b.getId().equals(bookId));
    }
}
