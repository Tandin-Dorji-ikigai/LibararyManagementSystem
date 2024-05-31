package state;

import product.*;
public class UpdateBookState implements State {
    @Override
    public void handle(Book book){
        book.setCurrentState(this);
    }

    @Override
    public String toString() {
        return "UpdateBookState";
    }
}
