package strategy;

/**

 * This is a concrete implementation of the `Strategy` interface.

 * The `SearchStrategy` class is a concrete implementation of the `Strategy`

 * interface that defines the behavior of searching for books.

 */

import product.Book;

import java.util.List;

public interface SearchStrategy {
    void search(List<Book> books, String query);
}