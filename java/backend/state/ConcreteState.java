package state;

import product.Book;

/**
 * 
 * This is a concrete implementation of the `State` interface.
 * 
 * The `ConcreteState` class represents a specific state that an object
 * 
 * can be in.
 * 
 */
public class ConcreteState implements State {
    @Override
    public void handle(Book book) {
        System.out.println("Handling state for book: " + book.getTitle());
    }
}