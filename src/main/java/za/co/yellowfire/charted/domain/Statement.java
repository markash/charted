package za.co.yellowfire.charted.domain;

import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.domain.data.ResponseMessage;
import net.sf.ofx4j.domain.data.ResponseMessageSet;
import net.sf.ofx4j.domain.data.banking.BankStatementResponse;
import net.sf.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import net.sf.ofx4j.domain.data.common.TransactionList;
import net.sf.ofx4j.io.AggregateUnmarshaller;
import za.co.yellowfire.charted.database.DataAccessException;
import za.co.yellowfire.charted.database.TransactionManager;

import javax.persistence.*;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
@Entity(name = "Statement")
@Table(name = "statement")
@Access(AccessType.FIELD)
@IdClass(StatementId.class)
public class Statement implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "account_nbr")
    private Account account;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "start")
    private Date start;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "end")
    private Date end;

    public static void importStatement(EntityManager entityManager, InputStream is) throws Exception {
        TransactionManager tx = new TransactionManager(entityManager);
        try {
            tx.beginIfLocal();

            AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<ResponseEnvelope>(ResponseEnvelope.class);
            ResponseEnvelope envelope = unmarshaller.unmarshal(is);
            for (ResponseMessageSet set : envelope.getMessageSets()) {
                System.out.println("set = " + set.getType());

                for (ResponseMessage message : set.getResponseMessages()) {
                    if ("signon".equals(message.getResponseMessageName())) {
                    }
                    if ("bank statement transaction".equals(message.getResponseMessageName())) {
                        BankStatementResponse response = ((BankStatementResponseTransaction) message).getMessage();

                        TransactionList transactions = response.getTransactionList();
                        Budget budget = determineBudget(entityManager, transactions);

                        Account account = persistAccount(entityManager, Account.from(response.getAccount()));
                        for (net.sf.ofx4j.domain.data.common.Transaction transaction : transactions.getTransactions()) {
                            persistTransaction(
                                    entityManager,
                                    account,
                                    Transaction.from(transaction),
                                    budget);
                        }
                    }
                }
            }

            tx.commitIfLocal();
        } catch (Exception e ) {
            e.printStackTrace();
            //tx.getRollbackOnly();

            throw e;
        }
    }

    protected static Budget determineBudget(EntityManager entityManager, TransactionList transactionList) throws DataAccessException {
        final Date budgetStartDate = Budget.budgetStartDateFor(transactionList.getStart());
        Budget budget = Budget.forId(entityManager, budgetStartDate);
        if (budget == null) {
            budget = Budget.fromTemplateFor(budgetStartDate);
            Budget.save(entityManager, budget);
        }
        return budget;
    }

    /**
     * Persists the transaction to the database
     * @param entityManager The entity manager to use for the persistence
     * @param account The account that the transaction should be assigned to
     * @param transaction The transaction instance
     * @param budget The budget that the transaction is linked to by date
     * @return The persisted transaction
     * @throws DataAccessException If there was an error with the persistence
     */
    protected static Transaction persistTransaction(
            EntityManager entityManager,
            final Account account,
            final Transaction transaction,
            final Budget budget) throws DataAccessException {

        /* Persist the transaction if it does not exist else return the instance in the database*/
        List<Transaction> results = Transaction.forId(entityManager, transaction.getId()).results();
        if (results == null || results.size() == 0) {
            transaction.setAccount(account);
            transaction.setCategory(budget.determineBudgetCategory(transaction));
            return Transaction.save(entityManager, transaction);
        } else {
            return results.get(0);
        }
    }

    protected static Account persistAccount(EntityManager entityManager, Account account) {
        List<Account> accounts = Account.forId(entityManager, account.getNumber()).results();
        if (accounts == null || accounts.size() == 0) {
            return Account.save(entityManager, account);
        } else {
            return accounts.get(0);
        }
    }
}
