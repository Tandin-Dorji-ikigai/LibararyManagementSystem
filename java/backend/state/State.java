package state;

/**

 * This is an interface that defines the behavior of a state.

 * The `State` interface has a single method, `handle`, which is

 * used to handle the state of an object.

 */

import product.Book;

public interface State {
    void handle(Book book);
}