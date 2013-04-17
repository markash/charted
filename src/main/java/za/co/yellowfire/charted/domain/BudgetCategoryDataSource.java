package za.co.yellowfire.charted.domain;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0,1,0
 */
public class BudgetCategoryDataSource implements GridDataSource {

//    private Budget budget;
//    private String sectionName;
    private List<BudgetCategory> categories;

    public BudgetCategoryDataSource(Budget budget, String sectionName) {
//        this.budget = budget;
//        this.sectionName = sectionName;
        System.out.println("sectionName = " + sectionName);
        this.categories = budget.getCategories(sectionName);
    }

    @Override
    public void prepare(final int startIndex, final int endIndex, List<SortConstraint> sortConstraints) {
        System.out.println("PREPARE");

    }

    @Override
    public int getAvailableRows() {
        return categories.size();
    }

    @Override
    public Object getRowValue(int i) {
        return this.categories.get(i);
    }

    @Override
    public Class<BudgetCategory> getRowType() {
        return BudgetCategory.class;
    }
}
