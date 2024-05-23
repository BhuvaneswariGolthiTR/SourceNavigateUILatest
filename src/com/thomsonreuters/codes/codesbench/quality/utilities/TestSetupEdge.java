package com.thomsonreuters.codes.codesbench.quality.utilities;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.loggers.CustomLogger;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.io.FileUtils;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.thomsonreuters.codes.codesbench.quality.utilities","com.thomsonreuters.codes.codesbench.quality.pages","com.thomsonreuters.codes.codesbench.quality.menus"}, lazyInit = true)
@PropertySources({@PropertySource("file:commonFiles\\properties\\urls.properties"), @PropertySource("file:commonFiles\\properties\\credentials.properties"), @PropertySource("file:commonFiles\\properties\\database.properties")})
public class TestSetupEdge
{
	protected static AnnotationConfigApplicationContext annotationConfigApplicationContext;

	public static CustomLogger logger = null;

	public static boolean headlessMode;
	public static String environmentTag;
	public static String userTag;
	private static String browserTag;
	public static String testName;
	public static String displayName;
	public static Set<String> getTags;
	private static int tagReturn;
	private static int testCount = 0;

	private static String userTempDirectory = System.getProperty("java.io.tmpdir");
	/*private static String delIETempFileCommand = String.format("if exist %sIED*.tmp (forfiles /P %s /M IED*.tmp /C \"cmd /c del @file\")", userTempDirectory, userTempDirectory);
	private static String delIETempFileCommand1 = String.format("if exist %s*\\IED*.tmp (forfiles /P %s*\\ /M IED*.tmp /C \"cmd /c del @file\")", userTempDirectory, userTempDirectory);
	private static String delEdgeTempFileCommand = String.format("if exist %sURL*.tmp (forfiles /P %s /M URL*.tmp /C \"cmd /c del @file\")", userTempDirectory, userTempDirectory);
	private static String delEdgeTempFileCommand1 = String.format("if exist %s*\\URL*.tmp (forfiles /P %s*\\ /M URL*.tmp /C \"cmd /c del @file\")", userTempDirectory, userTempDirectory);
	private static String rmdirIEDriverFolderCommand = String.format("if exist %sIEDriver-*-*-*-*-* (for /d %%a in (%sIEDriver-*-*-*-*-*) do del /f/s/q %%a > nul)", userTempDirectory, userTempDirectory);
	private static String rmdirIEDriverFolderCommandPoint5 = String.format("if exist %sIEDriver-*-*-*-*-* (for /d %%a in (%sIEDriver-*-*-*-*-*) do rmdir /s/q %%a)", userTempDirectory, userTempDirectory);
	private static String rmdirIEDriverFolderCommand1 = String.format("if exist %s*\\IEDriver-*-*-*-*-* (for /d %%a in (%s*\\IEDriver-*-*-*-*-*) do del /f/s/q %%a > nul)", userTempDirectory, userTempDirectory);
	private static String rmdirIEDriverFolderCommand1Point5 = String.format("if exist %s*\\IEDriver-*-*-*-*-* (for /d %%a in (%s*\\IEDriver-*-*-*-*-*) do rmdir /s/q %%a)", userTempDirectory, userTempDirectory);
	private static String rmdirEdgeBitsFolderCommand = String.format("if exist %s*\\edge_BITS_*_* (for /d %%a in (%sedge_BITS_*_*) do del /f/s/q %%a > nul)", userTempDirectory, userTempDirectory);
	private static String rmdirEdgeBitsFolderCommandPoint5 = String.format("if exist %s*\\edge_BITS_*_* (for /d %%a in (%sedge_BITS_*_*) do rmdir /s/q %%a)", userTempDirectory, userTempDirectory);
	private static String rmdirEdgeBitsFolderCommand1 = String.format("if exist %s*\\edge_BITS_*_* (for /d %%a in (%s*\\edge_BITS_*_*) do del /f/s/q %%a > nul)", userTempDirectory, userTempDirectory);
	private static String rmdirEdgeBitsFolderCommand1Point5 = String.format("if exist %s*\\edge_BITS_*_* (for /d %%a in (%s*\\edge_BITS_*_*) do rmdir /s/q %%a)", userTempDirectory, userTempDirectory);
	private static String msedgeUrlFetcherFolderCommand = String.format("if exist %smsedge_url_fetcher_*_* (for /d %%a in (%smsedge_url_fetcher_*_*) do del /f/s/q %%a > nul)", userTempDirectory, userTempDirectory);
	private static String msedgeUrlFetcherFolderCommandPoint5 = String.format("if exist %smsedge_url_fetcher_*_* (for /d %%a in (%smsedge_url_fetcher_*_*) do rmdir /s/q %%a)", userTempDirectory, userTempDirectory);
	private static String msedgeUrlFetcherFolderCommand1 = String.format("if exist %s*\\msedge_url_fetcher_*_* (for /d %%a in (%s*\\msedge_url_fetcher_*_*) do del /f/s/q %%a > nul)", userTempDirectory, userTempDirectory);
	private static String msedgeUrlFetcherFolderCommand1Point5 = String.format("if exist %s*\\msedge_url_fetcher_*_* (for /d %%a in (%s*\\msedge_url_fetcher_*_*) do rmdir /s/q %%a)", userTempDirectory, userTempDirectory);
	private static String[] commands = new String[] {delIETempFileCommand, delIETempFileCommand1,
			delEdgeTempFileCommand, delEdgeTempFileCommand1,
			rmdirIEDriverFolderCommand, rmdirIEDriverFolderCommand1,
			rmdirEdgeBitsFolderCommand, rmdirEdgeBitsFolderCommand1,
			msedgeUrlFetcherFolderCommand, msedgeUrlFetcherFolderCommand1,
			rmdirIEDriverFolderCommandPoint5, rmdirIEDriverFolderCommand1Point5,
			rmdirEdgeBitsFolderCommandPoint5, rmdirEdgeBitsFolderCommand1Point5,
			msedgeUrlFetcherFolderCommandPoint5, msedgeUrlFetcherFolderCommand1Point5};
	private static ProcessBuilder processBuilder = new ProcessBuilder();
*/
	@BeforeAll
	public static void beforeAll()
	{
		// If this value is null, it means we ran the test locally from the class or method, so we default to UAT
		// This can be changed to DEV if you're trying to run a test locally in DEV
		environmentTag = System.getProperty("ENVIRONMENT_TO_RUN") != null ? System.getProperty("ENVIRONMENT_TO_RUN") : "uat";
		// If this value is null, it means we ran the test locally from the class or method, so we default to false
		// This can be changed to true if you're trying to run a test locally with headless mode
		headlessMode = System.getProperty("HEADLESS_MODE") != null ? Boolean.parseBoolean(System.getProperty("HEADLESS_MODE")) : false;
	}

