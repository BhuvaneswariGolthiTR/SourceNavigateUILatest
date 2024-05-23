package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.popups.OnlineProductAssignmentsContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.ManageOnlineProductAssignmentsPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OnlineProductAssignmentsContextMenu extends ContextMenu
{
    private WebDriver driver;

    @Autowired
    public OnlineProductAssignmentsContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, OnlineProductAssignmentsContextMenuElements.class);
    }

    public boolean manageOnlineProductAssignment()
    {
        openContextMenu(OnlineProductAssignmentsContextMenuElements.MANAGE_ONLINE_PRODUCT_ASSIGNMENTS);
        boolean manageOnlineProductAssignmentsWindowAppeared = switchToWindow(ManageOnlineProductAssignmentsPageElements.PAGE_TITLE);
        enterTheInnerFrame();
        return manageOnlineProductAssignmentsWindowAppeared;
    }

}
