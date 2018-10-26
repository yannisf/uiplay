package fraglab.library;

import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import fraglab.library.valueobject.PagedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * <p>AuthorResourceImpl class.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
@RestController
@RequestMapping("/author")
public class AuthorResourceImpl implements AuthorResource {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorResourceImpl.class);

    @Autowired
    private AuthorService authorService;

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(produces = "application/json")
    public List<AuthorValue> findAllAuthors() {
        LOG.debug("Finding all Authors");
        return authorService.findAllValues();
    }

    /** {@inheritDoc} */
    @Override
    @GetMapping(value = "/search", produces = "application/json")
    public List<AuthorValue> findByName(@RequestParam(value = "q") String query) {
        LOG.debug("Finding Authors like [{}]", query);
        return authorService.findTop10ByNameContainingIgnoreCase(query);
    }

    /** {@inheritDoc} */
    @Override
    @GetMapping(value = "/page/{pageNumber}", produces = "application/json")
    public PagedValue<AuthorValue> pageAllAuthors(
            @PathVariable Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam (required = false) String sort,
            @RequestParam (required = false) String filter) {
        LOG.debug("Finding Authors page[{},{}]", pageNumber, sort);
        return authorService.findPageValue(pageNumber, Optional.ofNullable(pageSize).orElse(10), sort, filter);
    }

    /** {@inheritDoc} */
    @Override
    @GetMapping(value = "/{authorId}", produces = "application/json")
    public AuthorValue findAuthor(@PathVariable Long authorId) {
        LOG.debug("Finding Author[{}]", authorId);
        return authorService.findValue(authorId);
    }

    /** {@inheritDoc} */
    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public AuthorValue saveAuthor(@RequestBody AuthorValue authorValue) {
        LOG.debug("Saving Author[{}]", authorValue);
        return authorService.saveValue(authorValue);
    }

    /** {@inheritDoc} */
    @Override
    @DeleteMapping(value = "/{authorId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAuthor(@PathVariable Long authorId) {
        LOG.debug("Deleting Author[{}]", authorId);
        authorService.delete(authorId);
    }

    /** {@inheritDoc} */
    @Override
    @PostMapping(value = "/{authorId}/book", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void saveBook(@PathVariable Long authorId, @RequestBody BookValue bookValue) {
        LOG.debug("Adding Book[{}] to Author[{}]", bookValue, authorId);
        authorService.addBookValue(authorId, bookValue);
    }

    /** {@inheritDoc} */
    @Override
    @DeleteMapping(value = "/{authorId}/book/{bookId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBook(@PathVariable Long authorId, @PathVariable Long bookId) {
        LOG.debug("Removing Book[{}] to Author[{}]", bookId, authorId);
        authorService.deleteBook(authorId, bookId);
    }

    /** {@inheritDoc} */
    @Override
    @GetMapping(value = "/{authorId}/book", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookValue> getAuthorBooks(@PathVariable Long authorId) {
        LOG.debug("Finding books of Author[{}]", authorId);
        return authorService.findBooksValues(authorId);
    }

}
