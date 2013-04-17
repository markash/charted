package za.co.yellowfire.charted.domain;

import java.io.Serializable;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class DaoSettings implements Serializable {
    private String url;
    private String user;
    private String password;

    public DaoSettings(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
