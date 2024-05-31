package abstractfactory;

import factory.Member;
import product.Book;

/**
 * This is an abstract factory interface defining methods to create
 * instances of specific products (Book and Member) that belong to
 * a specific product family.
 */
public interface AbstractBookFactory {
    Book createBook(String title, String author, Boolean availability);

    Member createMember();
}
