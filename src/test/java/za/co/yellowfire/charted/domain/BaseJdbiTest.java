package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BaseJdbiTest {

    private Handle handle;
    private TransactionQuery txQuery;
    private BudgetAllocationQuery baQuery;

    @BeforeClass
    public void init() {
        DBI dbi = new DBI("jdbc:postgresql://localhost/charted", "uncharted", "uncharted");
        handle = dbi.open();

        txQuery = handle.attach(TransactionQuery.class);
        baQuery = handle.attach(BudgetAllocationQuery.class);

        handle.registerMapper(new BudgetAllocationMapper());
        handle.registerMapper(new TransactionMapper());
    }

    @AfterClass
    public void cleanUp() {
        handle.close();
    }

    public TransactionQuery getTransactionQuery() { return txQuery; }
    public BudgetAllocationQuery getBudgetAllocationQuery() { return baQuery; }
}
