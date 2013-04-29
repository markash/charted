package za.co.yellowfire.charted.domain;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
@Entity(name = "transaction")
@Table(name = "transaction")
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(
                name = "qry.transactions",
                query = "select t from transaction t"
        ),
        @NamedQuery(
                name = "qry.transaction.forId",
                query = "select t from transaction t where t.id = :id"
        ),
        @NamedQuery(
                name = "qry.transaction.forUniqueKey",
                query = "select t from transaction t where t.id = :id"
        ),
        @NamedQuery(
                name = "qry.transactions.forBudgetCategories",
                query = "select t from transaction t where t.category in :category"
        ),
        @NamedQuery(
            name = "qry.transactions.forPeriod",
            query = "select t from transaction t where t.datePosted >= :datePostedStart and t.datePosted <= :datePostedEnd"
        )
})
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String QRY_TRANSACTIONS = "qry.transactions";
    public static final String QRY_TRANSACTION_FOR_ID = "qry.transaction.forId";
    public static final String QRY_TRANSACTION_FOR_UNIQUE_KEY = "qry.transaction.forUniqueKey";
    public static final String QRY_TRANSACTIONS_FOR_CATEGORIES = "qry.transactions.forBudgetCategories";
    public static final String QRY_TRANSACTIONS_FOR_PERIOD = "qry.transactions.forPeriod";

    public static final String PARAM_ID = "id";
    public static final String PARAM_AMOUNT = "amount";
    public static final String PARAM_MEMO = "memo";
    public static final String PARAM_DATE_POSTED = "datePosted";
    public static final String PARAM_DATE_POSTED_START = "datePostedStart";
    public static final String PARAM_DATE_POSTED_END = "datePostedEnd";
    public static final String PARAM_BUDGET_CATEGORY = "category";

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name="account_nbr")
    private Account account;

    @Column(name = "amount", precision = 38, scale = 4)
    private BigDecimal amount;

    @Column(name = "date_available")
    @Temporal(TemporalType.DATE)
    private Date dateAvailable;

    @Column(name = "date_initiated")
    @Temporal(TemporalType.DATE)
    private Date dateInitiated;

    @Column(name = "date_posted")
    @Temporal(TemporalType.DATE)
    private Date datePosted;

    @Column(name = "memo")
    private String memo;

    @Transient
    private BigDecimal allocated;

//    @ManyToOne
//    @JoinFetch(JoinFetchType.OUTER)
//    @JoinColumn(name = "budget_category")
//    private BudgetCategory category;

    @SuppressWarnings("unused")
    public Transaction() { }

    public Transaction(String id) {
        this(id, null, null, null, null, null, null);
    }

    public Transaction(String id, BigDecimal amount, Date dateAvailable, Date dateInitiated, Date datePosted, String memo) {
        this(id, amount, dateAvailable, dateInitiated, datePosted, memo, null);
    }

    public Transaction(String id, BigDecimal amount, Date dateAvailable, Date dateInitiated, Date datePosted, String memo, Account account) {
        this.id = id;
        this.amount = amount;
        this.dateAvailable = dateAvailable;
        this.dateInitiated = dateInitiated;
        this.datePosted = datePosted;
        this.memo = memo;
        this.account = account;
    }

    public static Transaction from(net.sf.ofx4j.domain.data.common.Transaction transaction, Account account) {

        return new Transaction(
                transaction.getId(),
                transaction.getBigDecimalAmount(),
                transaction.getDateAvailable(),
                transaction.getDateInitiated(),
                transaction.getDatePosted(),
                transaction.getMemo(),
                account
        );
    }

    public static Transaction save(EntityManager entityManager, Transaction transaction) {
        entityManager.persist(transaction);
        return transaction;
    }

    public static za.co.yellowfire.charted.database.NamedQuery<Transaction> forTransactions(EntityManager entityManager) {
        return new za.co.yellowfire.charted.database.NamedQuery<Transaction>(entityManager, Transaction.class, QRY_TRANSACTIONS);
    }

    public static za.co.yellowfire.charted.database.NamedQuery<Transaction> forUniqueId(EntityManager entityManager, String id) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put(PARAM_ID, id);
        return new za.co.yellowfire.charted.database.NamedQuery<Transaction>(entityManager, Transaction.class, QRY_TRANSACTION_FOR_UNIQUE_KEY, params);
    }

    public static za.co.yellowfire.charted.database.NamedQuery<Transaction> forId(EntityManager entityManager, String id) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put(PARAM_ID, id);
        return new za.co.yellowfire.charted.database.NamedQuery<Transaction>(entityManager, Transaction.class, QRY_TRANSACTION_FOR_ID, params);
    }

    public static za.co.yellowfire.charted.database.NamedQuery<Transaction> forBudgetCategory(EntityManager entityManager, String[] budgetCategories) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put(PARAM_BUDGET_CATEGORY, Arrays.asList(budgetCategories));
        return new za.co.yellowfire.charted.database.NamedQuery<Transaction>(entityManager, Transaction.class, QRY_TRANSACTIONS_FOR_CATEGORIES, params);
    }

    public static za.co.yellowfire.charted.database.NamedQuery<Transaction> forBudget(EntityManager entityManager, Budget budget) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put(PARAM_DATE_POSTED_START, budget.getStartDate());
        params.put(PARAM_DATE_POSTED_END, budget.getEndDate());
        return new za.co.yellowfire.charted.database.NamedQuery<Transaction>(entityManager, Transaction.class, QRY_TRANSACTIONS_FOR_PERIOD, params);
    }

    public String getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getDateAvailable() {
        return dateAvailable;
    }

    public Date getDateInitiated() {
        return dateInitiated;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public String getMemo() {
        return memo;
    }

    public BigDecimal getAllocated() {
        return this.allocated;
    }

    public void setAllocated(BigDecimal allocated) {
        this.allocated = allocated;
    }

    public BigDecimal getRemainder() {
        return getAmount().subtract(getAllocated());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Transaction {" +
                "id='" + id + '\'' +
                ", amount=" + (amount != null ? amount : "null") +
                ", dateAvailable=" + (dateAvailable != null ? dateAvailable : "null" ) +
                ", dateInitiated=" + (dateInitiated != null ? dateInitiated : "null")  +
                ", datePosted=" + (datePosted != null ? datePosted : "null") +
                ", memo='" + (memo != null ? memo : "null") + '\'' +
                ", account='" + (account != null ? account.getNumber() : "null") + '\'' +
                '}';
    }
}
