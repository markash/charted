package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;
import za.co.yellowfire.charted.domain.Transaction;

import java.lang.annotation.*;

@BindingAnnotation(BindTransaction.BudgetBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindTransaction {

    public static class BudgetBinderFactory implements BinderFactory {

        public Binder build(Annotation annotation) {
          return new Binder<BindTransaction, Transaction>() {
            public void bind(SQLStatement q, BindTransaction bind, Transaction transaction) {
                q.bind(TransactionQuery.transaction_id, transaction.getId());
                q.bind(TransactionQuery.amount, transaction.getAmount());
                q.bind(TransactionQuery.date_available, transaction.getDateAvailable());
                q.bind(TransactionQuery.date_initiated, transaction.getDateInitiated());
                q.bind(TransactionQuery.date_posted, transaction.getDatePosted());
                q.bind(TransactionQuery.memo, transaction.getMemo());
                q.bind(TransactionQuery.account_nbr, transaction.getAccount().getNumber());
            }
          };
        }
  }
}