package state;

import product.Book;

public class AvailableState implements State {
    @Override
    public void handle(Book book) {
        System.out.println("The book is now available.");
        book.setCurrentState(this);
    }

    @Override
    public String toString() {
        return "Available";
    }
}
