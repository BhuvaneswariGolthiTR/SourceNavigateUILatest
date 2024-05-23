package com.thomsonreuters.codes.codesbench.quality.pages.nod.administrativeopinions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.AdministrativeOpinionsPageElements;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.EditOpinionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.InsertOpinionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;

@Component
public class InsertOpinionPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public InsertOpinionPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, InsertOpinionPageElements.class);
	}

	public void setOpinionType(String type)  
	{
		click(String.format(InsertOpinionPageElements.opinionTypeDropdown, type));
	}

	public void setOpinionNumber(String number)  
	{
		clearAndSendKeysToElement(InsertOpinionPageElements.opinionNumberField, number);
	}

	public void setDateOfOpinion(String date)  
	{
		clearAndSendKeysToElement(InsertOpinionPageElements.opinionDateField, date);
	}

	public void setOpinionText(String text)  
	{
		clearAndSendKeysToElement(InsertOpinionPageElements.opinionTextField, text);
	}

	public void setOpinionTextWithCtrlV(String text)  
	{
		click(InsertOpinionPageElements.opinionTextField);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection str = new StringSelection(text);
		clipboard.setContents(str, str);
		AutoITUtils.sendHotKey("v", Keys.CONTROL);
	}

	public void setOpinionCitation(String citation)  
	{
		clearAndSendKeysToElement(InsertOpinionPageElements.opinionCitationField, citation);
	}

	public void setOpinionWlNumber(String wlNumber)  
	{
		clearAndSendKeysToElement(InsertOpinionPageElements.opinionWlNumberField, wlNumber);
	}
	
	public boolean clickInsert()  
	{
		click(InsertOpinionPageElements.insertButton);
		return switchToWindow(AdministrativeOpinionsPageElements.ADMINISTRATIVE_OPINIONS_PAGE_TITLE);
	}

    public boolean clickSaveAndEditClassify()  
    {
    	click(InsertOpinionPageElements.saveAndEditClassifyButton);
    	return switchToWindow(EditOpinionPageElements.EDIT_OPINION_PAGE_TITLE);
    } 
	
}
