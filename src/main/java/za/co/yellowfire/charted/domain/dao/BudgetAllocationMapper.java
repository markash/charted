package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import za.co.yellowfire.charted.domain.BudgetAllocation;
import za.co.yellowfire.charted.domain.BudgetCategory;
import za.co.yellowfire.charted.domain.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetAllocationMapper implements ResultSetMapper<BudgetAllocation> {
    public BudgetAllocation map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new BudgetAllocation(
                r.getLong(BudgetAllocationQuery.allocation_id),
                new BudgetCategory(r.getLong(BudgetAllocationQuery.budget_category_id)),
                new Transaction(r.getString(BudgetAllocationQuery.transaction_id)),
                r.getBigDecimal(BudgetAllocationQuery.amount));
    }
}
