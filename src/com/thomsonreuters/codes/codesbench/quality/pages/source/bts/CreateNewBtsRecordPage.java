package com.thomsonreuters.codes.codesbench.quality.pages.source.bts;

import javax.annotation.PostConstruct;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.BtsWebUiPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.CreateNewBtsRecordPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class CreateNewBtsRecordPage extends BasePage 
{
	private WebDriver driver;
		
		@Autowired
		public CreateNewBtsRecordPage(WebDriver driver)
		{
			super(driver);
			this.driver = driver;
		}
		
		@PostConstruct
		public void init()
		{
			PageFactory.initElements(driver, CreateNewBtsRecordPageElements.class);
		}
		
		public void setDocumentType(String documentType)
		{
			//Old Code
			//selectDropdownOption(CreateNewBtsRecordPageElements.documentTypeDropdown, documentType);
			//New Code
			selectDropdownOptionUsingJavascript("pageForm:docType", documentType);
		}
		
		public void setDocumentNumber(String documentNumber)
		{
			sendKeysToElement(CreateNewBtsRecordPageElements.documentNumberField,  documentNumber);
		}
		
		public boolean notSameLegislativeYearMessageAppears()
		{
			return doesElementExist(CreateNewBtsRecordPageElements.CREATE_NEW_BTS_RECORD_CREATE_POPUP_MESSAGE_XPATH);
		}
		
		public void setLegislativeYear(String year)
		{
			//Old code
			//CreateNewBtsRecordPageElements.legislativeYear.clear();
			//sendKeysToElement(CreateNewBtsRecordPageElements.legislativeYear, year);
			//New code
			clearAndSendTextToTextbox(CreateNewBtsRecordPageElements.legislativeYear, year);
		}
		
		public void clickCreateButton()
		{
			click(CreateNewBtsRecordPageElements.createButton);
			waitForPageLoaded();
		}
		
		public void clickOkButtonOnMessage()
		{
			//Old code
			//sendEnterToElement(CreateNewBtsRecordPageElements.okButton);
			//New code
			click(CreateNewBtsRecordPageElements.okButton);
			switchToWindow(BtsWebUiPageElements.BTS_WEB_UI_PAGE_TITLE);
			waitForPageLoaded();
		}
}
