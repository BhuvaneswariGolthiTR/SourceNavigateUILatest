package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereport;

import java.awt.event.KeyEvent;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class QueryNoteReportGridPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public QueryNoteReportGridPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, QueryNoteReportGridPageElements.class);
	}

	/**
	 * Selects the first query note.
	 */
	public void selectFirstQueryNote()
	{

		checkCheckbox(QueryNoteReportGridPageElements.queryNotesInGrid.get(0));
	}

	/**
	 * Right clicks the first query note.
	 */
	public void rightClickFirstQueryNote()
	{
		rightClick(QueryNoteReportGridPageElements.queryNotesInGrid.get(0));
	}

	/**
	 * Gets the first query note status.
	 *
	 * @return the status
	 */
	public String getFirstQueryNoteStatus()
	{
		return getElementsText(QueryNoteReportGridPageElements.firstQueryNoteStatus).trim();
	}

	/**
	 * Gets the first query note resolved comment.
	 *
	 * @return the resolved comment
	 */
	public String getFirstQueryResolvedComment()
	{
		return getElementsText(QueryNoteReportGridPageElements.firstQueryNoteResolvedComment).trim();
	}

	/**
	 * Gets the number of query notes in the grid.
	 *
	 * @return the number of query notes
	 */
	public int getNumberOfQueryNotesInGrid()
	{
		if (doesElementExist(QueryNoteReportGridPageElements.NO_RECORDS_FOUND))
			{return 0;}
		else
			{return QueryNoteReportGridPageElements.queryNotesInGrid.size();}
	}

	/**
	 * Gets the query note status at a certain index.
	 *
	 * @param index the index
	 * @return the status
	 */
	public String getQueryNoteStatusAtIndex(int index)
	{
		scrollToElement(QueryNoteReportGridPageElements.firstQueryNoteStatus);
		return getElementsText(QueryNoteReportGridPageElements.queryNoteStatus.get(index));
	}

	/**
	 * Selects all the query notes currently in the grid.
	 */
	public void selectAllQueryNotes()
	{
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
			QueryNoteReportGridPageElements.queryNotesInGrid.forEach(e -> e.click());
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
	}
	
	public boolean verifyQueryTextIsCorrect(String expectedQueryText)
	{
		String actualQueryText = getElementsText(QueryNoteReportGridPageElements.QUERY_TEXT);
		return expectedQueryText.contains(actualQueryText);
	}

	public String getQueryNoteBreadcrumbAtIndex(int index)
	{
		scrollToElement(QueryNoteReportGridPageElements.firstQueryNoteHierarchyBreadcrumb);
		return getElementsText(QueryNoteReportGridPageElements.queryNoteHierarchyBreadcrumb.get(index));
	}

}
