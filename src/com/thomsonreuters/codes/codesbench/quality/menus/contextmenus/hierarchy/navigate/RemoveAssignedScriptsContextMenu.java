package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.RemoveAssignedScriptsContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RemoveAssignedScriptsContextMenu extends ContextMenu
{
    private WebDriver driver;

    @Autowired
    public RemoveAssignedScriptsContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RemoveAssignedScriptsContextMenuElements.class);
    }

    public void removeWithDescendants()
    {
        openContextMenu(RemoveAssignedScriptsContextMenuElements.removeWithDescendants);
        switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }

    public void removeSingle()
    {
        openContextMenu(RemoveAssignedScriptsContextMenuElements.removeSingle);
        switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }
}
