package za.co.yellowfire.charted.domain;

import net.sf.ofx4j.domain.data.banking.BankAccountDetails;

import javax.persistence.*;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
@Entity(name = "Account")
@Table(name = "account")
@Access(AccessType.FIELD)
//@ChangeTracking(ChangeTrackingType.OBJECT)
@NamedQueries({
        @NamedQuery(
                name = "qry.accounts",
                query = "select a from Account a"
        ),
        @NamedQuery(
                name = "qry.account.forId",
                query = "select a from Account a where a.number = :number"
        ),
        @NamedQuery(
                name = "qry.account.forUniqueKey",
                query = "select a from Account a where a.number = :number"
        )
})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String QRY_ACCOUNTS = "qry.accounts";
    public static final String QRY_ACCOUNT_FOR_ID = "qry.account.forId";
    public static final String QRY_ACCOUNT_FOR_UNIQUE_KEY = "qry.account.forUniqueKey";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_TYPE = "type";

    public static final String PARAM_ID = "number";
    public static final String PARAM_NUMBER = "number";
    public static final String PARAM_TYPE = "type";

    @Id
    @Column(name = "account_nbr")
    private String number;

    @Column(name = "account_type")
    private String type;

    @Column(name = "bank_id")
    private String bankId;

    @Column(name = "branch_id")
    private String branchId;

    @Column(name = "account_key")
    private String accountKey;

    @SuppressWarnings("unused")
    public Account() { }

    public Account(String number, String type, String bankId, String branchId, String accountKey) {
        this.number = number;
        this.type = type;
        this.bankId = bankId;
        this.branchId = branchId;
        this.accountKey = accountKey;
    }

    public static Account from(BankAccountDetails account) {
        return new Account(
                account.getAccountNumber(),
                account.getAccountType().name(),
                account.getBankId(),
                account.getBranchId(),
                account.getAccountKey()
        );
    }

    public static Account save(EntityManager entityManager, Account account) {
        entityManager.persist(account);
        return account;
    }

    public static za.co.yellowfire.charted.database.NamedQuery<Account> forAccounts(EntityManager entityManager) {
        return new za.co.yellowfire.charted.database.NamedQuery<Account>(entityManager, Account.class, QRY_ACCOUNTS);
    }

    public static za.co.yellowfire.charted.database.NamedQuery<Account> forUniqueId(EntityManager entityManager, String accountNumber) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put(PARAM_NUMBER, accountNumber);
        return new za.co.yellowfire.charted.database.NamedQuery<Account>(entityManager, Account.class, QRY_ACCOUNT_FOR_UNIQUE_KEY, params);
    }

    public static za.co.yellowfire.charted.database.NamedQuery<Account> forId(EntityManager entityManager,  String accountNumber) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put(PARAM_ID, accountNumber);
        return new za.co.yellowfire.charted.database.NamedQuery<Account>(entityManager, Account.class, QRY_ACCOUNT_FOR_ID, params);
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getBankId() {
        return bankId;
    }

    public String getBranchId() {
        return branchId;
    }

    public String getAccountKey() {
        return accountKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;
        return number == account.number;
    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Account {" +
                "number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", bankId='" + bankId + '\'' +
                ", branchId='" + branchId + '\'' +
                ", accountKey='" + accountKey + '\'' +
                '}';
    }
}
