package com.thomsonreuters.codes.codesbench.quality.pages;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.SetLawTrackingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.login.LoginESSOPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.FiltersAndSortsPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.Dimension;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.login.LoginESSOPageElements.LOGIN_ESSO_PAGE_TITLE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.login.LoginPageElements.LOGIN_PAGE_TITLE;

public abstract class BasePage extends TestService
{
	private WebDriver driver;

	@Autowired
	public BasePage(WebDriver driver)
	{
		this.driver = driver;
		this.driver = TestService.driver();
	}

	/*
	 * methods for windows handling
	 */
	protected void openPageWithUrl(String pageUrl, String pageTitle)
	{
		//driver.navigate().to(pageUrl); //-- This will not work for WebDriverManager (bonigarcia)
		driver.get(pageUrl); // -- This is for Edge
		driver.navigate().refresh();
		waitForPageLoaded();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(DateAndTimeUtils.ONE_MINUTE));
		wait.until(ExpectedConditions.or(
				ExpectedConditions.titleContains(pageTitle),
				ExpectedConditions.titleContains(LOGIN_PAGE_TITLE),
				ExpectedConditions.titleContains(LOGIN_ESSO_PAGE_TITLE)));
		switchToWindow(pageTitle);
	}

	public String getTitle()
	{
		return driver.getTitle();
	}

	public String getUrl()
	{
		return driver.getCurrentUrl();
	}

	public boolean switchToWindow(String expectedWordsInTitle, boolean waitForLoad)
	{
		return switchToWindow(expectedWordsInTitle, waitForLoad, DateAndTimeUtils.TEN_SECONDS);
	}

	public boolean switchToWindow(String expectedWordsInTitle, boolean waitForLoad, int timeToWait)
	{
		if(checkWindowIsPresented(LOGIN_ESSO_PAGE_TITLE))
		{
			loginESSOPage().logInESSOWithByPass();
		}

		boolean windowFound = waitForWindowByTitle(expectedWordsInTitle, true, timeToWait);

		if(waitForLoad)
		{
			waitForPageLoaded();
		}

		return windowFound;
	}

	public boolean switchToWindow(String wordsInTitle)
	{
		if(checkWindowIsPresented(LOGIN_ESSO_PAGE_TITLE))
		{
			loginESSOPage().logInESSOWithByPass();
		}
		waitForPageLoaded();
		return switchToWindow(wordsInTitle, true);
	}

	public boolean checkWindowIsPresented(String expectedWindowTitle)
	{
		if(isElementDisplayed(String.format("//div[@class='hd' and contains(@id,'dialog') and contains(text(), '%s')]", expectedWindowTitle))
				|| isElementDisplayed(String.format("//div[contains(@id,'dialog')]/div[@class='hd' and contains(text(), '%s')]", expectedWindowTitle))
				|| isElementDisplayed(String.format("//div[contains(@class, 'ui-dialog ui-widget ui-widget-content')]//div[contains(@class,'ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix')]/span[contains(text(), '%s')]", expectedWindowTitle))
				|| isElementDisplayed(String.format("//div[contains(@class, 'modal-content')]//h5[text() = '%s']", expectedWindowTitle)))
		{
			return true;
		}

		Set<String> windowHandles = driver.getWindowHandles();
		for (String windowHandle : windowHandles)
		{
			try
			{
				driver.switchTo().window(windowHandle);
			}
			catch (UnhandledAlertException e)
			{
				acceptAlertNoFail();
				driver.switchTo().window(windowHandle);
			}
			String currentWindowTitle = driver.getTitle();
			if(currentWindowTitle.contains(expectedWindowTitle))
			{
				return true;
			}
			if(currentWindowTitle.toLowerCase().contains("single sign on"))
			{
				Assertions.fail("Error: 'Single sign on' page reached, make sure no other instances of IE are opened");
			}
		}
		return false;
	}

	public void waitForWindowByTitle(String title)
	{
		waitForWindowByTitle(title, true);
	}

	public boolean waitForWindowByTitle(String title, boolean failOnNotAvailible)
	{
		return waitForWindowByTitle(title, failOnNotAvailible, DateAndTimeUtils.TEN_SECONDS);
	}

	public boolean waitForWindowByTitle(String title, boolean failOnNotAvailible, int timeToWait)
	{
		long timeStart = System.currentTimeMillis();
		while (System.currentTimeMillis() - timeStart < timeToWait)
		{
			if(checkWindowIsPresented(title))
			{
				return true;
			}
		}
		if(failOnNotAvailible)
		{
			Assert.fail("Window with title " + title + " not found within " + (timeToWait/1000) + " seconds");
		}
		return false;
	}

	public boolean doesWindowExistByTitle(String windowTitle)
	{
		boolean windowExists = false;
		if(checkWindowIsPresented(windowTitle))
		{
			long timeStart = System.currentTimeMillis();
			while (System.currentTimeMillis() - timeStart < DateAndTimeUtils.FIVE_SECONDS)
			{
				windowExists = checkWindowIsPresented(windowTitle);
			}
		}
		else
		{
			return false;
		}
		return windowExists;
	}

	//TODO: Come back to this to change
	public void waitForWindowGoneByTitle(String title)
	{
		if(isElementDisplayed(String.format("//div[@class='hd' and contains(@id,'dialog') and contains(text(), '%s')]", title))
				|| isElementDisplayed(String.format("//div[contains(@id,'dialog')]/div[@class='hd' and contains(text(), '%s')]", title))
				|| isElementDisplayed(String.format("//div[contains(@class, 'ui-dialog ui-widget ui-widget-content')]//div[contains(@class,'ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix')]/span[contains(text(), '%s')]", title)))
		{
			int htmlWindowCounter = 0;
			while((isElementDisplayed(String.format("//div[@class='hd' and contains(@id,'dialog') and contains(text(), '%s')]", title))
					|| isElementDisplayed(String.format("//div[contains(@id,'dialog')]/div[@class='hd' and contains(text(), '%s')]", title))
					|| isElementDisplayed(String.format("//div[contains(@class, 'ui-dialog ui-widget ui-widget-content')]//div[contains(@class,'ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix')]/span[contains(text(), '%s')]", title)))
					&& htmlWindowCounter < 60)
			{
				DateAndTimeUtils.takeNap(DateAndTimeUtils.HALF_SECOND);
				htmlWindowCounter++;
			}
			if(htmlWindowCounter >= 60)
			{
				Assertions.fail(String.format("Window with title %s still appears", title));
			}
		}
		else
		{
			int ieWindowCounter = 0;
			while(doesWindowExistByTitle(title) && ieWindowCounter < 60)
			{
				DateAndTimeUtils.takeNap(DateAndTimeUtils.HALF_SECOND);
				ieWindowCounter++;
			}
			if(ieWindowCounter >= 60)
			{
				Assertions.fail(String.format("Window with title %s still appears", title));
			}
		}
	}

	public void waitForPageLoaded()
	{
		ExpectedCondition<Boolean> expectation = driver ->
		{
			try
			{
				return ((JavascriptExecutor) driver).executeScript("return document.readyState")
						.equals("complete");
			}
			catch (Exception e)
			{
				DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);

				return ((JavascriptExecutor) driver).executeScript("return document.readyState")
						.equals("complete");
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofMillis(DateAndTimeUtils.ONE_MINUTE));
		wait.until(expectation);
	}


	// public void switchToModalWindow(String wordsInTitle)
	// {
	// if (!doesElementExist("//div[@class='hd' and contains(@id,'dialog') and contains(text(), '" +
	// wordsInTitle + "')]")
	// && !doesElementExist("//div[contains(@id,'dialog')]/div[@class='hd' and contains(text(), '" +
	// wordsInTitle + "')]"))
	// {
	// throw new RuntimeException("Found no modal window with title '" + wordsInTitle + "'");
	// }
	// }

	public void switchToModalWindow()
	{
		driver.switchTo().activeElement();
	}

