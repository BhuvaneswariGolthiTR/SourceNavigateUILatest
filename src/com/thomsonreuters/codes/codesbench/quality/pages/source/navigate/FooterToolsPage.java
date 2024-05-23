package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.ClamshellPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.DocumentSearchFilterPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.FooterToolsElements;
//import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.DocumentSearchFilterPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.viewmanagament.ViewManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.apache.poi.ss.usermodel.Footer;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.FooterToolsElements.*;

@Component
public class FooterToolsPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public FooterToolsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, FooterToolsElements.class);
		PageFactory.initElements(driver, ClamshellPageElements.class);
	}

	public void clickSelectButton()
	{

		logger.information("Click Select button");
		click(SELECT_BUTTON);
	}

	public void clearSection()
	{
		click(clearButton);
		openContextMenu(clearSelectionOption);
		waitForGridRefresh();
	}

	public void clickSelectAllOnPage()
	{
		logger.information("Click Select All on Page dropdown");
		clickSelectButton();
		click(SELECT_ALL_ON_PAGE_DROPDOWN);
	}

	public void refreshTheGrid()
	{
		click(FooterToolsElements.refreshButtonTop);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
		waitForGridRefresh();
		waitForPageLoaded();
		scrollToElement(ClamshellPageElements.sideContentContainer);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
		waitForGridRefresh();
		waitForPageLoaded();
	}

	public void clearFilters()
	{
		clearHelper(FooterToolsElements.clearFiltersFromClearButton);
	}

	public void clearSort()
	{
		clearHelper(FooterToolsElements.clearSortFromClearButton);
	}

	public void clearSelection()
	{
		clearHelper(FooterToolsElements.clearSelectionFromClearButton);
	}

	private void clearHelper(WebElement clearItem)
	{
		click(FooterToolsElements.clearButton);
		click(clearItem);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public int getResults()
	{
		String results = getElement(FooterToolsElements.RESULTS).getAttribute("innerHTML");
		results = results.split("\\s+")[0];
		return Integer.parseInt(results);
	}

	public void selectJurisdictionView(String view)
	{
		selectView(FooterToolsElements.jurisdictionButton, view);
	}

	public void selectPrivateView(String view)
	{
		selectView(FooterToolsElements.privateButton, view);
	}

	public void selectPublicView(String view)
	{
		selectView(FooterToolsElements.publicButton, view);
	}

	private void selectView(WebElement viewButton, String view)
	{
		click(viewButton);
		sendEnterToElement(String.format(FooterToolsElements.JURISDICTION,  view));
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public boolean compareActualViewToExpectedView(String expectedView)
	{
		String expectedLabel = "Current view: " + expectedView;
		String actualLabel;

		if(isElementDisplayed(FooterToolsElements.CURRENT_VIEW))
		{
			actualLabel = getElement(SourceNavigatePageElements.VIEW_MANAGEMENT_BUTTON).getText();
		}
		else
		{
			actualLabel = getElement(String.format("(%s)[2]", FooterToolsElements.CURRENT_VIEW)).getText();
		}

		return expectedLabel.equals(actualLabel);
	}

	public void selectAllOnPage()
	{
		click(FooterToolsElements.selectButton);
		click(FooterToolsElements.selectAllOnPageButton);
	}

	public boolean clickMagnifyingGlass()
	{
		click(FooterToolsElements.findDocumentButton);
		boolean pageAppeared = switchToWindow(DocumentSearchFilterPageElements.DOCUMENT_SEARCH_FILTER_TITLE);
		enterTheInnerFrame();
		return pageAppeared;
	}


	public boolean clickTheViewManager()
	{
		click(FooterToolsElements.viewManagementButton);
		boolean pageAppeared = switchToWindow(ViewManagementPageElements.VIEW_MANAGEMENT_WINDOW_TITLE);
		enterTheInnerFrame();
		return pageAppeared;
	}

	public boolean compareExpectedView(String expectedView)
	{
		String expectedLabel = "Current view: " + expectedView;
		String actualLabel;
		if(getElement(SourceNavigatePageElements.VIEW_LABEL_XPATH).isDisplayed())
		{
			actualLabel = getElement(ViewManagementPageElements.VIEW_MANAGEMENT_BUTTON).getText();
		}
		else {
			actualLabel = getElement("(" + SourceNavigatePageElements.VIEW_LABEL_XPATH + ")[2]").getText();
		}
		return expectedLabel.equals(actualLabel);
	}

	public void repopulateView()
	{
		click(FooterToolsElements.REPOPULATE_VIEW_BUTTON_XPATH);
		waitForGridRefresh();
	}
}
