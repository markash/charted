package za.co.yellowfire.charted.domain;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class RowStyle {
    private String name;
    private String style;

    public RowStyle(String name, String style) {
        this.name = name;
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public String getStyle() {
        return style;
    }

    public static RowStyle forBackgroundColor(Color color) {
        return new RowStyle(color.name(), backgroundColor(color.html()));
    }

    protected static String backgroundColor(String value) {
        return "background-color: " + value;
    }
}