//	public boolean switchToLastWindow(int expectedWindowCount)
//	{
//		Set<String> windowHandles = driver.getWindowHandles();
//		int actualWindowCount = windowHandles.size();
//		int iteration = 0;
//		int maxCounter = 10;
//
//		while (actualWindowCount < expectedWindowCount && iteration < maxCounter)
//		{
//			windowHandles = driver.getWindowHandles();
//			actualWindowCount = windowHandles.size();
//			iteration++;
//			DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
//		}
//		if (actualWindowCount == expectedWindowCount)
//		{
//			for(String handle : windowHandles)
//			{
//				driver.switchTo().window(handle);
//				String title = driver.getTitle();
//				logger.information(("Title: " + title));
//			}
//			return true;
//		}
//		logger.information("Windows present: "+ actualWindowCount);
//		return false;
//	}

	public void enterTheInnerFrame()
	{
		waitForPageLoaded();

		if(doesElementExist("//iframe"))
		{
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe")));
		}
	}

	public void enterTheInnerFrameById(String id)
	{
		waitForPageLoaded();

		if(doesElementExist(String.format("//iframe[@id='%s']", id)))
		{
			driver.switchTo().frame(driver.findElement(By.xpath(String.format("//iframe[@id='%s']", id))));
		}
	}

	public void switchToInnerIFrameByIndex(int index)
	{
		WebElement iframe = driver.findElements(By.xpath("//iframe")).get(index);
		driver.switchTo().frame(iframe);
	}

	public void switchToInnerIFrameByName(String name)
	{
		driver.switchTo().frame(name);
	}

	public boolean switchToWindowNoFail(final String wordsInTitle)
	{
		boolean found = false;

		waitForWindowByTitle(wordsInTitle, false);

		Set<String> windowHandles;
		try
		{
			windowHandles = driver.getWindowHandles();
		}
		catch (Exception e)
		{
			return false;
		}

		for (String window : windowHandles) {
			driver.switchTo().window(window);

			String title = driver.getTitle();

			if (title.contains(wordsInTitle)) {
				found = true;

				break;
			}
		}

		return found;
	}

	public boolean isWindowHandlesAsExpected(int count)
	{
		Set<String> windowHandles;
		try
		{
			windowHandles = driver.getWindowHandles();
		}
		catch (Exception e)
		{
			return false;
		}
		return windowHandles.size() >= count;
	}

	public void breakOutOfFrame()
	{
		driver.switchTo().defaultContent();
	}

	public void closeCurrentWindowIgnoreDialogue()
	{
		if(isElementDisplayed("//div[@class='hd' and contains(@id,'dialog')]"))
		{
			driver.findElement(By.xpath("//a[@class='container-close']")).click();
		}
		else
		{
			driver.close();
		}
	}

	/**
	 * Maximizes the current window then waits for the page to be loaded.
	 */
	public void maximizeCurrentWindow()
	{
		driver.manage().window().maximize();
		waitForPageLoaded();
	}

	/*
	 * methods for elements handling
	 */

	public WebElement getElement(String xpath)
	{
		if(xpath == null || xpath.isEmpty())
		{
			throw new IllegalArgumentException("Xpath is null/empty");
		}
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getElementById(String id)
	{
		if(id == null || id.isEmpty())
		{
			throw new IllegalArgumentException("Id is null/empty");
		}
		return driver.findElement(By.id(id));
	}

	public WebElement getElement(WebElement element, String xpath)
	{
		if(xpath == null || xpath.isEmpty())
		{
			throw new IllegalArgumentException("Xpath is null/empty");
		}
		return element.findElement(By.xpath(xpath));
	}

	public List<WebElement> getElements(WebElement element, String xpath)
	{
		return element.findElements(By.xpath(xpath));
	}

	public List<WebElement> getElements(String xpath)
	{
		return driver.findElements(By.xpath(xpath));
	}

	//6-30-2020: acceptable methods to place in BasePage?
	public WebElement getElementsNextSibling(WebElement element)
	{
		return element.findElement(By.xpath("following-sibling::*[1]"));
	}

	public WebElement getElementsPreviousSibling(WebElement element)
	{
		return element.findElement(By.xpath("preceding-sibling::*[1]"));
	}

	public WebElement returnExistingElement(String xpath)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	}

	public void waitForElement(String xpath)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	}

	public void highlightFollowingSiblingsUsingRobotF7(int countOfSiblingsToHighlight)
	{
		for (int i = 0; i < countOfSiblingsToHighlight; i++)
		{
			try
			{
				DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
				RobotUtils.getRobot().keyPress(KeyEvent.VK_F7);
			}
			finally
			{
				RobotUtils.getRobot().keyRelease(KeyEvent.VK_F7);
			}
		}
	}

	public void longWaitForElement(String xpath)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(660));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	}

	public void waitForVisibleElement(String xpath)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	public void waitForVisibleElement(String xpath, int timeout)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	public WebElement returnVisibleElement(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public WebElement returnClickableElement(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForElement(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElement(WebElement element, int timeout)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public boolean doesElementExist(String xpath, int timeout)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
			return driver.findElements(By.xpath(xpath)).size() != 0;
		}
		catch(Exception ex)
		{
			return false;
		}
		finally
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DateAndTimeUtils.IMPLICIT_ELEMENT_WAIT_SECONDS));
		}
	}

	public boolean doesElementExist(String xpath)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
			return driver.findElements(By.xpath(xpath)).size() != 0;
		}
		catch(Exception ex)
		{
			return false;
		}
		finally
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DateAndTimeUtils.IMPLICIT_ELEMENT_WAIT_SECONDS));
		}
	}

	public boolean isElementDisplayed(String xpath)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
			WebElement element = driver.findElement(By.xpath(xpath));
			return element.isDisplayed();
		}
		catch (Exception ex)
		{
			return false;
		}
		finally
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DateAndTimeUtils.IMPLICIT_ELEMENT_WAIT_SECONDS));
		}
	}

	public boolean isElementDisplayed(WebElement element)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
			return element.isDisplayed();
		}
		catch (Exception ex)
		{
			return false;
		}
		finally
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DateAndTimeUtils.IMPLICIT_ELEMENT_WAIT_SECONDS));
		}
	}

	public boolean isElementClickable(WebElement element)
	{
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(element)).isDisplayed();
		}
		catch(TimeoutException ignore) {
			return false;
		}
	}

	public boolean isElementDisabled(String xpath)
	{
		WebElement element = getElement(xpath);
		return isElementDisabled(element);
	}

	public boolean isElementDisabled(WebElement element)
	{
		String classAttr = element.getAttribute("class");
		if((classAttr != null) && classAttr.contains("disabled"))
		{
			return true;
		}

		String disabledAttr = element.getAttribute("disabled");
		if((disabledAttr != null) && (disabledAttr.contains("true") || disabledAttr.contains("disabled") || disabledAttr.contains("Disabled")))
		{
			return true;
		}

		return false;
	}

	public boolean waitForElementToBeDisabled(String xpath)
	{
		return waitForElementToBeDisabled(xpath, DateAndTimeUtils.THREE_SECONDS);
	}

	public boolean waitForElementToBeDisabled(String xpath, long timeToWait)
	{
		long start = (new Date()).getTime();
		while (((new Date()).getTime() - start) < timeToWait)
		{
			if(isElementDisabled(xpath))
			{
				return true;
			}

			DateAndTimeUtils.takeNap(100);
		}
		return false;
	}

	public boolean isElementEnabled(WebElement element)
	{
		return element.isEnabled();
	}

	public boolean isElementEnabled(String xpath)
	{
		WebElement element = getElement(xpath);
		return element.isEnabled();
	}

	public boolean waitForElementToBeEnabled(String xpath)
	{
		return waitForElementToBeEnabled(xpath, DateAndTimeUtils.THREE_SECONDS);
	}

	public boolean waitForElementToBeEnabled(String xpath, long timeToWaitInMsc)
	{
		WebElement element = getElement(xpath);
		long start = (new Date()).getTime();
		while (((new Date()).getTime() - start) < timeToWaitInMsc)
		{
			if(element.isEnabled())
			{
				return true;
			}

			DateAndTimeUtils.takeNap(100);
		}
		return false;
	}

	public boolean waitForElementExists(String xpath)
	{
		return waitForElementExists(xpath, DateAndTimeUtils.THIRTY_SECONDS);
	}

	public boolean waitForElementExists(String xpath, long timeToWaitInMsc)
	{
		long start = new Date().getTime();
		while (new Date().getTime() - start < timeToWaitInMsc)
		{
			if(getElements(xpath).size() > 0)
				return true;
		}
		return false;
	}

	// Same as above, but for webelements
	public boolean waitForElementExists(WebElement element)
	{
		return waitForElementExists(element, DateAndTimeUtils.THIRTY_SECONDS);
	}

	public boolean waitForElementExists(WebElement element, long timeToWaitInMsc)
	{
		long start = new Date().getTime();
		while (new Date().getTime() - start < timeToWaitInMsc)
		{
			if(element.isDisplayed())
				return true;
		}
		return false;
	}

	public boolean waitForGridRefresh()
	{
		waitForElementGone(CommonPageElements.PROCESSING_PLEASE_WAIT_XPATH);
		waitForPageLoaded();
		return doesElementExist(CommonPageElements.PROCESSING_PLEASE_WAIT_XPATH);
	}

	public void waitForProcessing()
	{
		waitForElementGone(CommonPageElements.PROCESSING_PLEASE_WAIT_XPATH, 10);
	}

	/*
	 * TODO
	 * public boolean waitForGridRefresh()
	 * {
	 * int count = 0;
	 * while(doesElementExist(SourceNavigatePageElements.PROGRESS_BAR) && count < 40)
	 * {
	 * waitForElementGone(SourceNavigatePageElements.PROGRESS_BAR);
	 * count ++;
	 * }
	 * return doesElementExist(SourceNavigatePageElements.PROGRESS_BAR);
	 * }
	 */

	public boolean isElementReadOnly(String xpath)
	{
		try
		{
			if(driver.findElement(By.xpath(xpath)).getAttribute("class")
					.matches("(.*)read-only|readonly(.*)"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception ex)
		{
			return false;// WTF!!!???
		}
	}

	public void waitForElementToBeClickable(String elementXpath)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementXpath)));
	}

	public void waitForElementToBeClickable(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public boolean waitForElementGone(String xpath)
	{
		/*
		Basically ping the page for 5 seconds, each second seeing if the element is there.
		If the element isn't there after 5 seconds, we assume it won't appear and return.
		This is in place because with the edge ie mode driver, waiting for grid refresh (example)
		takes the whole 30 seconds even when the element isn't there.
		 */
		for(int i = 0; i < 5; i++)
		{
			if(!isElementDisplayed(xpath))
			{
				DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
			}
		}
		if(!isElementDisplayed(xpath))
		{
			return true;
		}
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		boolean gone;
		try
		{
			gone = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		}
		catch (Exception e)
		{
			gone = true;
		}
		return gone;
	}

	public boolean waitForElementGone(String xpath, int timeout)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		boolean gone;

		try
		{
			gone = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		}
		catch (Exception e)
		{
			gone = true;
		}
		return gone;
	}

	public void waitForElementsCountToBe(String xPath, int count)
	{
		new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(100))
				.until(f -> countElements(xPath) == count);
	}

	public void waitForElementsAttributeToBe(WebElement element, String attribute, String attributeExpectedValue)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.attributeToBe(element, attribute, attributeExpectedValue));
	}

	public void waitUntilElementIsStale(WebElement element)
	{
		Wait wait = new FluentWait(driver)
				.withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(Exception.class);

		wait.until(ExpectedConditions.stalenessOf(element));
	}

	public void waitUntilElementsTextIsTheFollowing(String xpath, String text)
	{
		Wait wait = new FluentWait(driver)
				.withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(Exception.class);

		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpath), text));
	}

	public boolean longWaitForElementGone(String xpath)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
		boolean gone;

		try
		{
			gone = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		}
		catch (Exception e)
		{
			// Sometimes the page goes so fast that when we look for element
			// invisibility that we can't find it
			// because it's gone. If this error shows up we will try to set gone
			// equal to true and see if it fixes it.
			/*
			 * if (e.getMessage().contains("Error determining if element is displayed"))
			 * {
			 * gone = true;
			 * }
			 */
			gone = true;
		}
		return gone;
	}

	public boolean isElementSelected(String xpath)
	{
		try
		{
			if((driver.findElement(By.xpath(xpath)).getAttribute("class").contains("selected")) ||
				   (driver.findElement(By.xpath(xpath)).getAttribute("aria-selected").contains("true")))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception ex)
		{
			return false;// WTF!!!???
		}
	}

	public boolean isElementSelected(WebElement element)
	{
		return element.isSelected();
	}

	public void scrollTo(String xpath)
	{
		try
		{
			scrollTo(driver.findElement(By.xpath(xpath)));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void scrollTo(WebElement element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String id = element.getAttribute("id");
		js.executeScript("document.getElementById('" + id + "').scrollIntoView(true);");
	}

	public void scrollToJSXpath(String xpath)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementByXpath('" + xpath + "').scrollIntoView(true);");
	}

	public void scrollToView(WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void scrollToView(String elementXpath)
	{
		scrollToView(getElement(elementXpath));
	}

	public boolean checkAttributeValue(String elementXpath, String attribute, String expectedValue)
	{
		boolean checkValue;
		try
		{
			checkValue = driver.findElement(By.xpath(elementXpath))
					.getAttribute(attribute).equals(expectedValue);
		}

		catch (Exception e)
		{
			checkValue = false;// WTF!!!???
		}
		return checkValue;
	}

	public void waitForAttributeValueContains(WebElement element, String attribute, String expectedValue)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		wait.until(ExpectedConditions.attributeContains(element, attribute, expectedValue));
	}

	public int countElements(String xpath) {
		return getElements(xpath).size();
	}

	public String getElementsText(String xpath)
	{
		return returnExistingElement(xpath).getText();
	}

	public String getElementsText(WebElement element)
	{
		return element.getText();
	}

	public String getElementsAttribute(String xpath, String attribute)
	{
		return getElement(xpath).getAttribute(attribute);
	}

	public String getElementsAttribute(WebElement element, String attribute)
	{
		return element.getAttribute(attribute);
	}

	public String getElementsId(String xpath)
	{
		return getElement(xpath).getAttribute("id");
	}

	/*
	 * methods for alerts handling
	 */

	public Alert waitForAlert()
	{
		Alert alert = null;

		try
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.alertIsPresent());
			alert = driver.switchTo().alert();
			logger.information("Found an alert with text '" + alert.getText() + "'");
		}
		catch (NoAlertPresentException | TimeoutException e)
		{
			logger.information("No alert found");
		}

		return alert;
	}

	public void acceptAlert()
	{
		Alert alert = waitForAlert();

		if(alert == null)
		{
			logger.warning("Could not accept an alert!");
			Assert.fail();
		}

		alert.accept();
		// logger.step("Accepting the alert that says '" + alert.getText() + "'");
	}

	public boolean isAlertPresent()
	{
		try
		{
			driver.switchTo().alert();
			return true;
		}
		catch (NoAlertPresentException Ex)
		{
			return false;
		}
	}

	public boolean checkAlertTextMatchesGivenText(String alertText)
	{
		Alert alert = waitForAlert();
		boolean textMatches = alert.getText().equals(alertText);
		alert.accept();
		return textMatches;
	}

	public boolean checkAlertTextMatchesGivenTextAndDismiss(String alertText)
	{
		Alert alert = waitForAlert();
		boolean textMatches = alert.getText().equals(alertText);
		alert.dismiss();
		return textMatches;
	}

	public void dismissAlert()
	{
		Alert alert = waitForAlert();

		if(alert == null)
		{
			logger.warning("Could not dismiss an alert!");
			Assert.fail();
		}

		alert.dismiss();
		logger.step("Dismissing the alert that says '" + alert.getText() + "'");
	}

	public boolean acceptAlertNoFail()
	{
		Alert alert = waitForAlert();

		if(alert != null)
		{
			alert.accept();
			logger.step("Accepting the alert");
			return true;
		}
		else
		{
			return false;
		}
	}

	/*
	 * methods for elements interractions
	 */

	public void click(WebElement element)
	{
		element.click();
	}

	public void click(String elementXpath)
	{
		returnClickableElement(returnExistingElement(elementXpath)).click();
	}

	public void clear(String elementXpath)
	{
		returnClickableElement(returnExistingElement(elementXpath)).clear();
	}

	public void clear(WebElement element)
	{
		element.clear();
	}

	public void doubleClick(String elementXpath)
	{
		(new Actions(driver)).doubleClick(getElement(elementXpath)).build().perform();
	}

	public void doubleClick(WebElement element)
	{
		(new Actions(driver)).doubleClick(element).build().perform();
	}

	public void sendEnterToElement(String xpath)
	{
		driver.findElement(By.xpath(xpath)).sendKeys(Keys.ENTER);
	}

	public void sendEnterToElement(WebElement element)
	{
		element.sendKeys(Keys.ENTER);
	}

	public void rightClick(String xpath)
	{
		rightClick(driver.findElement(By.xpath(xpath)));
	}

	public void rightClick(WebElement element)
	{
		(new Actions(driver)).contextClick(element).build().perform();
	}

	public void sendKeys(CharSequence... keys)
	{
		(new Actions(driver)).sendKeys(keys).build().perform();
	}

	public void sendKeysToElement(WebElement element, String keys)
	{
		element.sendKeys(keys);
	}

	public void clearAndSendKeysToElement(WebElement element, String keys)
	{
		element.clear();
		click(element);
		click(element);
		element.sendKeys(keys);
	}

	public void sendKeysToElement(String elementXpath, String keys)
	{
		sendKeysToElement(getElement(elementXpath), keys);
	}

	public void sendKeyToElement(WebElement element, Keys key)
	{
		element.sendKeys(key);
	}

	public void sendKeyToElement(String elementXpath, Keys key)
	{
		getElement(elementXpath).sendKeys(key);
	}

	public void sendTextToTextBoxAuto(String xpathOfTextBox, String setValue)
	{
		sendTextToTextbox(xpathOfTextBox, setValue);
		waitForElement(String.format(FiltersAndSortsPageElements.FILTER_DROPDOWN_OPTION, setValue));
		sendEnterToElement(String.format(FiltersAndSortsPageElements.FILTER_DROPDOWN_OPTION, setValue));
		DateAndTimeUtils.takeNap(DateAndTimeUtils.HALF_SECOND);
	}

	public void sendTextToTextBoxAuto(WebElement xpathOfTextBox, String setValue)
	{
		sendTextToTextbox(xpathOfTextBox, setValue);
		waitForElement(String.format(FiltersAndSortsPageElements.FILTER_DROPDOWN_OPTION, setValue));
		click(String.format(FiltersAndSortsPageElements.FILTER_DROPDOWN_OPTION, setValue));
		DateAndTimeUtils.takeNap(DateAndTimeUtils.QUARTER_SECOND);
	}

	public void sendTextToTextbox(String textBoxXpath, String text)
	{
		//WebElement textbox = driver.findElement(By.xpath(textBoxXpath));
		WebElement textbox = getElement(textBoxXpath);
		try
		{
			scrollToElement(textbox);
		}
		catch (Exception e)
		{
			// TODO: handle exception - has catch but then throws another exveption ?
			throw new RuntimeException(e.getMessage()); // Temporary handler. Until this exception was not processed
		}
		try
		{
			clearAndSendKeysToElement(textbox, text);
//			textbox.clear();
//			Actions sendingText = new Actions(driver);
//			sendingText.sendKeys(textbox, text);
//			Action action = sendingText.build();
//			action.perform();
		}
		catch (Exception e)
		{
			// TODO: handle exception - has catch but then throws another exveption ?
			throw new RuntimeException(e.getMessage()); // Temporary handler. Until this exception was not processed
		}
	}

	//Clears textboxes without .clear because the .clear class in selenium does not work on pages not in IE11
	public void clearAndSendTextToTextbox(String textBoxXpath, String text)
	{
		WebElement textbox = getElement(textBoxXpath);
		try
		{
			scrollToElement(textbox);
		}
		catch (Exception e)
		{
			// TODO: handle exception - has catch but then throws another exveption ?
			throw new RuntimeException(e.getMessage()); // Temporary handler. Until this exception was not processed
		}
		try
		{
			textbox.click();
			textbox.sendKeys(Keys.HOME);
			textbox.sendKeys(Keys.chord(Keys.SHIFT, Keys.END));
			textbox.sendKeys(Keys.DELETE);
			sendKeysToElement(textbox, text);

		}
		catch (Exception e)
		{
			// TODO: handle exception - has catch but then throws another exveption ?
			throw new RuntimeException(e.getMessage()); // Temporary handler. Until this exception was not processed
		}
	}

