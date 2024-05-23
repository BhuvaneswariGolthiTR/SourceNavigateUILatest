package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import javax.annotation.PostConstruct;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertFootnotePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertFootnotePageElements.newFootnoteCheckbox;

@Component
public class InsertFootnotePage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public InsertFootnotePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, InsertFootnotePageElements.class);
    }
	
	public void setFootnoteReferenceType(String footnoteReferenceType)
    {

		selectDropdownOptionUsingJavascript("pageForm:footnoteRefType",footnoteReferenceType);
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript("arguments[0].onchange()",getElement("//select[@id='pageForm:footnoteRefType']"));

    }
    
    public void setFootnoteType(String footnoteType)
    {
    	getElement(String.format(InsertFootnotePageElements.FOOTNOTE_TYPE, footnoteType)).click();
    }
    
    public void setFootnoteReference(String reference)
    {
		InsertFootnotePageElements.footnoteReference.sendKeys(reference);
    }

	public void checkNewFootnoteCheckbox()
	{
		if (!newFootnoteCheckbox.isSelected() || !"true".equals(newFootnoteCheckbox.getAttribute("checked")))
		{
			InsertFootnotePageElements.newFootnoteCheckbox.click();
		}
	}
    
    public void uncheckNewFootnoteCheckbox()
    {
    	if (InsertFootnotePageElements.newFootnoteCheckbox.isSelected() || "true".equals(InsertFootnotePageElements.newFootnoteCheckbox.getAttribute("checked")))
		{
			InsertFootnotePageElements.newFootnoteCheckbox.click();
		}
    }
    
    public void uncheckNewFootnoteReferenceCheckbox()
    {
    	if (InsertFootnotePageElements.newFootnoteReferenceCheckbox.isSelected() || "true".equals(InsertFootnotePageElements.newFootnoteReferenceCheckbox.getAttribute("checked")))
		{
			InsertFootnotePageElements.newFootnoteReferenceCheckbox.click();
		}
    }
    
    public void clickSaveFootnote()  
    {
    	sendEnterToElement(InsertFootnotePageElements.saveButton);
    }
}
