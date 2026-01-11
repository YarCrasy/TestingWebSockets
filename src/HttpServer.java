import db_connections.BookDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import models.Book;

public class HttpServer {

    private final BookDAO dao = new BookDAO();

    public void start() throws IOException {
        try (ServerSocket server = new ServerSocket(8080)) {
            while (true) {
                Socket client = server.accept();
                handle(client);
            }
        }
    }

    private void handle(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); OutputStream out = client.getOutputStream()) {

            String requestLine = in.readLine();
            if (requestLine == null) {
                return;
            }

            String[] parts = requestLine.split(" ");
            String method = parts[0];
            String path = parts[1];

            int contentLength = 0;
            String line;
            while (!(line = in.readLine()).isEmpty()) {
                if (line.startsWith("Content-Length")) {
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
            }

            String body = "";
            if (contentLength > 0) {
                char[] buf = new char[contentLength];
                in.read(buf);
                body = new String(buf);
            }

            route(method, path, body, out);
        } catch (Exception e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    private void route(String method, String path, String body, OutputStream out) throws Exception {
        if (path.equals("/books") && method.equals("GET")) {
            write(out, 200, JsonUtils.toJson(dao.findAll()));
        } 
        else if (path.startsWith("/books/") && method.equals("GET")) {
            int id = Integer.parseInt(path.split("/")[2]);
            Book b = dao.findById(id);
            if (b == null) {
                write(out, 404, "{}"); 
            }else {
                write(out, 200, JsonUtils.toJson(b));
            }
        } else if (path.equals("/books") && method.equals("POST")) {
            Book b = JsonUtils.fromJson(body);
            dao.create(b);
            write(out, 201, JsonUtils.toJson(b));
        } else if (path.startsWith("/books/") && method.equals("PUT")) {
            int id = Integer.parseInt(path.split("/")[2]);
            Book b = JsonUtils.fromJson(body);
            if (dao.update(id, b)) {
                write(out, 200, "{}"); 
            }else {
                write(out, 404, "{}");
            }
        } else if (path.startsWith("/books/") && method.equals("DELETE")) {
            int id = Integer.parseInt(path.split("/")[2]);
            if (dao.delete(id)) {
                write(out, 200, "{}"); 
            }else {
                write(out, 404, "{}");
            }
        } else {
            write(out, 404, "{}");
        }
    }

    private void write(OutputStream out, int status, String body) throws IOException {
        String response = "HTTP/1.1 " + status + " OK\r\n"
                + "Content-Type: application/json\r\n"
                + "Content-Length: " + body.length() + "\r\n\r\n"
                + body;
        out.write(response.getBytes());
    }
}
