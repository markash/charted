package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import za.co.yellowfire.charted.domain.BudgetAllocation;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface BudgetAllocationQuery {
    String id = "id";
    String transaction_id = "transaction_id";
    String budget_category_id = "budget_category_id";
    String amount = "amount";

    String fields = id + "," + transaction_id + "," + budget_category_id + "," + amount;

    @SqlUpdate("create table if not exists budget_allocation (" +
            "id bigint primary key, " +
            "transaction_id character varying(255) not null, " +
            "budget_category_id bigint not null, " +
            "amount numeric(38, 4) not null" +
            ")")
    void createTable();

    @SqlUpdate("insert into budget_allocation (" + fields + ") values (nextval('seq_identity'), :tx_id, :bc_id, :amount)")
    void insert(@BindBudgetAllocation BudgetAllocation allocation);

    @SqlQuery("select " +
            "0 as \"ba_id\"," +
            "1 as \"ba_amount\"," +
            "tx_id," +
            "tx_amount," +
            "tx_date_available," +
            "tx_date_initiated," +
            "tx_date_posted," +
            "tx_memo," +
            "tx_account_nbr," +
            "bc_id," +
            "bc_name," +
            "bc_budget_section," +
            "bc_budget_date," +
            "bc_budget_amount," +
            "bc_color," +
            "bc_direction," +
            "bc_matches " +
            "from vw_budget_allocation where 1 = :id")
    @Mapper(BudgetAllocationMapper.class)
    BudgetAllocation findById(@Bind("id") int id);
}
