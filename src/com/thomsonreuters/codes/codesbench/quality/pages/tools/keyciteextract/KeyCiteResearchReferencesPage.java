package com.thomsonreuters.codes.codesbench.quality.pages.tools.keyciteextract;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.keyciteextract.KeyCiteResearchReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.lists.ListUtils;

@Component
public class KeyCiteResearchReferencesPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public KeyCiteResearchReferencesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, KeyCiteResearchReferencesPageElements.class);
	}
	
	/**
	 * Selects give analytical titles.
	 *
	 * @param availableAnalyticalTitles the analytical titles
	 */
	public void selectAvailableAnalyticalTitles(List<String> availableAnalyticalTitles)
	{
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
			
			availableAnalyticalTitles.forEach(title -> selectDropdownOption(KeyCiteResearchReferencesPageElements.availableAnalyticalTitlesSelect, title));

			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
	}
	
	/**
	 * Clicks the add one analytical title button.
	 */
	public void clickAddOneAnalyticalTitleButton()
	{
		click(KeyCiteResearchReferencesPageElements.addOneAnalyticalTitleButton);
		waitForPageLoaded();
	}
	
	/**
	 * Verifies the selected analytical titles match.
	 *
	 * @param selectedAvailableAnalyticalTitles the selected analytical titles
	 * @return boolean of whether they match
	 */
	public boolean verifySelectedAnalyticalTitlesMatch(List<String> selectedAvailableAnalyticalTitles)
	{
		List<String> selectedTitles = getSelectOptionsText(KeyCiteResearchReferencesPageElements.selectedAnalyticalTitlesSelect);
		return ListUtils.areListsEqualIgnoreOrder(selectedAvailableAnalyticalTitles, selectedTitles);
	}
	
	/**
	 * Clicks the save default titles button.
	 */
	public void clickSaveDefaultTitlesButton()
	{
		click(KeyCiteResearchReferencesPageElements.saveDefaultTitlesButton);
		waitForPageLoaded();
	}
	
	/**
	 * Checks if the available analytical titles grid is disabled.
	 *
	 * @return boolean of whether it is disabled
	 */
	public boolean isAvailableAnalyticalTitlesSelectDisabled()
	{
		return !isElementEnabled(KeyCiteResearchReferencesPageElements.availableAnalyticalTitlesSelect);
	}
	
	/**
	 * Checks if the selected analytical titles grid is disabled.
	 *
	 * @return boolean of whether it is disabled
	 */
	public boolean isSelectedAnalyticalTitlesSelectDisabled()
	{
		return !isElementEnabled(KeyCiteResearchReferencesPageElements.selectedAnalyticalTitlesSelect);
	}
	
	/**
	 * Selects the available volumes.
	 *
	 * @param availableVolumes the available volumes.
	 */
	public void selectAvailableVolumes(List<String> availableVolumes)
	{
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
			
			availableVolumes.forEach(title -> selectDropdownOption(KeyCiteResearchReferencesPageElements.availableVolumesSelect, title));

			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
	}
	
	/**
	 * Clicks the add one volume button.
	 */
	public void clickAddOneVolumeButton()
	{
		click(KeyCiteResearchReferencesPageElements.addOneVolumeButton);
		waitForPageLoaded();
	}
	
	/**
	 * Verifies the selected volumes match.
	 *
	 * @param expectedSelectedVolumes the selected volumes
	 * @return boolean of whether they match
	 */
	public boolean verifySelectedVolumesMatch(List<String> expectedSelectedVolumes)
	{
		List<String> selectedVolumes = getSelectOptionsText(KeyCiteResearchReferencesPageElements.selectedVolumesSelect);
		return ListUtils.areListsEqualIgnoreOrder(expectedSelectedVolumes, selectedVolumes);
	}
	
	/**
	 * Clicks the submit button.
	 * Passing true will expect a workflow to kick off
	 * Passing false will expect an error to occur when attempting to kick off a workflow
	 */
	public void clickSubmitButton(boolean expectWorkflowKickedOff)
	{
		click(KeyCiteResearchReferencesPageElements.submitButton);
		waitForPageLoaded();

		if (expectWorkflowKickedOff)
		{
			waitForElement(KeyCiteResearchReferencesPageElements.WORKFLOW_STARTED_ELEMENT);
		}
		else
		{
			waitForElement(KeyCiteResearchReferencesPageElements.WORKFLOW_ERROR_MESSAGE);
		}
	}

	
	/**
	 * Checks if the workflow start element is displayed.
	 *
	 * @return boolean of whether it is displayed
	 */
	public boolean doesWorkflowKickOff()
	{
//		if(getElementsText(KeyCiteResearchReferencesPageElements.WORKFLOW_STARTED_ELEMENT).contains("The workflow has been queued. The workflow ID")) //WORKS
//		if(doesElementExist(KeyCiteResearchReferencesPageElements.WORKFLOW_STARTED_ELEMENT))
		if(isElementDisplayed(KeyCiteResearchReferencesPageElements.WORKFLOW_STARTED_ELEMENT))
		{
			return true;
		}
		else
		{
			System.out.println(getElementsText(KeyCiteResearchReferencesPageElements.WORKFLOW_STARTED_ELEMENT));
			return false;
		}
	}
	
	/**
	 * Gets the workflow ID.
	 *
	 * @return the workflow ID as a string
	 */
	public String getWorkflowId()
	{
		String workflowMessageText = getElementsText(KeyCiteResearchReferencesPageElements.workflowStartedElement);
		return workflowMessageText.substring(workflowMessageText.length() - 7);
	}
	
	/**
	 * Clicks the empty selected analytival titles button.
	 */
	public void clickEmptySelectedAnalyticalTitles()
	{
		click(KeyCiteResearchReferencesPageElements.removeAllAnalyticalTitlesButton);
		waitForPageLoaded();
	}
	
	/**
	 * Checks if the error appears for not selecting volumes.
	 *
	 * @return boolean of whether the error appears
	 */
	public boolean doesWorkflowErrorAppearsForNotSelectingVolume()
	{
		return isElementDisplayed(KeyCiteResearchReferencesPageElements.WORKFLOW_VOLUME_ERROR);
	}
	
	/**
	 * Checks if the error appears for not selecting titles.
	 *
	 * @return boolean of whether the error appears
	 */
	public boolean doesWorkflowErrorAppearForNotSelectingAnalyticalTitle()
	{
		return isElementDisplayed(KeyCiteResearchReferencesPageElements.WORKFLOW_ANALYTICAL_TITLE_ERROR);
	}
}
