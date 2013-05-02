package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import za.co.yellowfire.charted.domain.BudgetCategory;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface BudgetCategoryQuery {
    String section_id = "section_id";
    String category_id = "category_id";
    String name = "name";
    String color = "color";
    String direction = "direction";
    String matches = "matches";
    String budget_amount = "budget_amount";
    String create_ts = "create_ts";
    String update_ts = "update_ts";
    String fields = category_id + "," + section_id + "," + name + "," + color + "," + direction + "," + matches + "," + budget_amount + "," + create_ts + "," + update_ts;
    String table = "budget_category";

    @SqlQuery("insert into " + table + "(" +
            section_id + "," + name + "," + color + "," + direction + "," + matches + "," + budget_amount +
            ") values (" +
            ":" + section_id + ",:" + name + ",:" + color + ",:" + direction + ",:" + matches + ",:" + budget_amount + ")" +
            " returning " + fields)
    @Mapper(BudgetCategoryMapper.class)
    BudgetCategory insert(@BindBudgetCategory BudgetCategory budgetCategory);

    @SqlQuery("update " + table +
            "set " + section_id + " = :" + section_id + "," +
            name + " = :" + section_id + "," +
            color + " = :" + section_id + "," +
            direction + " = :" + section_id + "," +
            matches + " = :" + section_id + "," +
            budget_amount + " = :" + section_id + "," +
            " update_ts = now() " +
            " where " + category_id + " = :" + category_id +
            " returning " + fields)
    @Mapper(BudgetCategoryMapper.class)
    BudgetCategory update(@BindBudgetCategory BudgetCategory budgetCategory);

    @SqlQuery("select " + fields + " from " + table)
    @Mapper(BudgetCategoryMapper.class)
    List<BudgetCategory> findAll();

    @SqlQuery("select " + fields + " from " + table + " where " + category_id + " = :" + category_id)
    @Mapper(BudgetCategoryMapper.class)
    BudgetCategory findById(@Bind(category_id) long id);

    @SqlQuery("select " + fields + " from " + table + " where " + section_id + " = :" + section_id)
    @Mapper(BudgetCategoryMapper.class)
    List<BudgetCategory> findForSectionId(@Bind(section_id) long id);
}
