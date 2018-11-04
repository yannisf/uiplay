package fraglab.library;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * <p>Book class.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
@Entity
public class Book extends BaseEntity {

    private String title;

    @ManyToOne(optional = false)
    private Author author;

    /**
     * <p>Constructor for Book.</p>
     */
    public Book() {
    }

    /**
     * <p>Constructor for Book.</p>
     *
     * @param title a {@link java.lang.String} object.
     */
    public Book(String title) {
        this.title = title;
    }

    /**
     * <p>Getter for the field <code>title</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p>Setter for the field <code>title</code>.</p>
     *
     * @param title a {@link java.lang.String} object.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <p>Getter for the field <code>author</code>.</p>
     *
     * @return a {@link fraglab.library.Author} object.
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * <p>Setter for the field <code>author</code>.</p>
     *
     * @param author a {@link fraglab.library.Author} object.
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
