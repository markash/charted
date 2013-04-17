package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Date;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface BudgetQuery {
    String budget_id = "budget_id";
    String start_date = "start_date";
    String end_date = "end_date";
    String objective_id = "objective_id";
    String create_ts = "create_ts";
    String update_ts = "update_ts";
    String fields = budget_id + "," + start_date + "," + end_date + "," + objective_id + "," + create_ts + "," + update_ts;
    String table = "budget";

    @SqlUpdate("insert into " + table + "(" +
            start_date + "," + end_date + "," + objective_id +
            ") values (" +
            ":" + start_date + ",:" + end_date + ",:" + objective_id + ")")
    void insert(@BindBudget Budget budget);

    @SqlQuery("select " + fields + " from " + table + " where " + budget_id + " = :" + budget_id)
    @Mapper(BudgetMapper.class)
    Budget findById(@Bind(budget_id) long id);

    @SqlQuery("select " + fields + " from " + table + " where " + start_date + " <= :date and :date <= " + end_date)
    @Mapper(BudgetMapper.class)
    Budget findForDate(@BindDate Date date);
}
