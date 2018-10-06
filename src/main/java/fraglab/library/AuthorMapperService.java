package fraglab.library;

import com.github.dozermapper.core.Mapper;
import fraglab.library.valueobject.AuthorValue;
import fraglab.library.valueobject.BookValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorMapperService {

    @Autowired
    private Mapper mapper;

    public AuthorValue toValue(Author author) {
        return mapper.map(author, AuthorValue.class);
    }

    public Author toEntity(AuthorValue authorValue) {
        return mapper.map(authorValue, Author.class);
    }

    public Author toEntity(AuthorValue authorValue, Author entity) {
        mapper.map(authorValue, entity);
        return entity;
    }

    public Book toBookEntity(BookValue bookValue) {
        return mapper.map(bookValue, Book.class);
    }
}
