package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface BudgetSectionQuery {
    String budget_id = "budget_id";
    String section_id = "section_id";
    String name = "name";
    String color = "color";
    String direction = "direction";
    String create_ts = "create_ts";
    String update_ts = "update_ts";
    String fields = budget_id + "," + section_id + "," + name + "," + color + "," + direction + "," + create_ts + "," + update_ts;
    String table = "budget_section";

    @SqlUpdate("insert into " + table + "(" +
            budget_id + "," + name + "," + color + "," + direction +
            ") values (" +
            ":" + budget_id + ",:" + name + ",:" + color + ",:" + direction + ")")
    void insert(@BindBean BudgetSection budgetSection);

    @SqlQuery("select " + fields + " from " + table + " where " + section_id + " = :" + section_id)
    @Mapper(BudgetSectionMapper.class)
    BudgetSection findById(@Bind(section_id) long id);

    @SqlQuery("select " + fields + " from " + table + " where " + budget_id + " = :" + budget_id)
    @Mapper(BudgetSectionMapper.class)
    List<BudgetSection> findForBudgetId(@Bind(budget_id) long id);
}
