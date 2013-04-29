package za.co.yellowfire.charted.domain;

import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetAllocationTest extends BaseJdbiTest {

    @Test
    public void findByIdTest() throws Exception {
        Transaction tx = getTransactionQuery().findAll().get(0);

        BudgetAllocation allocation = new BudgetAllocation(new BudgetCategory(202L), tx, new BigDecimal("100.00"));
        getBudgetAllocationQuery().insert(allocation);
    }
}
