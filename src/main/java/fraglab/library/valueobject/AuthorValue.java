package fraglab.library.valueobject;

import java.util.List;
import java.util.Objects;

public class AuthorValue {
    private Long id;
    private String name;
    private List<BookValue> books;

    public AuthorValue() {
    }

    public AuthorValue(String name) {
        this.name = name;
    }

    public AuthorValue(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookValue> getBooks() {
        return books;
    }

    public void setBooks(List<BookValue> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorValue value = (AuthorValue) o;
        return Objects.equals(id, value.id) &&
                Objects.equals(name, value.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AuthorValue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
