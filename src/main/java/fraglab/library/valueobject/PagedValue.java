package fraglab.library.valueobject;

import java.util.List;

public final class PagedValue<T> {

    private List<T> values;
    private long totalElements;
    private int totalPages;

    public static <T> PagedValue<T> of(long totalElements, int totalPages, List<T> values) {
        PagedValue<T> pagedValue = new PagedValue<>();
        pagedValue.totalElements = totalElements;
        pagedValue.totalPages = totalPages;
        pagedValue.values = values;
        return pagedValue;
    }

    public List<T> getValues() {
        return values;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
