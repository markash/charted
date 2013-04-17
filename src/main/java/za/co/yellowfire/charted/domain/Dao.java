package za.co.yellowfire.charted.domain;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface Dao<T> {
    T findById(Long id);
}
