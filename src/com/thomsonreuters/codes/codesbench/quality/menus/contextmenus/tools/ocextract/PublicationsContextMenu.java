package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.ocextract;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.ocextract.PublicationsContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PublicationsContextMenu extends ContextMenu
{
    private final WebDriver driver;

    @Autowired
    public PublicationsContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, PublicationsContextMenuElements.class);
    }


    public void selectPublication()
    {
        click(PublicationsContextMenuElements.selectPublication);
        publicationFilesTablePage().waitForHiddenOverlay();
    }
}