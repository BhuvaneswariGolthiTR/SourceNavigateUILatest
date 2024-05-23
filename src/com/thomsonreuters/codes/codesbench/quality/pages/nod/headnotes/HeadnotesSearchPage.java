package com.thomsonreuters.codes.codesbench.quality.pages.nod.headnotes;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.FindPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesSearchPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class HeadnotesSearchPage extends BasePage
{
	WebDriver driver;

	@Autowired
	public HeadnotesSearchPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, HeadnotesSearchPageElements.class); 
	}
	
	public boolean clickKeywordFind()  
    {
		click(HeadnotesSearchPageElements.keywordFindButton);
		boolean findPageAppeared = switchToWindow(FindPageElements.FIND_PAGE_TITLE);
		enterTheInnerFrame();
		return findPageAppeared;
    }
	
	public void setQuickFindField(String text)
	{
		clearAndSendKeysToElement(HeadnotesSearchPageElements.quickFindField, text);
	}
    
    public void clickQuickFindButton()
    {
    	sendEnterToElement(HeadnotesSearchPageElements.quickFindButton);
    	waitForPageLoaded();
    }
}
