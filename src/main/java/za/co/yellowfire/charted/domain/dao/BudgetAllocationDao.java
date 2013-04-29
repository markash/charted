package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;
import za.co.yellowfire.charted.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetAllocationDao extends BaseDao<BudgetAllocation> {

    public BudgetAllocationDao(String url, String user, String password) {
        super(url, user, password);
    }

    public List<BudgetAllocation> findAll() throws DataException {
        return dbi.withHandle(new HandleCallback<List<BudgetAllocation>>() {
            @Override
            public List<BudgetAllocation> withHandle(Handle handle) throws Exception {
                BudgetAllocationQuery query = handle.attach(BudgetAllocationQuery.class);
                TransactionQuery transactionQuery = handle.attach(TransactionQuery.class);
                BudgetCategoryQuery budgetCategoryQuery = handle.attach(BudgetCategoryQuery.class);

                List<BudgetAllocation> results = build(query.findAll(), transactionQuery, budgetCategoryQuery);
                return (results != null && results.size() > 0) ? results : new ArrayList<BudgetAllocation>(0);
            }
        });
    }

    public List<BudgetAllocation> build(final List<BudgetAllocation> budgetAllocations, final TransactionQuery transactionQuery, final BudgetCategoryQuery budgetCategoryQuery) {
        List<BudgetAllocation> results = new ArrayList<BudgetAllocation>(budgetAllocations.size());
        for (BudgetAllocation budgetAllocation : budgetAllocations) {
            results.add(build(budgetAllocation, transactionQuery, budgetCategoryQuery));
        }
        return results;
    }

    public BudgetAllocation build(final BudgetAllocation budgetAllocation, final TransactionQuery transactionQuery, final BudgetCategoryQuery budgetCategoryQuery) {

        Transaction transaction = transactionQuery.findById(budgetAllocation.getTransaction().getId());
        BudgetCategory category = budgetCategoryQuery.findById(budgetAllocation.getBudgetCategory().getId());

        return new BudgetAllocation(budgetAllocation.getId(), category, transaction, budgetAllocation.getAmount());
    }
}
