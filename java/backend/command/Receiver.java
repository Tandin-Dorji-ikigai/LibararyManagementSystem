package command;

/**

 * This is the receiver class that contains the actual business logic that

 * needs to be executed when a command is invoked.

 * In the command pattern, the receiver is the object that knows the actual

 * steps to perform a particular operation.

 */
import product.Book;

import java.util.List;
import java.util.stream.Collectors;

public class Receiver {
    public List<Book> search(List<Book> books, String query) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                        book.getCategory().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}