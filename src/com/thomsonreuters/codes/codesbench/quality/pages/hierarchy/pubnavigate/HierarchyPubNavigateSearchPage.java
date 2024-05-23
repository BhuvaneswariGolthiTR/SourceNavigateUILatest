package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.pubnavigate;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchySearchPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

@Component
public class HierarchyPubNavigateSearchPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public HierarchyPubNavigateSearchPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, HierarchySearchPageElements.class);
	}
	
	public void searchNodeUuid(String nodeUuid)
	{
		click(HierarchySearchPageElements.findNodeLabel);
		clearAndSendKeysToElement(HierarchySearchPageElements.nodeUuidInput, nodeUuid);
		click(HierarchySearchPageElements.findByNodeUuidButton);
		boolean windowFound = waitForWindowByTitle("Find Error", false, DateAndTimeUtils.FIVE_SECONDS); //TODO new page and elements for Find Error Window
		Assertions.assertFalse(windowFound,"Find Error window found.  Node does not exist.");
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void searchContentUuid(String contentUuid)
	{
		click(HierarchySearchPageElements.findContentLabel);
		clearAndSendKeysToElement(HierarchySearchPageElements.contentFindInput, contentUuid);
		click(HierarchySearchPageElements.contentFindResult);
		boolean windowFound = waitForWindowByTitle("Find Error", false, DateAndTimeUtils.FIVE_SECONDS); //TODO new page and elements for Find Error Window
		Assertions.assertFalse(windowFound,"Find Error window found.  Node does not exist.");
		waitForPageLoaded();
		waitForGridRefresh();
	}
	
	public void quickSearch(String keyword, String toSearch)
	{
		click(HierarchySearchPageElements.quickSearchLabel);
		click(HierarchySearchPageElements.quickSearchKeywordList);
		click(String.format(HierarchySearchPageElements.QUICK_SEARCH_KEYWORD_OPTION, keyword));
		clearAndSendKeysToElement(HierarchySearchPageElements.quickSearchInput, toSearch);
		click(HierarchySearchPageElements.quickSearchFindButton);
		waitForPageLoaded();
		waitForGridRefresh();
	}
}
