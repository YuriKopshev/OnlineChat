package client;

import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.ChatServer;
import server.Server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class ChatClientTest {
    private final String IPADDRESS = "localhost";
    private final int PORT = 8080;


    @Test
    public void startClientTest() throws IOException {
        Socket socket1 = new Socket();
        Server server = new Server(socket1);
        new Thread(() -> {
            try {
                server.startServer();
                Socket socket = new Socket(IPADDRESS, PORT);
                ChatClient chatClient = new ChatClient(socket);
                chatClient.startClient();
                assertTrue(chatClient.getSocket().isConnected());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Test
    public void testPressNickName() throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(socket.getOutputStream()).thenReturn(outputStream);
        when(socket.getInputStream()).thenReturn(ByteArrayInputStream.nullInputStream());
        ChatClient chatClient = new ChatClient(socket);
        chatClient.startClient();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("Yuri".getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        assertEquals("Yuri", reader.readLine());
    }

    @Test
    public void downServiceTest() throws IOException {
        Socket socket1 = new Socket();
        Server server = new Server(socket1);
        new Thread(() -> {
            try {
                server.startServer();
                Socket socket = new Socket(IPADDRESS, PORT);
                ChatClient chatClient = new ChatClient(socket);
                chatClient.startClient();
                chatClient.downService();
                assertTrue(chatClient.getSocket().isClosed());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
