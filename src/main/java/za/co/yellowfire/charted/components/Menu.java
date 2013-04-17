package za.co.yellowfire.charted.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import za.co.yellowfire.charted.domain.BudgetSection;
import za.co.yellowfire.charted.domain.MenuItem;
import za.co.yellowfire.charted.domain.MenuSection;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.2.0
 */
public class Menu {

    @Property
    private MenuSection section;

    @Property
    private MenuItem item;

    @Property
    @Parameter(required = true, autoconnect = true)
    private List<MenuSection> sections;

    @Inject
    private ComponentResources resources;

    public String getClassForPageName() {
        return resources.getPageName().equalsIgnoreCase(item.getUrl())
                ? "current_page_item"
                : null;
    }
}
