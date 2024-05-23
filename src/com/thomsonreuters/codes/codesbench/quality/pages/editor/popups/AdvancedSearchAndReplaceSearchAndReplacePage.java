package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.AdvancedSearchAndReplaceSearchAndReplaceElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class AdvancedSearchAndReplaceSearchAndReplacePage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public AdvancedSearchAndReplaceSearchAndReplacePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, AdvancedSearchAndReplaceSearchAndReplaceElements.class);
    }
	
	public void clickCommit()
	{
		click(AdvancedSearchAndReplaceSearchAndReplaceElements.commitButton);
		editorPage().switchDirectlyToTextFrame();
	}
	
	public List<String> getBeforeChanges()
	{
		List<String> textList = new ArrayList<>();
		for (WebElement element : AdvancedSearchAndReplaceSearchAndReplaceElements.beforeChanges)
		{
			textList.add(element.getText());
		}
		return textList;
	}
	
	public List<String> getAfterChanges()
	{
		List<String> textList = new ArrayList<>();
		for (WebElement element : AdvancedSearchAndReplaceSearchAndReplaceElements.afterChanges)
		{
			textList.add(element.getText());
		}
		return textList;
	}

}
