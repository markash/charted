package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;
import java.util.Date;

@BindingAnnotation(BindEndDate.BudgetBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindEndDate {

    public static class BudgetBinderFactory implements BinderFactory {

        public Binder build(Annotation annotation) {
          return new Binder<BindEndDate, Date>() {
            public void bind(SQLStatement q, BindEndDate bind, Date date) {
                q.bind("end_date", date != null ? new java.sql.Date(date.getTime()) : null);
            }
          };
        }
  }
}