package com.thomsonreuters.codes.codesbench.quality.pages.nod.subscribedcases;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.PropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.ViewWizardPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.viewmanagament.SourceNavigateReportsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.viewmanagament.ViewManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import java.util.*;

@Component
public class ViewWizardPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public ViewWizardPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ViewWizardPageElements.class);
	}
	
	public boolean switchToViewWizardWindow()
	{
		return switchToWindow(ViewWizardPageElements.VIEW_WIZARD_PAGE_TITLE);
	}

	private void addFilter(String optionToFilter, String valueToFilter)
	{
		clickAddFilterButton();
		click(ViewWizardPageElements.filterSelect);
		click(optionToFilter);
		click(ViewWizardPageElements.optionSelection);
		click(valueToFilter);
		click(String.format(ViewWizardPageElements.SAVE_BUTTON, valueToFilter));
	}

	/**
	 * This Method is the same as the method above, except, it handles selecting multiple filter values at the same time
	 * For example: if we want to add a filter and have 3 or 4 years included, this method will be accessed instead of addFilter
	 * @param optionToFilter what filter option (content set, year, lock status, etc...) we want to add
	 * @param values values that we want this specific filter to display
	 */
	private void addFilterMultipleValues(String optionToFilter, ArrayList values)
	{
		clickAddFilterButton();
		click(ViewWizardPageElements.filterSelect);
		click(optionToFilter);
		click(ViewWizardPageElements.optionSelection);
		controlClickElements(values);
		click(ViewWizardPageElements.SAVE_BUTTON);
	}

	
	private void addSort(String optionToSort, String valueToSort)
	{
		click(ViewWizardPageElements.addSortButton);
		click(ViewWizardPageElements.filterSelect);
		click(optionToSort);
		selectDropdownOption(ViewWizardPageElements.sortSelect, valueToSort);	
	}
	
	private void clickAddFilterButton()
	{
		click(ViewWizardPageElements.addFilterButton);
	}
	
	public void clickNext()
	{
		click(ViewWizardPageElements.nextButton);
	}
	
	public void clickAddColumnsButton()
	{
		click(ViewWizardPageElements.addColumnsToSelectedColumnsTableButton);
	}

	public void clickRemoveColumnsButton()
	{
		click(ViewWizardPageElements.removeColumnsToSelectedColumnsTableButton);
	}
	
	public void addFilterToView(String columnToFilter, String... optionsToFilter)
	{
		if(optionsToFilter.length > 1)
		{
			//Creating an array list of web elements to multi select if there is more than one option passed in for filter values
			WebElement optionToAdd;
			ArrayList<WebElement> optionsToFilterList = new ArrayList<>();

			for (String option : optionsToFilter)
			{
				optionToAdd = getElement(String.format(ViewWizardPageElements.VALUE_OPTION, option));
				optionsToFilterList.add(optionToAdd);
			}
			addFilterMultipleValues(String.format(ViewWizardPageElements.OPTION_COLUMN, columnToFilter), optionsToFilterList);
		}
		else if(optionsToFilter.length == 1)
		{
			//Selecting only one option to add to the filter values
			addFilter(String.format(ViewWizardPageElements.OPTION_COLUMN, columnToFilter), String.format(ViewWizardPageElements.VALUE_OPTION, optionsToFilter[0]));
		}
		else
		{
			Assertions.fail("optionsToFilter parameter was not passed into the addFilterToView method.  Please add an optionToFilter.");
		}
	}

	public void setYearFilter(String valueToFilter)
	{
		click(ViewWizardPageElements.backButton);
		waitForPageLoaded();
		click(ViewWizardPageElements.optionSelection);
		click(String.format(ViewWizardPageElements.VALUE_OPTION,valueToFilter));
		click(String.format(ViewWizardPageElements.SAVE_BUTTON, valueToFilter));
		clickNext();
	}
	
	public void addSortToView(String columnToSort, String sorting)
	{
		addSort(String.format(ViewWizardPageElements.OPTION_COLUMN, columnToSort), sorting);
	}

	//TODO: See if we can rework this method to be a little more elegant in the way it selects the specified views
	public void addColumnsToView(String... columnsToAdd)
	{
		click(String.format(ViewWizardPageElements.VIEW_WIZARD_NON_SELECTED_COLUMNS_LIST, columnsToAdd[0]));
		sendKeys(Keys.ARROW_UP);
		sendKeys(Keys.ESCAPE);
		clickAddColumnsButton();
		click(ViewWizardPageElements.VIEW_WIZARD_SELECTED_COLUMNS_LIST_FIRST_ELEMENT);
		clickRemoveColumnsButton();
		for(String column : columnsToAdd)
		{
			click(String.format(ViewWizardPageElements.VIEW_WIZARD_NON_SELECTED_COLUMNS_LIST, column));
			clickAddColumnsButton();
		}
	}


	public void setViewname(String viewName)
	{
		sendTextToTextbox(ViewWizardPageElements.viewNameInput, viewName);
	}
	
	public void setViewVisibilityToPublic()
	{
		click(ViewWizardPageElements.viewVisibilityPublic);
	}
	
	public boolean saveView()
	{
		click(ViewWizardPageElements.saveViewButton);
		waitForPageLoaded();
		boolean didViewManagementWindowAppear = switchToWindow(ViewManagementPageElements.VIEW_MANAGEMENT_WINDOW_TITLE);
		enterTheInnerFrame();
		return didViewManagementWindowAppear;
	}
	
	private void openSetDateWindowForNewDateFilter(String optionToFilter)  
	{
		click(ViewWizardPageElements.addFilterButton);
		click(ViewWizardPageElements.filterSelect);
		click(optionToFilter);
		click(ViewWizardPageElements.optionSelection);
		click(ViewWizardPageElements.openCalendarInFilter);
		switchToInnerIFrameByIndex(1);
	}
	
	public void addDateFilterWithGivenValue(String optionToFilter, String dateToFilter)
	{
		openSetDateWindowForNewDateFilter(String.format(ViewWizardPageElements.OPTION_COLUMN, optionToFilter));

		sendKeysToElement(getElement(SourceNavigateReportsPageElements.DATE_TEXTBOX),dateToFilter);

		submitAndSaveDateFilterSetting();
	}

	public boolean verifyViewWizardPane(String expectedPane)
	{
		return waitForElementExists(String.format(ViewWizardPageElements.VIEW_WIZARD_PANE, expectedPane));
	}

	public void addDateFilterCurrentDateViaCalendar(String optionToFilter)
	{
		openSetDateWindowForNewDateFilter(optionToFilter);

		click(ViewManagementPageElements.openCalender);
		enterTheInnerFrame();
		click(PropertiesPageElements.PROPERTIES_CALENDAR_TODAY);
		switchToInnerIFrameByIndex(1);

		submitAndSaveDateFilterSetting();
	}
	
	private void submitAndSaveDateFilterSetting()  
	{
		click(CommonPageElements.SUBMIT_BUTTON);

		switchToWindow(ViewManagementPageElements.VIEW_MANAGEMENT_WINDOW_TITLE);
		enterTheInnerFrame();
		switchToInnerIFrameByIndex(1);
		click(ViewManagementPageElements.saveFilterBlueButton);
	}
}
