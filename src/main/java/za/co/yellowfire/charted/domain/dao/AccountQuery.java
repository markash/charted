package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import za.co.yellowfire.charted.domain.Account;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface AccountQuery {
    String account_nbr = "account_nbr";
    String account_key = "account_key";
    String bank_id = "bank_id";
    String branch_id = "branch_id";
    String account_type = "account_type";
    String create_ts = "create_ts";
    String update_ts = "update_ts";

    String fields = account_nbr + "," + account_key + "," + bank_id + "," + branch_id + "," + account_type + "," + create_ts + "," + update_ts;
    String table = "account";

    @SqlQuery("insert into " + table + "(" +
            account_nbr + "," + account_key + "," + bank_id + "," + branch_id +"," + account_type +
            ") values (" +
            ":" + account_nbr + ",:" + account_key + ",:" + bank_id + ",:" + branch_id + ",:" + account_type + ") returning " + fields)
    @Mapper(AccountMapper.class)
    Account insert(@BindAccount za.co.yellowfire.charted.domain.Account account);

    @SqlUpdate("update " + table +
            "set " + account_key + " = :" + account_key + "," +
            bank_id + " = :" + bank_id + "," +
            branch_id + " = :" + branch_id + "," +
            account_type + " = :" + account_type + "," +
            " update_ts = now() " +
            " where " + account_nbr + " = :" + account_nbr +
            " returning " + fields)
    @Mapper(AccountMapper.class)
    Account update(@BindAccount Account account);

    @SqlQuery("select " + fields + " from " + table + " where account_nbr = :account_nbr")
    @Mapper(AccountMapper.class)
    Account findById(@Bind("account_nbr") String account_nbr);

    @SqlQuery("select " + fields + " from " + table)
    @Mapper(AccountMapper.class)
    List<Account> findAll();



}
