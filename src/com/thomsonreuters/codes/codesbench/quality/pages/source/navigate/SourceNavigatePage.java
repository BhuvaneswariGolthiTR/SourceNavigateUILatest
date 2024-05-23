package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.PendingRenditionNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.switchEnums.DocumentLevels;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SourceNavigatePage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public SourceNavigatePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, SourceNavigatePageElements.class);
    }
	
	public void goToSourcePage()
	{
		openPageWithUrl(String.format(urls().getSourcePageUrl(), environmentTag),
			SourceNavigatePageElements.PAGE_TITLE);
		waitForPageLoaded();
	}

	public void goToSourcePageWithRenditionUuids(String... uuids)
	{
		openPageWithUrl(urls().getSourcePageUrlWithRenditionUuids(uuids), SourceNavigatePageElements.PAGE_TITLE);
		waitForPageLoaded();
	}
	
	public void switchToSourceNavigatePage()
	{
		switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
	}

	public void switchToSourceNavigateWindow()
	{
		switchToWindow(SourceNavigatePageElements.NAVIGATE_PAGE_TITLE);
	}

	public void searchDocumentEditDS(String uuid)
	{
		waitForElement(SourceNavigatePageElements.FIND_DOCUMENT_BUTTON);
		click(SourceNavigatePageElements.FIND_DOCUMENT_BUTTON);
		enterTheInnerFrame();
		sendKeysToElement(SourceNavigatePageElements.DOCUMENT_UUID_INPUT, uuid);
		click(SourceNavigatePageElements.FIND_BY_UUID_BUTTON);
		waitForPageLoaded();
		waitForElementGone(SourceNavigatePageElements.PLEASE_WAIT);
		click(SourceNavigatePageElements.FIRST_RENDITION);
		rightClick(SourceNavigatePageElements.SELECTED_RENDITION);
		openContextSubMenu(SourceNavigatePageElements.EDIT_CM_SUBMENU, SourceNavigatePageElements.EDIT_RENDITION_WITH_DS_CM_ITEM);
	}

	public void selectTab(DocumentLevels documentLevel)
	{
		switch (documentLevel)
		{
			case SECTION:
				goToSectionTab();
				break;
			case SECTION_GROUP:
				goToSectionGroupTab();
				break;
			case DELTA:
				goToDeltaTab();
				break;
//			case DELTA_GROUP:
//				navigateToDeltaGroupTab(); // TODO create this method
//				break;
			default:
				throw new RuntimeException("Illegal argument! Use enum from DocumentLevels class");
		}
	}
	
	public void goToTab(String tabToNavigateTo)
    {
    	waitForElement(tabToNavigateTo);
    	doubleClick(tabToNavigateTo);
    	waitForGridRefresh();
    	waitForPageLoaded();
		waitForGridRefresh();
    }

	public void goToDeltaTab() { goToTab(SourceNavigatePageElements.DELTA_TAB_XPATH); }

	public void goToDeltaGroupTab() { goToTab(SourceNavigatePageElements.DELTA_GROUP_TAB_XPATH); }

	public void goToSectionTab() { goToTab(SourceNavigatePageElements.SECTION_TAB_XPATH); }

	public void goToSectionGroupTab() { goToTab(SourceNavigatePageElements.SECTION_GROUP_TAB_XPATH); }

	public void goToLineageTab()
	{
		goToTab(SourceNavigatePageElements.LINEAGE_TAB_XPATH);
	}

	public void goToRenditionTab()
	{
		goToTab(SourceNavigatePageElements.RENDITION_TAB_XPATH);
	}

	public void goToLinearNavigate()
	{
		switchToWindow(SourceNavigatePageElements.LINEAGE_NAVIGATE_PAGE_TITLE);
	}
	
	public void goToDeltaNavigate() 
	{
		switchToWindow(SourceNavigatePageElements.DELTA_NAVIGATE_PAGE_TITLE);
	}

	public void publicViewAllCols()
	{
		click(SourceNavigatePageElements.PUBLIC_VIEW_XPATH);
		waitForElementExists(SourceNavigatePageElements.PUBLIC_VIEW_ALL_COLS);
		sendEnterToElement(SourceNavigatePageElements.PUBLIC_VIEW_ALL_COLS);
		waitForGridRefresh();
	}

	public void publicViewAllColsWithCdpAutoIntegration()
	{
		click(SourceNavigatePageElements.PUBLIC_VIEW_XPATH);
		waitForElementExists(SourceNavigatePageElements.PUBLIC_VIEW_ALL_COLS_WITH_CDP_AUTO_INTEGRATION);
		sendEnterToElement(SourceNavigatePageElements.PUBLIC_VIEW_ALL_COLS_WITH_CDP_AUTO_INTEGRATION);
		waitForGridRefresh();
	}

	public void selectAllOnPage()
	{
		click(SourceNavigatePageElements.SELECT_BUTTON_XPATH);
		click(SourceNavigatePageElements.SELECT_DRODOWN_SELECT_ALL_XPATH);
	}

	public void clickRefreshButton()
	{
		click(SourceNavigatePageElements.REFRESH_BUTTON_XPATH);
	}

	public void clickRefreshButtonOnTop()
	{
		click(SourceNavigatePageElements.REFRESH_BUTTON_TOP_XPATH);
	}

	public void closeEditorPage(Boolean expectedEnabled, Boolean actual, Boolean closeWindow)
	{
		if((actual == true && expectedEnabled == true && closeWindow== true) || (actual == false && expectedEnabled == false && closeWindow== true))
		{
			waitForPageLoaded();
			editorPage().closeDocumentWithNoChanges();
			editorPage().waitForEditorToClose();
		}
	}
	
	public void closeDocument(Boolean expectedEnabled, Boolean actual, Boolean closeWindow)
	{
		if((actual == true && expectedEnabled == true && closeWindow== true) || (actual == false && expectedEnabled == false && closeWindow== true))
		{
			waitForPageLoaded();
			homePage().closeCurrentWindowIgnoreDialogue();
		}
	}

	public void closeDocumentWithIFrame(Boolean expectedEnabled, Boolean actual, Boolean closeWindow)
	{
		if((actual == true && expectedEnabled == true && closeWindow== true) || (actual == false && expectedEnabled == false && closeWindow== true))
		{
			waitForPageLoaded();
			breakOutOfFrame();
			homePage().closeCurrentWindowIgnoreDialogue();
		}
	}

	public void switchToSectionNavigatePage()
	{
		switchToWindow(SourceNavigatePageElements.SOURCE_NAVIGATE_SECTION_PAGE_TITLE);
	}
	public void switchToDeltaNavigatePage()
	{
		switchToWindow(SourceNavigatePageElements.PAGE_TITLE_DELTA_NAVIGATE);
	}

	public void switchToGroupingNavigatePage(String group)
	{
		switchToWindow("Navigate");
	}

	public void switchToDeltaGroupNavigatePage()
	{
		switchToWindow(SourceNavigatePageElements.PAGE_TITLE_DELTA_GROUP_NAVIGATE);
	}

	public void clickCreateBookmarkButton()
	{
		click(SourceNavigatePageElements.CREATE_BOOKMARK_BUTTON);
		createBookmarkPage().switchToCreateBookmarkWindow();
	}

	public boolean multipleMessageExists()
	{
		return !getElements(SourceNavigatePageElements.MULTIPLES_MESSAGE_XPATH).isEmpty();
	}

	public void switchToLineageNavigatePage()
	{
		switchToWindow(SourceNavigatePageElements.SOURCE_NAVIGATE_LINEAGE_TITLE);
	}

	public int openMultipleSourceRenditions(String... uuids)
	{
		for (int i = 0; i < uuids.length; i++)
		{
			if (i != 0)
			{
				switchToWindow(PendingRenditionNavigatePageElements.PAGE_TITLE);
			}
			goToSourcePageWithRenditionUuids(uuids[i]);
			sourceNavigateGridPage().firstRenditionEditContent();
		}
		return uuids.length;
	}

	public int openMultipleSourceSections(int numberOfSectionsToOpen)
	{
		for (int i = 0; i < numberOfSectionsToOpen; i++)
		{
			switchToSectionNavigatePage();
			sourceNavigateGridPage().sectionEditContentByIndex(i + 1);
		}
		return numberOfSectionsToOpen;
	}

	public int openMultipleSourceDeltas(int numberOfDeltasToOpen)
	{
		for (int i = 0; i < numberOfDeltasToOpen; i++) {
			switchToDeltaNavigatePage();
			sourceNavigateGridPage().deltaEditContentByIndex(i + 1);
		}
		return numberOfDeltasToOpen;
	}
}
