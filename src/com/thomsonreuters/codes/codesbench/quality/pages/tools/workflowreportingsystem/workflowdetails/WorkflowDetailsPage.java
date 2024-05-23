package com.thomsonreuters.codes.codesbench.quality.pages.tools.workflowreportingsystem.workflowdetails;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Date;

@Component
public class WorkflowDetailsPage extends BasePage {
	private WebDriver driver;

	@Autowired
	public WorkflowDetailsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init() {
		PageFactory.initElements(driver, WorkflowDetailsPageElements.class);
	}

	private String getStatusFieldValue() {
		waitForPageLoaded();
		return getElementsText(WorkflowDetailsPageElements.currentStepStatusTextXpath);
	}

	public boolean verifyWorkflowFinished() {
		return verifyWorkflowFinishedInSeconds(WorkflowDetailsPageElements.MAX_VERIFY_WORKFLOW_FINISHED_INTERVAL_IN_SECONDS);
	}

	private boolean verifyWorkflowFinishedInSeconds(int seconds) {
		if (seconds <= 0 || seconds > WorkflowDetailsPageElements.MAX_VERIFY_WORKFLOW_FINISHED_INTERVAL_IN_SECONDS) {
			throw new IllegalArgumentException(
					String.format("Seconds interval must be greater than 0 and less or equal to %d",
							WorkflowDetailsPageElements.MAX_VERIFY_WORKFLOW_FINISHED_INTERVAL_IN_SECONDS));
		}
		long start = new Date().getTime();
		long millis = (long) seconds * 1000;
		logger.information(String.format("Workflow ID: %s", getWorkflowID()));
		while (new Date().getTime() - start <= millis) {
			driver.navigate().refresh();
			String workflowStatus = getStatusFieldValue();
			if (workflowStatus.equals("Finished")) {
				return true;
			}
			if (workflowStatus.equals("Error")) {
				return false;
			}
			DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
		}
		return false;
	}

	public void closeAndSwitchToYourWorkflowHasBeenCreatedPage() {
		closeCurrentWindowIgnoreDialogue();
		switchToOpenedWindowByTitle(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void clickSignIn() {
		click(WorkflowDetailsPageElements.signInXpath);
		waitForPageLoaded();
	}

	public void setWorkflowId(String workflowId) {
		clearAndSendKeysToElement(WorkflowDetailsPageElements.workflowIdTextBox, workflowId);
	}

	public String getWorkflowID() {
		return getElementsAttribute(WorkflowDetailsPageElements.WORKFLOW_ID_INPUT, "value");
	}

	public void clickGo()
	{
		click(WorkflowDetailsPageElements.goButton);
		waitForPageLoaded();
	}

	public void clickPublishingLock()
	{
		sendEnterToElement(WorkflowDetailsPageElements.publishingLockCompleted);
		waitForPageLoaded();
	}

	public void switchToDetailsPage()
	{
		switchToWindow(WorkflowDetailsPageElements.PAGE_TITLE);
	}
	public boolean isCheckSkipMmrActionDisplayed()
	{
		return isElementDisplayed(WorkflowDetailsPageElements.CHECK_SKIP_MMR_ACTION_XPATH);
	}

	public void expandWorkflowProperties()
	{
		click(WorkflowDetailsPageElements.workflowProperiesButtonXpath);
		waitForPageLoaded();
	}

	public void expandWorkflowHistoryStepsDetail()
	{
		click(WorkflowDetailsPageElements.workflowHistoryStepsDetailXpath);
	}

	public void clickWorkflowSearchBreadcrumb()
	{
		sendEnterToElement("//span[@id='headerForm:breadcrumbArea']/a[text()='Workflow Search']");
		switchToWindow("Workflow Search");
		waitForPageLoaded();
		//waitForElement(WorkflowSearchPageElements.firstWorkflowIdXpath);
	}

	public void waitTillWorkflowIsFinished()
	{
		Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(60))
				.pollingEvery(Duration.ofSeconds(5))
				.ignoring(NoSuchElementException.class);

		wait.until(webDriver -> {
			webDriver.navigate().refresh();
			return doesElementExist("//span[@id='pageForm:wfi16' and text()='Finished']");
		});
	}

	public void closeWorkflowDetailPageAndSwitchToSourceNavigate()
	{
		closeCurrentWindowIgnoreDialogue();
		switchToOpenedWindowByTitle(SourceNavigateAngularPageElements.PAGE_TITLE);
	}
}
