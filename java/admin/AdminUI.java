package admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import product.Book;
import database.UserUtil;
import Library.AddBook;
import Library.LibraryManagementSystem;

public class AdminUI {
    private Scene adminScene;
    private TableView<Book> adminTable;
    private TableView<Book> searchTable;
    private Label adminMessageLabel;
    private LibraryManagementSystem mainApp;

    public AdminUI(Stage stage, LibraryManagementSystem mainApp) {
        this.mainApp = mainApp;
        adminTable = createTableView();
        adminTable.setItems(mainApp.getData());

        searchTable = createSearchTableView();

        TextField titleInput = new TextField();
        titleInput.setPromptText("Title");

        TextField authorInput = new TextField();
        authorInput.setPromptText("Author");

        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("Category");
        categoryComboBox.setItems(AddBook.getCategories());

        CheckBox availableInput = new CheckBox("Available");

        Button addButton = new Button("Add Book");
        addButton.setOnAction(e -> mainApp.addBook(titleInput, authorInput, categoryComboBox, availableInput));

        Button deleteButton = new Button("Delete Book");
        deleteButton.setOnAction(e -> mainApp.deleteBook());

        Button updateButton = new Button("Update Book");
        updateButton.setOnAction(e -> mainApp.updateBook(titleInput, authorInput, categoryComboBox, availableInput));

        TextField searchTitleInput = new TextField();
        searchTitleInput.setPromptText("Title");

        TextField searchAuthorInput = new TextField();
        searchAuthorInput.setPromptText("Author");

        TextField searchCategoryInput = new TextField();
        searchCategoryInput.setPromptText("Category");

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> mainApp.searchBooks(searchTitleInput.getText(), searchAuthorInput.getText(),
                searchCategoryInput.getText()));

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            UserUtil.deleteSessionData();
            stage.setScene(mainApp.getLoginUI().getLoginScene());
        });
        
        Button viewNotificationsButton = new Button("View Notifications");
        viewNotificationsButton.setOnAction(e -> mainApp.showNotifications());

        adminMessageLabel = new Label();
        adminMessageLabel.setFont(new Font("Arial", 14));

        HBox inputBox = new HBox();
        inputBox.setPadding(new Insets(10));
        inputBox.setSpacing(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.getChildren().addAll(titleInput, authorInput, categoryComboBox, availableInput, addButton,
                deleteButton,
                updateButton);

        HBox searchBox = new HBox();
        searchBox.setPadding(new Insets(10));
        searchBox.setSpacing(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(searchTitleInput, searchAuthorInput, searchCategoryInput, searchButton,
                logoutButton, viewNotificationsButton);

        VBox adminBox = new VBox();
        adminBox.setPadding(new Insets(10));
        adminBox.setSpacing(10);
        adminBox.setAlignment(Pos.CENTER);
        adminBox.getChildren().addAll(createHeader("Admin Panel"), adminTable, inputBox, searchBox, adminMessageLabel);
        adminScene = new Scene(adminBox);
        adminScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
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

    private TableView<Book> createSearchTableView() {
        TableView<Book> tableView = new TableView<>();

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setMinWidth(200);

        tableView.getColumns().add(titleColumn);

        return tableView;
    }

    private Label createHeader(String text) {
        Label header = new Label(text);
        header.setFont(new Font("Arial", 24));
        return header;
    }

    public Scene getAdminScene() {
        return adminScene;
    }

    public TableView<Book> getAdminTable() {
        return adminTable;
    }

    public Label getAdminMessageLabel() {
        return adminMessageLabel;
    }
}
