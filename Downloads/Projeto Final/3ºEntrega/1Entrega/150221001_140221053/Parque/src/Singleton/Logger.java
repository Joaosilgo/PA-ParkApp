package singleton;

import java.io.*;
import java.util.Date;

/**
 * Class Logger
 *
 * @author João e Jorge
 */
public final class Logger {

    private static final String LOGGERFILE = "logger.txt";
    private PrintStream printStream;
    private static Logger Singleton;

    /**
     * Constructor method for Logger
     */
    private Logger() {
        connect();
    }

    /**
     * Method that connects to the logger file
     *
     * @return true if connect was successful
     */
    public boolean connect() {
        if (printStream == null) {
            try {
                printStream = new PrintStream(new FileOutputStream(LOGGERFILE), true);
            } catch (FileNotFoundException ex) {
                printStream = null;
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * Method that prints into the logger file
     *
     * @param str string
     * @throws LoggerException
     */
    public void writeToLog(String str) throws LoggerException {
        if (printStream == null) {
            throw new LoggerException("Connection fail");
        }
        printStream.println(new Date().toString() + "  " + str);
    }

    /**
     * Getter method for the logger instance
     *
     * @return singleton
     */
    public static Logger getInstance() {
        if (Singleton == null) {
            Singleton = new Logger();
        }
        return Singleton;
    }
}
