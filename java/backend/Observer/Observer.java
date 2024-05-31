package Observer;

/**
 * This is an interface that defines the `update` method, which is used by the

 * Observer design pattern to notify observers of a change in state.

 * The `Observer` interface is implemented by classes that want to be notified

 * of changes in state.
 */

public interface Observer {
    void update(String message);
}