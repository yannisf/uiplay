package fraglab.library;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * <p>Book class.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @JsonIgnore
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
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return a {@link java.lang.Long} object.
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id a {@link java.lang.Long} object.
     */
    public void setId(Long id) {
        this.id = id;
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
