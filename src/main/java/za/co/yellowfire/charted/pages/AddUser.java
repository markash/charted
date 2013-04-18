package za.co.yellowfire.charted.pages;

import za.co.yellowfire.charted.domain.BudgetSection;

/**
 */
public class AddUser {

    private BudgetSection section;

    public BudgetSection getSection() {
        return section;
    }

    void onActivate() {
        section = new BudgetSection();
    }
}
