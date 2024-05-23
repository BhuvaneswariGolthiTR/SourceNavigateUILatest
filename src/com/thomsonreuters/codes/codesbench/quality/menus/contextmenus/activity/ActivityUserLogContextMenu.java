package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.activity;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.activity.ActivityUserLogContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.activity.EffectiveDatePageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ActivityUserLogContextMenu extends ContextMenu
{
	private WebDriver driver;

    public ActivityUserLogContextMenu(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
    }

    public void init()
    {
        PageFactory.initElements(driver, ActivityUserLogContextMenuElements.class);
    }
    
    public boolean checkInLegislative()
    {
    	ActivityUserLogContextMenuElements.checkInLegislative.click();
    	boolean effectiveDateWindowAppeared = switchToWindow(EffectiveDatePageElements.EFFECTIVE_DATE_PAGE_TITLE);
    	enterTheInnerFrame();
    	return effectiveDateWindowAppeared;
    }
}
