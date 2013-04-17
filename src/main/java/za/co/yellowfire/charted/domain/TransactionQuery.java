package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface TransactionQuery {
    String budget_category = "budget_category";

    String fields = "id, amount, date_available, date_initiated, date_posted, memo, account_nbr, " + budget_category;
    String table = "transaction";

    @SqlQuery("select " + fields + " from " + table + " where id = :id")
    @Mapper(TransactionMapper.class)
    Transaction findById(@Bind("id") String id);

    @SqlQuery("select " + fields + " from " + table)
    @Mapper(TransactionMapper.class)
    List<Transaction> findAll();
}
