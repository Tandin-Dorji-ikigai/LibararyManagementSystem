package Library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import admin.AdminUI;
import user.UserUI;
import product.Book;

public class SearchBooks {
    public static void searchBooks(ObservableList<Book> data, AdminUI adminUI, String title, String author,
            String category) {
        ObservableList<Book> filteredData = FXCollections.observableArrayList();
        for (Book book : data) {
            if ((title.isEmpty() || book.getTitle().toLowerCase().contains(title.toLowerCase())) &&
                    (author.isEmpty() || book.getAuthor().toLowerCase().contains(author.toLowerCase())) &&
                    (category.isEmpty() || book.getCategory().toLowerCase().contains(category.toLowerCase()))) {
                filteredData.add(book);
            }
        }
        adminUI.getAdminTable().setItems(filteredData);
    }

    public static void searchBooks(ObservableList<Book> data, UserUI userUI, String query) {
        ObservableList<Book> filteredData = FXCollections.observableArrayList();
        for (Book book : data) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                    book.getCategory().toLowerCase().contains(query.toLowerCase())) {
                filteredData.add(book);
            }
        }
        userUI.getUserTable().setItems(filteredData);
    }
}
