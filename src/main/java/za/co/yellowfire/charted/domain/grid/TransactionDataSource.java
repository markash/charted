package za.co.yellowfire.charted.domain.grid;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import za.co.yellowfire.charted.domain.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0,1,0
 */
public class TransactionDataSource implements GridDataSource {
    private List<Transaction> transactions;

    public TransactionDataSource(List<Transaction> transactions) {
        this.transactions = new ArrayList<Transaction>(transactions);
    }

    @Override
    public void prepare(final int startIndex, final int endIndex, List<SortConstraint> sortConstraints) {
        for (SortConstraint sortConstraint : sortConstraints) {
            System.out.println("property = " + sortConstraint.getPropertyModel().getPropertyName());
            System.out.println("sortConstraint = " + sortConstraint.getColumnSort().name());
        }
    }

    @Override
    public int getAvailableRows() {
        return transactions.size();
    }

    @Override
    public Object getRowValue(int i) {
        return this.transactions.get(i);
    }

    @Override
    public Class<Transaction> getRowType() {
        return Transaction.class;
    }
}
