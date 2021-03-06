package za.co.yellowfire.charted.domain.dao;

import org.skife.jdbi.v2.DBI;
import za.co.yellowfire.charted.domain.Dao;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public abstract class BaseDao<T> implements Dao<T> {

    protected DBI dbi;

    public BaseDao(String url, String user, String password) {
        this.dbi = new DBI(url, user, password);
    }
}
