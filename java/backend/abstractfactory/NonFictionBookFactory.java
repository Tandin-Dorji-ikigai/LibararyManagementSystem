package abstractfactory;

import factory.Member;
import product.Book;

/**
 * This is a concrete implementation of the AbstractBookFactory interface.
 * It creates instances of specific products (Book and Member) that belong
 * to a specific product family.
 */
public class NonFictionBookFactory implements AbstractBookFactory {
    @Override
    public Book createBook(String title, String author, Boolean availability) {
        return new Book(title, author, "Non-Fiction", availability);
    }

    @Override
    public Member createMember() {
        return new Member();
    }
}
