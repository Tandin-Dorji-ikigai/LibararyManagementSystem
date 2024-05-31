// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;

// import abstractfactory.AbstractBookFactory;
// import abstractfactory.ConcreteBookFactoryA;
// import abstractfactory.ConcreteBookFactoryB;
// import composite.BookComponentAdapter;
// import composite.Category;
// import command.Command;
// import command.Receiver;
// import command.SearchCommand;
// import database.FileUtil;
// import database.UserUtil;
// import Observer.Library;
// import Observer.User;
// import product.Book;
// import state.*;
// import strategy.Context;
// import strategy.SearchStrategy;
// import strategy.ConcreteSearchStrategy;


// public class LibraryApp {
    
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
//         boolean loggedIn = false;
//         // while (!loggedIn) {
//         //     loggedIn = login(scanner);
//         // }

//         // Observer Pattern
//         Library library = new Library();
//         User user1 = new User("User1");
//         library.attach(user1);

//         // Abstract Factory
//         AbstractBookFactory bookFactoryA = new ConcreteBookFactoryA();
//         AbstractBookFactory bookFactoryB = new ConcreteBookFactoryB();

//         // Composite Pattern
//         Category category = new Category("Fiction");
//         Category NonFictioncategory = new Category("Non-Fiction");

//         // List to hold books
//         List<Book> books = new ArrayList<>();

//         // List of categories
//         List<String> categories = new ArrayList<>();
//         categories.add("Fiction");
//         categories.add("Non-Fiction");

//         while (true) {
//             System.out.println("Select an option:");
//             System.out.println("2. Add a new Book");
//             System.out.println("4. Display categories");
//             System.out.println("5. Search books using Strategy Pattern");
//             System.out.println("6. Execute search command using Command Pattern");
//             System.out.println("7. Change state using State Pattern");
//             System.out.println("8. Display books from file");
//             System.out.println("9. Borrow a book");
//             System.out.println("10. Admin Signup");
//             System.out.println("11. Exit");
//             int option = scanner.nextInt();
//             scanner.nextLine(); // Consume newline

//             switch (option) {
//                 case 2:
//                     System.out.println("Select a category:");
//                     for (int i = 0; i < categories.size(); i++) {
//                         System.out.println((i + 1) + ". " + categories.get(i));
//                     }
//                     int categoryOption = scanner.nextInt();
//                     scanner.nextLine(); // Consume newline

//                     if (categoryOption < 1 || categoryOption > categories.size()) {
//                         System.out.println("Invalid category option.");
//                         break;
//                     }

//                     String selectedCategory = categories.get(categoryOption - 1);

//                     System.out.println("Enter details for the new " + selectedCategory + " Book:");
//                     System.out.print("Title: ");
//                     String title = scanner.nextLine();
//                     System.out.print("Author: ");
//                     String author = scanner.nextLine();
//                     System.out.print("Availability (true/false): ");
//                     boolean availability = scanner.nextBoolean();
//                     scanner.nextLine(); // Consume newline

//                     Book book = null;
//                     if (selectedCategory.equals("Fiction")) {
//                         book = bookFactoryA.createBook(title, author, selectedCategory, availability);
//                     } else if (selectedCategory.equals("Non-Fiction")) {
//                         book = bookFactoryB.createBook(title, author, selectedCategory, availability);
//                     }

//                     books.add(book);

//                     System.out.println("New Book Created: ");
//                     System.out.println("Title: " + book.getTitle());
//                     System.out.println("Author: " + book.getAuthor());
//                     System.out.println("Category: " + book.getCategory());
//                     System.out.println("Availability: " + book.getAvailability());

//                     // Wrap the book with the adapter and add it to the category
//                     Category bookCategory = null;
//                     if (selectedCategory.equals("Fiction")) {
//                         bookCategory = category.getCategoryByName("Fiction");
//                     } else if (selectedCategory.equals("Non-Fiction")) {
//                         bookCategory = NonFictioncategory.getCategoryByName("Non-Fiction");
//                     }

//                     if (bookCategory != null) {
//                         bookCategory.add(new BookComponentAdapter(book));
//                         System.out.println("Book added to the " + selectedCategory + " category.");
//                     } else {
//                         System.out.println(selectedCategory + " category not found.");
//                     }

//                     // Save the book information to a file
//                     FileUtil.saveBookToFile(book);

//                     // Notify observers about the new book
//                     library.notifyNewBook(book.getTitle(), book.getAuthor(), book.getCategory());
//                     break;

//                 case 4:
//                     System.out.println("Categories:");
//                     category.display();
//                     NonFictioncategory.display();
//                     break;
//                 case 5:
//                     Context context = new Context();
//                     context.setStrategy(new ConcreteSearchStrategy());
//                     System.out.println("Enter the title or part of the title of the book you are searching for:");
//                     String query = scanner.nextLine();
//                     context.performSearch(books, query);
//                     break;
//                 case 6:
//                     Receiver receiver = new Receiver();
//                     // Command command = new SearchCommand(receiver, scanner);
//                     // command.execute();
//                     break;
//                 case 7:
//                     System.out.println("Enter the title of the book to change its state:");
//                     String bookTitle = scanner.nextLine();
//                     Book bookToChangeState = findBookByTitle(books, bookTitle);

