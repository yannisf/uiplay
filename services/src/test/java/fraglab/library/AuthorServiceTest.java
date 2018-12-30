package fraglab.library;

import fraglab.library.valueobject.AuthorMapperService;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class AuthorServiceTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapperService authorMapperService = new AuthorMapperService();

    @BeforeMethod
    public void init() {
        initMocks(this);
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

    @Test
    public void reorderTest() {
        Author author = new Author();
        author.setId(0L);
        List<Book> books = new ArrayList<>();
        books.add(createTestBook(1L));
        books.add(createTestBook(2L));
        books.add(createTestBook(3L));
        author.setBooks(books);

        when(authorRepository.getById(eq(0L))).thenReturn(author);

        Long[] orderKey = {3L, 1L, 2L};
        authorService.reorderAuthorBooks(0L, orderKey);
        InOrder inOrder = inOrder(authorRepository);
        inOrder.verify(authorRepository, times(1)).getById(eq(0L));

        ArgumentCaptor<Author> authorCaptor = ArgumentCaptor.forClass(Author.class);
        inOrder.verify(authorRepository, times(1)).save(authorCaptor.capture());
        List<Book> reorderedBooks = authorCaptor.getValue().getBooks();

        assertThat(reorderedBooks).hasSize(3);
        for (int i = 0; i < orderKey.length; i++) {
            assertThat(reorderedBooks.get(i).getId()).isEqualTo(orderKey[i]);
        }
    }

    @Test
    public void reorderIllegalTest() {
        Author author = new Author();
        author.setId(0L);
        List<Book> books = new ArrayList<>();
        books.add(createTestBook(1L));
        books.add(createTestBook(2L));
        author.setBooks(books);
        when(authorRepository.getById(eq(0L))).thenReturn(author);
        Long[] orderKey = {3L, 1L, 2L};

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                authorService.reorderAuthorBooks(0L, orderKey)
        ).withMessage("Inconsistent reordering operation");
    }

    private Book createTestBook(Long id) {
        Book b = new Book();
        b.setId(id);
        b.setTitle("title" + id);
        return b;
    }

}