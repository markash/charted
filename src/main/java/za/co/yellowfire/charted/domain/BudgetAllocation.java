package za.co.yellowfire.charted.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetAllocation implements Serializable {

    private Long id;

    private BudgetCategory budgetCategory;

    private Transaction transaction;

    private BigDecimal amount;

    public BudgetAllocation(BudgetCategory budgetCategory, Transaction transaction, BigDecimal amount) {
        this(null, budgetCategory, transaction, amount);
    }

    public BudgetAllocation(Long id, BudgetCategory budgetCategory, Transaction transaction, BigDecimal amount) {
        this.id = id;
        this.budgetCategory = budgetCategory;
        this.transaction = transaction;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "BudgetAllocation {" +
                "id=" + id +
                ", budgetCategory=" + budgetCategory +
                ", transaction=" + transaction +
                ", amount=" + amount +
                '}';
    }
}
