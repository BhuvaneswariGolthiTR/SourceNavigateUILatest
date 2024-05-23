package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SelectPubtagsElements;

import java.util.List;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.AdvancedSearchAndReplaceElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class AdvancedSearchAndReplacePage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public AdvancedSearchAndReplacePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, AdvancedSearchAndReplaceElements.class);
    }
	
	public void clickSearchAndReplace()
	{
		click(AdvancedSearchAndReplaceElements.searchAndReplaceButton);
	}
	
	public void clickScan()
	{
		click(AdvancedSearchAndReplaceElements.scanButton);
		advancedSearchAndReplacePage().waitForPageLoaded();
	}
	
	public boolean setSearchPubtags(List<String> pubtags)  
	{
		click(AdvancedSearchAndReplaceElements.searchForPubtagsListButton);
		
		return selectSearchPubtags(pubtags);
	}
	
	public boolean setReplacePubtags(List<String> pubtags)  
	{
		click(AdvancedSearchAndReplaceElements.showPubtagsListButtonForReplace);
		
		return selectReplacePubtags(pubtags);
	}
	
	private boolean selectSearchPubtags(List<String> pubtags)  
	{
		click(AdvancedSearchAndReplaceElements.searchForPubtagsListButton);
		switchToWindow(AdvancedSearchAndReplaceElements.PUBTAGS_LIST_PAGE_TITLE);
		switchToInnerIFrameByIndex(1);
		
		pubtags.stream().forEach(pubtag -> click(String.format(SelectPubtagsElements.PUBTAG_CHECKBOX_XPATH, pubtag)));
		click(AdvancedSearchAndReplaceElements.okButton);
		
		switchToWindow(EditorPageElements.PAGE_TITLE);
		switchToWindow(AdvancedSearchAndReplaceElements.PAGE_TITLE);
		enterTheInnerFrame();
		
		boolean pubtagsListPopulatedCorrectly = true;
		for (String pubtag : pubtags)
		{
			pubtagsListPopulatedCorrectly &= AdvancedSearchAndReplaceElements.pubtagsSearchTextArea.getText().contains(pubtag);
		}
		
		return pubtagsListPopulatedCorrectly;
	}
	
	private boolean selectReplacePubtags(List<String> pubtags)  
	{
		click(AdvancedSearchAndReplaceElements.searchForPubtagsListButton);
		switchToWindow(AdvancedSearchAndReplaceElements.PUBTAGS_LIST_PAGE_TITLE);
		switchToInnerIFrameByIndex(1);
		
		pubtags.stream().forEach(pubtag -> click(String.format(SelectPubtagsElements.PUBTAG_CHECKBOX_XPATH, pubtag)));
		click(AdvancedSearchAndReplaceElements.okButton);
		
		switchToWindow(EditorPageElements.PAGE_TITLE);
		switchToWindow(AdvancedSearchAndReplaceElements.PAGE_TITLE);
		enterTheInnerFrame();
		
		boolean pubtagsListPopulatedCorrectly = true;
		for (String pubtag : pubtags)
		{
			pubtagsListPopulatedCorrectly &= AdvancedSearchAndReplaceElements.pubtagsReplaceTextArea.getText().contains(pubtag);
		}
		
		return pubtagsListPopulatedCorrectly;
	}
	
	public void setSearchPhrase(String phrase)
	{
		waitForElementExists(AdvancedSearchAndReplaceElements.searchPhraseTextArea);
		AdvancedSearchAndReplaceElements.searchPhraseTextArea.sendKeys(phrase);
	}
	
	public void setReplacePhrase(String phrase)
	{
		AdvancedSearchAndReplaceElements.replacePhraseTextArea.sendKeys(phrase);
	}

	public void setSearchMnemonic(String mnemonicToSearchFor)
	{
		waitForElementExists(AdvancedSearchAndReplaceElements.MNEMONIC_TO_SEARCH);
		click(AdvancedSearchAndReplaceElements.MNEMONIC_TO_SEARCH);
		sendKeys(mnemonicToSearchFor);
	}

	public void setReplaceMnemonic(String mnemonicToReplaceWith)
	{
		click(AdvancedSearchAndReplaceElements.MNEMONIC_TO_REPLACE_WITH);
		sendKeys(mnemonicToReplaceWith);
	}
}
