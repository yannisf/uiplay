package fraglab.library;

import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import fraglab.library.valueobject.PagedValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AuthorResource {

    @GetMapping(produces = "application/json")
    List<AuthorValue> findAllAuthors();

    @GetMapping(value = "/page/{pageNumber}", produces = "application/json")
    PagedValue<AuthorValue> pageAllAuthors(
            @PathVariable int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String sort);

    @GetMapping(value = "/{authorId}", produces = "application/json")
    AuthorValue findAuthor(@PathVariable Long authorId);

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    AuthorValue saveAuthor(@RequestBody AuthorValue authorValue);

    @DeleteMapping(value = "/{authorId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void deleteAuthor(@PathVariable Long authorId);

    @PostMapping(value = "/{authorId}/book", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void saveBook(@PathVariable Long authorId, @RequestBody BookValue bookValue);

    @DeleteMapping(value = "/{authorId}/book/{bookId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void deleteBook(@PathVariable Long authorId, @PathVariable Long bookId);

    @GetMapping(value = "/{authorId}/book", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    List<BookValue> getAuthorBooks(@PathVariable Long authorId);

}
