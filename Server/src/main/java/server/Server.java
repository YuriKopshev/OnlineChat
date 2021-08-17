package server;

import netology.AppHelper;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import static netology.AppHelper.getPort;


public class Server {
    public static LinkedList<ChatServer> serverList = new LinkedList<>();
    // список всех нитей - экземпляров сервера, слушающих каждый своего клиента

    public void startServer() throws IOException {
        try (ServerSocket server = new ServerSocket(getPort())) {
            System.out.println("Server Started");
            AppHelper.loggerInformation("Server Started");
            while (true) {
                Socket socket = server.accept();
                try {
                    serverList.add(new ChatServer(socket)); // добавляем новое соединение в список
                } catch (IOException e) {
                    AppHelper.loggerException(e);
                }
            }
        }
    }



    public static void main(String[] args) throws IOException {
          Server server = new Server();
          server.startServer();
    }
}

