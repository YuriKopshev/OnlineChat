package client;

import netology.AppHelper;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import static netology.AppHelper.getPort;


public class ChatClient {
    public static final String IPADDRESS = "localhost";
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private final String address;
    private final int port;
    private String nickname;
    private Date date;
    private String dateTime;
    private SimpleDateFormat format;

    public ChatClient(Socket socket, String address, int port) {
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

//    public ChatClient(String address, int port, Socket socket) {
//        this.address = address;
//        this.port = port;
//        try {
//          this.socket = new Socket(address, port);
//        } catch (IOException e) {
//            AppHelper.loggerException(e);
//        }
//    }

    public void startClient() throws IOException {
        this.socket = new Socket(address, port);
        try {
            // потоки чтения из сокета / записи в сокет, и чтения с консоли
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.pressNickname(); //  спросит имя
            new ReadMsg().start();
            new WriteMsg().start();
        } catch (IOException e) {
            AppHelper.loggerException(e);
            ChatClient.this.downService();
        }
    }

    public void pressNickname() {
        System.out.print("Press your nickName: ");
        AppHelper.loggerInformation("Press your nickName: ");
        try {
            nickname = inputUser.readLine();
            out.write("Hello " + nickname + "\n");
            AppHelper.loggerInformation("Hello " + nickname + "\n");
            out.flush();
        } catch (IOException e) {
            //logger.error("Exceptions happen!",e);
            AppHelper.loggerException(e);
        }
    }

    public void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            AppHelper.loggerException(e);
        }
    }

    // нить чтения сообщений с сервера
    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String str;
            try {
                while (true) {
                    str = in.readLine();
                    AppHelper.loggerInformation(str);
                    if (str.equals("exit")) {
                        ChatClient.this.downService();
                        break;
                    }
                    System.out.println(str); // пишем сообщение с сервера на консоль
                }
            } catch (IOException e) {
                AppHelper.loggerException(e);
                ChatClient.this.downService();
            }
        }
    }

    // нить отправляющая сообщения приходящие с консоли на сервер
    private class WriteMsg extends Thread {
        @Override
        public void run() {
            while (true) {
                String userWord;
                try {
                    date = new Date(); // текущая дата
                    format = new SimpleDateFormat("HH:mm:ss");
                    dateTime = format.format(date);
                    userWord = inputUser.readLine();
                    if (userWord.equals("exit")) {
                        out.write("exit" + "\n");
                        ChatClient.this.downService();
                        break; // выходим из цикла если пришло "exit"
                    } else {
                        out.write("(" + dateTime + ") " + nickname + ": " + userWord + "\n"); // отправляем на сервер
                        AppHelper.loggerInformation(nickname + ": " + userWord + "\n");
                    }
                    out.flush();
                } catch (IOException e) {
                    AppHelper.loggerException(e);
                    ChatClient.this.downService();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket1 = new Socket();
       ChatClient chatClient = new ChatClient(socket1,IPADDRESS, getPort());
       chatClient.startClient();
    }
}
