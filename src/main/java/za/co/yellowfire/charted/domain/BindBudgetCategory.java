package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation(BindBudgetCategory.BudgetBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindBudgetCategory {

    public static class BudgetBinderFactory implements BinderFactory {

        public Binder build(Annotation annotation) {
          return new Binder<BindBudgetCategory, Budget>() {
            public void bind(SQLStatement q, BindBudgetCategory bind, Budget budget) {
                q.bind(BudgetQuery.start_date, budget.getStartDate());
                q.bind(BudgetQuery.end_date, budget.getEndDate());
                q.bind(BudgetQuery.objective_id, budget.getObjective().getId());
            }
          };
        }
  }
}