//                     if (bookToChangeState != null) {
//                         System.out.println("Select the new state:");
//                         System.out.println("1. Available");
//                         System.out.println("2. Checked Out");
//                         System.out.println("3. Overdue");
//                         int stateOption = scanner.nextInt();
//                         scanner.nextLine(); // Consume newline

//                         State newState = null;
//                         switch (stateOption) {
//                             case 1:
//                                 newState = new AvailableState();
//                                 break;
//                             case 2:
//                                 newState = new CheckedOutState();
//                                 break;
//                             case 3:
//                                 newState = new OverdueState();
//                                 break;
//                             default:
//                                 System.out.println("Invalid state option.");
//                                 continue;
//                         }

//                         newState.handle(bookToChangeState);
//                         System.out.println("Book '" + bookToChangeState.getTitle() + "' state changed to: " + newState);
//                     } else {
//                         System.out.println("Book not found.");
//                     }
//                     break;
//                 case 8:
//                     List<Book> booksFromFile = FileUtil.readBooksFromFile();
//                     books.addAll(booksFromFile);
//                     System.out.println("Books from file:");
//                     for (Book bookFromFile : booksFromFile) {
//                         System.out.println("Title: " + bookFromFile.getTitle());
//                         System.out.println("Author: " + bookFromFile.getAuthor());
//                         System.out.println("Category: " + bookFromFile.getCategory());
//                         System.out.println("Availability: " + bookFromFile.getAvailability());
//                         System.out.println("-----");
//                     }
//                     break;
//                 case 9:
//                     List<Book> booksToBorrow = FileUtil.readBooksFromFile();
//                     borrowBook(scanner, booksToBorrow);
//                     break;
//                 case 10:
//                     System.out.println("Enter admin username:");
//                     String adminUsername = scanner.nextLine();
//                     System.out.println("Enter admin email:");
//                     String email = scanner.nextLine();
//                     System.out.println("Enter admin password:");
//                     String adminPassword = scanner.nextLine();
//                     UserUtil.saveUserCredentials(adminUsername, email, adminPassword);
//                     System.out.println("Admin user registered successfully.");
//                     break;
//                 case 11:
//                     System.out.println("Exiting...");
//                     scanner.close(); // Properly close the scanner
//                     System.exit(0);
//                     break;
//                 default:
//                     System.out.println("Invalid option. Please try again.");
//             }
//         }
//     }

//     // private static boolean login(Scanner scanner) {
//     //     System.out.println();
//     //     System.out.println("Enter your username:");
//     //     String username = scanner.nextLine();
//     //     System.out.println("Enter your password:");
//     //     String password = scanner.nextLine();

//     //     if (UserUtil.verifyCredentials(username, password)) {
//     //         System.out.println("Login successful.");
//     //         return true;
//     //     } else {
//     //         System.out.println("Login failed. Invalid username or password.");
//     //         return false;
//     //     }
//     // }

//     public static void borrowBook(Scanner scanner, List<Book> books) {
//         System.out.println("Available Books:");
//         displayAvailableBooks(books);
//         System.out.println("Enter the name of the book to lend:");
//         String bookName = scanner.nextLine();
//         Book bookToLend = null;

//         for (Book book : books) {
//             if (book.getTitle().equalsIgnoreCase(bookName) && book.isAvailable()) {
//                 bookToLend = book;
//                 break;
//             }
//         }

//         if (bookToLend != null) {
//             System.out.println("Enter user's name to lend the book:");
//             String userName = scanner.nextLine();
//             // Logic to lend the book to the user
//             // Update book availability
//             bookToLend.setAvailable(false);
//             System.out.println("Book '" + bookToLend.getTitle() + "' lent to user '" + userName + "'.");

//             String notification = "Book '" + bookToLend.getTitle() + "' lent to user '" + userName + "'.";
//             FileUtil.saveNotificationToFile(notification);
//             // Update the file with the new availability
//             FileUtil.updateBookAvailability(bookToLend);
//         } else {
//             System.out.println("Book is not available for lending or does not exist.");
//         }

//         // Display available books again after lending
//         System.out.println("Available Books:");
//         displayAvailableBooks(books);
//     }

//     private static void displayAvailableBooks(List<Book> books) {
//         for (Book book : books) {
//             if (book.isAvailable()) {
//                 System.out.println("Title: " + book.getTitle());
//                 System.out.println("Author: " + book.getAuthor());
//                 System.out.println("Category: " + book.getCategory());
//                 System.out.println("Availability: " + book.getAvailability());
//                 System.out.println("-----");
//             }
//         }
//     }

//     private static Book findBookByTitle(List<Book> books, String title) {
//         for (Book book : books) {
//             if (book.getTitle().equalsIgnoreCase(title)) {
//                 return book;
//             }
//         }
//         return null;
//     }
// }
