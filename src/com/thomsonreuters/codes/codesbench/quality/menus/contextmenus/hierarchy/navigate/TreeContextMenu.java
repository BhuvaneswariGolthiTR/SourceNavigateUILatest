package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.TreeContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.DeleteNodePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.UndeleteNodePageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TreeContextMenu extends ContextMenu
{
    private WebDriver driver;

    @Autowired
    public TreeContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, TreeContextMenuElements.class);
    }

    public void expandPublishingWorkflows()
    {
        click(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);
        waitForElementExists(TreeContextMenuElements.BULK_PUBLISH_BY_HIERARCHY);
    }

    public void expandPublishingWorkflowsPublishingDisabled()
    {
        click(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);
        waitForElementExists(TreeContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
    }

    public void deleteFunctionsDelete()
    {
        openContextMenu(TreeContextMenuElements.DELETE_FUNCTIONS,TreeContextMenuElements.DELETE_FUNCTIONS_DELETE);
        switchToWindow(DeleteNodePageElements.DELETE_PAGE_TITLE);
        enterTheInnerFrame();
    }

    public void deleteFunctionsUndelete()
    {
        openContextMenu(TreeContextMenuElements.DELETE_FUNCTIONS,TreeContextMenuElements.DELETE_FUNCTIONS_UNDELETE);
        switchToWindow(UndeleteNodePageElements.UNDELETE_PAGE_TITLE);
        enterTheInnerFrame();
    }

    public void openQueriesSubmenu()
    {
        openContextMenuSubMenu(getElement(TreeContextMenuElements.QUERIES));
    }
}
