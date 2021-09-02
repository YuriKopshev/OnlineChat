package server;

import netology.AppHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import static netology.AppHelper.getPort;


public class Server {
    private Socket socket;
    public static LinkedList<ChatServer> serverList = new LinkedList<>();
    // список всех нитей - экземпляров сервера, слушающих каждый своего клиента

    public Server(Socket socket) {
        this.socket = socket;
    }

    public static LinkedList<ChatServer> getServerList() {
        return serverList;
    }

    public void startServer() throws IOException {
        try (ServerSocket server = new ServerSocket(getPort())) {
            System.out.println("Server Started");
            AppHelper.loggerInformation("Server Started");
            while (true) {
                this.socket = server.accept();
                try {
                    serverList.add(new ChatServer(socket)); // добавляем новое соединение в список
                } catch (IOException e) {
                    AppHelper.loggerException(e);
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        Server server = new Server(socket);
        server.startServer();
    }
}

