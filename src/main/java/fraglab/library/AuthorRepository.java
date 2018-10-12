package fraglab.library;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @EntityGraph(attributePaths = {"books"})
    Author getById(Long id);

    List<Author> findTop10ByNameContainingIgnoreCase(String query);

    Page<Author> findByNameContainingIgnoreCase(String query, Pageable pageable);
}
