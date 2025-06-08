import java.sql.*;
import java.util.Scanner;

public class LibraryService {

    static final String JDBC_URL = "jdbc:mysql://localhost:3306/librarydb";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "your_mysql_password";

    public void addBook(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            System.out.print("Enter Book Title: ");
            String title = sc.nextLine();
            System.out.print("Enter Author Name: ");
            String author = sc.nextLine();
            System.out.print("Enter Book Price: ");
            double price = sc.nextDouble();

            String sql = "INSERT INTO books (title, author, price, status) VALUES (?, ?, ?, 'available')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setDouble(3, price);
            stmt.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewBooks() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n--- Book List ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Price: ₹" + rs.getDouble("price"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("--------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteBook(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            System.out.print("Enter Book ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM books WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Book deleted successfully." : "Book not found.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void borrowBook(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            System.out.print("Enter Book ID to borrow: ");
            int id = sc.nextInt();

            String checkSql = "SELECT status FROM books WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                if ("available".equalsIgnoreCase(rs.getString("status"))) {
                    String sql = "UPDATE books SET status = 'borrowed' WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                    System.out.println("Book borrowed successfully.");
                } else {
                    System.out.println("Book is already borrowed.");
                }
            } else {
                System.out.println("Book not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void returnBook(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            System.out.print("Enter Book ID to return: ");
            int id = sc.nextInt();

            String checkSql = "SELECT status FROM books WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                if ("borrowed".equalsIgnoreCase(rs.getString("status"))) {
                    String sql = "UPDATE books SET status = 'available' WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                    System.out.println("Book returned successfully.");
                } else {
                    System.out.println("Book is not currently borrowed.");
                }
            } else {
                System.out.println("Book not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
