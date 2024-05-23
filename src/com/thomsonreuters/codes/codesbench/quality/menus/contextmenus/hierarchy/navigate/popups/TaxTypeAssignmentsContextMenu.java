package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.popups.TaxTypeAssignmentsContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.ManageTaxTypeAssignmentsPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TaxTypeAssignmentsContextMenu extends ContextMenu
{
    private WebDriver driver;

    @Autowired
    public TaxTypeAssignmentsContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, TaxTypeAssignmentsContextMenuElements.class);
    }

    public boolean manageTaxTypeAssignment()
    {
        openContextMenu(TaxTypeAssignmentsContextMenuElements.MANAGE_TAX_TYPE_ASSIGNMENTS);
        boolean manageTaxTypeAssignmentsWindowAppeared = switchToWindow(ManageTaxTypeAssignmentsPageElements.PAGE_TITLE);
        enterTheInnerFrame();
        return manageTaxTypeAssignmentsWindowAppeared;
    }

}
