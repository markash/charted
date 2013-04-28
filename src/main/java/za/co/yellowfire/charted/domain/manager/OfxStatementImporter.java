package za.co.yellowfire.charted.domain.manager;

import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.domain.data.ResponseMessage;
import net.sf.ofx4j.domain.data.ResponseMessageSet;
import net.sf.ofx4j.domain.data.banking.BankStatementResponse;
import net.sf.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import net.sf.ofx4j.domain.data.common.TransactionList;
import net.sf.ofx4j.io.AggregateUnmarshaller;
import za.co.yellowfire.charted.domain.Account;
import za.co.yellowfire.charted.domain.DataException;
import za.co.yellowfire.charted.domain.Transaction;
import za.co.yellowfire.charted.domain.dao.AccountDao;
import za.co.yellowfire.charted.domain.dao.TransactionDao;

import java.io.InputStream;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class OfxStatementImporter implements StatementImporter {

    private AccountDao accountDao;
    private TransactionDao transactionDao;

    public OfxStatementImporter(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    public void importStatement(InputStream is) throws StatementImportException {
        try {
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

                        Account account = persistAccount(Account.from(response.getAccount()));
                        for (net.sf.ofx4j.domain.data.common.Transaction transaction : transactions.getTransactions()) {
                            persistTransaction(Transaction.from(transaction, account));
                        }
                    }
                }
            }
        } catch (Exception e ) {
            throw new StatementImportException("Unable to import statement", e);
        }
    }

    /**
     * Persists the transaction to the database
     * @param transaction The transaction instance
     * @return The persisted transaction
     * @throws DataException If there was an error with the persistence
     */
    protected Transaction persistTransaction(final Transaction transaction) throws DataException {

        /* Persist the transaction if it does not exist else return the instance in the database*/
        Transaction existing = transactionDao.findById(transaction.getId());
        if (existing != null) {
            return existing;
        } else {
            return transactionDao.save(transaction);
        }
    }

    protected Account persistAccount(Account account) throws DataException {
        Account existing = accountDao.findById(account.getNumber());
        if (existing != null) {
            return existing;
        } else {
            return accountDao.save(account);
        }
    }
}
