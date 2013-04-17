package za.co.yellowfire.charted.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import za.co.yellowfire.charted.domain.BudgetSection;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.2.0
 */
public class BudgetSectionDropdown {

    @Property
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String label;

    @Property
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String page;

    @Property
    @Parameter(required = true, autoconnect = true)
    private List<BudgetSection> source;

    @Property
    @Parameter(required = true, autoconnect = true)
    private BudgetSection value;

    public boolean beginRender(MarkupWriter writer) {
        return true;
    }

    public void afterRender(MarkupWriter writer) {
        //writer.end();
    }
}
