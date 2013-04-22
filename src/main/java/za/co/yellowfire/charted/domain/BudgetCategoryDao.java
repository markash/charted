package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetCategoryDao extends BaseDao<BudgetCategory> {

    public BudgetCategoryDao(String url, String user, String password) {
        super(url, user, password);
    }

    @Override
    public BudgetCategory findById(final Long id) {
        return dbi.withHandle(new HandleCallback<BudgetCategory>() {
            @Override
            public BudgetCategory withHandle(Handle handle) throws Exception {
                BudgetCategoryQuery query = handle.attach(BudgetCategoryQuery.class);
                return query.findById(id);
            }
        });
    }

    public BudgetCategory update(final BudgetCategory category) {
        return dbi.withHandle(new HandleCallback<BudgetCategory>() {
            @Override
            public BudgetCategory withHandle(Handle handle) throws Exception {
                BudgetCategoryQuery query = handle.attach(BudgetCategoryQuery.class);
                return query.update(category);
            }
        });
    }

    public BudgetCategory insert(final BudgetCategory category) {
        return dbi.withHandle(new HandleCallback<BudgetCategory>() {
            @Override
            public BudgetCategory withHandle(Handle handle) throws Exception {
                BudgetCategoryQuery query = handle.attach(BudgetCategoryQuery.class);
                return query.findById(query.insert(category).longValue());
            }
        });
    }

    public BudgetCategory save(BudgetCategory category) throws DataException {
        BudgetCategory existing = null;
        if (category.getId() != null) {
            existing = findById(category.getId());
        }

        if (existing != null) {
            return update(category);
        } else {
            return insert(category);
        }
    }
}
