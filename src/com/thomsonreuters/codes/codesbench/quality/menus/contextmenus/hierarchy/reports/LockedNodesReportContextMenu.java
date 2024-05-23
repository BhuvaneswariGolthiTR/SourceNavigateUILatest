package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.reports;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.reports.LockedNodesReportContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class LockedNodesReportContextMenu extends ContextMenu
{
    WebDriver driver;

    @Autowired
    public LockedNodesReportContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, LockedNodesReportContextMenuElements.class);
    }

    public boolean areGivenContextMenuOptionsDisplayed(List<String> usersContextMenuOptions)
    {
        List<String> valuesShown = getElementsTextList(LockedNodesReportContextMenuElements.LOCKED_NODES_REPORT_CONTEXT_MENU_ITEMS);

        if(valuesShown.size() == usersContextMenuOptions.size())
        {
            return valuesShown.containsAll(usersContextMenuOptions);
        }

        return false;
    }

    public void clickUnlock()
    {
        openContextMenu(LockedNodesReportContextMenuElements.unlock);
    }

    public void clickForceUnlock()
    {
        openContextMenu(LockedNodesReportContextMenuElements.forceUnlock);
    }

    public void clickViewInHierarchy()
    {
        openContextMenu(LockedNodesReportContextMenuElements.viewInHierarchy);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        maximizeCurrentWindow();
        waitForGridRefresh();
    }
}
