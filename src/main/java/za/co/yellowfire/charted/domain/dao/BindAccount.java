package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;
import za.co.yellowfire.charted.domain.Account;

import java.lang.annotation.*;

@BindingAnnotation(BindAccount.BudgetBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindAccount {

    public static class BudgetBinderFactory implements BinderFactory {

        public Binder build(Annotation annotation) {
          return new Binder<BindAccount, Account>() {
            public void bind(SQLStatement q, BindAccount bind, Account account) {
                q.bind(AccountQuery.account_nbr, account.getNumber());
                q.bind(AccountQuery.account_key, account.getAccountKey());
                q.bind(AccountQuery.bank_id, account.getBankId());
                q.bind(AccountQuery.branch_id, account.getBranchId());
                q.bind(AccountQuery.account_type, account.getType());
            }
          };
        }
  }
}