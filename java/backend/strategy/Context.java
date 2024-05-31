package strategy;

/**

 * This is a class that uses a strategy object to perform an operation.

 * The `Context` class has a reference to a `Strategy` object, which it uses

 * to perform an operation. The `Context` class can change the `Strategy` object

 * at runtime, allowing it to alter its behavior dynamically.

 */
import product.Book;

import java.util.List;

public class Context {
    private SearchStrategy strategy;

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public void performSearch(List<Book> books, String query) {
        if (strategy != null) {
            strategy.search(books, query);
        }
    }
}