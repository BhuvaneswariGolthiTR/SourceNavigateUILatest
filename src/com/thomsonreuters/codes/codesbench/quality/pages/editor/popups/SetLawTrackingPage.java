package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.PreviousWipVersionsPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SetLawTrackingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

@Component
public class SetLawTrackingPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public SetLawTrackingPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SetLawTrackingPageElements.class);
	}

	public void setFilterYear(String setValue)
	{
		clearAndSendKeysToElement(SetLawTrackingPageElements.setLawTrackingYearInput, setValue);
		sendEnterToElement(SetLawTrackingPageElements.setLawTrackingYearInput);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void setFilterSession(String setValue)
	{
		clearAndSendKeysToElement(SetLawTrackingPageElements.setLawTrackingSessionInput, setValue);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void setFilterDocNumber(String setValue)
	{
		clearAndSendKeysToElement(SetLawTrackingPageElements.setLawTrackingDocNumberInput, setValue);
		sendEnterToElement(SetLawTrackingPageElements.setLawTrackingDocNumberInput);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void setFilterClassNumber(String setValue)
	{
		clearAndSendKeysToElement(SetLawTrackingPageElements.setLawTrackingClassNumberInput, setValue);
		sendEnterToElement(SetLawTrackingPageElements.setLawTrackingClassNumberInput);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void rightClickByRenditionLoadDate(String setValue)
	{
		click(String.format(SetLawTrackingPageElements.SOURCE_LOAD_DATE_OPTION_XPATH, setValue));
		rightClick(String.format(SetLawTrackingPageElements.SOURCE_LOAD_DATE_OPTION_XPATH, setValue));
	}

	public void clickQuickLoadTrackingButton()
	{
		sendEnterToElement(SetLawTrackingPageElements.quickLoadTrackingButton);
		waitForGridRefresh();
	}

 	public void clickOkButton()
	{
		sendEnterToElement(CommonPageElements.OK_BUTTON);
		waitForWindowGoneByTitle(SetLawTrackingPageElements.PAGE_TITLE);
		breakOutOfFrame();
	}

	public void switchToSetLawTrackingPage()
	{
		switchToWindow(SetLawTrackingPageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void switchToPreviousWipVersionsPage()
	{
		switchToWindow(PreviousWipVersionsPageElements.PREVIOUS_WIP_VERSIONS_PAGE_TITLE);
	}

	/*
	 * Grab all years in columns and convert it to an integer list
	 */
	public List<Integer> getYearFromAllItems()
	{
		List<String> yearList = getElementsTextList(SetLawTrackingPageElements.LAW_TRACKING_ITEM_YEAR_XPATH);
		return yearList.stream().map(Integer::parseInt).collect(Collectors.toList());
	}

	/*
	 * Compare given list of years to the current year and the previous year
	 */
	public boolean compareYearListToCurrentAndPreviousYear(List<Integer> yearList)
	{
		int currentYear = Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy());
		return (yearList.stream().allMatch(x -> x==currentYear || x==(currentYear-1)));
	}

}