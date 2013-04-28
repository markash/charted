package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import za.co.yellowfire.charted.domain.Budget;
import za.co.yellowfire.charted.domain.Transaction;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface TransactionQuery {
    String budget_category = "budget_category";
    String transaction_id = "transaction_id";
    String amount = "amount";
    String date_available = "date_available";
    String date_initiated = "date_initiated";
    String date_posted = "date_posted";
    String memo = "memo";
    String account_nbr = "account_nbr";
    String create_ts = "create_ts";
    String update_ts = "update_ts";

    String fields = transaction_id + "," + amount + "," + date_available + "," + date_initiated + "," + date_posted + "," + memo + "," + account_nbr+ "," + create_ts + "," + update_ts;
    String table = "transaction";

    @SqlQuery("insert into " + table + "(" +
            transaction_id + "," + amount + "," + date_available + "," + date_initiated +"," + date_posted + "," + memo + "," + account_nbr +
            ") values (" +
            ":" + transaction_id + ",:" + amount + ",:" + date_available + ",:" + date_initiated + ",:" + date_posted + ",:" + memo + ",:" + account_nbr + ") returning " + fields)
    @Mapper(TransactionMapper.class)
    Transaction insert(@BindTransaction Transaction transaction);

    @SqlUpdate("update " + table +
            "set " + amount + " = :" + amount + "," +
            date_available + " = :" + date_available + "," +
            date_initiated + " = :" + date_initiated + "," +
            date_posted + " = :" + date_posted + "," +
            memo + " = :" + memo + "," +
            account_nbr + " = :" + account_nbr + "," +
            " update_ts = now() " +
            " where " + transaction_id + " = :" + transaction_id +
            " returning " + fields)
    @Mapper(TransactionMapper.class)
    Transaction update(@BindTransaction Transaction transaction);

    @SqlQuery("select " + fields + " from " + table + " where transaction_id = :transaction_id")
    @Mapper(TransactionMapper.class)
    Transaction findById(@Bind("transaction_id") String transaction_id);

    @SqlQuery("select " + fields + " from " + table)
    @Mapper(TransactionMapper.class)
    List<Transaction> findAll();



}
