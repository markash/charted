package za.co.yellowfire.charted.translator;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import za.co.yellowfire.charted.domain.BudgetAllocation;
import za.co.yellowfire.charted.domain.DomainObject;
import za.co.yellowfire.charted.domain.dao.BaseDao;
import za.co.yellowfire.charted.domain.dao.BudgetAllocationDao;
import za.co.yellowfire.charted.domain.dao.DomainObjectDao;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class DomainObjectValueEncoder<T extends DomainObject> implements ValueEncoder<T> {

    private DomainObjectDao<T> dao;

    public DomainObjectValueEncoder(DomainObjectDao<T> dao) {
        this.dao = dao;
    }

    /**
     * Converts a value into a client-side representation. The value should be parsable by {@link #toValue(String)}. In
     * some cases, what is returned is an identifier used to locate the true object, rather than a string representation
     * of the value itself.
     *
     * @param value to be encoded
     * @return a string representation of the value, or the value's identity
     */
    @Override
    public String toClient(T value) {
        return Long.toString(value.getId());
    }

    /**
     * Converts a client-side representation, provided by {@link #toClient(Object)}, back into a server-side value.
     *
     * @param clientValue string representation of the value's identity
     * @return the corresponding entity, or null if not found
     */
    @Override
    public T toValue(String clientValue) {
        return dao.findById(Long.parseLong(clientValue));
    }
}
