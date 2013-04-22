package za.co.yellowfire.charted.domain.jdbi;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class LocalDateArgument implements Argument {
    private final DateTime dateTime;

    public LocalDateArgument(DateTime dateTime) {
        this.dateTime = dateTime;
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
        if (dateTime == null) {
            statement.setNull(position, Types.TIMESTAMP);
        } else {
            statement.setTimestamp(position, new Timestamp(dateTime.getMillis()));
        }
    }

    @Override
    public String toString() {
        return String.valueOf(dateTime);
    }
}