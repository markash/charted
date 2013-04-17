package za.co.yellowfire.charted.domain;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.unitils.core.ConfigurationLoader;
import org.unitils.dbunit.DbUnitModule;
import org.unitils.dbunit.annotation.DataSet;
import org.unitilsnew.UnitilsTestNG;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
@DataSet
public class BudgetDatabaseTest extends UnitilsTestNG {
    private DbUnitModule dbUnitModule;

    @BeforeClass
    public void setUp() throws Exception {
        Properties configuration = new ConfigurationLoader().loadConfiguration();
        dbUnitModule = new DbUnitModule();
        dbUnitModule.init(configuration);
    }

    @Test
    public void testFindById() throws Exception {
        dbUnitModule.insertDataSet(BudgetDatabaseTest.class.getMethod("testFindById"), this);

        BudgetDao dao = new BudgetDao("jdbc:postgresql://localhost/charted-test", "uncharted", "uncharted");
        Budget budget = dao.findById(1L);

        assertNotNull(budget);
        assertEquals(budget.getStartDate(), new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-25"));
        assertEquals(budget.getEndDate(), new SimpleDateFormat("yyyy-MM-dd").parse("2013-04-24"));
        assertNotNull(budget.getSections());

        Objective objective = budget.getObjective();
        assertNotNull(objective);
        assertEquals(objective.getId(), new Long(1L));
        assertEquals(objective.getName(), "par");
        assertEquals(objective.getExpression(), "0 <= amount <= 500");

        List<BudgetSection> sections = budget.getSections();
        assertNotNull(sections);
        assertEquals(sections.size(), 2);

        BudgetSection section = sections.get(0);
        assertNotNull(section);
        assertEquals(section.getId(), new Long(1L));
        assertEquals(section.getName(), "Income");
        assertEquals(section.getColor(), Color.medium_faded_yellow.name());
        assertEquals(section.getDirection(), CashFlowDirection.INCOME);

        List<BudgetCategory> categories = section.getCategories();
        assertNotNull(categories);
        assertEquals(categories.size(), 1);

        BudgetCategory category = categories.get(0);
        assertNotNull(category);
        assertEquals(category.getId(), new Long(1L));
        assertEquals(category.getName(), "Salary");
        assertEquals(category.getColor(), Color.pale_very_light_yellow.name());
        assertEquals(category.getDirection(), CashFlowDirection.INCOME);
        assertEquals(category.getBudgetAmount(), new BigDecimal("1000.00"));
        assertNotNull(category.getMatches());
        assertEquals(category.getMatches().size(), 0);


        section = sections.get(1);
        assertNotNull(section);
        assertEquals(section.getId(), new Long(2L));
        assertEquals(section.getName(), "Expense");
        assertEquals(section.getColor(), Color.medium_faded_red.name());
        assertEquals(section.getDirection(), CashFlowDirection.EXPENSE);

        categories = section.getCategories();
        assertNotNull(categories);
        assertEquals(categories.size(), 2);

        category = categories.get(0);
        assertNotNull(category);
        assertEquals(category.getId(), new Long(2L));
        assertEquals(category.getName(), "Groceries");
        assertEquals(category.getColor(), Color.pale_very_light_red.name());
        assertEquals(category.getDirection(), CashFlowDirection.EXPENSE);
        assertEquals(category.getBudgetAmount(), new BigDecimal("200.00"));
        assertEquals(category.getMatches(), Arrays.asList(StringUtils.split("Checkers|Walmart", "|")));
    }

    @Test
    public void testCategories() throws Exception {
        dbUnitModule.insertDataSet(BudgetDatabaseTest.class.getMethod("testFindById"), this);

        BudgetDao dao = new BudgetDao("jdbc:postgresql://localhost/charted-test", "uncharted", "uncharted");
        Budget budget = dao.findById(1L);

        List<BudgetCategory> categories = budget.getCategories();
        assertNotNull(categories);
        assertEquals(categories.size(), 3);
    }
}
