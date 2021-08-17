package client;

import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChatClientTest {
    private final String ADDRESS = "localhost";
    private final int PORT = 8080;
    @Test
   public void  testPressNickname() throws IOException {
        Socket socket = mock(Socket.class);
        ChatClient chatClient = new ChatClient(socket,ADDRESS,PORT);
        chatClient.startClient();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream("Yuri".getBytes());
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        assertThat(out.toString(),is("Hello Yuri"));
    }

}