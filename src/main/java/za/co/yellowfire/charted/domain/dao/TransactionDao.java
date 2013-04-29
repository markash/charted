package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;
import za.co.yellowfire.charted.domain.DataException;
import za.co.yellowfire.charted.domain.Transaction;

import java.util.ArrayList;
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
                BudgetAllocationQuery budgetAllocationQuery = handle.attach(BudgetAllocationQuery.class);
                return build(query.findById(id), budgetAllocationQuery);
            }
        });
    }

    public List<Transaction> findAll() {
        return dbi.withHandle(new HandleCallback<List<Transaction>>() {
            @Override
            public List<Transaction> withHandle(Handle handle) throws Exception {
                TransactionQuery query = handle.attach(TransactionQuery.class);
                BudgetAllocationQuery budgetAllocationQuery = handle.attach(BudgetAllocationQuery.class);
                return build(query.findAll(), budgetAllocationQuery);
            }
        });
    }

    public Transaction update(final Transaction transaction) {
        return dbi.withHandle(new HandleCallback<Transaction>() {
            @Override
            public Transaction withHandle(Handle handle) throws Exception {
                TransactionQuery query = handle.attach(TransactionQuery.class);
                BudgetAllocationQuery budgetAllocationQuery = handle.attach(BudgetAllocationQuery.class);
                return build(query.update(transaction), budgetAllocationQuery);
            }
        });
    }

    public Transaction insert(final Transaction transaction) {
        return dbi.withHandle(new HandleCallback<Transaction>() {
            @Override
            public Transaction withHandle(Handle handle) throws Exception {
                TransactionQuery query = handle.attach(TransactionQuery.class);
                BudgetAllocationQuery budgetAllocationQuery = handle.attach(BudgetAllocationQuery.class);
                return build(query.insert(transaction), budgetAllocationQuery);
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

    public List<Transaction> build(final List<Transaction> transactions, final BudgetAllocationQuery budgetAllocationQuery) {
        List<Transaction> results = new ArrayList<Transaction>(transactions.size());
        for (Transaction transaction : transactions) {
            results.add(build(transaction, budgetAllocationQuery));
        }
        return results;
    }

    public Transaction build(Transaction transaction, BudgetAllocationQuery budgetAllocationQuery) {
        transaction.setAllocated(budgetAllocationQuery.sumForTransaction(transaction.getId()));
        return transaction;
    }
}
