package fraglab.library;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>AuthorRepository interface.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * <p>getById.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link fraglab.library.Author} object.
     */
    @EntityGraph(attributePaths = {"books"})
    Author getById(Long id);

    /**
     * <p>findTop10ByNameContainingIgnoreCase.</p>
     *
     * @param query a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    List<Author> findTop10ByNameContainingIgnoreCase(String query);

    /**
     * <p>findByNameContainingIgnoreCase.</p>
     *
     * @param query    a {@link java.lang.String} object.
     * @param pageable a {@link org.springframework.data.domain.Pageable} object.
     * @return a {@link org.springframework.data.domain.Page} object.
     */
    Page<Author> findByNameContainingIgnoreCase(String query, Pageable pageable);
}
