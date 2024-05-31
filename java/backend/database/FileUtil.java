package database;

import product.Book;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String FILE_PATH = "backend/database/books.txt";
    private static final String NOTIFICATION_FILE_PATH = "backend/database/notification.txt";
    private static final String USER_NOTIFICATION_FILE_PATH = "backend/database/usernotification.txt";

    public static void saveBookToFile(Book book) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write("Title: " + book.getTitle() + "\n");
            writer.write("Author: " + book.getAuthor() + "\n");
            writer.write("Category: " + book.getCategory() + "\n");
            writer.write("Availability: " + book.getAvailability() + "\n");
            writer.write("-----\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the book information.");
            e.printStackTrace();
        }
    }

    public static List<Book> readBooksFromFile() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title: ")) {
                    String title = line.substring(7);
                    String author = reader.readLine().substring(8);
                    String category = reader.readLine().substring(10);
                    String availabilityString = reader.readLine().substring(13).trim();
                    boolean availability = parseAvailability(availabilityString);
                    books.add(new Book(title, author, category, availability));
                    reader.readLine(); // Read the separator line
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the book information.");
            e.printStackTrace();
        }
        return books;
    }

    private static boolean parseAvailability(String availabilityString) {
        return availabilityString.equalsIgnoreCase("Available");
    }

    public static void updateBookAvailability(Book book) {
        List<String> lines = new ArrayList<>();
        boolean isBookFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                if (line.startsWith("Title: ") && line.substring(7).equals(book.getTitle())) {
                    isBookFound = true;
                }
                if (isBookFound && line.startsWith("Availability: ")) {
                    lines.remove(lines.size() - 1); // Remove the old availability line
                    lines.add("Availability: " + book.getAvailability()); // Add the updated availability line
                    isBookFound = false;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating the book availability.");
            e.printStackTrace();
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating the book availability.");
            e.printStackTrace();
        }
    }

    public static void DeleteBook(Book book) {
        List<String> lines = new ArrayList<>();
        boolean isBookFound = false;

        // Read all existing lines from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title: ") && line.substring(7).equals(book.getTitle())) {
                    isBookFound = true;
                    // Skip the lines of the book to be deleted
                    reader.readLine(); // Skip author line
                    reader.readLine(); // Skip category line
                    reader.readLine(); // Skip availability line
                    reader.readLine(); // Skip separator line
                    isBookFound = false; // Reset flag for next potential book
                } else {
                    lines.add(line); // Add line to the list if it's not part of the book to be deleted
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the book details.");
            e.printStackTrace();
            return;
        }

        // Write the remaining lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while deleting the book details.");
            e.printStackTrace();
        }
    }

    public static void updateBookDetails(Book updatedBook, String oldTitle) {
        List<String> lines = new ArrayList<>();
        boolean isBookFound = false;

        // Read all existing lines from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title: ") && line.substring(7).equals(oldTitle)) {
                    isBookFound = true;
                    // Update title if a new title is provided, else keep the old title
                    if (!updatedBook.getTitle().isEmpty()) {
                        lines.add("Title: " + updatedBook.getTitle());
                    } else {
                        lines.add(line); // Keep old title line
                    }
                    line = reader.readLine();
                    // Update author if a new author is provided, else keep the old author
                    if (!updatedBook.getAuthor().isEmpty()) {
                        lines.add("Author: " + updatedBook.getAuthor());
                    } else {
                        lines.add(line); // Keep old author line
                    }
                    line = reader.readLine();
                    // Update category if a new category is provided, else keep the old category
                    if (!updatedBook.getCategory().isEmpty()) {
                        lines.add("Category: " + updatedBook.getCategory());
                    } else {
                        lines.add(line); // Keep old category line
                    }
                    line = reader.readLine();
                    // Always update availability
                    lines.add("Availability: " + updatedBook.getAvailability());
                    reader.readLine(); // Skip separator line
                    lines.add("-----");
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the book details.");
            e.printStackTrace();
            return;
        }

        // Write the updated lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating the book details.");
            e.printStackTrace();
        }
    }

    public static void saveNotificationToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTIFICATION_FILE_PATH, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the notification.");
            e.printStackTrace();
        }
    }

    public static void usernotification(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_NOTIFICATION_FILE_PATH, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readNotifications() {
        List<String> notifications = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(NOTIFICATION_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                notifications.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading notifications.");
            e.printStackTrace();
        }
        return notifications;
    }

    public static List<String> readUserNotifications() {
        List<String> userNotifications = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_NOTIFICATION_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                userNotifications.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading user notifications.");
            e.printStackTrace();
        }
        return userNotifications;
    }
}
