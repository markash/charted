package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation(BindBudgetSection.BudgetBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindBudgetSection {

    public static class BudgetBinderFactory implements BinderFactory {

        public Binder build(Annotation annotation) {
          return new Binder<BindBudgetSection, BudgetCategory>() {
            public void bind(SQLStatement q, BindBudgetSection bind, BudgetCategory category) {
                q.bind(BudgetCategoryQuery.section_id, category.getSection().getId());
                q.bind(BudgetCategoryQuery.name, category.getName());
                q.bind(BudgetCategoryQuery.color, category.getColor());
                q.bind(BudgetCategoryQuery.direction, category.getDirection());
                q.bind(BudgetCategoryQuery.matches, category.getMatches());
                q.bind(BudgetCategoryQuery.budget_amount, category.getBudgetAmount());
            }
          };
        }
  }
}