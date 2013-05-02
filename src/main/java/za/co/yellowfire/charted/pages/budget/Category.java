package za.co.yellowfire.charted.pages.budget;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import za.co.yellowfire.charted.database.DataAccessException;
import za.co.yellowfire.charted.domain.*;
import za.co.yellowfire.charted.domain.dao.BudgetCategoryDao;
import za.co.yellowfire.charted.domain.dao.BudgetSectionDao;
import za.co.yellowfire.charted.pages.AbstractPage;
import za.co.yellowfire.charted.support.BudgetSectionSupport;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class Category extends AbstractPage {
    @Persist
    private long budgetId;

    @Inject
    private BudgetSectionDao sectionDao;

    @Inject
    private BudgetCategoryDao categoryDao;

    @Inject
    private Messages messages;

    @Property
    private String name;

    @Property()
    private CashFlowDirection direction = CashFlowDirection.EXPENSE;

    @Property
    private Color color = Color.medium_faded_red;

    @Property
    private BudgetSection section;

    @Property
    private BudgetSectionSupport budgetSectionSupport;

    @InjectComponent("name")
    private TextField nameField;

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public void onActivate(EventContext eventContext) throws DataAccessException {
        budgetSectionSupport = new BudgetSectionSupport(budgetId, sectionDao);
    }

    public void onValidateFromCategoryForm() {
        System.out.println("onValidate");
    }

    public Object onSuccess() throws DataException {

        BudgetCategory category = new BudgetCategory(name, direction, color, section);
        categoryDao.save(category);

        return BudgetCurrent.class;
    }
}
