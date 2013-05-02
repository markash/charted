package za.co.yellowfire.charted.pages.budget;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import za.co.yellowfire.charted.domain.*;
import za.co.yellowfire.charted.domain.dao.BudgetDao;
import za.co.yellowfire.charted.domain.dao.BudgetSectionDao;
import za.co.yellowfire.charted.pages.AbstractPage;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class Section extends AbstractPage {

    @Persist
    private long budgetId;

    @Inject
    private BudgetDao budgetDao;

    @Inject
    private BudgetSectionDao sectionDao;

    @Inject
    private Messages messages;

    @Property
    private String name;
    @Property()
    private CashFlowDirection direction = CashFlowDirection.EXPENSE;

    @Property
    private Color color = Color.medium_faded_red;

    @InjectComponent("name")
    private TextField nameField;


    public void setBudgetId(long budgetId) {
        System.out.println("budgetId = " + budgetId);
        this.budgetId = budgetId;
    }

    public void onActivate() {
        System.out.println("onActivate");
    }

    public void onValidateFromSectionForm() {
        BudgetSection section = new BudgetSection(name, direction, color.name());
        sectionDao.insert(budgetId, section);
    }

    public Object onSuccess() {
        return BudgetCurrent.class;
    }
}
