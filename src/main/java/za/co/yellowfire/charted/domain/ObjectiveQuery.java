package za.co.yellowfire.charted.domain;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface ObjectiveQuery {
    String objective_id = "objective_id";
    String objective_name = "objective_name";
    String objective_expression = "objective_expression";
    String create_ts = "create_ts";
    String update_ts = "update_ts";
    String fields = objective_id + "," + objective_name + "," + objective_expression + "," + create_ts + "," + update_ts;
    String table = "objective";

    @SqlUpdate("insert into " + table + "(" +
            objective_name + "," + objective_expression +
            ") values (" +
            ":" + objective_name + ",:" + objective_expression + ")")
    void insert(@BindBean Objective objective);

    @SqlQuery("select " + fields + " from " + table + " where " + objective_id + " = :" + objective_id)
    @Mapper(ObjectiveMapper.class)
    Objective findById(@Bind(objective_id) long id);
}
