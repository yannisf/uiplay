package fraglab.library;

import fraglab.library.valueobject.AuthorMapperService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthorServiceTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapperService authorMapperService = new AuthorMapperService();

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void afterMethod() {
        this.authorService = null;
        this.authorRepository = null;
        this.authorMapperService = null;
    }

    @DataProvider(name = "pageRequestParameter")
    public Object[][] pageRequestParameterDataProvider() {
        return new Object[][]{
                {0, 10, null},
                {1, 5, "ASC"},
                {1, 5, "DESC"},
        };
    }

    @Test(dataProvider = "pageRequestParameter")
    public void createPageRequest(int page, int pageSize, String sort) {
        PageRequest pageRequest = authorService.createPageRequest(page, pageSize, sort);
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getPageNumber()).isEqualTo(page);
        assertThat(pageRequest.getPageSize()).isEqualTo(pageSize);
        Sort sortObject = sort == null ? Sort.unsorted() : new Sort(Sort.Direction.fromString(sort), "name");
        assertThat(pageRequest.getSort()).isEqualTo(sortObject);
    }

}