package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.pubnavigate;

import java.util.List;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyTreePageElements;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.BulkPublishPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPubNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.popname.PopNameDatabaseConstants;

@Component
public class HierarchyPubNavigatePage extends BasePage
{
	WebDriver driver;

	@Autowired
	public HierarchyPubNavigatePage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, HierarchyPubNavigatePageElements.class);
	}
	
	public int getPopNamesCount(String testLetter)
	{
		String popNameTitle = getElementsText((String.format(HierarchyPubNavigatePageElements.POPNAME_WITH_STARTING_LETTER_XPATH,testLetter)));
		String popNameCount = popNameTitle.substring(popNameTitle.indexOf("(") + 1, popNameTitle.lastIndexOf(")"));

		return Integer.parseInt(popNameCount);
	}
	
	public void sendEnterToPopname(String letter)
	{
		getElement(String.format(HierarchyPubNavigatePageElements.POPNAME_WITH_STARTING_LETTER_EXPANDER_XPATH,letter)).sendKeys(Keys.ENTER);
	}
	
	public void rightClickPopNameItemByIndex(int index, String popNameLetter)
	{
		// Selecting all cells of the HTML table
		List<WebElement> elementNumList = getElements(String.format(HierarchyPubNavigatePageElements.POPNAME_WITH_STARTING_LETTER_XPATH, popNameLetter));
		// Taking an indexed cell
		WebElement elem = elementNumList.get(index);
		scrollTo(elem);
		// Right Clicking
		click(elem);
		waitForPageLoaded();
		rightClick(elem);
	}	
	
	public boolean switchToPubNavigatePage()
	{
		return switchToWindow(HierarchyPubNavigatePageElements.HIERARCHY_MENU_PUB_NAVIGATE_PAGE_TITLE);
	}
	
	public boolean republishSelectedNode()
	{
		openContextMenu(HierarchyPubNavigatePageElements.publishingWorkflowsXpath, HierarchyPubNavigatePageElements.republishByHierarcyXpath);
		boolean windowAppeared = switchToWindow(BulkPublishPageElements.BULK_PUBLISH_PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
	}

	public void waitForHierarchyPubPageToLoad(String nodeValue)
	{
		waitForPageLoaded();
		waitForGridRefresh();
		waitForElementToBeClickable(String.format(HierarchyTreePageElements.SELECTED_TREE_NODE_WITH_VALUE_GIVEN,nodeValue));
	}
}
