package fraglab.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorResource {

    @Autowired
    AuthorService authorService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public Author find(@PathVariable Long id) {
        return authorService.find(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Author save(@RequestBody Author author) {
        return authorService.save(author);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }

    @PostMapping(value = "/{authorId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addBook(@PathVariable Long authorId, @RequestBody Book book) {
        authorService.addBook(authorId, book);
    }

    @DeleteMapping(value = "/{authorId}/{bookId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBook(@PathVariable Long authorId, @PathVariable Long bookId) {
        authorService.deleteBook(authorId, bookId);
    }

    @GetMapping(value = "/{id}/books", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAuthorBooks(@PathVariable Long id) {
        return authorService.findWithBooks(id).getBooks();
    }

}
