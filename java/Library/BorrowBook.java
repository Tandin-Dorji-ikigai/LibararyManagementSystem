package Library;

import javafx.collections.ObservableList;
import user.UserUI;
import product.Book;

public class BorrowBook {
    public static void borrowBook(UserUI userUI, Book book, ObservableList<String> notifications) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            userUI.getUserTable().refresh();
            String notification = "The book '" + book.getTitle() + "' has been borrowed.";
            notifications.add(notification);
            userUI.getUserMessageLabel().setText(notification);
        }
    }
}
