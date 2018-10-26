package fraglab.library.valueobject;

import java.util.List;

/**
 * <p>PagedValue class.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
public final class PagedValue<T> {

    private List<T> values;
    private long totalElements;
    private int totalPages;

    /**
     * <p>of.</p>
     *
     * @param totalElements a long.
     * @param totalPages    a int.
     * @param values        a {@link java.util.List} object.
     * @param <T>           a T object.
     * @return a {@link fraglab.library.valueobject.PagedValue} object.
     */
    public static <T> PagedValue<T> of(long totalElements, int totalPages, List<T> values) {
        PagedValue<T> pagedValue = new PagedValue<>();
        pagedValue.totalElements = totalElements;
        pagedValue.totalPages = totalPages;
        pagedValue.values = values;
        return pagedValue;
    }

    /**
     * <p>Getter for the field <code>values</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<T> getValues() {
        return values;
    }

    /**
     * <p>Getter for the field <code>totalElements</code>.</p>
     *
     * @return a long.
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * <p>Getter for the field <code>totalPages</code>.</p>
     *
     * @return a int.
     */
    public int getTotalPages() {
        return totalPages;
    }
}
