package fraglab.library.valueobject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * <p>AuthorValue class.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
public class AuthorValue {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String name;

    /**
     * <p>Constructor for AuthorValue.</p>
     */
    public AuthorValue() {
    }

    /**
     * <p>Constructor for AuthorValue.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public AuthorValue(String name) {
        this.name = name;
    }

    /**
     * <p>Constructor for AuthorValue.</p>
     *
     * @param id   a {@link java.lang.Long} object.
     * @param name a {@link java.lang.String} object.
     */
    public AuthorValue(Long id, String name) {
        this.id = id;
        this.name = name;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorValue value = (AuthorValue) o;
        return Objects.equals(id, value.id) &&
                Objects.equals(name, value.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AuthorValue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
