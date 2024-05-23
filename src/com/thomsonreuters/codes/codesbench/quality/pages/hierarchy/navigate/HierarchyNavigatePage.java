package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.*;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements.*;

@Component
public class HierarchyNavigatePage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public HierarchyNavigatePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, HierarchyPageElements.class);
		PageFactory.initElements(driver, HierarchySearchPageElements.class);
    }

	/**
	 *
	 * @param contentSetNumber
	 * @return
	 */
	public boolean goToHierarchyPage(String contentSetNumber)
	{
		openPageWithUrl(String.format(urls().getHierarchyPageUrl(), environmentTag, contentSetNumber),
				HierarchyPageElements.PAGE_TITLE);
		waitForPageLoaded();
		return true;
	}

	public void editSelectedSiblingMetadata()
	{
		rightClick(SIBLING_METADATA_SELECTED_GRID_ROW_XPATH);
		openContextMenu(HierarchyContextMenuElements.EDIT_CONTENT);
		waitForPageLoaded();
		editorPage().switchToEditor();
	}

	public boolean switchToEditor()
	{
		waitForWindowByTitle(EditorPageElements.PAGE_TITLE);
		return switchToWindow(EditorPageElements.PAGE_TITLE);
	}

	public boolean switchToBulkPublishWindow()
	{
		boolean windowAppeared = switchToWindow("Bulk Publish");
		enterTheInnerFrame();
		return windowAppeared;
	}

	public boolean switchToPreviousWipVersionsWindow()
	{
		boolean windowAppeared = switchToWindow(PreviousWipVersionsPageElements.PREVIOUS_WIP_VERSIONS_PAGE_TITLE);
		return windowAppeared;
	}

	public boolean switchToSetLawTrackingWindow()
	{
		boolean windowAppeared = switchToWindow("Set Law Tracking");
		enterTheInnerFrame();
		return windowAppeared;
	}

	public boolean switchToInsertNewNodesWindow()
	{
		waitForWindowByTitle("Insert New Nodes");
		boolean windowAppeared = switchToWindow("Insert New Nodes");
		enterTheInnerFrame();
		return windowAppeared;
	}

	public boolean switchToHierarchyEditPage()
	{
		return switchToWindow(HierarchyPageElements.PAGE_TITLE);
	}

	public void waitForHierarchyEditPageToLoad(String nodeValue)
	{
		waitForPageLoaded();
		waitForGridRefresh();
		waitForElementToBeClickable(String.format(HierarchyTreePageElements.SELECTED_TREE_NODE_WITH_VALUE_GIVEN,nodeValue));
	}

	public boolean switchToOpenedHierarchyNavigateByTitle()
	{
		return switchToOpenedWindowByTitle(HierarchyPageElements.PAGE_TITLE);
	}

	public boolean doesPublishingColumnExist()
	{
		return doesElementExist(SiblingMetadataElements.PUBLISHING_STATUS_COLUMN_XPATH);
	}

	public void openNewBrowserAsGivenUser(String userID) //See credentials.properties to find the UserID for each user
	{
		openBrowser();
		user().setUsername(userID);
	}

	public boolean checkIfPageIsOpened()
	{
		return checkWindowIsPresented(PAGE_TITLE);
	}

	public void searchTocNodeUuidHandleAlert(final String uuid)
	{
		click(HierarchyPageElements.findNodeTabXpath);
		sendKeysToElement(HierarchyPageElements.nodeUuidInput, uuid);
		sendEnterToElement(HierarchySearchPageElements.nodeUuidInput);

		checkAlert(); // TODO: add instead of two methods
		// acceptAlert();
		waitForElementGone(CommonPageElements.PROCESSING_PLEASE_WAIT_XPATH);
		waitForPageLoaded();
	}


	public String getSelectedModifiedDate()
	{
		return siblingMetadataSelectedModifiedDate.getText();
	}

	public int openMultipleTargetNodes(String... uuids)
	{
		Arrays.stream(uuids).forEach(uuid ->
				{
					switchToHierarchyEditPage();
					hierarchySearchPage().searchNodeUuid(uuid);
					switchToHierarchyEditPage();
					siblingMetadataPage().selectedSiblingMetadataEditContent();
				}
		);
		return uuids.length;
	}

	public void handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets contentSet)
	{
		if (getUrl().contains("cas?ticket=") && getUrl().endsWith("-cas"))
		{
			logger.warning("CAS issue has been occurred with opening 'Hierarchy Edit' page as start page");
			goToHierarchyPage(contentSet.getCode());
		}
	}
}
