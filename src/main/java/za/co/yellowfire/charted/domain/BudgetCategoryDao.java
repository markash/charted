package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;

import java.util.Date;
import java.util.List;

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
}
