package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.CreateQueryNotePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

@Component
public class CreateQueryNotePage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public CreateQueryNotePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, CreateQueryNotePageElements.class);
	}
	
	/**
	 * Sets the query note action date to current date.
	 */
	public void setQueryNoteActionDateToCurrentDate()
	{
		clearAndSendKeysToElement(CreateQueryNotePageElements.actionTextbox, DateAndTimeUtils.getCurrentDateMMddyyyy());
	}
	
	/**
	 * Sets the query note text field.
	 *
	 * @param text
	 */
	public void setQueryNoteText(String text)
	{
		clearAndSendKeysToElement(CreateQueryNotePageElements.textField, text);
	}
	
	/**
	 * Clicks save button on the query note window.
	 */
	public void save()
	{
		click(CreateQueryNotePageElements.saveButton);
		switchToWindow(EditorPageElements.PAGE_TITLE);
	}
	
	/**
	 * Sets the type for the query note page.
	 *
	 * @param type the type
	 */
	public void setType(String type)
	{
		selectDropdownOptionUsingJavascript(CreateQueryNotePageElements.typeSelect_ID, type);
	}

	
	public void clickRefreshButton()
	{
		click(CreateQueryNotePageElements.refreshButton);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void switchToHierarchyEditWindow()
	{
		switchToWindow(HierarchyPageElements.PAGE_TITLE);
		waitForGridRefresh();
		waitForPageLoaded();
	}
}
