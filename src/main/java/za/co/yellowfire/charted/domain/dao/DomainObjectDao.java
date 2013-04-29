package za.co.yellowfire.charted.domain.dao;

import za.co.yellowfire.charted.domain.Dao;
import za.co.yellowfire.charted.domain.DomainObject;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface DomainObjectDao<T extends DomainObject> extends Dao<T> {
    /**
     * Finds the persistent object using an id
     * @param id The id of the object
     * @return The object
     */
    T findById(Long id);
}
