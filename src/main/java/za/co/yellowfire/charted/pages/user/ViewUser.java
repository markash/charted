package za.co.yellowfire.charted.pages.user;

import za.co.yellowfire.charted.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 */
public class ViewUser {

    @PersistenceContext
    private EntityManager em;

    private User user;

    public User getUser() {
        return user;
    }

    void onActivate(String name) {
        this.user = em.find(User.class, name);
    }
}
