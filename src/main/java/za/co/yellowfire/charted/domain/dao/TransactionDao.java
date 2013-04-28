package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;
import za.co.yellowfire.charted.domain.DataException;
import za.co.yellowfire.charted.domain.Transaction;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class TransactionDao extends BaseDao<Transaction> {

    public TransactionDao(String url, String user, String password) {
        super(url, user, password);
    }

    public Transaction findById(final String id) {
        return dbi.withHandle(new HandleCallback<Transaction>() {
            @Override
            public Transaction withHandle(Handle handle) throws Exception {
                TransactionQuery query = handle.attach(TransactionQuery.class);
                return query.findById(id);
            }
        });
    }

    public List<Transaction> findAll() {
        return dbi.withHandle(new HandleCallback<List<Transaction>>() {
            @Override
            public List<Transaction> withHandle(Handle handle) throws Exception {
                TransactionQuery query = handle.attach(TransactionQuery.class);
                return query.findAll();
            }
        });
    }

    public Transaction update(final Transaction transaction) {
        return dbi.withHandle(new HandleCallback<Transaction>() {
            @Override
            public Transaction withHandle(Handle handle) throws Exception {
                TransactionQuery query = handle.attach(TransactionQuery.class);
                return query.update(transaction);
            }
        });
    }

    public Transaction insert(final Transaction transaction) {
        return dbi.withHandle(new HandleCallback<Transaction>() {
            @Override
            public Transaction withHandle(Handle handle) throws Exception {
                TransactionQuery query = handle.attach(TransactionQuery.class);
                return query.insert(transaction);
            }
        });
    }

    public Transaction save(Transaction transaction) throws DataException {
        Transaction existing = null;
        if (transaction.getId() != null) {
            existing = findById(transaction.getId());
        }

        if (existing != null) {
            return update(transaction);
        } else {
            return insert(transaction);
        }
    }
}
