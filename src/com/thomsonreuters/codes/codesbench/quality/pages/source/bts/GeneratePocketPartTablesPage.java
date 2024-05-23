package com.thomsonreuters.codes.codesbench.quality.pages.source.bts;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.GeneratePocketPartTablesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class GeneratePocketPartTablesPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public GeneratePocketPartTablesPage(WebDriver driver)
	{
			super(driver);
			this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, GeneratePocketPartTablesPageElements.class);
	}
    
    public boolean clickViewEditButton()
    {
		sendEnterToElement(GeneratePocketPartTablesPageElements.viewEditButton);
		return doesElementExist(GeneratePocketPartTablesPageElements.TEXT_EDIT_BOX_XPATH);
    }
	
    public String getViewEditText()
    {
		return getElementsText(GeneratePocketPartTablesPageElements.TEXT_EDIT_BOX_XPATH);
    }
    
    public void sendTextToViewEditTextArea(String textToSend)
    {
		click(GeneratePocketPartTablesPageElements.TEXT_EDIT_BOX_XPATH);
		sendKeysToElement(GeneratePocketPartTablesPageElements.TEXT_EDIT_BOX_XPATH, textToSend);
		//clearAndSendTextToTextbox(GeneratePocketPartTablesPageElements.TEXT_EDIT_BOX_XPATH, textToSend);
    }	
    
    public void clickOnGenerateTab()
    {
		click(GeneratePocketPartTablesPageElements.generateTab);
    }
  
    public boolean clickGenerateButton()
    {
		click(GeneratePocketPartTablesPageElements.generateButton);
		return doesElementExist(GeneratePocketPartTablesPageElements.POCKET_PARTS_SUCCESS_MESSAGE);
    }	
    
    public void setLegislativeYear(String year)
    {
		//Old code
    	//GeneratePocketPartTablesPageElements.legislativeYearField.clear(); //fix the pageElements
    	//sendKeysToElement(GeneratePocketPartTablesPageElements.legislativeYear, year);
		//New code
		clearAndSendTextToTextbox(GeneratePocketPartTablesPageElements.legislativeYear, year);
    }
    
    public void clickCancelButton()
    {
    	click(CommonPageElements.CANCEL_BUTTON);
    }

	public void clickListTab() 
	{
		click(GeneratePocketPartTablesPageElements.listTab);
	}
	
	
    
}
