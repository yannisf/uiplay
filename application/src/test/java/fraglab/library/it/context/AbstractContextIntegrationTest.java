package fraglab.library.it.context;

import fraglab.ApplicationConfiguration;
import fraglab.library.AuthorRepository;
import fraglab.library.AuthorService;
import fraglab.library.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@ContextConfiguration(classes = {ApplicationConfiguration.class})
@WebAppConfiguration
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
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
