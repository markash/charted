package za.co.yellowfire.charted.pages;

import za.co.yellowfire.charted.domain.MenuItem;
import za.co.yellowfire.charted.domain.MenuSection;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public abstract class AbstractPage {
    public List<MenuSection> getMenuSections() {
        return Arrays.asList(
                new MenuSection(
                        "Budgeting",
                        new MenuItem[]{
                                new MenuItem("Current Budget", "budget/current"),
                                new MenuItem("Statement Upload", "statement/upload"),
                                new MenuItem("Transactions", "transaction/list")
                        })
        );
    }
}
