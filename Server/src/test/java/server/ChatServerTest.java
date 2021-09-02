package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ChatServerTest {

    @Test
    void sendMessageTest() throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
        when(socket.getOutputStream()).thenReturn(outputStream);
        when(socket.getInputStream()).thenReturn(inputStream);
        ChatServer chatServer = new ChatServer(socket);
        chatServer.send("Message");
        assertEquals("Message", outputStream.toString().trim());
    }

    @Test
    void downServiceTest() throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
        when(socket.getOutputStream()).thenReturn(outputStream);
        when(socket.getInputStream()).thenReturn(inputStream);
        ChatServer chatServer = new ChatServer(socket);
        chatServer.downService();
        assertFalse(socket.getKeepAlive());
    }
}