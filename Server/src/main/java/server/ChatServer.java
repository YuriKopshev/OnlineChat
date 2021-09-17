package server;

import netology.AppHelper;

import java.io.*;
import java.net.Socket;

public class ChatServer extends Thread {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;


    public ChatServer(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        String word;
        try {
            // первое сообщение отправленное сюда - это никнейм
            System.out.println("Press your nickName:");
            AppHelper.loggerInformation("Press your nickName:");
            word = in.readLine();
            try {
                out.write(word + "\n");
                AppHelper.loggerInformation(word + "\n");
                out.flush();
            } catch (IOException e) {
                AppHelper.loggerException(e);
            }

                while (true) {
                    word = in.readLine();
                    AppHelper.loggerInformation(word + "\n");
                    if (word.equals("exit")) {
                        this.downService();
                        break; // если пришла пустая строка - выходим из цикла
                    }
                    System.out.println("Server message: " + word);
                    synchronized (this) {
                        for (ChatServer vr : Server.serverList) {
                            vr.send(word); // отослать принятое сообщение с привязанного клиента всем остальным включая его
                        }
                    }
            }

        } catch (IOException e) {
            AppHelper.loggerException(e);
            this.downService();
        }
    }


    public void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException e) {
            AppHelper.loggerException(e);
        }

    }

    public void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ChatServer member : Server.serverList) {
                    if (member.equals(this)) {
                        member.interrupt();
                        Server.serverList.remove(this);
                    }
                }
            }
        } catch (IOException e) {
            AppHelper.loggerException(e);
        }
    }
}


