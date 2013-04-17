package za.co.yellowfire.charted.components;

import org.apache.tapestry5.dom.Element;

public class Text extends AbstractBootstrapEditable {

    public Text() { }

    @Override
    protected void contributeDataParams(Element element){
        element.attribute("data-type", "text");
    }

    @Override
    protected Object toValue(String value) {
        System.out.println("VALUE ********" + value);
        return value;
    }
}