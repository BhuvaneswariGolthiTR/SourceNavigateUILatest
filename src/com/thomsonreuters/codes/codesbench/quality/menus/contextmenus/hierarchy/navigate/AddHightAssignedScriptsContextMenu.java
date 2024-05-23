package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.AddHighAssignedScriptsContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddHightAssignedScriptsContextMenu extends ContextMenu {
    private WebDriver driver;

    @Autowired
    public AddHightAssignedScriptsContextMenu(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, AddHighAssignedScriptsContextMenuElements.class);
    }

    public void assignHighSingle()
    {
        openContextMenu(AddHighAssignedScriptsContextMenuElements.assignHighSingle);
        switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }

    public void assignHighWithDescendants()
    {
        openContextMenu(AddHighAssignedScriptsContextMenuElements.assignHighWithDescendants);
        switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }
}
