package za.co.yellowfire.charted.pages.transaction;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import za.co.yellowfire.charted.domain.DataException;
import za.co.yellowfire.charted.domain.dao.BudgetAllocationDao;
import za.co.yellowfire.charted.domain.dao.TransactionDao;
import za.co.yellowfire.charted.domain.Transaction;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class TransactionAllocate {

    @Persist
    private String transactionId;

    @Property
    private Transaction transaction;

    @Inject
    private TransactionDao transactionDao;

    @Inject
    private BudgetAllocationDao budgetAllocationDao;

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void onActivate(EventContext eventContext) throws DataException {
        this.transaction = transactionDao.findById(transactionId);
    }
}
