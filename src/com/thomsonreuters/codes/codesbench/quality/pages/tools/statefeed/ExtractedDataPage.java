package com.thomsonreuters.codes.codesbench.quality.pages.tools.statefeed;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.statefeed.ExtractedDataPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.tableAngular.TablePageAngular;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExtractedDataPage extends TablePageAngular
{
	private final WebDriver driver;

	public ExtractedDataPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ExtractedDataPageElements.class);
	}

	public boolean isClearFiltersAndSortsClickable()
	{
		return isElementClickable(ExtractedDataPageElements.clearFiltersAndSorts);
	}

	public boolean isRefreshCurrentGridClickable()
	{
		return isElementClickable(ExtractedDataPageElements.refreshCurrentGrid);
	}

	public String getTableHeader()
	{
		return getElementsText(ExtractedDataPageElements.header);
	}

	public boolean doesGivenGroupNameExist(String groupName)
	{
		return doesElementExist(String.format(ExtractedDataPageElements.GROUP_NAME_ELEMENT_XPATH, groupName));
	}

	public List<WebElement> getFilesGivenGroupNameList(String groupName)
	{
		return new ArrayList<>(extractedDataPage().getElements(String.format(ExtractedDataPageElements.GROUP_NAME_ELEMENT_XPATH, groupName) + "/../../*"));
	}

	public void clickGroupName(String groupName)
	{
		click(String.format(ExtractedDataPageElements.GROUP_NAME_ELEMENT_XPATH, groupName));
		waitForGridRefresh();
	}

	public boolean doesGivenYearContainGroupName(String year)
	{
		return isElementDisplayed(String.format(ExtractedDataPageElements.GROUP_NAME_IN_YEAR, year));
	}

	public boolean doesGivenJurisdictionContainGroupName(String jurisdiction)
	{
		return isElementDisplayed(String.format(ExtractedDataPageElements.JURISDICTION_IN_YEAR, jurisdiction));
	}

	public void clickGroupFilterMenu()
	{
		clickOnInvisibleElement(ExtractedDataPageElements.FILTER_MENU_XPATH);
	}

	public void filterForGroup(String groupName)
	{
		click(ExtractedDataPageElements.FILTER_ICON_XPATH);
		clearAndSendKeysToElement(ExtractedDataPageElements.filterInput, groupName);
		waitForGridRefresh();
		click(ExtractedDataPageElements.EXTRACTED_DATA_XPATH);
	}
}
