package za.co.yellowfire.charted.translator;

import org.apache.tapestry5.Translator;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public abstract class AbstractTranslator<T> implements Translator<T> {
    private final String name;
    private final Class<T> type;
    private final String messageKey;

    protected AbstractTranslator(String name, Class<T> type, String messageKey) {
        this.name = name;
        this.type = type;
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Class<T> getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
