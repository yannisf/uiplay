package fraglab.library;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Author class.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
@Entity
public class Author extends BaseEntity {

    private String name;

    private String yearOfBirth;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @OrderColumn(name = "POSITION")
    private List<Book> books;

    /**
     * <p>Constructor for Author.</p>
     */
    public Author() {
    }

    /**
     * <p>Constructor for Author.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public Author(String name) {
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * <p>Getter for the field <code>books</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * <p>Setter for the field <code>books</code>.</p>
     *
     * @param books a {@link java.util.List} object.
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * <p>addBook.</p>
     *
     * @param book a {@link fraglab.library.Book} object.
     * @return a {@link fraglab.library.Author} object.
     */
    public Author addBook(Book book) {
        if (this.books == null) {
            this.books = new ArrayList<>();
        }
        this.books.add(book);
        book.setAuthor(this);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
