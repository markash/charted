package za.co.yellowfire.charted.pages.budget;

import org.apache.tapestry5.Translator;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
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
    private BudgetDao budgetDao;

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
    }

    public void onValidateFromCategoryForm() {
        System.out.println("onValidate");
    }

    public Object onSuccess() {
        System.out.println("onSuccess");
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
