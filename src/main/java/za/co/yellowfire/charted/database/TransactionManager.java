package za.co.yellowfire.charted.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class TransactionManager {

    private final EntityManager entityManager;
    private EntityTransaction tx;
    private boolean local;

    public TransactionManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        try {
            this.tx = entityManager.getTransaction();
            this.local = true;
        } catch (IllegalStateException e) {
            this.local = false;
        }
    }

    public void beginIfLocal() {
        if (local && !tx.isActive()) {
            tx.begin();
        }
    }

    public void commitIfLocal() {
        if (local && tx.isActive()) {
            tx.commit();
        }
    }

    public void rollbackIfLocal() {
        if (local && tx.isActive()) {
            tx.rollback();
        }
    }

    public boolean getRollbackOnly() {
        if (local) {
            return tx.getRollbackOnly();
        }
        return false;
    }

    public void setRollbackOnly() {
        if (local) {
            tx.setRollbackOnly();
        }
    }
}
