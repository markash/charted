package za.co.yellowfire.charted.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
@Import(library = {
        "context:js/bootstrap.js",
        "context:js/jquery-1.9.1.js",
        "context:js/bootstrap-datepicker.js"},
        stylesheet = {"context:css/datepicker.css"})
@Events(EventConstants.VALIDATE)
public class DatePicker extends AbstractField {

    @Parameter(required = true, principal = true, autoconnect = true)
    private Date value;

    /**
     * The format used to format <em>and parse</em> dates. This is typically specified as a string which is coerced to a
     * DateFormat. You should be aware that using a date format with a two digit year is problematic: Java (not
     * Tapestry) may get confused about the century.
     */
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String format;


    /**
     * The object that will perform input validation (which occurs after translation). The translate binding prefix is
     * generally used to provide this object in a declarative fashion.
     */
    @Parameter(defaultPrefix = BindingConstants.VALIDATE)
    @SuppressWarnings("unchecked")
    private FieldValidator<Object> validate;


    /**
     * Used to override the component's message catalog.
     *
     * @since 5.2.0.0
     * @deprecated Since 5.4; override the global message key "core-date-value-not-parsable" instead (see {@link org.apache.tapestry5.services.messages.ComponentMessagesSource})
     */
    @Parameter("componentResources.messages")
    private Messages messages;

    private static final String RESULT = "result";
    private static final String ERROR = "error";
    private static final String INPUT_PARAMETER = "input";

    /**
     * Ajax event handler, used when initiating the popup. The client sends the input value form the field to the server
     * to parse it according to the server-side format. The response contains a "result" key of the formatted date in a
     * format acceptable to the JavaScript Date() constructor. Alternately, an "error" key indicates the the input was
     * not formatted correct.
     */
    JSONObject onParse(@RequestParameter(INPUT_PARAMETER) String input)
    {
        JSONObject response = new JSONObject();

        try {
            Date date = getDateFormat().parse(input);

            response.put(RESULT, date.getTime());
        } catch (ParseException ex) {
            response.put(ERROR, ex.getMessage());
        }

        return response;
    }

    /**
     * Ajax event handler, used after the client-side popup completes. The client sends the date, formatted as
     * milliseconds since the epoch, to the server, which reformats it according to the server side format and returns
     * the result.
     */
    JSONObject onFormat(@RequestParameter(INPUT_PARAMETER) String input) {
        JSONObject response = new JSONObject();

        try {
            long millis = Long.parseLong(input);

            Date date = new Date(millis);

            response.put(RESULT, getDateFormat().format(date));
        } catch (NumberFormatException ex) {
            response.put(ERROR, ex.getMessage());
        }

        return response;
    }

    void beginRender(MarkupWriter writer) {

        String value = validationTracker.getInput(this);

        /*
        <div class="input-append date" id="dp3" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
            <input class="span2" size="16" type="text" value="12-02-2012" />
            <span class="add-on"><i class="icon-th"></i></span>
        </div>
         */
        if (value == null) {
            value = formatCurrentValue();
        }


        String clientId = getClientId();

        writer.element("div",
                "class",            "input-append date",
                "data-date",        value,
                "data-date-format", format
                );

        writer.element("input",
                "id",               clientId,
                "name",             getControlName(),
                "type",             "text",
                "class",            "span2",
                "size",             "16",
                "value",            value);

        writeDisabled(writer);

        putPropertyNameIntoBeanValidationContext("value");

        validate.render(writer);

        removePropertyNameFromBeanValidationContext();

        resources.renderInformalParameters(writer);

        decorateInsideField();

        writer.end();

        writer.element("span",
                "class",            "add-on");

        writer.element("i",
                "class",            "icon-calendar");

        writer.end();
        writer.end();



        writer.end(); // outer div
    }

    private void writeDisabled(MarkupWriter writer) {
        if (isDisabled()) {
            writer.attributes("disabled", "disabled");
        }
    }

    private String formatCurrentValue() {
        if (value == null)
            return "";

        return getDateFormat().format(value);
    }

    private DateFormat getDateFormat() {
        final String javaFormat = format.replace("mm", "MM");
        return new SimpleDateFormat(javaFormat);
    }

    @Override
    protected void processSubmission(String controlName) {
        String value = request.getParameter(controlName);

        validationTracker.recordInput(this, value);

        Date parsedValue = null;

        try {
            if (InternalUtils.isNonBlank(value))
                parsedValue = getDateFormat().parse(value);
        } catch (ParseException ex) {
            validationTracker.recordError(this, messages.format("core-date-value-not-parseable", value));
            return;
        }

        putPropertyNameIntoBeanValidationContext("value");
        try {
            fieldValidationSupport.validate(parsedValue, resources, validate);

            this.value = parsedValue;
        } catch (ValidationException ex) {
            validationTracker.recordError(this, ex.getMessage());
        }

        removePropertyNameFromBeanValidationContext();
    }

    void injectResources(ComponentResources resources) {
        this.resources = resources;
    }

    @Override
    public boolean isRequired() {
        return validate.isRequired();
    }
}
