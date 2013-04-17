package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetMapper implements ResultSetMapper<Budget> {
    public Budget map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Budget(
                r.getLong(BudgetQuery.budget_id),
                new Date(r.getDate(BudgetQuery.start_date).getTime()),
                new Date(r.getDate(BudgetQuery.end_date).getTime()),
                new Objective(r.getLong(BudgetQuery.objective_id)),
                new BudgetSection[0]);
    }
}
