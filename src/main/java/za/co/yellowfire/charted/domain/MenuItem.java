package za.co.yellowfire.charted.domain;

import java.io.Serializable;

/**
 */
public class MenuItem implements Serializable {
    private String name;
    private String url;

    public MenuItem(String name) {
        this(name, name);
    }

    public MenuItem(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
