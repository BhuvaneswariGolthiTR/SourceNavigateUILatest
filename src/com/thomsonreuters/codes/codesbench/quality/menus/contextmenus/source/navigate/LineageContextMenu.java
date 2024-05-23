package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.LineageContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.Menu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LineageContextMenu  extends Menu
{
	private WebDriver driver;

	@Autowired
	public LineageContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, LineageContextMenuElements.class);
	}
	
	public void goToLineageNavigate()
	{
		switchToWindow(LineageContextMenuElements.LINEAGE_NAVIGATE_TITLE);
	}

	public boolean displayAllResultsandClearFilters()
	{
		return isElementDisplayed("//span[text()='Displaying all results regardless of filters. Clear all filters to continue.']");
	}
	
	public boolean switchToEditor()  
	{
		waitForWindowByTitle(EditorPageElements.PAGE_TITLE);
		return switchToWindow(EditorPageElements.PAGE_TITLE);
	}

	public void openViewSubMenu()
	{
		openContextMenu(LineageContextMenuElements.view);
	}
}
