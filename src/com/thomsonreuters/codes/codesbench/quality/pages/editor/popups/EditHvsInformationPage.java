package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.EditHVSInformationPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

@Component
public class EditHvsInformationPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public EditHvsInformationPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditHVSInformationPageElements.class);
	}
	
	public String getSerialNumberInput()
	{
		return EditHVSInformationPageElements.serialNumberInput.getAttribute("value");
	}
	
	public String getReporterVolumeInput()
	{
		return EditHVSInformationPageElements.reporterVolumeInput.getAttribute("value");
	}
	
	public String getReporterNumberInput()
	{
		return EditHVSInformationPageElements.reporterNumberInput.getAttribute("value");
	}
	
	public String getReporterPageInput()
	{
		return EditHVSInformationPageElements.reporterPageInput.getAttribute("value");
	}
	
	public String getHeadnoteNumberInput()
	{
		return EditHVSInformationPageElements.headnoteNumberInput.getAttribute("value");
	}
	
	public void suggestNewSerialNumberForHVSInformation(String newSerialNumber)
	{
		clearAndSendTextToTextbox(EditHVSInformationPageElements.serialNumberInput, newSerialNumber);
        click(EditHVSInformationPageElements.suggestCourtValuesButton);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        checkAlert();
        waitForGridRefresh();
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
	}

	public void setCCDB (String newCcdb)
	{
		clearAndSendTextToTextbox(EditHVSInformationPageElements.ccdb, newCcdb);
	}

	public void setHeadnoteNumber (String newHeadnoteNumber)
	{
		clearAndSendTextToTextbox(EditHVSInformationPageElements.headnoteNumberInput, newHeadnoteNumber);
	}

	public void selectNodType(String newNodType)
	{
		selectDropdownOptionUsingJavascript(EditHVSInformationPageElements.NOD_TYPE_ID, newNodType);
	}

	public void setNeutralCitation (String newNeutralCitation)
	{
		clearAndSendTextToTextbox(EditHVSInformationPageElements.neutralCitation, newNeutralCitation);
	}
	
	public void clickSaveButton() throws InterruptedException
	{
		click(CommonPageElements.SAVE_BUTTON);
		Thread.sleep(DateAndTimeUtils.THREE_SECONDS);
		checkAlert();
        Thread.sleep(DateAndTimeUtils.THREE_SECONDS);
	}
}
