package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation(BindBudgetAllocation.BudgetAllocationBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindBudgetAllocation {

    public static class BudgetAllocationBinderFactory implements BinderFactory {

        public Binder build(Annotation annotation) {
          return new Binder<BindBudgetAllocation, BudgetAllocation>() {
            public void bind(SQLStatement q, BindBudgetAllocation bind, BudgetAllocation allocation) {
                q.bind("tx_id", allocation.getTransaction().getId());
                q.bind("bc_id", allocation.getBudgetCategory().getId());
                q.bind("amount", allocation.getAmount());
            }
          };
        }
  }
}