package com.thomsonreuters.codes.codesbench.quality.utilities;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.Architecture;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.time.Duration;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge.logger;

public class TestDriver
{
    private static WebDriver driver;

    private static final String DRIVER_BIT_VERSION = "32";

    private static final String IE_EDGE_DRIVER_NAME = "IEDriverServer_4.8.1.exe";
    private static final String IE_EDGE_DRIVER_PATH = String.format("commonFiles\\drivers\\%s\\%s", DRIVER_BIT_VERSION, IE_EDGE_DRIVER_NAME);
    private static final String EDGE_DRIVER_NAME = "msedgedriver_119.0.2151.44.exe";
    private static final String EDGE_DRIVER_PATH = String.format("commonFiles\\drivers\\edge\\%s\\%s", DRIVER_BIT_VERSION, EDGE_DRIVER_NAME);

    private static final String CHROME_DRIVER_NAME = "chromedriver_113.0.5672.63.exe";
    private static final String CHROME_DRIVER_PATH = String.format("commonFiles\\drivers\\chrome\\%s\\%s", DRIVER_BIT_VERSION, CHROME_DRIVER_NAME);

    private static InternetExplorerOptions ieOptions;
    private static EdgeOptions edgeOptions;
    private static ChromeOptions chromeOptions;

    public static WebDriver getDriver()
    {
        if(driver == null && TestSetupEdge.getBrowserTag().equals(CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE))
        {
            //setIeEdgeDriverPath();

            WebDriverManager.iedriver().architecture(Architecture.X32).arch32().setup();
            setIeEdgeDriverOptions();
            driver = new InternetExplorerDriver(ieOptions);
            //driver = new EdgeDriver(edgeOptions);

            /*WebDriverManager.edgedriver().setup();
            setEdgeDriverOptions();
            driver = new EdgeDriver(edgeOptions);*/
            return driver;
        }
        else if(driver == null && TestSetupEdge.getBrowserTag().equals(CustomAnnotations.BrowserAnnotations.EDGE))
        {
            //setEdgeDriverPath();

            WebDriverManager.edgedriver().setup();
            setEdgeDriverOptions();
            driver = new EdgeDriver(edgeOptions);
            return driver;
        }
        else if(driver == null && TestSetupEdge.getBrowserTag().equals(CustomAnnotations.BrowserAnnotations.CHROME))
        {
            //setChromeDriverPath();
            setChromeDriverOptions();
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(chromeOptions);
            return driver;
        }
        return driver;
    }

    public static void killDriverAndBrowserTasks()
    {
        String[] tasks = new String[] {"taskkill /F /IM iexplore.exe",
                "taskkill /F /IM msedge.exe",
                String.format("taskkill /F /IM %s", "IEDriverServer.exe"),
                String.format("taskkill /F /IM %s", "msedgedriver.exe"),
                String.format("taskkill /F /IM %s", "chromedriver.exe")};
        try
        {
            for (String task : tasks)
            {
                Runtime.getRuntime().exec(task).waitFor();
            }
        }
        catch (Exception e)
        {
            logger.warning("There was a problem with killing lingering tasks.");
            e.printStackTrace();
        }
    }

    public static void quitDriver()
    {
        if(driver != null)
        {
            try
            {
                driver.quit();
            }
            catch(WebDriverException e)
            {
                logger.warning("Driver quit threw a WebDriverException timing out to stop the server.  Just a heads up, but the rest of the cleanup code should destroy everything.");
            }
            driver = null;
        }
    }

    public static void maximizeAndSetTimeouts()
    {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DateAndTimeUtils.IMPLICIT_ELEMENT_WAIT_SECONDS));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(DateAndTimeUtils.PAGE_LOAD_TIMEOUT_SECONDS));
    }

    private static void setChromeDriverOptions()
    {
        chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
        chromeOptions.addArguments("--remote-allow-origins=*");
        if(TestSetupEdge.headlessMode)
        {
            chromeOptions.addArguments("--headless");
        }
    }

    private static void setChromeDriverPath()
    {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
    }

    private static void setEdgeDriverOptions()
    {
        edgeOptions = new EdgeOptions();
        edgeOptions.setBinary("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
        edgeOptions.addArguments("--disable-popup-blocking");
        edgeOptions.addArguments("--remote-allow-origins=*");
        if(TestSetupEdge.headlessMode)
        {
            edgeOptions.addArguments("--headless");
        }
    }

    private static void setEdgeDriverPath()
    {
        System.setProperty("webdriver.edge.driver", EDGE_DRIVER_PATH);
    }

    private static void setIeEdgeDriverOptions()
    {
        ieOptions = new InternetExplorerOptions();
        ieOptions.attachToEdgeChrome();
        ieOptions.addCommandSwitches("--disable-popup-blocking");
    }

    private static void setIeEdgeDriverPath()
    {
        System.setProperty("webdriver.ie.driver", IE_EDGE_DRIVER_PATH);
    }   
}
