import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html",
            "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");

    public void start() {
        final ExecutorService clientPool = Executors.newFixedThreadPool(64);
        try (final var serverSocket = new ServerSocket(9999)) {
            Socket socket;
            while (true) {
                System.out.println("Сервер ждет нового клиента...");
                try {
                    socket = serverSocket.accept();
                    clientPool.execute(new ClientHandler(socket, validPaths));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}