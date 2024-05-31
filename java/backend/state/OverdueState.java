package state;

import product.Book;

public class OverdueState implements State {
    @Override
    public void handle(Book book) {
        System.out.println("The book is overdue.");
        book.setCurrentState(this);
    }

    @Override
    public String toString() {
        return "Overdue";
    }
}
