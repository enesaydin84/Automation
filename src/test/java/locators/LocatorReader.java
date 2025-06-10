package locators;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LogUtils;

import java.io.File;
import java.util.Map;

public class LocatorReader {

    private static final Logger logger = LoggerFactory.getLogger(LocatorReader.class);
    private static Map<String, Map<String, String>> locatorMap;

    static {
        try {
            logger.info("Loading locators from locators.json...");
            ObjectMapper mapper = new ObjectMapper();
            locatorMap = mapper.readValue(
                    new File("src/test/java/locators/locators.json"),
                    new com.fasterxml.jackson.core.type.TypeReference<Map<String, Map<String, String>>>() {}
            );
            logger.info("Locators loaded successfully.");
        } catch (Exception e) {
            LogUtils.logSimpleException(logger, "Failed to load locators.json file", e);
            throw new RuntimeException("Failed to load locators.json file", e);
        }
    }

    public static String getLocator(String pageName, String elementName) {
        logger.debug("Retrieving locator for page: '{}', element: '{}'", pageName, elementName);

        Map<String, String> pageLocators = locatorMap.get(pageName);
        if (pageLocators == null) {
            String message = "No locators found for page: " + pageName;
            logger.error(message);
            throw new RuntimeException(message);
        }

        String locator = pageLocators.get(elementName);
        if (locator == null) {
            String message = "No locator found for element: " + elementName + " on page: " + pageName;
            logger.error(message);
            throw new RuntimeException(message);
        }

        logger.debug("Locator found for '{}' on '{}': {}", elementName, pageName, locator);
        return locator;
    }
}
