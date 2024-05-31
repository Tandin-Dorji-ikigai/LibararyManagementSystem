package state;

import product.Book;

public class CheckedOutState implements State {
    @Override
    public void handle(Book book) {
        System.out.println("The book has been checked out.");
        book.setCurrentState(this);
    }

    @Override
    public String toString() {
        return "Checked Out";
    }
}
