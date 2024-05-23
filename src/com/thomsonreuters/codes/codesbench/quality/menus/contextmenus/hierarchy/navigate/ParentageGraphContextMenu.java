package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.ParentageGraphContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ParentageGraphContextMenu extends ContextMenu
{
    WebDriver driver;

    @Autowired
    public ParentageGraphContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ParentageGraphContextMenuElements.class);
    }

    public void refreshSelection()
    {
        openContextMenu(ParentageGraphContextMenuElements.refreshSelection);
        waitForPageLoaded();
    }

    public void openQueriesSubmenu()
    {
        openContextMenuSubMenu(getElement(ParentageGraphContextMenuElements.QUERIES));
    }
}
