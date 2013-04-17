package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;

import java.util.Date;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetDao extends BaseDao<Budget> {

    public BudgetDao(String url, String user, String password) {
        super(url, user, password);
    }

    @Override
    public Budget findById(final Long id) {
        return dbi.withHandle(new HandleCallback<Budget>() {
            @Override
            public Budget withHandle(Handle handle) throws Exception {
                BudgetQuery query = handle.attach(BudgetQuery.class);
                return buildBudget(query.findById(id), handle);
            }
        });
    }

    public Budget findForDate(final Date date) {
        return dbi.withHandle(new HandleCallback<Budget>() {
            @Override
            public Budget withHandle(Handle handle) throws Exception {
                BudgetQuery query = handle.attach(BudgetQuery.class);
                return buildBudget(query.findForDate(date), handle);
            }
        });
    }

    public Budget buildBudget(final Budget budget, final Handle handle) {

        /* Resolve objective */
        ObjectiveQuery objectiveQuery = handle.attach(ObjectiveQuery.class);
        budget.setObjective(objectiveQuery.findById(budget.getObjective().getId()));

        /* Resolve sections */
        BudgetSectionQuery sectionQuery = handle.attach(BudgetSectionQuery.class);
        List<BudgetSection> sections = sectionQuery.findForBudgetId(budget.getId());

        /* Resolve categories */
        BudgetCategoryQuery categoryQuery = handle.attach(BudgetCategoryQuery.class);
        for (BudgetSection budgetSection : sections) {
            budgetSection.setCategories(categoryQuery.findForSectionId(budgetSection.getId()));
        }
        budget.setSections(sections);

        return budget;
    }
}
