package log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SingletonLogger {

    // Singleton instance
    private static final SingletonLogger instance = new SingletonLogger();

    // Java Logger
    private final Logger logger;

    // Özel constructor (dışarıdan erişilemez)
    private SingletonLogger() {
        logger = Logger.getLogger("GlobalLogger");

        try {
            // Log dosyasını oluştur ve bağla
            FileHandler fileHandler = new FileHandler("CSE3063F24P1_GRP17/src/log/application.log", true); // Dosyaya ekleme modu
            fileHandler.setFormatter(new SimpleFormatter()); // Basit formatta loglama
            logger.addHandler(fileHandler);

            // Konsol çıktısını devre dışı bırak
            logger.setUseParentHandlers(false);

        } catch (IOException e) {
            System.err.println("Log dosyası oluşturulamadı: " + e.getMessage());
        }
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
