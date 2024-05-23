package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.FindCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.FindTemplatesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchySearchPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

@Component
public class HierarchySearchPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public HierarchySearchPage(WebDriver driver)
	{
		super(driver);
		this.driver = TestService.driver();
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, HierarchySearchPageElements.class);
	}

	
	public void searchNodeUuid(String nodeUuid)
	{
		click(HierarchySearchPageElements.findNodeLabel);
		waitForElementExists(HierarchySearchPageElements.NODE_UUID_INPUT);
		//clear(HierarchySearchPageElements.NODE_UUID_INPUT);
		//JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		//javascriptExecutor.executeScript(String.format("arguments[0].value='%s'",nodeUuid), getElement(HierarchySearchPageElements.NODE_UUID_INPUT));
		setTextOfElement(nodeUuid, getElement(HierarchySearchPageElements.NODE_UUID_INPUT));
		sendEnterToElement(HierarchySearchPageElements.FIND_BY_NODE_UUID_BUTTON);
		if (isAlertPresent())
		{
			acceptAlert();
		}
		// TODO this is currently hee to handle incorrect start date in Hierarchy tree
		//  Will try regression 02/2021 without this to see where it is effected
		boolean windowFound = waitForWindowByTitle("Find Error", false, DateAndTimeUtils.FIVE_SECONDS);//TODO new page and elements for Find Error Window
		Assertions.assertFalse(windowFound,"Find Error window found. Node does not exist.");
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void searchNodeUuidHandlingIncorrectStartDate(String nodeUuid)
	{
		click(HierarchySearchPageElements.findNodeLabel);
		waitForElementExists(HierarchySearchPageElements.NODE_UUID_INPUT);
		clear(HierarchySearchPageElements.NODE_UUID_INPUT);
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript(String.format("arguments[0].value='%s'",nodeUuid), getElement(HierarchySearchPageElements.NODE_UUID_INPUT));
		click(HierarchySearchPageElements.FIND_BY_NODE_UUID_BUTTON);
		AutoITUtils.verifyAlertTextContainsAndAccept(true, "THIS IS NOT AN ERROR, just a notice that the selected node");
		boolean windowFound = waitForWindowByTitle("Find Error", false, DateAndTimeUtils.FIVE_SECONDS);
		Assertions.assertFalse(windowFound,"Find Error window found. Node does not exist.");
		waitForPageLoaded();
		waitForGridRefresh();
	}

	
	public void quickSearch(String keyword, String toSearch)
	{
		click(HierarchySearchPageElements.quickSearchLabel);
		selectDropdownOption(HierarchySearchPageElements.quickSearchKeywordList,keyword);
		waitForElementExists(HierarchySearchPageElements.quickSearchInput);
		clear(HierarchySearchPageElements.quickSearchInput);
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript(String.format("arguments[0].value='%s'",toSearch), HierarchySearchPageElements.quickSearchInput);
		click(HierarchySearchPageElements.quickSearchFindButton);
		boolean windowFound = waitForWindowByTitle("Find Error", false, DateAndTimeUtils.FIVE_SECONDS);
		Assertions.assertFalse(windowFound,"Find Error window found. Node does not exist.");
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void clickFindCiteTab()
	{
		click(HierarchySearchPageElements.findCiteLabel);
		switchToWindow(FindCitePageElements.FIND_CITE_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void clickQuickSearchBeginsWithCheckBox()
	{
		click(HierarchySearchPageElements.quickSearchBeginsWithCheckbox);
	}

	public void quickSearchClickEnter(String keyword,String value)
	{
		click(HierarchySearchPageElements.quickSearchLabel);
		selectDropdownOption(HierarchySearchPageElements.quickSearchKeywordList,keyword);
		clearAndSendKeysToElement(HierarchySearchPageElements.quickSearchInput,value);
		sendEnterToElement(HierarchySearchPageElements.quickSearchFindButton);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void clickPrevResultButton()
	{
		click(HierarchySearchPageElements.nodeFindPrevResult);
	}

	public void clickFindTemplatesTab()
	{
		click(HierarchySearchPageElements.findTemplatesLabel);
		switchToWindow(FindTemplatesPageElements.FIND_TEMPLATES_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void searchContentUuid(String contentUuid)
	{
		click(HierarchySearchPageElements.findContentLabel);
		waitForElementExists(HierarchySearchPageElements.contentFindInput);
		clear(HierarchySearchPageElements.contentFindInput);
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript(String.format("arguments[0].value='%s'",contentUuid), HierarchySearchPageElements.contentFindInput);
		click(HierarchySearchPageElements.contentFindResult);
		boolean windowFound = waitForWindowByTitle("Find Error", false, DateAndTimeUtils.FIVE_SECONDS);
		Assertions.assertFalse(windowFound,"Find Error window found. Node does not exist.");
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void searchHidUnderFindHidTab(String headingId)
	{
		HierarchySearchPageElements.findHidLabel.click();
		HierarchySearchPageElements.hidFindInput.sendKeys(headingId);
		HierarchySearchPageElements.hidFindResult.click();
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void setVolNumberUnderFindVolsTab(String volNumber)
	{
		HierarchySearchPageElements.findVolsLabel.click();
		HierarchySearchPageElements.volsFindInput.sendKeys(volNumber);
		sendEnterToElement(HierarchySearchPageElements.volsFindResult);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void setFirstDropdownUnderQuickSearchTab(String optionToSelect)
	{
		click(HierarchySearchPageElements.quickSearchLabel);
		selectDropdownOption(HierarchySearchPageElements.quickSearchCodeDropdown,optionToSelect);
	}
}
