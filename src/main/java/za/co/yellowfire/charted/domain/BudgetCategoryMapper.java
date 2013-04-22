package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetCategoryMapper implements ResultSetMapper<BudgetCategory> {
    public BudgetCategory map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new BudgetCategory(
                r.getLong(BudgetCategoryQuery.category_id),
                r.getString(BudgetCategoryQuery.name),
                CashFlowDirection.valueOf(r.getString(BudgetCategoryQuery.direction)),
                Color.valueOf(r.getString(BudgetCategoryQuery.color)),
                r.getBigDecimal(BudgetCategoryQuery.budget_amount),
                new BudgetSection(r.getLong(BudgetCategoryQuery.section_id)),
                r.getString(BudgetCategoryQuery.matches)
                );
    }
}
