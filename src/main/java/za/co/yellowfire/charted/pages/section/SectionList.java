package za.co.yellowfire.charted.pages.section;

import org.apache.tapestry5.annotations.Property;
import za.co.yellowfire.charted.domain.BudgetSection;
import za.co.yellowfire.charted.domain.CashFlowDirection;

import java.util.Arrays;
import java.util.List;

/**
 */
public class SectionList {
    @Property
    private BudgetSection section;

    public List<BudgetSection> getSections() {
        return Arrays.asList(
                new BudgetSection("Test", CashFlowDirection.EXPENSE, "RED"),
                new BudgetSection("Sample", CashFlowDirection.INCOME, "BLUE")
        );
    }
}
