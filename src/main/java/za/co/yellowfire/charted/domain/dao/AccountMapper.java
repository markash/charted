package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import za.co.yellowfire.charted.domain.Account;
import za.co.yellowfire.charted.domain.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class AccountMapper implements ResultSetMapper<Account> {

    public Account map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Account(
                        r.getString(AccountQuery.account_nbr),
                        r.getString(AccountQuery.account_type),
                        r.getString(AccountQuery.bank_id),
                        r.getString(AccountQuery.branch_id),
                        r.getString(AccountQuery.account_key));
    }
}
