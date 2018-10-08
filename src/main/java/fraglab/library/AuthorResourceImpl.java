package fraglab.library;

import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorResourceImpl implements AuthorResource {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorResourceImpl.class);

    @Autowired
    private AuthorService authorService;

    @Override
    @GetMapping(produces = "application/json")
    public List<AuthorValue> findAllAuthors() {
        LOG.debug("Finding all Authors");
        return authorService.findAllValues();
    }

    @Override
    @GetMapping(value = "/{authorId}", produces = "application/json")
    public AuthorValue findAuthor(@PathVariable Long authorId) {
        LOG.debug("Finding Author[{}]", authorId);
        return authorService.findValue(authorId);
    }

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public AuthorValue saveAuthor(@RequestBody AuthorValue authorValue) {
        LOG.debug("Saving Author[{}]", authorValue);
        return authorService.saveValue(authorValue);
    }

    @Override
    @DeleteMapping(value = "/{authorId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAuthor(@PathVariable Long authorId) {
        LOG.debug("Deleting Author[{}]", authorId);
        authorService.delete(authorId);
    }

    @Override
    @PostMapping(value = "/{authorId}/book", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void saveBook(@PathVariable Long authorId, @RequestBody BookValue bookValue) {
        LOG.debug("Adding Book[{}] to Author[{}]", bookValue, authorId);
        authorService.addBookValue(authorId, bookValue);
    }

    @Override
    @DeleteMapping(value = "/{authorId}/book/{bookId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBook(@PathVariable Long authorId, @PathVariable Long bookId) {
        LOG.debug("Removing Book[{}] to Author[{}]", bookId, authorId);
        authorService.deleteBook(authorId, bookId);
    }

    @Override
    @GetMapping(value = "/{authorId}/book", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookValue> getAuthorBooks(@PathVariable Long authorId) {
        LOG.debug("Finding books of Author[{}]", authorId);
        return authorService.findValueWithBooksValues(authorId).getBooks();
    }

}
