package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;
import za.co.yellowfire.charted.domain.BudgetCategory;
import za.co.yellowfire.charted.domain.BudgetSection;
import za.co.yellowfire.charted.domain.DataException;

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

//    public BudgetSection update(final BudgetSection category) {
//        return dbi.withHandle(new HandleCallback<BudgetSection>() {
//            @Override
//            public BudgetSection withHandle(Handle handle) throws Exception {
//                BudgetSectionQuery query = handle.attach(BudgetSectionQuery.class);
//                return query.update(category);
//            }
//        });
//    }

    public BudgetSection insert(final long budgetId, final BudgetSection section) {
        return dbi.withHandle(new HandleCallback<BudgetSection>() {
            @Override
            public BudgetSection withHandle(Handle handle) throws Exception {
                BudgetSectionQuery query = handle.attach(BudgetSectionQuery.class);
                return query.insert(budgetId, section);
            }
        });
    }

//    public BudgetSection save(BudgetSection section) throws DataException {
//        BudgetSection existing = null;
//        if (section.getId() != null) {
//            existing = findById(section.getId());
//        }
//
//        if (existing != null) {
//            return update(section);
//        } else {
//            return insert(section);
//        }
//    }
}
