package za.co.yellowfire.charted.support;

import org.apache.tapestry5.ValueEncoder;
import za.co.yellowfire.charted.domain.BudgetCategory;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface SelectSupport<T> extends Serializable {
    /**
     * Returns the encoder used for the model items
     * @return The value encoder
     */
    ValueEncoder<T> getEncoder();

    /**
     * Returns the list model
     * @return The list model
     */
    List<T> getModel();
}
