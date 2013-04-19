package za.co.yellowfire.charted.pages.budget;

import za.co.yellowfire.charted.domain.BudgetSection;
import za.co.yellowfire.charted.domain.CashFlowDirection;

/**
 */
public class Section {

    private BudgetSection section = new BudgetSection("Test Budget Section", CashFlowDirection.INCOME, "Yellow");

    public BudgetSection getSection() {
        return section;
    }

    void onActivate() {
        section = new BudgetSection("Test Budget Section", CashFlowDirection.INCOME, "Yellow");
    }
}
