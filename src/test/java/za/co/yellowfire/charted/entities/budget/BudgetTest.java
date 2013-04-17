package za.co.yellowfire.charted.entities.budget;

import static org.testng.Assert.*;

import org.joda.time.LocalDate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import za.co.yellowfire.charted.domain.Budget;

import java.util.Date;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetTest {

    private Date D20130225;
    private Date D20130324;
    private Date D20130325;
    private Date D20130326;

    @BeforeClass
    public void init () {
//        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
//        calendar.set(Calendar.ZONE_OFFSET, 0);
//        calendar.set(Calendar.DST_OFFSET, 0);
//        calendar.set(Calendar.DATE, 26);
//        calendar.set(Calendar.MONTH, Calendar.MARCH);
//        calendar.set(Calendar.YEAR, 2013);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.set(Calendar.AM_PM, Calendar.AM);

        D20130326 = new LocalDate(2013, 3, 26).toDate();

//        calendar.set(Calendar.DATE, 25);
        D20130325 = new LocalDate(2013, 3, 25).toDate();

//        calendar.set(Calendar.DATE, 24);
        D20130324 = new LocalDate(2013, 3, 24).toDate();

//        calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
//        calendar.set(Calendar.DATE, 25);
        D20130225 = new LocalDate(2013, 2, 25).toDate();
    }

    @Test
    public void determineBudgetDateWhenBeforeTwentyFifth() {
        Date budgetStartDate = Budget.budgetStartDateFor(D20130324);
        assertEquals(budgetStartDate, D20130225);
    }

    @Test
    public void determineBudgetDateWhenAfterTwentyFifth() {
        Date budgetStartDate = Budget.budgetStartDateFor(D20130326);
        assertEquals(budgetStartDate, D20130325);
    }

    @Test
    public void determineBudgetDateWhenTwentyFifth() {
        Date budgetStartDate = Budget.budgetStartDateFor(D20130325);
        assertEquals(budgetStartDate, D20130325);
    }
}
