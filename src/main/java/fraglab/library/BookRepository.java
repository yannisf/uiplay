package fraglab.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>BookRepository interface.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
