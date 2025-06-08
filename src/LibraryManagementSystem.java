import java.util.Scanner;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibraryService service = new LibraryService();

        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Delete Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    service.addBook(sc);
                    break;
                case 2:
                    service.viewBooks();
                    break;
                case 3:
                    service.deleteBook(sc);
                    break;
                case 4:
                    service.borrowBook(sc);
                    break;
                case 5:
                    service.returnBook(sc);
                    break;
                case 6:
                    System.out.println("Exiting. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
