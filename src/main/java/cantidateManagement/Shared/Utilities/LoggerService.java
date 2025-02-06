package cantidateManagement.Shared.Utilities;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class LoggerService implements ILoggerService{
    String LOG_DIR = "logs";
    
    @Override
    public void logInformation(String message) {
        writeLog("INFO", message);
    }

    @Override
    public void logError(String message) {
        writeLog("ERROR", message);
    }

    private void writeLog(String logType, String message) {
        try {
            // Crear la carpeta si no existe
            Files.createDirectories(Paths.get(LOG_DIR));

            // Obtener el archivo de log del día
            String fileName = LOG_DIR + "/log_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".txt";
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String logMessage = timestamp + " [" + logType + "] " + message + System.lineSeparator();

            // Escribir el log en el archivo
            Files.write(Paths.get(fileName), logMessage.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace(); // Puedes cambiar esto a otro tipo de manejo de errores
        }
    }
}
