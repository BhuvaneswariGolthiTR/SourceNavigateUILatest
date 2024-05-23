package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.publishing.novus;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.novus.CitelineManagementContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CiteLineManagementContextMenu extends ContextMenu
{
    private WebDriver driver;

    @Autowired
    public CiteLineManagementContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CitelineManagementContextMenuElements.class);
    }

    public void copy()
    {
        click(CitelineManagementContextMenuElements.COPY);
    }

    public void copyWithHeaders()
    {
        click(CitelineManagementContextMenuElements.COPY_WITH_HEADERS);
    }
}