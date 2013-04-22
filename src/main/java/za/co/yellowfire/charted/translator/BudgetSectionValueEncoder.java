package za.co.yellowfire.charted.translator;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import za.co.yellowfire.charted.domain.BudgetSection;
import za.co.yellowfire.charted.domain.BudgetSectionDao;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetSectionValueEncoder implements ValueEncoder<BudgetSection> {

    @Inject
    private BudgetSectionDao sectionDao;

    /**
     * Converts a value into a client-side representation. The value should be parseable by {@link #toValue(String)}. In
     * some cases, what is returned is an identifier used to locate the true object, rather than a string representation
     * of the value itself.
     *
     * @param value to be encoded
     * @return a string representation of the value, or the value's identity
     */
    @Override
    public String toClient(BudgetSection value) {
        return Long.toString(value.getId());
    }

    /**
     * Converts a client-side representation, provided by {@link #toClient(Object)}, back into a server-side value.
     *
     * @param clientValue string representation of the value's identity
     * @return the corresponding entity, or null if not found
     */
    @Override
    public BudgetSection toValue(String clientValue) {
        return sectionDao.findById(Long.parseLong(clientValue));
    }
}
