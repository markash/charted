package za.co.yellowfire.charted.pages.budget;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.Date;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetNew {

    @Inject
    private Messages messages;

    @Property
    private Date startDate = new Date();

    @Property
    private Date endDate;

    @Environmental
    private JavaScriptSupport javaScriptSupport;

    void setupRender() {
        javaScriptSupport.addScript("$('#startDate').datepicker()");
    }
}
