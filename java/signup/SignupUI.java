package signup;

import Library.LibraryManagementSystem;
import database.UserUtil;
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

public class SignupUI {
    private Scene signupScene;

    public SignupUI(Stage stage, LibraryManagementSystem mainApp) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        Label signupLabel = new Label("Sign Up");
        signupLabel.setFont(new Font("Arial", 24));
        GridPane.setConstraints(signupLabel, 0, 0, 2, 1);

        Label userLabel = new Label("Username:");
        GridPane.setConstraints(userLabel, 0, 1);

        TextField userInput = new TextField();
        userInput.setPromptText("Username");
        GridPane.setConstraints(userInput, 1, 1);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 2);

        TextField emailInput = new TextField();
        emailInput.setPromptText("Email");
        GridPane.setConstraints(emailInput, 1, 2);

        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 3);

        PasswordField passInput = new PasswordField();
        passInput.setPromptText("Password");
        GridPane.setConstraints(passInput, 1, 3);

        Label confirmPassLabel = new Label("Confirm Password:");
        GridPane.setConstraints(confirmPassLabel, 0, 4);

        PasswordField confirmPassInput = new PasswordField();
        confirmPassInput.setPromptText("Confirm Password");
        GridPane.setConstraints(confirmPassInput, 1, 4);

        Button signupButton = new Button("Sign Up");
        GridPane.setConstraints(signupButton, 1, 5);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 0, 5);

        Label messageLabel = new Label();
        GridPane.setConstraints(messageLabel, 1, 6);

        loginButton.setOnAction(e -> {
            stage.setScene(mainApp.getLoginUI().getLoginScene());
        });

        signupButton.setOnAction(e -> {
            String username = userInput.getText();
            String email = emailInput.getText();
            String password = passInput.getText();
            String confirmPassword = confirmPassInput.getText();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                messageLabel.setText("Passwords do not match.");
                return;
            }

            boolean isSignupSuccessful = mainApp.signup(username, password, email);
            if (isSignupSuccessful) {
                // Save user credentials
                UserUtil.saveUserCredentials(username, password, email);

                messageLabel.setText("Signup successful! Please log in.");
                stage.setScene(mainApp.getLoginUI().getLoginScene());
            } else {
                messageLabel.setText("Signup failed. Please try again.");
            }
        });

        grid.getChildren().addAll(signupLabel, userLabel, userInput, emailLabel, emailInput, passLabel, passInput,
                confirmPassLabel, confirmPassInput, signupButton, loginButton, messageLabel);

        signupScene = new Scene(grid, 400, 350);
        signupScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    }

    public Scene getSignupScene() {
        return signupScene;
    }
}
