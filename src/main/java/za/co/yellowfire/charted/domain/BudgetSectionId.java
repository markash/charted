package za.co.yellowfire.charted.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class BudgetSectionId implements Serializable {

    private String name;
    private Date budget;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBudget() {
        return budget;
    }

    public void setBudget(Date budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetSectionId that = (BudgetSectionId) o;

        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        return result;
    }
}
