
import java.util.*;
import models.Book;

public class JsonUtils {

    public static String toJson(Book b) {
        return String.format(
                "{\"id\":%d,\"title\":\"%s\",\"author\":\"%s\",\"year\":%d}",
                b.id, b.title, b.author, b.year
        );
    }

    public static String toJson(List<Book> books) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < books.size(); i++) {
            sb.append(toJson(books.get(i)));
            if (i < books.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static Book fromJson(String json) {
        Book b = new Book();
        json = json.replace("{", "").replace("}", "");
        String[] fields = json.split(",");
        for (String f : fields) {
            String[] kv = f.split(":");
            String key = kv[0].replace("\"", "").trim();
            String value = kv[1].replace("\"", "").trim();
            switch (key) {
                case "title" ->
                    b.title = value;
                case "author" ->
                    b.author = value;
                case "year" ->
                    b.year = Integer.parseInt(value);
            }
        }
        return b;
    }
}
