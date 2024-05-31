package command;

import javafx.collections.FXCollections;

/**
 * 
 * This is a concrete implementation of the `Command` interface that represents
 * 
 * a search command.
 * 
 * The `SearchCommand` class contains a reference to the `Receiver` object,
 * 
 * which is the object that will execute the actual search action.
 * 
 * When the `execute()` method is called on the `SearchCommand` object, it will
 * 
 * call the `searchAction()` method on the `Receiver` object.
 * 
 */

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import product.Book;
import java.util.List;

public class SearchCommand implements Command {
    private Receiver receiver;
    private ObservableList<Book> data;
    private String query;
    private TableView<Book> tableView;

    public SearchCommand(Receiver receiver, ObservableList<Book> data, String query, TableView<Book> tableView) {
        this.receiver = receiver;
        this.data = data;
        this.query = query;
        this.tableView = tableView;
    }

    @Override
    public void execute() {
        List<Book> searchResults = receiver.search(data, query);
        tableView.setItems(FXCollections.observableArrayList(searchResults));
    }
}