package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetSectionMapper implements ResultSetMapper<BudgetSection> {
    public BudgetSection map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new BudgetSection(
                r.getLong(BudgetSectionQuery.section_id),
                r.getString(BudgetSectionQuery.name),
                CashFlowDirection.valueOf(r.getString(BudgetSectionQuery.direction)),
                Color.valueOf(r.getString(BudgetSectionQuery.color)),
                Color.valueOf(r.getString(BudgetSectionQuery.color))
                );
    }
}
