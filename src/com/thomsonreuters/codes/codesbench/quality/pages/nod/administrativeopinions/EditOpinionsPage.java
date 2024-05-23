package com.thomsonreuters.codes.codesbench.quality.pages.nod.administrativeopinions;
import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.AdministrativeOpinionsPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.FindPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.EditOpinionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class EditOpinionsPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public EditOpinionsPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditOpinionPageElements.class);
	}
	
	public void setOpinionType(String type)  
	{
		selectDropdownOption(EditOpinionPageElements.opinionTypeDropdown, type);
	}

	public void setOpinionNumber(String number)  
	{
		clearAndSendKeysToElement(EditOpinionPageElements.opinionNumberField, number);
	}

	public void setDateOfOpinion(String date)  
	{
		clearAndSendKeysToElement(EditOpinionPageElements.opinionDateField, date);
	}
	
	public void setOpinionText(String text)
	{
		clearAndSendKeysToElement(EditOpinionPageElements.opinionTextField, text);
	}

	public void setOpinionCitation(String citation)  
	{
		clearAndSendKeysToElement(EditOpinionPageElements.citationField, citation);
	}
	
	public void setOpinionWlNumber(String wlNumber)  
	{
		clearAndSendKeysToElement(EditOpinionPageElements.opinionWlNumberField, wlNumber);
	}

	public boolean clickUpdate()  
	{
		click(EditOpinionPageElements.updateButton);
		return switchToWindow(AdministrativeOpinionsPageElements.ADMINISTRATIVE_OPINIONS_PAGE_TITLE);
	}

	public boolean clickCancel()  
	{
		click(EditOpinionPageElements.cancelButton);
		return switchToWindow(AdministrativeOpinionsPageElements.ADMINISTRATIVE_OPINIONS_PAGE_TITLE);
	}
	
	public String getOpinionText()
	{
		return getElementsText(EditOpinionPageElements.opinionTextField);
	}
	
	public boolean resultFromFindAppearsSelectedInTree(String targetSection)  
    {
		waitForGridRefresh();
        return isElementDisplayed(String.format(EditOpinionPageElements.highlightedNodeInTreeWithGivenText, targetSection));
    }
	
	public boolean clickKeywordFind()  
	{
		click(EditOpinionPageElements.keywordFindButton);
		boolean findWindowAppeared = switchToWindow(FindPageElements.FIND_PAGE_TITLE);
		enterTheInnerFrame(); 
		return findWindowAppeared;
	}
	
	public boolean switchToEditOpinionPage()
	{
		return switchToWindow(EditOpinionPageElements.EDIT_OPINION_PAGE_TITLE);
	}
	
	public void rightClickHighlightedNode(String targetSection)
	{
		rightClick(String.format(EditOpinionPageElements.highlightedNodeInTreeWithGivenText, targetSection));
	}
}
