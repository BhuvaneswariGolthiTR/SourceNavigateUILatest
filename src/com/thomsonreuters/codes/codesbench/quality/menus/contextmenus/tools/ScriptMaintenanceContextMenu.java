package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.ScriptMaintenanceContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScriptMaintenanceContextMenu extends ContextMenu
{
    private WebDriver driver;

    @Autowired
    public ScriptMaintenanceContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ScriptMaintenanceContextMenuElements.class);
    }

    public void copy()
    {
        sendEnterToElement(ScriptMaintenanceContextMenuElements.copy);
    }

    public void copyWithHeaders()
    {
        sendEnterToElement(ScriptMaintenanceContextMenuElements.copyWithHeaders);
    }

    public void paste()
    {
        sendEnterToElement(ScriptMaintenanceContextMenuElements.paste);
    }

    public void expandExport()
    {
        click(ScriptMaintenanceContextMenuElements.export);
    }

    public void exportCsvExport()
    {
        sendEnterToElement(ScriptMaintenanceContextMenuElements.exportCsvExport);
    }

    public void exportExcelXlsxExport()
    {
        click(ScriptMaintenanceContextMenuElements.exportExcelXlsxExport);
    }
}