//	public void sendKeysChord(Keys modifier, String button)
//	{
//		Actions builder = new Actions(driver);
//		Action select= builder
//				.keyDown(modifier)
//				.sendKeys(button)
//				.keyUp(modifier)
//				.build();
//		select.perform();
//	}

	public void sendTextToTextbox(WebElement textBoxXpath, String text)// TODO: rewrite - use prev paramenter name
	{
		WebElement textbox = textBoxXpath;
		try
		{
			scrollTo(textbox);
		}
		catch (Exception e)
		{
			// TODO: handle exception - has catch but then throws another exveption ?
			throw new RuntimeException(e.getMessage()); // Temporary handler. Until this exception was not processed
		}
		//waitForElementToBeClickable(textBoxXpath);
		try
		{
			clearAndSendKeysToElement(textbox, text);
//			textbox.clear();
//			Actions sendingText = new Actions(driver);
//			sendingText.sendKeys(textbox, text);
//			Action action = sendingText.build();
//			action.perform();
		}
		catch (Exception e)
		{
			// TODO: handle exception - has catch but then throws another exveption ?
			throw new RuntimeException(e.getMessage()); // Temporary handler. Until this exception was not processed
		}
	}

	//Clears textboxes without .clear because the .clear class in selenium does not work on pages not in IE11
	public void clearAndSendTextToTextbox(WebElement textbox, String text)
	{
		try
		{
			scrollTo(textbox);
		}
		catch (Exception e)
		{
			// TODO: handle exception - has catch but then throws another exveption ?
			throw new RuntimeException(e.getMessage()); // Temporary handler. Until this exception was not processed
		}
		//waitForElementToBeClickable(textBoxXpath);
		try
		{
			textbox.click();
			textbox.sendKeys(Keys.HOME);
			textbox.sendKeys(Keys.chord(Keys.SHIFT, Keys.END));
			sendKeysToElement(textbox, text);
		}
		catch (Exception e)
		{
			// TODO: handle exception - has catch but then throws another exveption ?
			throw new RuntimeException(e.getMessage()); // Temporary handler. Until this exception was not processed
		}
	}

	//new method for loginPage and HierarchySearchPage
	public void setTextOfElement(String text, WebElement textBox)
	{
		((JavascriptExecutor) driver).executeScript(String.format("arguments[0].value='%s'",text), textBox);
	}


	public void selectDropdownOption(String xpath, String optionToSelect)
	{
		selectDropdownOption(getElement(xpath), optionToSelect);
	}

	public void selectDropdownOption(WebElement element, String optionToSelect)
	{
		Select select = new Select(element);
		select.selectByVisibleText(optionToSelect);
		waitForPageLoaded();

	}

	public void selectDropdownOptionByValue(WebElement element, String valueToSelect)
	{
		Select select = new Select(element);
		select.selectByValue(valueToSelect);
		waitForPageLoaded();
	}

	public void selectDropdownOptionUsingJavascript(String id, String valueToSelect)
	{
		String javascriptString = "document.getElementById('" + id + "').value = '" + valueToSelect + "'";
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript(javascriptString);
		waitForPageLoaded();
	}

	public String getSource()
	{
		return driver.getPageSource();
	}

	public WebElement getSelectedDropdownOption(WebElement element)
	{
		Select select = new Select(element);
		return select.getFirstSelectedOption();
	}

	public WebElement getSelectedDropdownOption(String xpath)
	{
		Select select = new Select(driver.findElement(By.xpath(xpath)));
		return select.getFirstSelectedOption();
	}

	public String getSelectedDropdownOptionText(String xpath)
	{
		return getSelectedDropdownOption(driver.findElement(By.xpath(xpath))).getText();
	}

	public String getSelectedDropdownOptionText(WebElement element)
	{
		return getSelectedDropdownOption(element).getText();
	}

	public List<String> getAllDropDownOptions(String xpath)
	{
		return getSelectOptionsText(getElement(xpath));
	}

	public boolean verifyAllDropDownOptionsExist(String dropDownSelectXpath, List<String> expectedList)
	{
		return getAllDropDownOptions(dropDownSelectXpath).containsAll(expectedList);
	}

	public void controlClickElements(List<WebElement> elements)
	{
		Actions builder = new Actions(driver);

		builder.keyDown(Keys.CONTROL);

		for (WebElement element : elements)
		{
			builder.click(element);
		}

		builder.keyUp(Keys.CONTROL);

		Action toPerform = builder.build();

		toPerform.perform();
	}

	public void openContextMenu(WebElement... items)
	{
		if(items == null || items.length < 1)
		{
			throw new RuntimeException("Menu Items array is incorrect");
		}
		Actions builder = new Actions(driver);
		WebElement last = items[items.length - 1];
		for (WebElement element : items)
		{
			if(element != last)
			{
				element.sendKeys(Keys.ARROW_RIGHT);
			}
			else
			{
				/*new Actions(driver)
						.moveToElement(element)
						.sendKeys(Keys.ENTER)
						.build()
						.perform();*/
				element.sendKeys(Keys.ENTER);
			}
		}
	}

	public void openContextMenu(String... items)
	{
//		if(items == null || items.length < 1)
//		{
//			throw new RuntimeException("Menu Items array is incorrect");
//		}
//		Actions builder = new Actions(driver);
//
//		String last = items[items.length - 1];
//		Arrays.stream(items)
//						.filter(item -> !item.equals(last))
//						// .forEach( item -> driver.findElement(By.xpath(item)).sendKeys(Keys.RIGHT));
//						// sendEnterToElement(last);
//						.forEach(item -> builder.sendKeys(getElement(item), Keys.ARROW_RIGHT).build().perform());
//		builder.sendKeys(getElement(last), Keys.ENTER).build().perform();
		if(items == null || items.length < 1)
		{
			throw new RuntimeException("Menu Items array is incorrect");
		}
		Actions builder = new Actions(driver);
		String last = items[items.length - 1];
		for (String string : items)
		{
			if(string != last)
			{
				getElement(string).sendKeys(Keys.ARROW_RIGHT);
			}
			else
			{
				getElement(string).sendKeys(Keys.ENTER);
			}
		}
	}

	public void openContextMenu(String menuXpath)
	{
		getElement(menuXpath).sendKeys(Keys.ENTER);
	}

	public void openContextMenuWithClick(String menuXpath)
	{
		getElement(menuXpath).click();
	}

	public void openContextMenu(WebElement element)
	{
		element.sendKeys(Keys.ENTER);
		//waitForPageLoaded();
	}

	public void openContextMenuForElement(String element, String... items)
	{
		int lastItem = items.length - 1;
		if(items == null || lastItem < 0)
		{
			throw new RuntimeException("Menu Items array is incorrect");
		}
		rightClick(element);
		for(int i = 0; i < lastItem; i++)
		{
			waitForElement(items[i]);
			getElement(items[i]).sendKeys(Keys.ARROW_RIGHT);
		}
		waitForElement(items[lastItem]);
		click(items[lastItem]);
	}

	public void openContextSubMenu(String... items)
	{
		if(items == null || items.length < 1)
		{
			throw new RuntimeException("Menu Items array is incorrect");
		}
		Actions builder = new Actions(driver);
		String last = items[items.length - 1];
		for (String string : items)
		{
			if(string != last)
			{
				getElement(string).sendKeys(Keys.ARROW_RIGHT);
			}
			else
			{
				getElement(string).sendKeys(Keys.ENTER);
			}
		}
	}

	public void uncheckCheckbox(String checkboxXpath)
	{
		WebElement checkbox = getElement(checkboxXpath);
		if(checkbox.isSelected() || "true".equals(checkbox.getAttribute("checked")))
		{
			checkbox.click();
		}
	}

	public void checkCheckbox(String checkboxXpath)
	{
		WebElement checkbox = getElement(checkboxXpath);
		if(!checkbox.isSelected() || !"true".equals(checkbox.getAttribute("checked")))
		{
			checkbox.click();
		}
	}

	public boolean isCheckboxChecked(String checkboxXpath)
	{
		if(checkboxXpath == null || checkboxXpath.isEmpty())
			throw new IllegalArgumentException("Checkbox xpath not specified");
		WebElement checkbox = getElement(checkboxXpath);
		return isCheckboxChecked(checkbox);
	}

	public boolean isCheckboxChecked(WebElement checkbox)
	{
		return checkbox.isSelected() || "true".equals(checkbox.getAttribute("checked"));
	}

	public boolean checkWindowIsClosed(String windowtitle)
	{
		boolean notFound = true;
		if(isElementDisplayed("//div[@class='hd' and contains(@id,'dialog') and text()='" + windowtitle + "']"))
		{
			notFound = false;
		}
		return notFound;
	}

	public List<String> getElementsTextList(String xpath)
	{
		List<WebElement> elements = getElements(xpath);
		List<String> textList = new ArrayList<>();
		for (WebElement element : elements)
		{
			textList.add(element.getText());
		}
		return textList;
	}

	public boolean getAcceptAlert(String alertTextMsg)
	{
		String alertText = getAlertText();
		boolean passCondition = alertText.contains(alertTextMsg);
		acceptAlert();
		DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
		return passCondition;
	}

	// @Override
	public String getAlertText()
	{
		waitForAlert();
		return driver.switchTo().alert().getText();
	}

	public void refreshPage()
	{
		driver().navigate().refresh();
		waitForGridRefresh();
		waitForPageLoaded();
	}

	/*
	 * miscellaneous
	 */

	public boolean checkCheckbox(WebElement checkbox)
	{
		if(!checkbox.isSelected())
		{
			checkbox.click();
		}
		return checkbox.isSelected();
	}

	public boolean uncheckCheckbox(WebElement checkbox)
	{
		if(checkbox.isSelected())
		{
			checkbox.click();
		}

		return !checkbox.isSelected();
	}

	public boolean pageSourceContains(String string)
	{
		return driver.getPageSource().contains(string);
	}

	public boolean checkAlert()
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return true;
		}
		catch (Exception e)
		{
			logger.information("There was no alert!");
			return false;
		}
	}

	public void clickSubmitButtonOnForm()
	{
		click(CommonPageElements.SUBMIT_BUTTON);
		waitForElementGone(CommonPageElements.SUBMIT_BUTTON);
	}

	public void clickSaveButtonOnForm()
	{
		click(CommonPageElements.SAVE_BUTTON);
		waitForElementGone(CommonPageElements.SAVE_BUTTON);
	}

	public void clickCancelButtonOnForm()
	{
		click(CommonPageElements.CANCEL_BUTTON);
		waitForElementGone(CommonPageElements.CANCEL_BUTTON);
		checkAlert();
	}

	public void rightClickByIndex(final String str, final int index)
	{
		rightClick(driver.findElements(By.xpath(SetLawTrackingPageElements.LAW_TRACKING_ITEM)).get(index));
	}

	public String getElementsInnerHTML(String elementsXpath)
	{
		return getElement(elementsXpath).getAttribute("innerHTML");
	}

	public boolean checkFieldValueIsExpectedOne(WebElement element, String expectedValue)
	{
		boolean checkValueIsAsExpected;
		try
		{
			checkValueIsAsExpected = element.getAttribute("value").equals(expectedValue);
		}
		catch (Exception e)
		{
			checkValueIsAsExpected = element.getText().contains(expectedValue);
		}
		return checkValueIsAsExpected;
	}

	public void clickOK()
	{
		sendEnterToElement(CommonPageElements.OK_BUTTON);
		waitForGridRefresh();
	}

	public boolean switchToWindowByUrl(String url)
	{
		for (String windowHandle : driver.getWindowHandles())
		{
			if (driver.switchTo().window(windowHandle).getCurrentUrl().contains(url))
			{
				return true;
			}
		}
		return false;
	}

	public boolean waitForWindowByUrl(String title)
	{
		return waitForWindowByUrl(title, true, DateAndTimeUtils.TEN_SECONDS);
	}

	public boolean waitForWindowByUrl(String url, boolean failOnNotAvailable, int timeToWait)
	{
		long timeStart = System.currentTimeMillis();
		while (System.currentTimeMillis() - timeStart < timeToWait)
		{
			for (String windowHandle : driver.getWindowHandles())
			{
				driver.switchTo().window(windowHandle);
				if(driver.getCurrentUrl().contains(url))
				{
					return true;
				}
			}
		}
		if (failOnNotAvailable)
		{
			Assert.fail("Window with url " + url + " was not found within " + timeToWait + " ms");
		}
		return false;
	}

	public boolean switchToOpenedWindowByTitle(String title)
	{
		for (String windowHandle : driver.getWindowHandles())
		{
			driver.switchTo().window(windowHandle);
			if(driver.getTitle().contains(title))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * This function is used to switch to a window with no title
	 * It takes in the number of expected windows present so that it can iterate through a list of window handles, looking for the first window with no title
	 * Bdunk: On 3/2/22 I realized that we could derive this from our switch to last method, and that this functionality was what we needed (switching to a window with no title)
	 * If we want to change this back to switch to last window, we can simply edit the logic inside the the for loop on line 1565
	 * @param expectedWindowCount
	 * @return
	 */
	public boolean switchToWindowWithNoTitle(int expectedWindowCount)
	{
		Set<String> windowHandles = driver.getWindowHandles();
		int actualWindowCount = windowHandles.size();
		int iteration = 0;
		int maxCounter = 10;

		//Grabbing all available windows
		while (actualWindowCount < expectedWindowCount && iteration < maxCounter)
		{
			windowHandles = driver.getWindowHandles();
			actualWindowCount = windowHandles.size();
			iteration++;
			DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
		}
		if (actualWindowCount == expectedWindowCount)
		{
			for(String handle : windowHandles)
			{
				driver.switchTo().window(handle);
				String title = driver.getTitle();
				//Logic to make sure we switch to the window without a title.
				// There is a weird behavior where sometimes the windows don't load in the same order and the target window is not the last window
				if(title.length() < 1)
				{
					waitForPageLoaded();
					return true;
				}
			}
		}
		return false;
	}

	public List<String> getSelectOptionsText(WebElement selectElement)
	{
		return new Select(selectElement).getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
	}

	public void scrollToElement(WebElement element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void scrollToElement(String xpath) {
		try {
			scrollToElement(getElement(xpath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getScreenMiddleHeight()
	{
		return (int) getScreenDimension().getHeight()/2;
	}

	public int getScreenMiddleWidth()
	{
		return (int) getScreenDimension().getWidth()/2;
	}

	private Dimension getScreenDimension()
	{
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public String getBackgroundColor(WebElement element)
	{
		return element.getCssValue("background-color");
	}

	public String getBackgroundColor(String xpath)
	{
		return getBackgroundColor(getElement(xpath));
	}

	public String getBackgroundColorHex(String xpath)
	{
		String color = getBackgroundColor(xpath);
		return Color.fromString(color).asHex();
	}

	public String getElementsColorHex(String xpath)
	{
		String color = driver.findElement(By.xpath(xpath)).getCssValue("color");
		return Color.fromString(color).asHex();
	}

	public void clickOnInvisibleElement(WebElement element)
	{
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().build().perform();
	}

	public void clickOnInvisibleElement(String xpath)
	{
		clickOnInvisibleElement(getElement(xpath));
	}

	public int getElementsWidth(String xpath)
	{
		WebElement element = driver.findElement(By.xpath(xpath));
		return element.getSize().getWidth();
	}

	public int getElementsHeight(String xpath)
	{
		WebElement element = driver.findElement(By.xpath(xpath));
		return element.getSize().getHeight();
	}

	public void dragAndDropElementToElement(WebElement elementToClickAndDrag, WebElement elementWhereToDrag)
	{
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(elementToClickAndDrag).moveToElement(elementWhereToDrag).release().build();
		dragAndDrop.perform();
	}

	public void dragAndDropElementToOffset(WebElement elementToClickAndDrag, int offsetX, int offsetY)
	{
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(elementToClickAndDrag).moveByOffset(offsetX, offsetY).release().build();
		dragAndDrop.perform();
	}

	public void cleanLocalStorage()
	{
		String scriptToExecute = "localStorage.clear();";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(scriptToExecute);
	}

	public List<String> getTextOfElementsToList(String xpath)
	{
		return getElements(xpath).stream().map(WebElement::getText).collect(Collectors.toList());
	}

	public String copyAndGetTextFromClipBoard()
	{
		String result = "";
		try
		{
			AutoITUtils.sendHotKey(Keys.SHIFT, Keys.HOME);
			AutoITUtils.sendHotKey("c", Keys.CONTROL);
			Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
			DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
			result = c.getData(DataFlavor.stringFlavor).toString();
		}
		catch (IOException e)
		{
			Assertions.fail("IOException " + e);
		}
		catch (UnsupportedFlavorException e)
		{
			Assertions.fail("UnsupportedFlavorException " + e);
		}
		return result;
	}

	public String getFontStyle(String xpath)
	{
		return driver.findElement(By.xpath(xpath)).getCssValue("font-style");
	}

	public String getTextAlignParameter(String xpath)
	{
		return driver.findElement(By.xpath(xpath)).getCssValue("text-align");
	}

	public Point getElementLocation(WebElement webElement)
	{
		return webElement.getLocation();
	}

	public void sendKeysWithException(WebElement element, String keys)
	{
		try
		{
			element.sendKeys(keys);
		}
		catch (ElementNotInteractableException e)
		{

		}
	}

	public void clickBackButton()
	{
		driver().navigate().back();
		waitForPageLoaded();
	}

	public void scrollToTop()
	{
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}
}
