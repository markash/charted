package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import za.co.yellowfire.charted.domain.*;
import za.co.yellowfire.charted.domain.dao.TransactionQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetAllocationMapper implements ResultSetMapper<BudgetAllocation> {
    private TransactionQuery query;

    public BudgetAllocationMapper() {}

    public BudgetAllocationMapper(TransactionQuery query) {
        this.query = query;
    }

    public BudgetAllocation map(int index, ResultSet r, StatementContext ctx) throws SQLException {


        Transaction tx =
                new Transaction(
                        r.getString("tx_id"),
                        r.getBigDecimal("tx_amount"),
                        r.getDate("tx_date_available"),
                        r.getDate("tx_date_initiated"),
                        r.getDate("tx_date_posted"),
                        r.getString("tx_memo"));

        BudgetCategory category =
                new BudgetCategory(
                        r.getLong("bc_id"),
                        r.getString("bc_name"),
                        CashFlowDirection.valueOf(r.getString("bc_direction")),
                        Color.valueOf(r.getString("bc_color")),
                        r.getBigDecimal("bc_budget_amount"),
                        null,
                        r.getString("bc_matches")
                );

        return new BudgetAllocation(r.getLong("ba_id"),category, tx, r.getBigDecimal("ba_amount"));
    }
}
