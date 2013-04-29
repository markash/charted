package za.co.yellowfire.charted.translator;

import za.co.yellowfire.charted.domain.DomainObject;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class AjaxLoopHolder<T extends DomainObject> {
    private long id;
    private T object;
    private boolean removed;

    public AjaxLoopHolder(T object) {
        this.id = object.getId();
        this.object = object;
        this.removed = false;
    }

    public AjaxLoopHolder(long id, T object, boolean removed) {
        this.id = id;
        this.object = object;
        this.removed = removed;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public T getObject() { return object; }
    public void setObject(T object) { this.object = object; }
    public boolean isRemoved() { return removed; }
    public void setRemoved(boolean removed) { this.removed = removed; }
}