	@BeforeEach
	public void beforeEach(TestInfo testInfo)
	{
		getTags = testInfo.getTags();
		testName = testInfo.getTestMethod().get().getName();
		displayName = testInfo.getDisplayName();

		setupLogger();
		logger.information("killDriverAndBrowserTasks starting before each");
		TestDriver.killDriverAndBrowserTasks();
		logger.information("killDriverAndBrowserTasks finished before each");

		logger.information("tagsCheck starting before each");
		tagReturn = tagsCheck();
		logger.information("tagsCheck finished before each");
		if(tagReturn == 1)
		{
			logger.information("createContext starting before each");
			createContext();
			logger.information("createContext finished before each");
			logger.information("maximizeAndSetTimeouts starting before each");
			TestDriver.maximizeAndSetTimeouts();
			logger.information("maximizeAndSetTimeouts finished before each");
		}

		logger.information("Running test: " + displayName);
		logger.information("Environment: " + environmentTag);
		logger.information("Browser: " + browserTag);
		logger.information("Headless Mode: " + headlessMode);
		RobotUtils.resetMouseToUpperLeft();
		testCount++;
	}

	@AfterEach
	public void afterEach()
	{
		if(HierarchyDatapodConfiguration.getConfig() != null)
		{
			HierarchyDatapodConfiguration.resetConfig();
		}
		if(tagReturn == 1)
		{
			logger.information("quitDriver started after each");
			TestDriver.quitDriver();
			logger.information("quitDriver finished after each");

			logger.information("closeContext starting after each");
			closeContext();
			logger.information("closeContext finished after each");

			logger.information("killDriverAndBrowserTasks starting after each");
			TestDriver.killDriverAndBrowserTasks();
			logger.information("killDriverAndBrowserTasks finished after each");

			logger.information("deleteIEAndEdgeTempFiles starting after each");
			deleteIEAndEdgeTempFiles();
			logger.information("deleteIEAndEdgeTempFiles finished after each");
		}
		System.out.println("closeLogger starting after each");
		closeLogger();
		System.out.println("closeLogger finished after each");
	}
	
