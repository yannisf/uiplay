package fraglab.library.it.context;

import org.springframework.mock.web.MockServletContext;
import org.testng.annotations.Test;

import javax.servlet.ServletContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ContextTest extends AbstractContextIntegrationTest {

    @Test
    public void contextTest() {
        ServletContext servletContext = wac.getServletContext();
        assertThat(servletContext).isNotNull();
        assertThat(servletContext).isInstanceOf(MockServletContext.class);
        assertThat(wac.getBean("authorResourceImpl")).isNotNull();
    }

}
