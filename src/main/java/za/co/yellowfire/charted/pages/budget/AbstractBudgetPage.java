package za.co.yellowfire.charted.pages.budget;

import za.co.yellowfire.charted.domain.MenuItem;
import za.co.yellowfire.charted.domain.MenuSection;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.2.0
 */
public abstract class AbstractBudgetPage {

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
