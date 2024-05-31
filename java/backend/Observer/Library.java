package Observer;

import java.util.ArrayList;
import java.util.List;

import database.FileUtil;

/**
 * This is a class that represents a library that implements the Subject
 * interface
 * 
 * from the Observer design pattern.
 * 
 * The `Library` class maintains a list of observers that are interested in
 * receiving
 * 
 * updates from the library.
 * 
 * The `Library` class provides methods for attaching and detaching observers,
 * 
 * and for notifying all observers when an update is available.
 */

public class Library implements Subject {
    private List<Observer> observers;

    public Library() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void notifyNewBook(String title, String author, String category) {
        String message = "New book added - Title: " + title + ", Author: " + author + ", Category: " + category;
        FileUtil.usernotification(message);
        notifyObservers(message);
    }

    
}
