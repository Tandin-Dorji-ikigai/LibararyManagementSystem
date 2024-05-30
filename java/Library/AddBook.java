package Library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import product.Book;
import database.FileUtil;
import composite.*;
import abstractfactory.*;
import Observer.*;

public class AddBook {
    private static Category rootCategory = initializeCategories();
    private static Library newNotification = new Library();

    private static Category initializeCategories() {
        Category root = new Category("Library");
        Category fiction = new Category("Fiction");
        Category nonFiction = new Category("Non-Fiction");
        Category science = new Category("Science");
        root.add(fiction);
        root.add(nonFiction);
        root.add(science);
        return root;
    }

    public static void addBook(ObservableList<Book> data, TextField titleInput, TextField authorInput,
            ComboBox<String> categoryComboBox, CheckBox availableInput, ObservableList<String> notifications) {
        String title = titleInput.getText();
        String author = authorInput.getText();
        String category = categoryComboBox.getValue();
        boolean available = availableInput.isSelected();

        AbstractBookFactory bookFactory = null;

        // Determine which factory to use based on the selected category
        switch (category) {
            case "Fiction":
                bookFactory = new FictionBookFactory();
                break;
            case "Non-Fiction":
                bookFactory = new NonFictionBookFactory();
                break;
            case "Science":
                bookFactory = new ScienceBookFactory();
                // Add more cases for other category types if needed
            default:
                // Handle unknown category
                System.err.println("Unknown category: " + category);
                return;
        }

        // Create book using the appropriate factory
        Book book = bookFactory.createBook(title, author, available);
        if (book != null) {
            data.add(book);
            clearInputs(titleInput, authorInput, categoryComboBox, availableInput);
            FileUtil.saveBookToFile(book);
            newNotification.notifyNewBook(title, author, category);
            // FileUtil.usernotification(
            //         "New book added - Title: " + title + ", Author: " + author + ", Category: " + category);
            notifications.add("New book added - Title: " + title + ", Author: " + author + ", Category: " + category);

            // Categorize the book
            Category bookCategory = rootCategory.getCategoryByName(category);
            if (bookCategory != null) {
                bookCategory.add(new BookComponentAdapter(book));
                System.out.println("Book added to the " + category + " category.");
            } else {
                System.out.println(category + " category not found.");
            }
        } else {
            System.err.println("Failed to create book for category: " + category);
        }
    }

    private static void clearInputs(TextField titleInput, TextField authorInput, ComboBox<String> categoryComboBox,
            CheckBox availableInput) {
        titleInput.clear();
        authorInput.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        availableInput.setSelected(false);
    }

    public static ObservableList<String> getCategories() {
        ObservableList<String> categories = FXCollections.observableArrayList();
        for (String categoryName : rootCategory.getAllCategoryNames()) {
            if (!categoryName.equals("Library")) {
                categories.add(categoryName);
            }
        }
        return categories;
    }
}
