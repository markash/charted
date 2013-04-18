package za.co.yellowfire.charted.pages.user;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.jpa.JpaGridDataSource;
import za.co.yellowfire.charted.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ListUser {

    @PersistenceContext
    private EntityManager em;

    @Property
    private User user;

    private  GridDataSource users;

    public GridDataSource getUsers() {
        if (users == null) {
            users = new JpaGridDataSource<User>(em, User.class);
        }
        return users;
    }
}
