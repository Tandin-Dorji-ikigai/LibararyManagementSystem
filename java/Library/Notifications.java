package Library;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Notifications {
    public static Scene getNotificationScene(Stage stage, ObservableList<String> notifications,
            LibraryManagementSystem libraryManagementSystem) {
        VBox notificationBox = new VBox();
        notificationBox.setPadding(new Insets(10));
        notificationBox.setSpacing(10);
        notificationBox.setAlignment(Pos.CENTER);

        Label notificationHeader = new Label("Notifications");
        notificationHeader.setFont(new Font("Arial", 24));

        ListView<String> notificationListView = new ListView<>(notifications);
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            Scene targetScene = null;
            if ("admin".equalsIgnoreCase(libraryManagementSystem.getCurrentUserRole())) {
                targetScene = libraryManagementSystem.getAdminUI().getAdminScene();
            } else if ("user".equalsIgnoreCase(libraryManagementSystem.getCurrentUserRole())) {
                targetScene = libraryManagementSystem.getUserUI().getUserScene();
            }
            stage.setScene(targetScene);
        });

        notificationBox.getChildren().addAll(notificationHeader, notificationListView, backButton);

        // Set preferred width and height to match other scenes
        Scene notificationScene = new Scene(notificationBox, 800, 600);
        return notificationScene;
    }

}