	@AfterAll
	public static void afterAll()
	{
		if(testCount > 1)
		{
			if(tagReturn == 1)
			{
				System.out.println("quitDriver started after all");
				TestDriver.quitDriver();
				System.out.println("quitDriver finished after all");

				System.out.println("killDriverAndBrowserTasks starting after all");
				TestDriver.killDriverAndBrowserTasks();
				System.out.println("killDriverAndBrowserTasks finished after all");

				System.out.println("deleteIEAndEdgeTempFiles starting after all");
				deleteIEAndEdgeTempFiles();
				System.out.println("deleteIEAndEdgeTempFiles finished after all");
			}
			System.out.println("closeLogger starting after all");
			closeLogger();
			System.out.println("closeLogger finished after all");
		}
	}

	public static void openBrowser()
	{
		setupLogger();
		createContext();
		TestDriver.maximizeAndSetTimeouts();
		RobotUtils.resetMouseToUpperLeft();
	}

	public static void closeBrowser()
	{
		TestSetupEdge.afterAll();
	}

	private static void closeContext()
	{
		annotationConfigApplicationContext.close();
	}
	
	private static void createContext()
    {
		annotationConfigApplicationContext = new AnnotationConfigApplicationContext(TestSetupEdge.class);
    }

    @Bean
	@Scope("singleton")
	public static WebDriver driver()
	{
		return TestDriver.getDriver();
    }
	
	/**
	 * Checks test for Custom Annotations, changes TestSetup based on return integer.
	 * The Test annotation does not get counted, only our custom annotations are counted
	 * @return 0: Detected Rest tag, prevents driver setup. <br> 1: Detected User tags, allows driver setup.
	 */
	public static int tagsCheck()
	{
		int toReturn = -1;
		int tagsCount = getTags.size();
		String logMessage = "\nYou didn't set the correct %s of Annotations for this test... Good job.\nAnnotation presence and order should be: @Test, @BrowserAnnotation, @UserAnnotation, @LogAnnotation if this is a non-rest test.\nFor rest tests, annotation presence and order should be: @Test, @RestAnnotation, @LogAnnotation.\nThe CustomAnnotations class contains the available Browser, User, Rest, and Log annotations.\n";

		if(tagsCount == 2)
		{
			Iterator<String> tagIterator = getTags.iterator();
			String restTestTag = tagIterator.next();
			String logTestTag = tagIterator.next();

			if(CustomAnnotations.RestAnnotations.restAnnotations.contains(restTestTag) &&
					CustomAnnotations.LogAnnotations.logAnnotations.contains(logTestTag))
			{
				toReturn = 0;
			}
			else
			{
				Assertions.fail(displayName + String.format(logMessage, "order/value"));
			}
		}
		else if(tagsCount == 3)
		{
			Iterator<String> tagIterator = getTags.iterator();
			String browserTestTag = tagIterator.next();
			String userTestTag = tagIterator.next();
			String logTestTag = tagIterator.next();

			if(CustomAnnotations.BrowserAnnotations.browserAnnotations.contains(browserTestTag) &&
					CustomAnnotations.UserAnnotations.userAnnotations.contains(userTestTag) &&
					CustomAnnotations.LogAnnotations.logAnnotations.contains(logTestTag))
			{
				browserTag = browserTestTag;
				userTag = userTestTag;
				toReturn = 1;
			}
			else
			{
				Assertions.fail(displayName + String.format(logMessage, "order/value"));
			}
		}
		else
		{
			Assertions.fail(displayName + String.format(logMessage, "number"));
		}
		return toReturn;
	}
	
	private static void setupLogger()
	{
		if (logger == null)
		{
			try
			{
				logger = new CustomLogger(getTags);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static void deleteIEAndEdgeTempFiles()
	{
		try
		{
			File tempDirectory = FileUtils.getTempDirectory();
			File[] allFiles = tempDirectory.listFiles();
			if (allFiles != null) {
				for (File file : allFiles) {
					try
					{
						FileUtils.forceDelete(file);
					}
					catch(IOException e)
					{
						System.out.println("An error occurred while deleting files: " + e.getMessage());
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.information("An error occurred while deleting files: " + e.getMessage());
		}
	}

    private static void closeLogger()
	{
		if(logger != null)
		{
			logger.closeTempLog();
			logger = null;
		}
	}

	public static String getEnvironment()
	{
		return environmentTag;
	}

	public static String getDisplayName()
	{
		return displayName;
	}

	public static String getBrowserTag()
	{
		return browserTag;
	}
}
