package za.co.yellowfire.charted.database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class DatabaseManager implements Database {

    @PersistenceContext(unitName = "")
    private EntityManager em;
    /**
     * Queries for a list of results
     *
     * @param query The query
     * @return The list of results
     * @throws DataAccessException
     *          If there was an exception
     */
    @Override
    public List query(NamedQuery query) throws DataAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
