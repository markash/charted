package za.co.yellowfire.charted.database;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface Database extends Serializable {
    /**
     * Queries for a list of results
     * @param query The query
     * @return The list of results
     * @throws za.co.yellowfire.charted.database.DataAccessException If there was an exception
     */
    List query(NamedQuery query) throws DataAccessException;
}
