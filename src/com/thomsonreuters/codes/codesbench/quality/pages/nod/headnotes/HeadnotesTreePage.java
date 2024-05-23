package com.thomsonreuters.codes.codesbench.quality.pages.nod.headnotes;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.nod.HeadnotesContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesDetailsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class HeadnotesTreePage extends BasePage
{
	WebDriver driver;

	@Autowired
	public HeadnotesTreePage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, HeadnotesTreePageElements.class);
	}

	public boolean isNodeHighlightedInTree(String expectedResult)  
    {
		waitForElement(String.format(HeadnotesPageElements.HIGHLIGHTED_HEADNOTE_IN_TREE_WITH_GIVEN_TEXT, expectedResult));
        return isElementDisplayed(String.format(HeadnotesPageElements.HIGHLIGHTED_HEADNOTE_IN_TREE_WITH_GIVEN_TEXT, expectedResult));
    }
    
    public boolean clickHeadnoteNumber(int number)
    {
    	click(String.format(HeadnotesPageElements.HEADNOTE_WITH_NUMBER_GIVEN, number));
    	boolean headnotesDetailsWindowAppeared = switchToWindow(HeadnotesDetailsPageElements.HEADNOTE_DETAIL_PAGE_TITLE);
    	enterTheInnerFrame();
    	return headnotesDetailsWindowAppeared;
    }
    
    public boolean compareActualTextHintsToExpected(String text)
    {
    	List<String> displayedHints = getElements(HeadnotesPageElements.BLUELINE_TEXT_HINTS).
				stream().map(WebElement::getText).collect(Collectors.toList());
		Predicate<String> expectedHints = hint -> hint.toLowerCase().contains(text.toLowerCase());
		return displayedHints.stream().allMatch(expectedHints);
    }
    
    public boolean checkFieldValueIsExpectedOne(WebElement elementXpath, String expectedValue) 
	{
		boolean checkValueIsAsExpected;
		try
		{
			checkValueIsAsExpected = elementXpath.getAttribute("value").equals(expectedValue);
		}
		catch (Exception e)
		{
			checkValueIsAsExpected = elementXpath.getText().contains(expectedValue);
		}
		return checkValueIsAsExpected;
	}
    
    public void editBluelineWithGivenText(String text)
    {
		rightClick(String.format(HeadnotesTreePageElements.HEADNOTE_TREE_NODE_WITH_VALUE_GIVEN, text));
		sendEnterToElement(HeadnotesContextMenuElements.editBlueline);
		waitForPageLoaded();
    }
    
    public boolean isBluelineWithGivenValuesDeleted(String firstValue, String secondValue)
    {
    	return doesElementExist(String.format(HeadnotesTreePageElements
				.HEADNOTE_TREE_NODE_WITH_VALUE_GIVEN, firstValue+"-"+secondValue));
    }
    
    public void rightClickNodeWithGivenText(String node)
    {
    	rightClick(String.format(HeadnotesTreePageElements.HEADNOTE_TREE_NODE_WITH_VALUE_GIVEN, node));
    }
    
    public void rightClickCurrentlyHighlightedNode()
    {
		waitForElementToBeClickable(HeadnotesTreePageElements.navTreeSelectedNodeXpath);
    	rightClick(HeadnotesTreePageElements.navTreeSelectedNodeXpath);
    }
   
    public boolean selectedBluelineIsFound()
    {
    	return getElementsText(HeadnotesPageElements.selectedBluelineField).contains("BL 1");
    }
}
