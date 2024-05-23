package com.thomsonreuters.codes.codesbench.quality.pages.tools.stocknotemanager;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteTableManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class StocknoteTableManagementPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public StocknoteTableManagementPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, StocknoteTableManagementPageElements.class);
	}
	
	/**
	 * Returns the name of the first stocknote selected in the selected stocknotes menu.
	 *
	 * @return
	 */
	public String getFirstSelectedStocknoteName()
	{
		Select select = new Select(StocknoteTableManagementPageElements.selectedStocknotesSelect);
		
		logger.information(select.getFirstSelectedOption().getText());
		return new Select(StocknoteTableManagementPageElements.selectedStocknotesSelect).getFirstSelectedOption().getText();
	}
	
	/**
	 * Selects the given content set.
	 *
	 * @param contentSet
	 */
	public void selectContentSet(String contentSet)
	{
		String mappedContentSet = contentSetMapper(contentSet);
		selectDropdownOptionUsingJavascript(StocknoteTableManagementPageElements.COPY_TO_CONTENT_SET_SELECT_ID, mappedContentSet);
	}

	private String contentSetMapper(String contentSet)
	{
		switch(contentSet)
		{
			case "Iowa (Development)": return "SOS.IAT";
			case "USCA(Development)": return "SOS.UST";
			case "Alabama (Development)": return "SOS.ALT";
			default: logger.information("Unsupported setValue.  Please check to see if we have a map for your content set.");
				return "";
		}
	}
	
	/**
	 * Clicks the copy button that is for copying to a different content set.
	 */
	public void copyToContentSet()
	{
		click(StocknoteTableManagementPageElements.copyToContentSetButton);
		waitForPageLoaded();
	}
	
	/**
	 * Checks if the results text area contains the given text.
	 *
	 * @param expectedText
	 * @return
	 */
	public boolean resultsTextAreaContains(String expectedText)
	{
		return getElementsText(StocknoteTableManagementPageElements.resultsTextArea).contains(expectedText);
	}
	
	/**
	 * Clicks the close button.
	 */
	public void close()
	{
		click(StocknoteTableManagementPageElements.closeButton);
		switchToWindow(StocknoteManagerPageElements.PAGE_TITLE);
	}
	
}
