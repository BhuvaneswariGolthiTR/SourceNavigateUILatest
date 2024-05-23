package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.nod;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.nod.HeadnotesContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.blueline.BluelineAnalysisPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.blueline.DeleteBluelinePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.blueline.InsertBluelinePageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HeadnotesContextMenu extends ContextMenu
{
	WebDriver driver;

	@Autowired
	public HeadnotesContextMenu(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, HeadnotesContextMenuElements.class);
	}

	public boolean deleteBlueline()  
	{
		sendEnterToElement(HeadnotesContextMenuElements.deleteBlueline);
		boolean deleteBluelineWindowAppeared = switchToWindow(DeleteBluelinePageElements.DELETE_BLUELINE_PAGE_TITLE);
		enterTheInnerFrame();
		return deleteBluelineWindowAppeared;
	}
	
	public boolean insertBlueline()  
	{
		sendEnterToElement(HeadnotesContextMenuElements.insertBlueline);
		boolean insertBluelineWindowAppeared = switchToWindow(InsertBluelinePageElements.INSERT_BLUELINE_PAGE_TITLE);
		enterTheInnerFrame();
		return insertBluelineWindowAppeared;
	}
	
	public boolean bluelineAnalysisView()
	{
		sendEnterToElement(HeadnotesContextMenuElements.bluelineAnalysisView);
		boolean bluelineAnalysisWindowAppeared = switchToWindow(BluelineAnalysisPageElements.BLUELINE_ANALYSIS_PAGE_TITLE);
		enterTheInnerFrame();
		return bluelineAnalysisWindowAppeared;
	}

	public boolean findInHierarchy()
	{
		openContextMenu(HeadnotesContextMenuElements.findInHierarchy);
		boolean HierarchyNavigateWindowAppeared = switchToWindow(HierarchyPageElements.PAGE_TITLE);
		maximizeCurrentWindow();
		return HierarchyNavigateWindowAppeared;
	}
}
