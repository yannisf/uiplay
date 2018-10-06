package fraglab.library.valueobject;

import java.util.Objects;

public class BookValue {
    private Long id;
    private String title;

    public BookValue() {
    }

    public BookValue(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookValue bookValue = (BookValue) o;
        return Objects.equals(id, bookValue.id) &&
                Objects.equals(title, bookValue.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "BookValue{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}

