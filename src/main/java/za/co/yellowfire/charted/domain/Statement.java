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



    protected static Budget determineBudget(EntityManager entityManager, TransactionList transactionList) throws DataAccessException {
        final Date budgetStartDate = Budget.budgetStartDateFor(transactionList.getStart());
        Budget budget = Budget.forId(entityManager, budgetStartDate);
        if (budget == null) {
            budget = Budget.fromTemplateFor(budgetStartDate);
            Budget.save(entityManager, budget);
        }
        return budget;
    }


}
