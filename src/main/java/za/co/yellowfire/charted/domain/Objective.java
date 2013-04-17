package za.co.yellowfire.charted.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class Objective implements Serializable {

    @Id
    @Column(name = "objective_id")
    private Long id;

    @Column(name = "objective_name")
    private String name;

    @Column(name = "objective_expression")
    private String expression;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_ts")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_ts")
    private Date updated;

    public Objective() { }

    public Objective(Long id) {
        this(id, null, null);
    }

    public Objective(String name, String expression) {
        this(null, name, expression);
    }

    public Objective(Long id, String name, String expression) {
        this.id = id;
        this.name = name;
        this.expression = expression;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    @Override
    public String toString() {
        return "Objective {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", expression='" + expression + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
