package za.co.yellowfire.charted.support;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import za.co.yellowfire.charted.domain.BudgetSection;
import za.co.yellowfire.charted.domain.dao.BudgetSectionDao;
import za.co.yellowfire.charted.translator.BudgetSectionValueEncoder;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1,0
 */
public class BudgetSectionSupport implements SelectSupport<BudgetSection> {

    private long budgetId;
    private List<BudgetSection> budgetSections;
    private BudgetSectionValueEncoder encoder;
    private BudgetSectionDao dao;

    public BudgetSectionSupport(long budgetId, BudgetSectionDao dao) {
        this.budgetId = budgetId;
        this.dao = dao;
        this.encoder = new BudgetSectionValueEncoder(dao);
    }

    @Override
    public ValueEncoder<BudgetSection> getEncoder() {
        return encoder;
    }

    @Override
    public List<BudgetSection> getModel() {
        if (this.budgetSections == null) {
            this.budgetSections = dao.findForBudgetId(budgetId);
        }
        return budgetSections;
    }
}
