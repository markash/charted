package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;
import za.co.yellowfire.charted.domain.BudgetSection;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetSectionDao extends BaseDao<BudgetSection> {

    public BudgetSectionDao(String url, String user, String password) {
        super(url, user, password);
    }

    public BudgetSection findById(final Long id) {
        return dbi.withHandle(new HandleCallback<BudgetSection>() {
            @Override
            public BudgetSection withHandle(Handle handle) throws Exception {
                BudgetSectionQuery query = handle.attach(BudgetSectionQuery.class);
                return query.findById(id);
            }
        });
    }

    public List<BudgetSection> findForBudgetId(final Long id) {
        return dbi.withHandle(new HandleCallback<List<BudgetSection>>() {
            @Override
            public List<BudgetSection> withHandle(Handle handle) throws Exception {
                BudgetSectionQuery query = handle.attach(BudgetSectionQuery.class);
                return query.findForBudgetId(id);
            }
        });
    }
}
