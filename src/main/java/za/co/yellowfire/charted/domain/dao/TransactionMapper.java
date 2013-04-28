package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import za.co.yellowfire.charted.domain.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class TransactionMapper implements ResultSetMapper<Transaction> {

    public Transaction map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Transaction(
                        r.getString(TransactionQuery.transaction_id),
                        r.getBigDecimal(TransactionQuery.amount),
                        r.getDate(TransactionQuery.date_available),
                        r.getDate(TransactionQuery.date_initiated),
                        r.getDate(TransactionQuery.date_posted),
                        r.getString(TransactionQuery.memo));
    }
}
