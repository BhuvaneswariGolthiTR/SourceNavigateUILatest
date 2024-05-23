package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.QueryNoteReportContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportEditPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportResolvePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QueryNoteReportContextMenu extends ContextMenu
{
	private WebDriver driver;
	
	@Autowired
	public QueryNoteReportContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, QueryNoteReportContextMenuElements.class);
	}
	
	/**
	 * Clicks the resolve query note option.
	 *
	 * @return whether the window opened
	 */
	public boolean resolveQueryNote()
	{
		sendEnterToElement(QueryNoteReportContextMenuElements.resolveQueryNote);
		if(waitForWindowByUrl(QueryNoteReportResolvePageElements.QUERY_NOTE_RESOLVE_PAGE_TITLE))
		{
			enterTheInnerFrame();
			return true;
		}
		return false;
	}
	
	/**
	 * Clicks the edit query note option.
	 *
	 * @return whether the window opened
	 */
	public boolean editQueryNote()
	{
		sendEnterToElement(QueryNoteReportContextMenuElements.editQueryNote);
		if(waitForWindowByUrl(QueryNoteReportEditPageElements.QUERY_NOTE_EDIT_PAGE_TITLE))
		{
			enterTheInnerFrame();
			return true;
		}
		return false;
	}
	
	/**
	 * Clicks the find in hierarchy query note option.
	 *
	 * @return whether the window opened
	 */
	public boolean findDocumentInHierarchy()
	{
		sendEnterToElement(QueryNoteReportContextMenuElements.findDocumentInHierarchy);
		return switchToWindow(HierarchyPageElements.PAGE_TITLE);
	}
	
	/**
	 * Clicks the delete query note option.
	 *
	 * @return whether the window opened
	 */
	public boolean deleteQueryNote()
	{
		sendEnterToElement(QueryNoteReportContextMenuElements.deleteQueryNote);
		// 4 because at the time of implementation, the only test using this method has 4 windows open
		boolean windowOpened = switchToWindowWithNoTitle(4);
		enterTheInnerFrame();
		return windowOpened;
	}
	
	/**
	 * Clicks the find Document In Hierarchy query note option.
	 *
	 * @return whether the window opened
	 */
	public boolean findDocumentInHierarchyQueryNote()
	{
		sendEnterToElement(QueryNoteReportContextMenuElements.findDocumentInHierarchy);
		boolean windowOpened = switchToWindow(HierarchyPageElements.PAGE_TITLE);
		return windowOpened;
	}
	
	/**
	 * Clicks the edit query note option.
	 *
	 * @return whether the window opened
	 */
	public boolean editDocument()
	{
		sendEnterToElement(QueryNoteReportContextMenuElements.editDocument);
		boolean editorWindowAppeared = switchToWindow(EditorPageElements.PAGE_TITLE);
		waitForPageLoaded();
		waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
		return editorWindowAppeared;
	}
	
	/**
	 * Clicks the resolve query note option.
	 */
	public void resolveMultipleQueryNotes()
	{
		sendEnterToElement(QueryNoteReportContextMenuElements.resolveQueryNote);
		switchToModalWindow();
		enterTheInnerFrame();
	}
	
	/**
	 * Clicks the Delete query note option.
	 */
	public void deleteMultipleQueryNotes()
	{
		sendEnterToElement(QueryNoteReportContextMenuElements.deleteQueryNotes);
		switchToModalWindow();
		enterTheInnerFrame();
	}
	
	/**
	 * Clicks the Edit query note option.
	 */
	public void editMulitpleQueryNotes()
	{
		sendEnterToElement(QueryNoteReportContextMenuElements.editQueryNote);
		switchToModalWindow();
		enterTheInnerFrame();
	}
}
