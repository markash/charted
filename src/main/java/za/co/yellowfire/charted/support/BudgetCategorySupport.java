package za.co.yellowfire.charted.support;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import za.co.yellowfire.charted.domain.BudgetCategory;
import za.co.yellowfire.charted.domain.dao.BudgetCategoryDao;
import za.co.yellowfire.charted.translator.BudgetCategoryValueEncoder;


import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1,0
 */
public class BudgetCategorySupport implements SelectSupport<BudgetCategory> {

    private List<BudgetCategory> budgetCategories;

    @Inject
    private BudgetCategoryValueEncoder encoder;

    @Inject
    private BudgetCategoryDao dao;

    @Override
    public ValueEncoder<BudgetCategory> getEncoder() {
        return encoder;
    }

    @Override
    public List<BudgetCategory> getModel() {
        if (this.budgetCategories == null) {
            this.budgetCategories = dao.findAll();
        }
        return budgetCategories;
    }
}
