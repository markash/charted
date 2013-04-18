package za.co.yellowfire.charted.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class MenuSection implements Serializable {
    private String name;
    private String url;
    private List<MenuItem> items = new ArrayList<MenuItem>();

    public MenuSection() { }

    public MenuSection(String name) {
        this(name, name, new MenuItem[0]);
    }

    public MenuSection(String name, String url) {
        this(name, url, new MenuItem[0]);
    }

    public MenuSection(String name, MenuItem[] items) {
        this(name, name, Arrays.asList(items));
    }

    public MenuSection(String name, String url, MenuItem[] items) {
        this(name, url, Arrays.asList(items));
    }

    public MenuSection(String name, String url, List<MenuItem> items) {
        this.name = name;
        this.url = url;
        this.items = items;
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

    public MenuItem[] getItems() {
        return items.toArray(new MenuItem[items.size()]);
    }

    public void setItems(List<MenuItem> items) {
        this.items.clear();
        if (items != null) {
            this.items.addAll(items);
        }
    }
}
