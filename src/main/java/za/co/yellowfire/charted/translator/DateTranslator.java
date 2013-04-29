package za.co.yellowfire.charted.translator;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.services.FormSupport;
import za.co.yellowfire.charted.domain.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class DateTranslator extends AbstractTranslator<Date> {

    public DateTranslator() {
        super("date", Date.class, "date-as-a-string");
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
    public String toClient(Date value) {
        return value == null ? null : new SimpleDateFormat("yyyy-MM-dd").format(value);
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
    public Date parseClient(Field field, String clientValue, String message) throws ValidationException {
        try {
            return clientValue == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(clientValue);
        } catch (ParseException e) {
            throw new ValidationException("Unable to parse date " + clientValue + " : " + e.getMessage());
        }

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
    }
}
