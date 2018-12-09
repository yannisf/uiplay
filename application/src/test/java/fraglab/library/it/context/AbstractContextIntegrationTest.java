package fraglab.library.it.context;

import fraglab.library.AuthorRepository;
import fraglab.library.AuthorService;
import fraglab.library.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractContextIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected WebApplicationContext wac;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected AuthorService authorService;

    @Autowired
    protected AuthorRepository authorRepository;

    @Autowired
    protected BookRepository bookRepository;

    @BeforeClass
    public void beforeClass() {
    }

    @AfterClass
    public void afterClass() {
    }

    String getRandomString() {
        return UUID.randomUUID().toString();
    }

}
