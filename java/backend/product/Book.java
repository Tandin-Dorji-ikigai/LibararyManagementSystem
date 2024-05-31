package product;

import state.State;
import state.AvailableState;
import state.CheckedOutState;
import state.UpdateBookState;;

public class Book {
    private String title;
    private String author;
    private String category;
    private boolean availability;
    private State currentState;

    public Book(String title, String author, String category, boolean availability) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.availability = availability;
        this.currentState = availability ? new AvailableState() : new CheckedOutState();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailable(boolean availability) {
        this.availability = availability;
        this.currentState = availability ? new AvailableState() : new CheckedOutState();
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public void changeState(State state) {
        this.currentState = state;
        state.handle(this);
    }

    public String getAvailability() {
        return availability ? "Available" : "Not Available";
    }

    public void updateBook(Book book, String newTitle, String newAuthor, String newCategory, boolean newAvailability) {
        // Transition to UpdateBookState
        book.changeState(new UpdateBookState());

        // Update book details
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setCategory(newCategory);
        book.setAvailable(newAvailability);

        // Transition to AvailableState or CheckedOutState based on availability
        if (newAvailability) {
            book.changeState(new AvailableState());
        } else {
            book.changeState(new CheckedOutState());
        }
    }


}
