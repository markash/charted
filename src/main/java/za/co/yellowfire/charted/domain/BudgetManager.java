package za.co.yellowfire.charted.domain;

import org.apache.tapestry5.ioc.annotations.Inject;
import za.co.yellowfire.charted.domain.dao.BudgetCategoryDao;
import za.co.yellowfire.charted.domain.dao.BudgetDao;
import za.co.yellowfire.charted.domain.dao.BudgetSectionDao;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetManager {
    @Inject
    private BudgetDao budgetDao;

    @Inject
    private BudgetSectionDao sectionDao;

    @Inject
    private BudgetCategoryDao categoryDao;

    public List<BudgetSection> getBudgetSections(long budgetId) throws DataException {
        return sectionDao.findForBudgetId(budgetId);
    }
}
