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
          return new Binder<BindBudgetSection, BudgetSection>() {
            public void bind(SQLStatement q, BindBudgetSection bind, BudgetSection section) {
                q.bind(BudgetSectionQuery.name, section.getName());
                q.bind(BudgetSectionQuery.color, section.getColor());
                q.bind(BudgetSectionQuery.direction, section.getDirection());
            }
          };
        }
  }
}