package com.thomsonreuters.codes.codesbench.quality.pages.nod;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.FindPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class FindPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public FindPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, FindPageElements.class);
	}
	
    public List<String> getActualColumns()
    {
    	return getElements("//th").stream().map(WebElement::getText).collect(Collectors.toList());
    }
	
    public List<String> getQuickSearchResults()
    {
    	return getElements(FindPageElements.quickSearchTableContents.getText()).stream().map(WebElement::getText).collect(Collectors.toList());
    }
    
    public void clickWindowCloseButton()
    {
    	click(CommonPageElements.WINDOW_CLOSE_CROSS_BUTTON);
    }
    
	public void setFirstKeywordDropdown(String option)  
    {
    	selectDropdownOption(FindPageElements.firstKeywordDropdown, option);
    }
    
    public void setSecondKeywordDropdown(String option)  
    {
    	selectDropdownOption(FindPageElements.secondKeywordDropdown, option);
    }
    
    public void setThirdKeywordDropdown(String option)  
    {
    	selectDropdownOption(FindPageElements.thirdKeywordDropdown, option);
    }
    
    public void setFirstKeywordValue(String value)  
    {
    	sendKeysToElement(FindPageElements.firstValueField, value);
    }
    
    public void setSecondKeywordValue(String value)  
    {
    	sendKeysToElement(FindPageElements.secondValueField, value);
    }
    
    public void setThirdKeywordValue(String value)  
    {
    	sendKeysToElement(FindPageElements.thirdValueField, value);
    }
    
    public void clickSearch()  
    {
    	click(FindPageElements.findSearchButton);
    	waitForPageLoaded();
    }

    public boolean switchToFindPage()
	{
		boolean findWindowAppeared = switchToWindow(FindPageElements.FIND_PAGE_TITLE);
		enterTheInnerFrame();
		return findWindowAppeared;
	}
    
    public String getFirstFindResultUuid()
    {
    	return getElementsText(FindPageElements.firstFindResultNodeUuid);
    }
	
	public void clickFirstFindResult()  
	{
		click(FindPageElements.firstFindResultNodeUuid);
		waitForWindowGoneByTitle(FindPageElements.FIND_PAGE_TITLE);
	}
	
	public List<String> getKeywordSearchResults()
    {
    	return getElements(HeadnotesPageElements.quickSearchTableContents.getText()).stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
