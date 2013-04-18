package za.co.yellowfire.charted.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@SupportsInformalParameters
@Import(
        library = {
                "jquery.js",
                "bootstrap-editable.js"/*,
                "classpath:pines/jquery.pnotify.js"*/
        },
        stylesheet = {
                "bootstrap-editable.css"/*,
                "classpath:pines/jquery.pnotify.default.css"*/
        }
)
public abstract class AbstractBootstrapEditable implements ClientElement {

    @Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL, allowNull = false)
    private String client;

    @Parameter(autoconnect = true, required = true)
    private Object value;

    @Parameter
    private Object[] context;

    @Parameter
    private boolean disabled;

    private String assignedClientId;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private ComponentResources resources;

    @Inject
    private Request request;

    @Inject
    private TypeCoercer typeCoercer;

    protected AbstractBootstrapEditable() { }

    @BeginRender
    void begin(MarkupWriter writer) {
        assignedClientId = javaScriptSupport.allocateClientId(resources);

        System.out.println(" BEGIN" );
        if (disabled) {
            writer.element("span");
        } else {
            Element element = writer.element("span",
                    "id", getClientId(),
                    "data-name", getClientId(),
                    "data-url", getPostbackLink());

            contributeDataParams(element);
        }

        resources.renderInformalParameters(writer);
    }

    protected void contributeDataParams(Element element) {

    }

    @AfterRender
    void after(MarkupWriter writer) {
        writer.end();

        if (!disabled) {
            javaScriptSupport.addScript(
                    "jQuery('#%s').editable({" +
                            "success:function(data){ " +
                            "if(!data.success){return data.msg; }else return null;}" +
                            "});",
                    getClientId());
        }
    }

    private String getPostbackLink() {
        return resources.createEventLink("postback", context).toAbsoluteURI();
    }

    @SuppressWarnings("unchecked")
    @OnEvent("postback")
    Object submit(@RequestParameter("value") String value, EventContext context) {
        this.value = toValue(value);
        CaptureResultCallback<String> callback = new CaptureResultCallback<String>();
        boolean handled = resources.triggerContextEvent(EventConstants.SUBMIT, context, callback);

        JSONObject result = new JSONObject();

        if (handled) {
            String response = callback.getResult();
            if (response != null) {
                result.put("success", false);
                result.put("msg", response);
                return result;
            }
        }

        return result.put("success", true);
    }

    protected abstract Object toValue(String value);

    @Override
    public String getClientId() {
        return assignedClientId;
    }
}