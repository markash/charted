package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;
import java.util.Date;

@BindingAnnotation(BindStartDate.BudgetBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindStartDate {

    public static class BudgetBinderFactory implements BinderFactory {

        public Binder build(Annotation annotation) {
          return new Binder<BindStartDate, Date>() {
            public void bind(SQLStatement q, BindStartDate bind, Date date) {
                q.bind("start_date", date != null ? new java.sql.Date(date.getTime()) : null);
            }
          };
        }
  }
}