package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import za.co.yellowfire.charted.domain.BudgetAllocation;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface BudgetAllocationQuery {
    String allocation_id = "allocation_id";
    String transaction_id = "transaction_id";
    String budget_category_id = "budget_category_id";
    String amount = "amount";
    String create_ts = "create_ts";
    String update_ts = "update_ts";

    String table = "budget_allocation";
    String fields = allocation_id + "," + transaction_id + "," + budget_category_id + "," + amount + "," + create_ts + "," + update_ts;

    @SqlQuery("insert into " + table + " (" +
            transaction_id + "," + budget_category_id + "," + amount  +
            ") values (" +
            ":" + transaction_id + ", :" + budget_category_id + ", :" + amount + ") returning " + fields)
    @Mapper(BudgetAllocationMapper.class)
    BudgetAllocation insert(@BindBudgetAllocation BudgetAllocation allocation);

    @SqlQuery("select " + fields + " from " + table + " where :allocation_id = allocation_id")
    @Mapper(BudgetAllocationMapper.class)
    BudgetAllocation findById(@Bind("allocation_id") int allocation_id);

    @SqlQuery("select " + fields + " from " + table)
    @Mapper(BudgetAllocationMapper.class)
    List<BudgetAllocation> findAll();

    @SqlQuery("select coalesce(sum(" + amount + "), 0.00) \"amount\" from budget_allocation where transaction_id = :transaction_id")
    BigDecimal sumForTransaction(@Bind("transaction_id") String transactionId);
}
