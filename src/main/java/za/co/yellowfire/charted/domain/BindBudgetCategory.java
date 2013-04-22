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
          return new Binder<BindBudgetCategory, BudgetCategory>() {
            public void bind(SQLStatement q, BindBudgetCategory bind, BudgetCategory category) {
                q.bind(BudgetCategoryQuery.category_id, category.getId());
                q.bind(BudgetCategoryQuery.section_id, category.getSection().getId());
                q.bind(BudgetCategoryQuery.name, category.getName());
                q.bind(BudgetCategoryQuery.color, category.getColor());
                q.bind(BudgetCategoryQuery.direction, category.getDirection().name());
                q.bind(BudgetCategoryQuery.matches, category.getMatchesValue());
                q.bind(BudgetCategoryQuery.budget_amount, category.getBudgetAmount());
            }
          };
        }
  }
}