package strategy;

/**

 * This is an interface for a strategy object.

 * The `Strategy` interface is an interface for a strategy object that defines

 * the behavior of an operation. The `Strategy` interface has a single method,

 * `execute`, which is used to perform the operation.

 */
import product.Book;
import database.*;
import java.util.List;

public class ConcreteSearchStrategy implements SearchStrategy {
    @Override
    public void search(List<Book> books, String query) {
        // Read books from file
        List<Book> booksFromFile = FileUtil.readBooksFromFile();

        boolean found = false;
        query = query.toLowerCase();

        for (Book book : booksFromFile) {
            if (book.getTitle().toLowerCase().contains(query)) {
                System.out.println("Book found:");
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Category: " + book.getCategory());
                System.out.println("Availability: " + book.getAvailability());
                System.out.println("-----");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with the given title.");
        }
    }
}