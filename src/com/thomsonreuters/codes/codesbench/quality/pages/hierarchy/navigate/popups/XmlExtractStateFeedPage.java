package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.XmlExtractStateFeedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class XmlExtractStateFeedPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public XmlExtractStateFeedPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, XmlExtractStateFeedPageElements.class);
	}

	public void clickSubmit()
	{
		click(XmlExtractStateFeedPageElements.submitButton);
	}

	public void clickCancel()
	{
		click(XmlExtractStateFeedPageElements.cancelButton);
		waitForWindowGoneByTitle(XmlExtractStateFeedPageElements.XML_EXTRACT_STATE_FEED_PAGE_TITLE);
	}

	public void clickClose()
	{
		click(XmlExtractStateFeedPageElements.close);
		waitForWindowGoneByTitle(XmlExtractStateFeedPageElements.XML_EXTRACT_STATE_FEED_PAGE_TITLE);
	}

	public String getPlaceholderOfGroupName()
	{
		return getElementsAttribute(XmlExtractStateFeedPageElements.groupNameInput, "placeholder");
	}

	public String getDefaultYearOption()
	{
		return getElementsAttribute(XmlExtractStateFeedPageElements.selectedYearInputOption, "value");
	}

	public List<String> getListOfYearOptions()
	{
		return getElementsTextList(XmlExtractStateFeedPageElements.LIST_OF_YEAR_OPTIONS_XPATH);
	}

	public void setYearDropdown(String year)
	{
		selectDropdownOption(XmlExtractStateFeedPageElements.yearInputDropdown, year);
	}

	public void setGroupName(String groupName)
	{
		clearAndSendKeysToElement(XmlExtractStateFeedPageElements.groupNameInput, groupName);
	}

	public String getWorkflowId()
	{
		return XmlExtractStateFeedPageElements.workflowLink.getAttribute("innerHTML");
	}

	public void goToWorkflow()
	{
		click(XmlExtractStateFeedPageElements.workflowLink);
	}
}
