package login;

import Library.LibraryManagementSystem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginUI {
    private Scene loginScene;
    private TextField userInput;
    private PasswordField passInput;

    public LoginUI(Stage stage, LibraryManagementSystem mainApp) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        Label loginLabel = new Label("Library Management System");
        loginLabel.setFont(new Font("Arial", 24));
        GridPane.setConstraints(loginLabel, 0, 0, 2, 1);

        Label userLabel = new Label("Username:");
        GridPane.setConstraints(userLabel, 0, 1);

        userInput = new TextField();
        userInput.setPromptText("Username");
        GridPane.setConstraints(userInput, 1, 1);

        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 2);

        passInput = new PasswordField();
        passInput.setPromptText("Password");
        GridPane.setConstraints(passInput, 1, 2);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 3);

        Button signupButton = new Button("Sign Up");
        GridPane.setConstraints(signupButton, 1, 4);

        Label messageLabel = new Label();
        GridPane.setConstraints(messageLabel, 1, 5);

        loginButton.setOnAction(e -> {
            String username = userInput.getText();
            String password = passInput.getText();
            String role = mainApp.authenticate(username, password);
            if ("Admin".equals(role)) {
                stage.setScene(mainApp.getAdminUI().getAdminScene());
            } else if ("User".equals(role)) {
                stage.setScene(mainApp.getUserUI().getUserScene());
            } else {
                messageLabel.setText("Invalid username or password.");
            }
            // Clear input fields
            userInput.clear();
            passInput.clear();
        });

        signupButton.setOnAction(e -> {
            stage.setScene(mainApp.getSignupUI().getSignupScene());
        });

        grid.getChildren().addAll(loginLabel, userLabel, userInput, passLabel, passInput, loginButton, signupButton, messageLabel);

        loginScene = new Scene(grid, 400, 300);
        loginScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    }

    public Scene getLoginScene() {
        return loginScene;
    }
}
