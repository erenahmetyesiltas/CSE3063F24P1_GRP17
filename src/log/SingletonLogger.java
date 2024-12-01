package log;

import java.util.logging.Logger;
import java.util.logging.Level;

public class SingletonLogger {

    // Singleton instance
    private static final SingletonLogger instance = new SingletonLogger();

    // Java Logger
    private final Logger logger;

    // Özel constructor (dışarıdan erişilemez)
    private SingletonLogger() {
        logger = Logger.getLogger(SingletonLogger.class.getName());
    }

    // Singleton instance'ı dönen metod
    public static SingletonLogger getInstance() {
        return instance;
    }

    // Logger'ı döndüren yardımcı metod
    public Logger getLogger() {
        return logger;
    }
}
