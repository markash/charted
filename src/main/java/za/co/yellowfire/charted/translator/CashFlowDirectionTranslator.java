package za.co.yellowfire.charted.translator;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.Translator;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.internal.translator.AbstractTranslator;
import org.apache.tapestry5.services.FormSupport;
import za.co.yellowfire.charted.domain.CashFlowDirection;

/**
 * Created with IntelliJ IDEA.
 * User: marka
 * Date: 2013/04/18
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class CashFlowDirectionTranslator implements Translator<CashFlowDirection> {
    /**
     * Returns a unique name for the translator. This is used to identify the translator by name, but is also used when
     * locating override messages for the translator.
     *
     * @return unique name for the translator
     */
    @Override
    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Converts a server-side value to a client-side string. This allows for formatting of the value in a way
     * appropriate to the end user. The output client value should be parsable by
     * {@link #parseClient(org.apache.tapestry5.Field, String, String)}.
     *
     * @param value the server side value (which will not be null)
     * @return client-side value to present to the user
     */
    @Override
    public String toClient(CashFlowDirection value) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the type of the server-side value.
     *
     * @return a type
     */
    @Override
    public Class<CashFlowDirection> getType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the message key, within the application's global message catalog, normally used by this validator. This
     * is used to provide the formatted message to {@link #parseClient(org.apache.tapestry5.Field, String, String)} or
     * {@link #render(org.apache.tapestry5.Field, String, org.apache.tapestry5.MarkupWriter, org.apache.tapestry5.services.FormSupport)}.
     *
     * @return a message key
     */
    @Override
    public String getMessageKey() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Converts a submitted request value into an appropriate server side value.
     *
     * @param field       for which a value is being parsed
     * @param clientValue to convert to a server value; this will not be null, but may be blank
     * @param message     formatted validation message, either from validation messages, or from an override
     * @return equivalent server-side value (possibly null)
     * @throws org.apache.tapestry5.ValidationException
     *          if the value can not be parsed
     */
    @Override
    public CashFlowDirection parseClient(Field field, String clientValue, String message) throws ValidationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Hook used by components to allow the validator to contribute additional attributes or (more often) client-side
     * JavaScript (via the
     * {@link org.apache.tapestry5.services.FormSupport#addValidation(org.apache.tapestry5.Field, String, String, Object)}).
     *
     * @param field       the field which is currently being rendered
     * @param message     formatted validation message, either from validation messages, or from an override
     * @param writer      markup writer, allowing additional attributes to be written into the active element
     * @param formSupport used to add JavaScript
     */
    @Override
    public void render(Field field, String message, MarkupWriter writer, FormSupport formSupport) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
