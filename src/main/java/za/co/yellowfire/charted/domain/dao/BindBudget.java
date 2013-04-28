package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;
import za.co.yellowfire.charted.domain.Budget;

import java.lang.annotation.*;

@BindingAnnotation(BindBudget.BudgetBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindBudget {

    public static class BudgetBinderFactory implements BinderFactory {

        public Binder build(Annotation annotation) {
          return new Binder<BindBudget, Budget>() {
            public void bind(SQLStatement q, BindBudget bind, Budget budget) {
                q.bind(BudgetQuery.start_date, budget.getStartDate());
                q.bind(BudgetQuery.end_date, budget.getEndDate());
                q.bind(BudgetQuery.objective_id, budget.getObjective().getId());
            }
          };
        }
  }
}