package Observer;
/**

 * This is an interface that defines the contract for a subject in the

 * Observer design pattern.

 * The `Subject` interface defines methods for attaching and detaching observers,

 * and for notifying all observers when an update is available.

 */
public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(String message);
}
