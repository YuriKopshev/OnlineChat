package netology;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class AppHelper {
    public static final Logger logger = Logger.getLogger(AppHelper.class.getName());
    public static FileHandler handler;

    static {
        try {
            handler = new FileHandler("./log_file.log", true);
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getPort() {
        int port = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("settings.txt"));
            port = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            loggerException(e);
        }
        return port;
    }

    public static void loggerException(Exception exception) {
        logger.severe(Arrays.toString(exception.getStackTrace()));
    }

    public static void loggerInformation(String string) {
        logger.info(string);
    }

}
