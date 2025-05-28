package utils;

import org.slf4j.Logger;

public class LogUtils {

    // Sadece exception mesajını logla (DEBUG seviyesi için değilse stack trace yok)
    public static void logSimpleException(Logger logger, String context, Exception e) {
        if (logger.isDebugEnabled()) {
            logger.error("{} - Exception occurred: {}", context, e.getMessage(), e);
        } else {
            logger.error("{} - {}", context, e.getMessage());
        }
    }
}
