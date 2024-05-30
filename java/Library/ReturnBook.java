package Library;

import javafx.collections.ObservableList;
import user.UserUI;
import product.Book;

public class ReturnBook {
    public static void returnBook(UserUI userUI, Book book, ObservableList<String> notifications) {
        if (!book.isAvailable()) {
            book.setAvailable(true);
            userUI.getUserTable().refresh();
            String notification = "The book '" + book.getTitle() + "' has been returned.";
            notifications.add(notification);
            userUI.getUserMessageLabel().setText(notification);
        }
    }
}
