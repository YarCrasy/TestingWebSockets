package db_connections;

import static db_connections.DBConnection.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Book;

public class BookDAO {

    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book b = new Book();
                b.id = rs.getInt("id");
                b.title = rs.getString("title");
                b.author = rs.getString("author");
                b.year = rs.getInt("year");
                books.add(b);
            }
        }
        return books;
    }

    public Book findById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id=?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book b = new Book();
                b.id = rs.getInt("id");
                b.title = rs.getString("title");
                b.author = rs.getString("author");
                b.year = rs.getInt("year");
                return b;
            }
        }
        return null;
    }

    public Book create(Book b) throws SQLException {
        String sql = "INSERT INTO books(title, author, year) VALUES (?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, b.title);
            ps.setString(2, b.author);
            ps.setInt(3, b.year);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                b.id = keys.getInt(1);
            }
        }
        return b;
    }

    public boolean update(int id, Book b) throws SQLException {
        String sql = "UPDATE books SET title=?, author=?, year=? WHERE id=?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, b.title);
            ps.setString(2, b.author);
            ps.setInt(3, b.year);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id=?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
