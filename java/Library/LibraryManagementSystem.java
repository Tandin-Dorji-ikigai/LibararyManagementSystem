package Library;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.LoginUI;
import signup.SignupUI;
import user.UserUI;
import admin.AdminUI;
import database.FileUtil;
import product.Book;
import database.UserUtil;
import java.util.List;
import java.util.Map;

public class LibraryManagementSystem extends Application {

    private Stage stage;
    private Scene notificationScene;
    private ObservableList<Book> data;
    private ObservableList<String> notifications;
    private String currentUserRole;
    private LoginUI loginUI;
    private SignupUI signupUI;
    private AdminUI adminUI;
    private UserUI userUI;

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Library Management System");

        // Setup data
        List<Book> booksFromFile = FileUtil.readBooksFromFile();
        data = FXCollections.observableArrayList(booksFromFile);

        notifications = FXCollections.observableArrayList();

        // Initialize UI Components
        loginUI = new LoginUI(stage, this);
        signupUI = new SignupUI(stage, this);
        adminUI = new AdminUI(stage, this);
        userUI = new UserUI(stage, this);

        // Setup Notification Scene
        notificationScene = Notifications.getNotificationScene(stage, notifications, this);

        primaryStage.setScene(loginUI.getLoginScene());
        primaryStage.show();
    }

    public String authenticate(String username, String password) {
        Map<String, Map<String, String>> users = UserUtil.readUserCredentials();
        for (Map.Entry<String, Map<String, String>> entry : users.entrySet()) {
            String storedUsername = entry.getKey();
            String storedPassword = entry.getValue().get("password");
            String storedRole = entry.getValue().get("role");

            if (storedUsername.equalsIgnoreCase(username) && storedPassword.equals(password)) {
                System.out.println(storedUsername);
                System.out.println(storedPassword);
                currentUserRole = storedRole;
                UserUtil.saveSession(username, storedRole);
                return storedRole;
            }
        }
        currentUserRole = "guest";
        return "guest";
    }

    public void addBook(TextField titleInput, TextField authorInput, ComboBox<String> categoryComboBox,
            CheckBox availableInput) {
        AddBook.addBook(data, titleInput, authorInput, categoryComboBox, availableInput, notifications);
    }

    public void deleteBook() {
        DeleteBook.deleteBook(data, adminUI);
    }

    public void returnBook(Book book) {
        ReturnBook.returnBook(userUI, book, notifications);
    }

    public void updateBook(TextField titleInput, TextField authorInput, ComboBox<String> categoryComboBox,
            CheckBox availableInput) {
        UpdateBook.updateBook(adminUI, titleInput, authorInput, categoryComboBox, availableInput);
    }

    public void searchBooks(String title, String author, String category) {
        SearchBooks.searchBooks(data, adminUI, title, author, category);
    }

    public boolean signup(String username, String password, String email) {
        return true;
    }

    public void searchBooks(String query) {
        SearchBooks.searchBooks(data, userUI, query);
    }

    public void borrowBook(Book book) {
        BorrowBook.borrowBook(userUI, book, notifications);
    }

    public void showNotifications() {
        notifications.clear();
        List<String> notificationData = null;
        if ("admin".equalsIgnoreCase(currentUserRole)) {
            notificationData = FileUtil.readNotifications();
        } else if ("user".equalsIgnoreCase(currentUserRole)) {
            notificationData = FileUtil.readUserNotifications();
        }
        if (notificationData != null) {
            notifications.addAll(notificationData);
        }
        stage.setScene(Notifications.getNotificationScene(stage, notifications, this));
    }

    public ObservableList<Book> getData() {
        return data;
    }

    public LoginUI getLoginUI() {
        return loginUI;
    }

    public SignupUI getSignupUI() {
        return signupUI;
    }

    public AdminUI getAdminUI() {
        return adminUI;
    }

    public UserUI getUserUI() {
        return userUI;
    }

    public String getCurrentUserRole() {
        return currentUserRole;
    }
}
