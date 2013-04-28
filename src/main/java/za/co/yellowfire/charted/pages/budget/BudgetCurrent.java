package za.co.yellowfire.charted.pages.budget;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import za.co.yellowfire.charted.database.DataAccessException;
import za.co.yellowfire.charted.domain.*;
import za.co.yellowfire.charted.domain.dao.BudgetCategoryDao;
import za.co.yellowfire.charted.domain.dao.BudgetDao;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
@Import(library={"context:js/bootstrap.js"})
public class BudgetCurrent {

    @Inject
    private BudgetDao budgetDao;

    @Inject
    private BudgetCategoryDao categoryDao;

    @Persist(PersistenceConstants.FLASH)
    private Budget budget;

    @Property
    private String context;

    @Property
    private BudgetSection budgetSection;

    @Property
    private BudgetCategory budgetCategory;

    @Property
    private final BudgetCategoryEncoder budgetCategoryEncoder = new BudgetCategoryEncoder();

    @Property
    private BeanModel<BudgetCategory> categoryModel;

    @Property
    private BudgetCategoryDataSource budgetCategories;

    @Property
    private RowStyle rowStyle;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    @Inject
    private PageRenderLinkSource pageRenderLinkSource;

    @InjectPage
    private BudgetCurrent onSuccessPage;

    @InjectPage
    private Category categoryPage;

    @InjectPage
    private Section sectionPage;

    public Map<String, String> getChildren() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("type", "expense");
        params.put("category", "children");
        return params;
    }

    public String getBudgetMonth() {
        return new SimpleDateFormat("MMMM yyyy").format(getBudget().getStartDate());
    }

    public Budget getBudget() {
        return this.budget;
    }

    public RowStyle[] getRowStyles() {
        return new RowStyle[]{
                RowStyle.forBackgroundColor(Color.pale_faded_azure_blue),
                RowStyle.forBackgroundColor(Color.medium_faded_yellow),
                RowStyle.forBackgroundColor(Color.medium_faded_red),
                RowStyle.forBackgroundColor(Color.vivid_red),
                RowStyle.forBackgroundColor(Color.pale_light_azure_blue),
                RowStyle.forBackgroundColor(Color.pale_very_light_yellow),
                RowStyle.forBackgroundColor(Color.pale_very_light_red),
                RowStyle.forBackgroundColor(Color.pale_pastel_red),
                RowStyle.forBackgroundColor(Color.pale_vivid_red)
        };
    }

//    public String getCategoryRowColor() {
//        if (budgetCategory != null) {
//            return budgetCategory.getColor();
//        }
//        return "";
//    }

    public void onActivate(EventContext eventContext) throws DataAccessException {

        this.budget = budgetDao.findForDate(new Date());

        System.out.println("budget = " + budget);
//        this.budget = Budget.current(em);
//        if (this.budget == null) {
//            this.budget = Budget.fromTemplateCurrent();
//        }
//        this.budget.loadTransactions(em);
//
        this.context = null;
        if (eventContext.getCount() > 0) {
            context = eventContext.get(String.class, 0);
        }
        this.budgetCategories = new BudgetCategoryDataSource(budget, context);
    }

    public void setupRender() {
        categoryModel = beanModelSource.createDisplayModel(BudgetCategory.class, messages);
        categoryModel.add("action", null);
        categoryModel.include("name", "sectionName", "direction", "budgetAmount", "actualAmount", "varianceAmount", "action");
        categoryModel.get("name").sortable(false);
        categoryModel.get("sectionName").sortable(false);
        categoryModel.get("direction").sortable(false);
        categoryModel.get("budgetAmount").sortable(false);
        categoryModel.get("actualAmount").sortable(false);
        categoryModel.get("varianceAmount").sortable(false);
        categoryModel.get("budgetAmount").label("Budget");
        categoryModel.get("actualAmount").label("Actual");
        categoryModel.get("varianceAmount").label("Variance");
        categoryModel.get("sectionName").label("Section");
    }

    public void onSubmit() throws DataAccessException {
        //System.out.println("SUBMIT");
        //Budget.save(em, budget);
    }

    public String onPassivate() {
        return this.context;
    }

    public void onRefresh() {}

    public Object onActionFromAddCategory(long budgetId) {
        categoryPage.setBudgetId(budgetId);
        return categoryPage;
    }

    public Object onActionFromAddSection(long budgetId) {
        sectionPage.setBudgetId(budgetId);
        return sectionPage;
    }

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

    private class BudgetCategoryEncoder implements ValueEncoder<BudgetCategory> {
        /**
         * Converts a value into a client-side representation. The value should be parseable by {@link #toValue(String)}. In
         * some cases, what is returned is an identifier used to locate the true object, rather than a string representation
         * of the value itself.
         *
         * @param value to be encoded
         * @return a string representation of the value, or the value's identity
         */
        @Override
        public String toClient(BudgetCategory value) {
            return value.getId().toString();
        }

        /**
         * Converts a client-side representation, provided by {@link #toClient(Object)}, back into a server-side value.
         *
         * @param clientValue string representation of the value's identity
         * @return the corresponding entity, or null if not found
         */
        @Override
        public BudgetCategory toValue(String clientValue) {
            return categoryDao.findById(Long.valueOf(clientValue));
        }
    }
}
