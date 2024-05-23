package com.thomsonreuters.codes.codesbench.quality.pages.source.bts;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.BtsWebUiPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.DeleteRecordsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class DeleteRecordsPage extends BasePage 
{
	private WebDriver driver;

	@Autowired
	public DeleteRecordsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init() 
	{
		PageFactory.initElements(driver, DeleteRecordsPageElements.class);
	}
	
	public boolean deleteMessageAppears()
	{
		return doesElementExist(DeleteRecordsPageElements.DELETE_RECORDS_MESSAGE_XPATH);
	}

	public void clickOkButtonOnMessage()
	{
		//Old Code
		//sendEnterToElement(DeleteRecordsPageElements.okButton);
		//New Code
		click(DeleteRecordsPageElements.okButton);
		switchToWindow(BtsWebUiPageElements.BTS_WEB_UI_PAGE_TITLE);
		waitForPageLoaded();

	}

}
