package fraglab.library.valueobject;

import java.util.Objects;

/**
 * <p>BookValue class.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
public class BookValue {
    private Long id;
    private String title;

    /**
     * <p>Constructor for BookValue.</p>
     */
    public BookValue() {
    }

    /**
     * <p>Constructor for BookValue.</p>
     *
     * @param title a {@link java.lang.String} object.
     */
    public BookValue(String title) {
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
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookValue bookValue = (BookValue) o;
        return Objects.equals(id, bookValue.id) &&
                Objects.equals(title, bookValue.title);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "BookValue{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}

