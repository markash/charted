package za.co.yellowfire.charted.pages.budget;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.Date;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
@Import(library = {
        "context:js/jquery-1.9.1.js",
        "context:js/bootstrap-datepicker.js",
        "context:js/budgetnew.js"})
public class BudgetNew {

    @Inject
    private Messages messages;

    @Property
    private String name;

    @Property
    private Date startDate = new Date();

    @Property
    private Date endDate = new Date();

    @Environmental
    private JavaScriptSupport javaScriptSupport;

    void setupRender() {
        //javaScriptSupport.addScript("$('#endDate').change(function() {alert})")
        //javaScriptSupport.addScript("$('#startDate').datepicker()");
    }
}
