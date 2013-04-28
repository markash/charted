package za.co.yellowfire.charted.pages.budget;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import za.co.yellowfire.charted.domain.Budget;
import za.co.yellowfire.charted.domain.dao.BudgetDao;
import za.co.yellowfire.charted.domain.Objective;

import java.util.Date;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetNew {

    @Inject
    private BudgetDao budgetDao;

    @Inject
    private Messages messages;

    @Property
    private String name;

    @Property
    private Date startDate = new Date();

    @Property
    private Date endDate = new Date();

    @Component(id = "budgetForm")
    private Form form;

    @Component(id = "name")
    private TextField nameField;

    @Component(id = "startDate")
    private DateField startDateField;

    @Component(id = "endDate")
    private DateField endDateField;

    @Environmental
    private JavaScriptSupport javaScriptSupport;

    void setupRender() { }

    void onValidateFromBudgetForm() {

        if (startDate.after(endDate)) {
            form.recordError(endDateField, "The End Date is before the Start Date");
        }

        if (budgetDao.existsForDates(startDate, endDate)) {
            form.recordError(startDateField, "A budget already exists for the specified dates");
            form.recordError(endDateField, "A budget already exists for the specified dates");
        }

        Budget budget = new Budget(startDate, endDate, new Objective(1L, "par", "< 100"));

        budgetDao.insert(budget);

    }

    Object onSuccess() {
        return BudgetCurrent.class;
    }

    Object onCancel() {
        return BudgetCurrent.class;
    }
}
