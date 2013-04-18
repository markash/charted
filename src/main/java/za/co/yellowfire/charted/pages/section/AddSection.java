package za.co.yellowfire.charted.pages.section;

import za.co.yellowfire.charted.domain.BudgetSection;
import za.co.yellowfire.charted.domain.CashFlowDirection;

/**
 */
public class AddSection {

    private BudgetSection section = new BudgetSection("Test Budget Section", CashFlowDirection.INCOME, "Yellow");

    public BudgetSection getSection() {
        return section;
    }

    void onActivate() {
        section = new BudgetSection("Test Budget Section", CashFlowDirection.INCOME, "Yellow");
    }
}
