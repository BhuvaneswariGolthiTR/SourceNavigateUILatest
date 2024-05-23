package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.DeltaContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.SourceNavigateContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SourceNavigateContextMenu extends ContextMenu
{

    private WebDriver driver;

    @Autowired
    public SourceNavigateContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateContextMenuElements.class);
    }

    public boolean doesContextMenuElementExist(String element)
    {
        return doesElementExist(element);
    }

    public boolean doesEditTaxTypeAddExist()
    {
        openContextMenuSubMenu(getElement(SourceNavigateContextMenuElements.EDIT));
        return doesContextMenuElementExist(SourceNavigateContextMenuElements.TAX_TYPE_ADD);
    }

    public void goToModifyResetIntegrationStatus()
    {
        openContextMenu(DeltaContextMenuElements.modify, DeltaContextMenuElements.modifyResetIntegrationStatus);
        waitForPageLoaded();
    }

    public void viewRenditionBaselines()
    {
        openContextMenu(SourceNavigateContextMenuElements.VIEW,
                SourceNavigateContextMenuElements.RENDITION_BASELINES);
    }

    public void openRenditionProperties()
    {
        openContextMenu(SourceNavigateContextMenuElements.RENDITION_PROPERTIES);
    }
}
