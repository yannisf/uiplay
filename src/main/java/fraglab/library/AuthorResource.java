package fraglab.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorResource {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorResource.class);

    @Autowired
    private AuthorService authorService;

    @GetMapping(produces = "application/json")
    public List<Author> findAll() {
        LOG.debug("Finding all Authors");
        return authorService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Author find(@PathVariable Long id) {
        LOG.debug("Finding Author[{}]", id);
        return authorService.find(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Author save(@RequestBody Author author) {
        LOG.debug("Saving Author[{}]", author);
        return authorService.save(author);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        LOG.debug("Deleting Author[{}]", id);
        authorService.delete(id);
    }

    @PostMapping(value = "/{authorId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addBook(@PathVariable Long authorId, @RequestBody Book book) {
        LOG.debug("Adding Book[{}] to Author[{}]", book, authorId);
        authorService.addBook(authorId, book);
    }

    @DeleteMapping(value = "/{authorId}/{bookId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBook(@PathVariable Long authorId, @PathVariable Long bookId) {
        LOG.debug("Removing Book[{}] to Author[{}]", bookId, authorId);
        authorService.deleteBook(authorId, bookId);
    }

    @GetMapping(value = "/{id}/books", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAuthorBooks(@PathVariable Long id) {
        LOG.debug("Finding books of Author[{}]", id);
        return authorService.findWithBooks(id).getBooks();
    }

}
