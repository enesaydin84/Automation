package factory;

import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.LaunchOptions;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

public class PlaywrightFactory {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    Properties prop;

    private static ThreadLocal<Browser> tlBrowser=new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlBrowserContext=new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage=new ThreadLocal<>();
    private static ThreadLocal<Playwright> tlPlaywright=new ThreadLocal<>();

    public static Playwright getPlaywright() {

        return tlPlaywright.get();
    }

    public static Browser getBrowser() {

        return tlBrowser.get();
    }
    public static BrowserContext getBrowserContext() {

        return tlBrowserContext.get();
    }
    public static Page getPage() {

        return tlPage.get();
    }

    public Page initBrowser(Properties prop) {
        String browserName=prop.getProperty("browser").trim();

        tlPlaywright.set(Playwright.create());

        switch (browserName.toLowerCase()) {
            case "chromium":
                tlBrowser.set(getPlaywright().chromium().launch(new LaunchOptions().setHeadless(false)));
                break;

            case "firefox":
                tlBrowser.set(getPlaywright().firefox().launch(new LaunchOptions().setHeadless(false)));
                break;

            case "safari":
                tlBrowser.set(getPlaywright().webkit().launch(new LaunchOptions().setHeadless(false)));
                break;

            case "chrome":
                tlBrowser.set(getPlaywright().chromium().launch(new LaunchOptions().setChannel("chrome").setHeadless(false)));
                break;

            default:
                System.out.println("Please pass the right browser name...");
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        tlBrowserContext.set(getBrowser().newContext(new Browser.NewContextOptions()
            .setViewportSize(
                Integer.parseInt(prop.getProperty("width").trim()),
                Integer.parseInt(prop.getProperty("height").trim())
            )));

        // Trace'i başlat
        getBrowserContext().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        tlPage.set(getBrowserContext().newPage());
        getPage().navigate(prop.getProperty("url").trim(),new Page.NavigateOptions().setTimeout(60000));
        return getPage();

    }

    public Properties init_prop()  {

        try {
            FileInputStream ip=new FileInputStream("./src/test/resources/config/config.properties");
            prop=new Properties();
            prop.load(ip);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prop;
    }

}