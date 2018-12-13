package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.util.ArrayList;
import java.util.List;

public class V4__assign_book_positions extends BaseJavaMigration {

    private static final String SELECT_AUTHOR_ID = "SELECT ID FROM AUTHOR";
    private static final String SELECT_BOOKS_FOR_AUTHOR_ID = "SELECT ID FROM BOOK WHERE AUTHOR_ID=? ORDER BY ID";
    private static final String UPDATE_BOOKS = "UPDATE BOOK SET POSITION=? WHERE ID=?";

    @Override
    public void migrate(Context context) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true));
        List<Long> authorIds = jdbcTemplate.queryForList(SELECT_AUTHOR_ID, Long.class);
        for (Long id : authorIds) {
            List<Long> bookIds = new ArrayList<>();
            jdbcTemplate.query(SELECT_BOOKS_FOR_AUTHOR_ID, resultSet -> { bookIds.add(resultSet.getLong(1)); }, id);
            for (int i = 0; i < bookIds.size(); i++) {
                jdbcTemplate.update(UPDATE_BOOKS, i, bookIds.get(i));
            }
        }
    }
}
