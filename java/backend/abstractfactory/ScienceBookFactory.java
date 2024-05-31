package abstractfactory;

import factory.Member;
import product.Book;

public class ScienceBookFactory implements AbstractBookFactory {
    @Override
    public Book createBook(String title, String author, Boolean availability) {
        return new Book(title, author, "Science", availability);
    }

    @Override
    public Member createMember() {
        return new Member();
    }
}
