package za.co.yellowfire.charted.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class StatementId implements Serializable {
    private String account;
    private Date start;
    private Date end;

    public String getAccount() {
        return account;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatementId that = (StatementId) o;

        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }
}
