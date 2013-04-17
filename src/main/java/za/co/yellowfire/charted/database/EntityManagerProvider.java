package za.co.yellowfire.charted.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class EntityManagerProvider {

    public static EntityManager forContainer() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("charted");
        return factory.createEntityManager();
    }
}
