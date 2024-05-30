package Library;

import javafx.collections.ObservableList;
import admin.AdminUI;
import product.Book;
import database.FileUtil;

public class DeleteBook {
    public static void deleteBook(ObservableList<Book> data, AdminUI adminUI) {
        Book selectedBook = adminUI.getAdminTable().getSelectionModel().getSelectedItem();
       
        if (selectedBook != null) {
            // Remove the book from the ObservableList
            data.remove(selectedBook);
            
            // Remove the book from the file
            FileUtil.DeleteBook(selectedBook);
        }
    }
}
