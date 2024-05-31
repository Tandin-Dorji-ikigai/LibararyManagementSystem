package composite;

import product.Book;

public class BookComponentAdapter implements Component {
    private Book book;

    public BookComponentAdapter(Book book) {
        this.book = book;
    }

    @Override
    public void add(Component component) {
        // Do nothing since a book cannot have children
    }

    @Override
    public void remove(Component component) {
        // Do nothing since a book cannot have children
    }

    @Override
    public Component getChild(int index) {
        // A book cannot have children, so return null
        return null;
    }

    @Override
    public void display() {
        // Display information about the book
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Category: " + book.getCategory());
        System.out.println("Availability: " + book.isAvailable());
    }
}
