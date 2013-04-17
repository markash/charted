package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class TransactionMapper implements ResultSetMapper<Transaction> {

    public Transaction map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        BudgetCategory category = new BudgetCategory(r.getLong(TransactionQuery.budget_category));

        return new Transaction(
                        r.getString("id"),
                        r.getBigDecimal("amount"),
                        r.getDate("date_available"),
                        r.getDate("date_initiated"),
                        r.getDate("date_posted"),
                        r.getString("memo"),
                        category);
    }
}
