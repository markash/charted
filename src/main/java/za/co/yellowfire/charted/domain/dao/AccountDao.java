package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;
import za.co.yellowfire.charted.domain.Account;
import za.co.yellowfire.charted.domain.DataException;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class AccountDao extends BaseDao<Account> {

    public AccountDao(String url, String user, String password) {
        super(url, user, password);
    }

    public Account findById(final String accountNumber) {
        return dbi.withHandle(new HandleCallback<Account>() {
            @Override
            public Account withHandle(Handle handle) throws Exception {
                AccountQuery query = handle.attach(AccountQuery.class);
                return query.findById(accountNumber);
            }
        });
    }

    public Account update(final Account account) {
        return dbi.withHandle(new HandleCallback<Account>() {
            @Override
            public Account withHandle(Handle handle) throws Exception {
                AccountQuery query = handle.attach(AccountQuery.class);
                return query.update(account);
            }
        });
    }

    public Account insert(final Account account) {
        return dbi.withHandle(new HandleCallback<Account>() {
            @Override
            public Account withHandle(Handle handle) throws Exception {
                AccountQuery query = handle.attach(AccountQuery.class);
                return query.insert(account);
            }
        });
    }

    public Account save(Account account) throws DataException {
        Account existing = null;
        if (account.getNumber() != null) {
            existing = findById(account.getNumber());
        }

        if (existing != null) {
            return update(account);
        } else {
            return insert(account);
        }
    }
}
