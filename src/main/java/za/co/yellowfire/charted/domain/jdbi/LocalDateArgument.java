package za.co.yellowfire.charted.domain.jdbi;

import org.joda.time.LocalDate;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class LocalDateArgument implements Argument {
    private final LocalDate date;

    public LocalDateArgument(LocalDate date) {
        this.date = date;
    }

    /**
     * Callback method invoked right before statement execution.
     *
     * @param position  the position to which the argument should be bound, using the
     *                  stupid JDBC "start at 1" bit
     * @param statement the prepared statement the argument is to be bound to
     * @param ctx
     * @throws java.sql.SQLException if anything goes wrong
     */
    @Override
    public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
        if (date == null) {
            statement.setNull(position, Types.DATE);
        } else {
            statement.setDate(position, new Date(date.toDate().getTime()));
        }
    }

    @Override
    public String toString() {
        return String.valueOf(date);
    }
}