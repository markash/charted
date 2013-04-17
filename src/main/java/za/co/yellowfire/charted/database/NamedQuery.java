package za.co.yellowfire.charted.database;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.ScrollableCursor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class NamedQuery<T> implements Serializable {

    private String name;
    private Class<T> domainClass;
    private Map<String, Object> parameters;
    private EntityManager entityManager;
    private Map<String, Object> hints = new HashMap<String, Object>();

    public NamedQuery(EntityManager entityManager, Class domainClass, String name) {
        this(entityManager, domainClass, name, null);
    }

    public NamedQuery(EntityManager entityManager, Class domainClass, String name, Map<String, Object> parameters) {
        this.name = name;
        this.domainClass = domainClass;
        this.entityManager = entityManager;

        if (parameters == null) {
            this.parameters = new HashMap<String, Object>();
        } else {
            this.parameters = new HashMap<String, Object>(parameters);
        }
    }

    public String getName() {
        return name;
    }

    public void addParameter(String name, Object value) {
        this.parameters.put(name, value);
    }

    public void removeParameter(String name) {
        if (this.parameters.containsKey(name)) {
            this.parameters.remove(name);
        }
    }

    /**
     * The eclipselink.read-only hint retrieves read-only results back from the query: on nontransactional read operations, where the requested entity types are stored in the shared cache, you can request that the shared instance be returned instead of a detached copy.
     * Note: You should never modify objects returned from the shared cache.
     * @return NamedQuery
     */
    public NamedQuery<T> readonly() {
        this.hints.put(QueryHints.READ_ONLY, HintValues.TRUE);
        return this;
    }

    /**
     * The eclipselink.read-only hint retrieves read-only results back from the query: on nontransactional read operations, where the requested entity types are stored in the shared cache, you can request that the shared instance be returned instead of a detached copy.
     * Note: You should never modify objects returned from the shared cache.
     * @return NamedQuery
     */
    public NamedQuery<T> editable() {
        this.hints.put(QueryHints.READ_ONLY, HintValues.FALSE);
        return this;
    }

    /**
     * The eclipselink.refresh hint controls whether or not to update the EclipseLink session cache with objects that the query returns.
     * @return NamedQuery
     */
    public NamedQuery<T> refresh() {
        this.hints.put(QueryHints.REFRESH, HintValues.TRUE);
        return this;
    }

    /**
     * The eclipselink.batch hint supplies EclipseLink with batching information so subsequent queries of related objects can be optimized in batches instead of being retrieved one-by-one or in one large joined read. Batch reading is more efficient than joining because it avoids reading duplicate data.
     * Batching is only allowed on queries that have a single object in their select clause.
     * Valid values: a single-valued relationship path expression.
     * Note: Use dot notation to access nested attributes. For example, to batch-read an employee's manager's address, specify e.manager.address
     * @param expression single-valued relationship path expression
     * @return NamedQuery
     */
    public NamedQuery<T> batch(String expression) {
        this.hints.put(QueryHints.BATCH, expression);
        return this;
    }

    private Query createQuery() {
        Query query = entityManager.createNamedQuery(getName(), domainClass);
        for (Map.Entry<String, Object> param : parameters.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        for (Map.Entry<String, Object> hint : hints.entrySet()) {
            query.setHint(hint.getKey(), hint.getValue());
        }
        return query;
    }

    public List<T> results() {
        return (List<T>) createQuery().getResultList();
    }

    public T single() {
        return (T) createQuery().getSingleResult();
    }

    public ScrollableCursor cursor() {
        Query query = createQuery();
        query.setHint(QueryHints.SCROLLABLE_CURSOR, HintValues.TRUE);
        return (ScrollableCursor) query.getSingleResult();
    }
}
