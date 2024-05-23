package com.thomsonreuters.codes.codesbench.quality.pages.tools.workflowreportingsystem.workflowsearch;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowsearch.WorkflowFiltersPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowsearch.WorkflowSearchPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils.FIVE_MINUTES;
import static com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils.TEN_SECONDS;

@Component
public class WorkflowSearchPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public WorkflowSearchPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, WorkflowSearchPageElements.class);
	}

	
	public void setWorkflowID(String workflowId)
	{
		clearAndSendTextToTextbox(WorkflowSearchPageElements.workflowIdField, workflowId);
	}

	
	public void clickFilterButton()
	{
		click(WorkflowSearchPageElements.filterButton);
		waitForPageLoaded();
	}

	// use for workflows that require filters other than id
	private boolean waitForFirstWorkflowAppearedAndFinished(int timeLimit, int timeStep)
	{
		/*
		This checks the status of the workflow.
		 */
		int timePassed = 0;

		while (timePassed < timeLimit)
		{
			// if Finished we return true, no further waiting needed
			waitForElement(WorkflowSearchPageElements.WORKFLOW_REPORTING_STATUS_COLUMN);
			if (getElementsText(WorkflowSearchPageElements.WORKFLOW_REPORTING_STATUS_COLUMN).equals("Finished"))
			{
				return true;
			}
			// if Error we return false, no further waiting needed
			// there is one case where we would expect a workflow to Error, and that's when
			// the rendition we synced had no classifications.  These insufficient classification errors
			// should only appear when we sync a rendition document that has no classifications.  If we need classifications,
			// we can ask Pam Greene or Andrea Griga to add classifications to the rendition.
			else if (getElementsText(WorkflowSearchPageElements.WORKFLOW_REPORTING_STATUS_COLUMN).equals("Error"))
			{
				logger.warning("The first workflow in grid has Error status");
				// check to see if the workflow errored out due to insufficient classifications
				// grab the workflow id
				String workflowId = getWorkflowIdOfFirstWorkflow();
				// open the workflow with that id
				openWorkflow(workflowId);
				// open the workflow properties
				workflowDetailsPage().expandWorkflowProperties();
				// grab the value of the name "_log"
				String logValue = workflowPropertiesPage().getWorkflowPropertyValueByName("_log");
				// go back to the previous page in case we need to switch between windows and perform additional verifications
				workflowDetailsPage().clickWorkflowSearchBreadcrumb();
				// use regex to check and see that we only have one <Error...>, and that the error message contains
				// 'One or more nodes contained insufficient classifications in workflow'
				// <Error actionId="111010" dateTime="2018-11-12 08:46:57 AM" serviceName="COPSRiskShelldocNovusConversion" serviceReturnCode="-1" serviceServer="ctcod079-08">One or more nodes contained insufficient classifications in workflow 2054774, which was from content set: FINRA</Error>
				// return true log a warning saying we are overriding the error
				// Pattern to see how many errors appear in the log -> '</Error>'
				Pattern patrickSwayze = Pattern.compile("(<Error.*?</Error>)");
				Matcher mattDamon = patrickSwayze.matcher(logValue);
				int count = 0;
				while(mattDamon.find())
				{
					count++;
				}
				// Check to see if the log value contains "One or more nodes contained insufficient classifications in workflow"
				// and the error count was 1.  If the error count is more than 1, there is possibly a more serious issue and we need to
				// fail the test
				if(count == 1 && logValue.contains("One or more nodes contained insufficient classifications in workflow"))
				{
					logger.warning("Overriding Error status due to 'insufficient classifications'");
					return true;
				}
				// else return false
				else
				{
					return false;
				}
			}
			// in other cases we wait
			else
			{
				DateAndTimeUtils.takeNap(timeStep);
				timePassed += timeStep;
				clickFilterButton();
			}
		}

		logger.warning("The first workflow in grid is not Finished or Error within the time limit set");
		return false;
	}

	// use for workflows that use id to filter
	private boolean waitForFirstWorkflowFinished(int timeLimit, int timeStep)
	{
		/*
		This checks the status of the workflow.
		 */
		int timePassed = 0;

		while (timePassed < timeLimit)
		{
			// if Finished we return true, no further waiting needed
			if (getElementsText(WorkflowSearchPageElements.WORKFLOW_REPORTING_STATUS_COLUMN).equals("Finished"))
			{
				return true;
			}
			// if Error we return false, no further waiting needed
			// there is one case where we would expect a workflow to Error, and that's when
			// the rendition we synced had no classifications.  These insufficient classification errors
			// should only appear when we sync a rendition document that has no classifications.  If we need classifications,
			// we can ask Pam Greene or Andrea Griga to add classifications to the rendition.
			else if (getElementsText(WorkflowSearchPageElements.WORKFLOW_REPORTING_STATUS_COLUMN).equals("Error"))
			{
				logger.warning("The first workflow in grid has Error status");
				// check to see if the workflow errored out due to insufficient classifications
				// grab the workflow id
				String workflowId = getWorkflowIdOfFirstWorkflow();
				// open the workflow with that id
				openWorkflow(workflowId);
				// open the workflow properties
				workflowDetailsPage().expandWorkflowProperties();
				// grab the value of the name "_log"
				String logValue = workflowPropertiesPage().getWorkflowPropertyValueByName("_log");
				// go back to the previous page in case we need to switch between windows and perform additional verifications
				workflowDetailsPage().clickWorkflowSearchBreadcrumb();
				// use regex to check and see that we only have one <Error...>, and that the error message contains
				// 'One or more nodes contained insufficient classifications in workflow'
				// <Error actionId="111010" dateTime="2018-11-12 08:46:57 AM" serviceName="COPSRiskShelldocNovusConversion" serviceReturnCode="-1" serviceServer="ctcod079-08">One or more nodes contained insufficient classifications in workflow 2054774, which was from content set: FINRA</Error>
				// return true log a warning saying we are overriding the error
				// Pattern to see how many errors appear in the log -> '</Error>'
				Pattern patrickSwayze = Pattern.compile("(<Error.*?</Error>)");
				Matcher mattDamon = patrickSwayze.matcher(logValue);
				int count = 0;
				while(mattDamon.find())
				{
					count++;
				}
				// Check to see if the log value contains "One or more nodes contained insufficient classifications in workflow"
				// and the error count was 1.  If the error count is more than 1, there is possibly a more serious issue and we need to
				// fail the test
				if(count == 1 && logValue.contains("One or more nodes contained insufficient classifications in workflow"))
				{
					logger.warning("Overriding Error status due to 'insufficient classifications'");
					return true;
				}
				// else return false
				else
				{
					return false;
				}
			}
			// in other cases we wait
			else
			{
				DateAndTimeUtils.takeNap(timeStep);
				timePassed += timeStep;
				clickFilterButton();
			}
		}

		logger.warning("The first workflow in grid is not Finished or Error within the time limit set");
		return false;
	}

	private boolean waitForFirstWorkflowWithCorrectTime()
	{
		//setting correct time range
		int timeGapAfter = 5;
		int timeGapBefore = 2;
		LocalDateTime currentDate = LocalDateTime.now(ZoneId.of("UTC"));
		LocalDateTime leftTimeEdge = currentDate.minus(timeGapBefore, ChronoUnit.MINUTES);
		LocalDateTime rightTimeEdge = currentDate.plus(timeGapAfter, ChronoUnit.MINUTES);
		logger.information(String.format("Left edge: %s", leftTimeEdge.toString()));
		logger.information(String.format("Right edge: %s", rightTimeEdge.toString()));

		//waiting for a workflow with expected start time
		int timeLimit = FIVE_MINUTES;
		int timeStep = TEN_SECONDS;
		int timePassed = 0;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

		while (timePassed < timeLimit)
		{
			LocalDateTime workflowStartTime = null;

			if(doesElementExist(WorkflowFiltersPageElements.WORKFLOW_START_TIME_XPATH))
			{
				workflowStartTime = LocalDateTime.parse(getElement(WorkflowFiltersPageElements.WORKFLOW_START_TIME_XPATH).getText(), formatter);
				logger.information(String.format("Starting time: %s", getWorkflowStartTime()));

				if (workflowStartTime.isBefore(rightTimeEdge) && workflowStartTime.isAfter(leftTimeEdge))
				{
					return true;
				}
			}
			//workflow could not appear for some time
			else
			{
				logger.information("There is no workflow with current filtering now");
			}

			//if no workflow found or it is not in correct time range we wait
			try
			{
				Thread.sleep(timeStep);
				timePassed += timeStep;
				clickFilterButton();
			}
			catch (InterruptedException e) {
				logger.warning("The sleep was interrupted");
			}
		}
		logger.warning("The first workflow in grid is not within correct time range");
		return false;
	}

	// use for workflows that require filters other than id
	private boolean waitForFirstWorkflowAppearedAndFinishedFiveMinutes()
	{
		return waitForFirstWorkflowAppearedAndFinished(FIVE_MINUTES, DateAndTimeUtils.TEN_SECONDS);
	}

	// use for workflows that require filters other than id
	public boolean waitForFirstWorkflowAppearedAndFinishedTenMinutes()
	{
		return waitForFirstWorkflowAppearedAndFinished(DateAndTimeUtils.TEN_MINUTES, DateAndTimeUtils.TEN_SECONDS);
	}

	// use for workflows that use id to filter
	public boolean checkFirstWorkflowFinishedFiveMinutes()
	{
		return waitForFirstWorkflowAppearedAndFinished(FIVE_MINUTES, DateAndTimeUtils.TEN_SECONDS);
	}

	// use for workflows that use id to filter
	public boolean checkFirstWorkflowFinishedTenMinutes()
	{
		if(isWorkflowGridEmpty())
		{
			logger.warning("The workflow grid is empty");
			return false;
		}
		return waitForFirstWorkflowAppearedAndFinished(DateAndTimeUtils.TEN_MINUTES, DateAndTimeUtils.TEN_SECONDS);
	}

	// use for workflows that require filters other than id
	public boolean checkFirstWorkflowTimeAndStatus()
	{
		/*if (!waitForFirstWorkflowWithCorrectTime())
		{
			return false;
		}*/

		logger.information("Workflow ID: " + getWorkflowIdOfFirstWorkflow());

		if (!waitForFirstWorkflowAppearedAndFinishedFiveMinutes())
		{
			return false;
		}

		return true;
	}

	// use for workflows that require filters other than id
	public boolean checkFirstWorkflowTimeAndStatusWaitTenMintues()
	{
		if (!waitForFirstWorkflowWithCorrectTime())
		{
			return false;
		}

		if (!waitForFirstWorkflowAppearedAndFinishedTenMinutes())
		{
			return false;
		}

		return true;
	}

	public String getWorkflowIdOfFirstWorkflow()
	{
		waitForElement(WorkflowSearchPageElements.firstWorkflowIdXpath);
		return getElementsText(WorkflowSearchPageElements.firstWorkflowIdXpath);
	}

	public String getWorkflowModifiedDate()
	{
		return WorkflowSearchPageElements.workflowModifiedDateXpath.getText();
	}

	public String getFirstWorkflowUserName()
	{
		return WorkflowSearchPageElements.workflowUserName.getText();
	}
	
	public void openWorkflow(String workflowId)  
	{
		click("//span[text()='" + workflowId + "']");
		waitForPageLoaded();
	}

	public void openFirstWorkflow()
	{
		click(WorkflowSearchPageElements.firstWorkflowIdXpath);
		waitForPageLoaded();
	}

	public String getWorkflowStartTime()
	{
		return getElement(WorkflowFiltersPageElements.WORKFLOW_START_TIME_XPATH).getText();
	}

	public boolean isClassificationNormDisplayed()
	{
		return isElementDisplayed(WorkflowSearchPageElements.CLASSIFICATION_NORM_ACTION_XPATH);
	}

	public boolean verifyMultipleWorkflowStatuses(ArrayList<String> workflowIds)
	{
		for (int index = 0; index < workflowIds.size(); index++)
		{
			setWorkflowID(workflowIds.get(index));
			clickFilterButton();

			if(!workflowSearchPage().checkFirstWorkflowTimeAndStatus())
			{
				System.out.println("The workflow with the ID: " + workflowIds.get(index) + " did not finish");
				return false;
			}
		}
		return true;
	}

	public boolean filterWorkflowAndVerifyStatus(String workflowType, String workflowContentSet, String workflowDescription, String... username)
	{
		String user = username.length > 0 ? username[0] : "";
		setWorkflowType(workflowType);
		setContentSet(workflowContentSet);
		setDescription(workflowDescription);
		setUser(user);
		clickFilterButton();

		return checkFirstWorkflowTimeAndStatus();
	}

	public boolean filterWorkflowAndCheckGridStatus(String type, String contentSet, String description, String username)
	{
		setWorkflowType(type);
		setContentSet(contentSet);
		setDescription(description);
		setUser(username);
		clickFilterButton();

		return isWorkflowGridEmpty();
	}

	public void setWorkflowType(String workflowType)
	{
		//selectDropdownOption(WorkflowSearchPageElements.workflowTypeFilter, workflowType);
		String javascriptString = "document.getElementById('pageForm:workflowGrid:id25').value = '" + workflowType + "'";
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript(javascriptString);
	}

	private void setContentSet(String contentSet)
	{
		//selectDropdownOption(WorkflowSearchPageElements.contentSetFilter,contentSet);
		String javascriptString = "document.getElementById('pageForm:workflowGrid:id32').value = '" + contentSet + "'";
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript(javascriptString);
	}

	private void setDescription(String description)
	{
		//clearAndSendKeysToElement(WorkflowSearchPageElements.descriptionFilter, description);
		String javascriptString = "document.getElementById('pageForm:workflowGrid:id39').value = '" + description + "'";
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript(javascriptString);
	}

	private boolean isWorkflowGridEmpty()
	{
		return getElementsText(WorkflowSearchPageElements.numberOfRowsDisplayed).equals("0");
	}

	private void setUser(String username)
	{
		//selectDropdownOption(WorkflowSearchPageElements.userFilter,username);
		//NOTE: In Edge, there's a stupid number of spaces after the username option.  The below number exactly for the BOT accounts.  I have seen this change slightly depending on if it's a contractor or not.
		String javascriptString = "document.getElementById('pageForm:workflowGrid:id61').value = '" + username + "                                                                                                                 '";
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript(javascriptString);
	}

	public void closeWorkflowSearchPage()
	{
		closeCurrentWindowIgnoreDialogue();
	}

	@Deprecated //No use
	public long getStartTimeMs()
	{
		return DateAndTimeUtils.getDateInMs(WorkflowSearchPageElements.startTimeText1Xpath.getText());
	}

	public String getStartTime()
	{
		return WorkflowSearchPageElements.startTimeText1Xpath.getText();
	}
}
