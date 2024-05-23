package com.thomsonreuters.codes.codesbench.quality.pages.source.bts;

import javax.annotation.PostConstruct;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.GenerateLegislativeServiceTablesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class GenerateLegislativeServiceTablesPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public GenerateLegislativeServiceTablesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, GenerateLegislativeServiceTablesPageElements.class);
	}

	public void setTableNumberDropdown(String tableNumber)
	{
		//Old Code
		//selectDropdownOption(GenerateLegislativeServiceTablesPageElements.tableNumberDropDown, tableNumber);
		//New code
		selectDropdownOptionUsingJavascript("pageForm:tableNumber2", tableNumber);
	}
	
	public void setPamphletNumber(String pamphletNumber)
	{
		sendKeysToElement(GenerateLegislativeServiceTablesPageElements.pamphletNumberField, pamphletNumber);
	}

	public void setLegislativeYear(String year)
	{
		//Old code
		//GenerateLegislativeServiceTablesPageElements.legislativeYearField.clear();
		//sendKeysToElement(GenerateLegislativeServiceTablesPageElements.legislativeYearField, year);
		//New code
		clearAndSendTextToTextbox(GenerateLegislativeServiceTablesPageElements.legislativeYearField, year);
	}
	
	public void addNewWestNumberForRow(int rowNumber)
	{
		click(String.format(GenerateLegislativeServiceTablesPageElements.ADD_RANGE_BUTTON, rowNumber));
		waitForElement(String.format(GenerateLegislativeServiceTablesPageElements.ADD_RANGE_BUTTON, ++rowNumber));
	}
	
	public void setWestNumberFrom(String rowNumber, String fromValue)
	{
		sendKeysToElement(String.format(GenerateLegislativeServiceTablesPageElements.ADD_RANGE_FROM_TEXTFIELD, rowNumber), fromValue);
	}

	public void setWestNumberTo(String rowNumber, String toValue)
	{
		sendKeysToElement(String.format(GenerateLegislativeServiceTablesPageElements.ADD_RANGE_TO_TEXTFIELD, rowNumber), toValue);
	}
	
    public boolean generateTable()
    {
    	click(GenerateLegislativeServiceTablesPageElements.generateButton);
    	waitForElementGone(GenerateLegislativeServiceTablesPageElements.TABLE_WAITING_FOR_GENERATING_TABLES_TEXT_ELEMENT);
    	return doesElementExist(GenerateLegislativeServiceTablesPageElements.TABLE_SUCCESFULLY_GENERATED_TEXT);
    }
	
    public void clickCancelButton()
    {
    	click(CommonPageElements.CANCEL_BUTTON);
    }
    
    public void clickOnListTab()
    {
    	sendEnterToElement(GenerateLegislativeServiceTablesPageElements.legislativeTableListTab);
    }
    
}
