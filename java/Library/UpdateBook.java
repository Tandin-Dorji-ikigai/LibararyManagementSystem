package Library;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import admin.AdminUI;
import product.Book;
import database.FileUtil;
import state.UpdateBookState;

public class UpdateBook {
    public static void updateBook(AdminUI adminUI, TextField titleInput, TextField authorInput, ComboBox<String> categoryComboBox,
                                  CheckBox availableInput) {
        Book selectedBook = adminUI.getAdminTable().getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            String oldTitle = selectedBook.getTitle();
            if (!titleInput.getText().isEmpty()) {
                selectedBook.setTitle(titleInput.getText());
            }
            if (!authorInput.getText().isEmpty()) {
                selectedBook.setAuthor(authorInput.getText());
            }
            if (categoryComboBox.getValue() != null) {
                selectedBook.setCategory(categoryComboBox.getValue());
            }
            selectedBook.setAvailable(availableInput.isSelected());

            // Set the state to UpdateBookState and handle the state
            UpdateBookState updateState = new UpdateBookState();
            updateState.handle(selectedBook);

            adminUI.getAdminTable().refresh();
            clearInputs(titleInput, authorInput, categoryComboBox, availableInput);

            // Update the file with the modified book
            FileUtil.updateBookDetails(selectedBook, oldTitle);
        }
    }

    private static void clearInputs(TextField titleInput, TextField authorInput, ComboBox<String> categoryComboBox,
                                    CheckBox availableInput) {
        titleInput.clear();
        authorInput.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        availableInput.setSelected(false);
    }
}
