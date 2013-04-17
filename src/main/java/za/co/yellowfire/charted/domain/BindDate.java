package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;
import java.util.Date;

@BindingAnnotation(BindDate.BudgetBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindDate {

    public static class BudgetBinderFactory implements BinderFactory {

        public Binder build(Annotation annotation) {
          return new Binder<BindDate, Date>() {
            public void bind(SQLStatement q, BindDate bind, Date date) {
                q.bind("date", date != null ? new java.sql.Date(date.getTime()) : null);
            }
          };
        }
  }
}