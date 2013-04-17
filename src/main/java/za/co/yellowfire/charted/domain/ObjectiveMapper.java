package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class ObjectiveMapper implements ResultSetMapper<Objective> {
    public Objective map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Objective(
                r.getLong(ObjectiveQuery.objective_id),
                r.getString(ObjectiveQuery.objective_name),
                r.getString(ObjectiveQuery.objective_expression));
    }
}
