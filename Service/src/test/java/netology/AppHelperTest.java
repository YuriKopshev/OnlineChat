package netology;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


class AppHelperTest {

    @Test
    void getPort() {
        int actual = 8080;
        int expected = AppHelper.getPort();
        assertEquals(expected,actual);
    }

    @Test
    void loggerInformation() throws IOException {
        String actual = "<message>Hello</message>";
        String expected = null;
        AppHelper.loggerInformation("Hello");
        for (String readAllLine : Files.readAllLines(Path.of("./log_file.log"))) {
            if (readAllLine.contains("Hello")){
                expected = readAllLine.trim();
            }
        }
        assertEquals(expected,actual);
    }

    @Test
    void loggerException() throws IOException {
        Exception exception = new NullPointerException();
        String actual = "<method>loggerException</method>";
        String expected = null;
        AppHelper.loggerException(exception);
        for (String readAllLine : Files.readAllLines(Path.of("./log_file.log"))) {
            if (readAllLine.contains("<method>loggerException</method>")){
                expected = readAllLine.trim();
            }
        }
        assertEquals(expected,actual);
    }
}