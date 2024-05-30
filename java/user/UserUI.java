package user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import command.Command;
import command.Receiver;
import command.SearchCommand;
import product.Book;
import Library.LibraryManagementSystem;
import database.FileUtil;
import database.UserUtil;

public class UserUI {
    private Scene userScene;
    private TableView<Book> userTable;
    private Label userMessageLabel;

    public UserUI(Stage stage, LibraryManagementSystem mainApp) {
        userTable = createTableView();
        userTable.setItems(mainApp.getData());

        TableColumn<Book, Void> borrowColumn = new TableColumn<>("Action");
        borrowColumn.setCellFactory(param -> new TableCell<Book, Void>() {
            private final Button borrowButton = new Button("Borrow");

            {
                borrowButton.setOnAction(event -> {
                    String currentUsername = UserUtil.getCurrentUsername();
                    Book book = getTableView().getItems().get(getIndex());
                    mainApp.borrowBook(book);
                    System.out.println("Borrowed book: " + book.getTitle());
                    FileUtil.updateBookAvailability(book);
                    FileUtil.saveNotificationToFile(
                            "Book borrowed by user " + currentUsername + ": " + book.getTitle());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Book book = getTableView().getItems().get(getIndex());
                    borrowButton.setDisable(!book.isAvailable());
                    setGraphic(borrowButton);
                }
            }
        });

        userTable.getColumns().add(borrowColumn);

        TableColumn<Book, Void> returnColumn = new TableColumn<>("Return");
        returnColumn.setCellFactory(param -> new TableCell<Book, Void>() {
            private final Button returnButton = new Button("Return");

            {
                returnButton.setOnAction(event -> {
                    String currentUsername = UserUtil.getCurrentUsername();
                    Book book = getTableView().getItems().get(getIndex());
                    mainApp.returnBook(book);
                    System.out.println("Returned book: " + book.getTitle());
                    FileUtil.updateBookAvailability(book);
                    FileUtil.saveNotificationToFile("Book Returned by " + currentUsername + ": " + book.getTitle());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Book book = getTableView().getItems().get(getIndex());
                    returnButton.setDisable(book.isAvailable()); // Disable if book is not borrowed
                    setGraphic(returnButton);
                }
            }
        });

        userTable.getColumns().add(returnColumn);

        TextField userSearchInput = new TextField();
        userSearchInput.setPromptText("Search by Title, Author, or Category");
        Button userSearchButton = new Button("Search");
        userSearchButton.setOnAction(e -> {
            String query = userSearchInput.getText();
            Receiver receiver = new Receiver();
            Command searchCommand = new SearchCommand(receiver, mainApp.getData(), query, userTable);
            searchCommand.execute();
        });

        Button userLogoutButton = new Button("Logout");
        userLogoutButton.setOnAction(e -> {
            UserUtil.deleteSessionData();
            stage.setScene(mainApp.getLoginUI().getLoginScene());
        });

        Button viewNotificationsButton = new Button("View Notifications");
        viewNotificationsButton.setOnAction(e -> mainApp.showNotifications());

        userMessageLabel = new Label();
        userMessageLabel.setFont(new Font("Arial", 14));

        HBox userSearchBox = new HBox();
        userSearchBox.setPadding(new Insets(10));
        userSearchBox.setSpacing(10);
        userSearchBox.setAlignment(Pos.CENTER);
        userSearchBox.getChildren().addAll(userSearchInput, userSearchButton, userLogoutButton,
                viewNotificationsButton);

        VBox userBox = new VBox();
        userBox.setPadding(new Insets(10));
        userBox.setSpacing(10);
        userBox.setAlignment(Pos.CENTER);
        userBox.getChildren().addAll(createHeader("User Panel"), userTable, userSearchBox, userMessageLabel);
        userScene = new Scene(userBox);
        userScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    }

    @SuppressWarnings("unchecked")
    private TableView<Book> createTableView() {
        TableView<Book> tableView = new TableView<>();

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setMinWidth(200);

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setMinWidth(200);

        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setMinWidth(200);

        TableColumn<Book, Boolean> availableColumn = new TableColumn<>("Available");
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        availableColumn.setMinWidth(100);

        tableView.getColumns().addAll(titleColumn, authorColumn, categoryColumn, availableColumn);

        return tableView;
    }

    private Label createHeader(String text) {
        Label header = new Label(text);
        header.setFont(new Font("Arial", 24));
        return header;
    }

    public Scene getUserScene() {
        return userScene;
    }

    public TableView<Book> getUserTable() {
        return userTable;
    }

    public Label getUserMessageLabel() {
        return userMessageLabel;
    }
}
