package com.thomsonreuters.codes.codesbench.quality.pages.home;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.home.HomePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.home.HomePageElements.HOME_PAGE_TITLE;

@Component
public class HomePage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public HomePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, HomePageElements.class);
	}
	
	public void goToHomePage()
	{
		openPageWithUrl(String.format(urls().getHomePageUrl(), environmentTag),
						HOME_PAGE_TITLE);
	}
	public void openGroupingForRenditionWithUuid(String groupingLevel, String uuid, String... environment)
	{
		if(groupingLevel.equals("Section"))
		{
			openPageWithUrl("http://uat.magellan3.int.westgroup.com:9144/sourceNavigateWeb/appPages/sourceNav/sectionGrouping.xhtml?selectedRowIds=" + uuid, "Grouping");
		}
		else if(groupingLevel.equals("Delta"))
		{
			openPageWithUrl("http://uat.magellan3.int.westgroup.com:9144/sourceNavigateWeb/appPages/sourceNav/deltaGrouping.xhtml?selectedRowIds=" + uuid, "Grouping");
		}
	}

	//6-30-2020: placement of this method?
	public void goToContentSetPublishingConfigurationPage()
	{
		openPageWithUrl("http://uat.magellan3.int.westgroup.com:9177/targetBatchUiWeb/appPages/PublishingUI/contentSetConfiguration.jsf",
				"Content Set Publishing Configuration");
	}
	
	/**
	 * Sets the content to the given one.
	 *
	 * @param contentSet
	 */
	public void setMyContentSet(String contentSet)
	{
		Select select = new Select(HomePageElements.contentSelect);
		
		if(!select.getFirstSelectedOption().getText().equals(contentSet))
		{
			select.selectByVisibleText(contentSet);
			if(TestSetupEdge.getBrowserTag().equals(CustomAnnotations.BrowserAnnotations.EDGE))
			{
				checkAlertTextMatchesGivenText(String.format("Are you sure you want to change your current content set to %s?", contentSet));
			}
			else
			{
				AutoITUtils.verifyAlertTextAndAccept(true, String.format("Are you sure you want to change your current content set to %s?", contentSet));
			}
			waitForPageLoaded();
		}
	}

	public boolean switchToPage()
	{
		return switchToWindow(HomePageElements.HOME_PAGE_TITLE);
	}
	
	public String getCurrentContentSetFromHomepageDropdown()
	{
		return getElementsText(HomePageElements.SELECTED_CONTENT_SET_OPTION_DROPDOWN_XPATH);
	}
	
	public String getCurrentContentSetFromUpperRight()
	{
		return Stream.of((HomePageElements.currentContentSet.getText()).split(" \\| "))
				.map (String::new)
				.collect(Collectors.toList()).get(1);
	}
	
	public void closeDialogReturnToHomePage()
	{
		switchToWindow("Home Page");
		sendEnterToElement("//div[@id='dialog']//a[@class='container-close']");
		switchToWindow("Home Page");
	}
	
    public boolean isTopRightContentSetEqualToDropDownContentSet()
	{
    	String dropdownContentSet = getCurrentContentSetFromHomepageDropdown();
        String topRightContentSet = getCurrentContentSetFromUpperRight();
        return topRightContentSet.contains(dropdownContentSet);
	}

	public boolean isBookmarkDisplayed(String bookmarkName)
	{
		return doesElementExist(String.format(HomePageElements.TARGET_NOTE_BOOKMARKS,bookmarkName));
	}

	public boolean bookmarkIsInMyBookmarks(String bookmarkName)
	{
		return doesElementExist(String.format(HomePageElements.MY_BOOKMARKS,bookmarkName));
	}

	public void deleteBookmark(String bookmarkName)
	{
		click(String.format(HomePageElements.BOOKMARK_DELETE,bookmarkName));
	}

	public void clickMyBookmarks(String bookmarkName)
	{
		click(String.format(HomePageElements.TARGET_NOTE_BOOKMARKS, bookmarkName));
		waitForGridRefresh();
	}
}
