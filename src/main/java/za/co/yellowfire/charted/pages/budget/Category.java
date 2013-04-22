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

import java.util.Arrays;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class Category {
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
    private List<BudgetSection> sections;

    @InjectComponent("name")
    private TextField nameField;

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public void onActivate(EventContext eventContext) throws DataAccessException {
        sections = sectionDao.findForBudgetId(budgetId);
    }

    public void onValidateFromCategoryForm() {
        System.out.println("onValidate");
    }

    public Object onSuccess() throws DataException {

        BudgetCategory category = new BudgetCategory(name, direction, color, section);
        categoryDao.save(category);

        return BudgetCurrent.class;
    }

    public List<MenuSection> getMenuSections() {
        return Arrays.asList(
                new MenuSection(
                        "Budgeting",
                        new MenuItem[]{
                                new MenuItem("Current Budget", "budget/current"),
                                new MenuItem("Statement Upload", "statement/upload"),
                        })
        );
    }
}
