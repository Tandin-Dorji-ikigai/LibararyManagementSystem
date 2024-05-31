package command;
/**

 * This is an interface that defines a single method, execute().

 * It represents a command that can be executed by an invoker.

 * The command pattern is a behavioral design pattern that turns a request

 * into a stand-alone object that contains all information about the request.

 * This transformation lets you pass requests as a method argument, delay or

 * queue a request's execution, and support undoable operations.

 */
public interface Command {
    void execute();
}
