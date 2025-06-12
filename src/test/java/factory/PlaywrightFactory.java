package factory;

import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import utils.ConfigReader;

public class PlaywrightFactory {

    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
    private static ThreadLocal<APIRequestContext> tlApiRequest = new ThreadLocal<>();

    public static APIRequestContext getApiRequestContext() {
        return tlApiRequest.get();
    }

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

    public Page initBrowser() {
        String browserName = ConfigReader.get("browser");

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
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        tlBrowserContext.set(getBrowser().newContext(new Browser.NewContextOptions()
                .setViewportSize(
                        ConfigReader.getInt("width"),
                        ConfigReader.getInt("height")
                )));

        getBrowserContext().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        tlPage.set(getBrowserContext().newPage());
        getPage().navigate(ConfigReader.get("url"));
        return getPage();
    }
    public static void initApiRequestContext() {
        Playwright playwright = Playwright.create();
        tlPlaywright.set(playwright);
        tlApiRequest.set(playwright.request().newContext());
    }
}
