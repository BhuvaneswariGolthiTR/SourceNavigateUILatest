package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.popups.TargetLocatorContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TargetLocatorContextMenu extends ContextMenu
{
        WebDriver driver;

        @Autowired
        public TargetLocatorContextMenu(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, TargetLocatorContextMenuElements.class);
        }

        public void selectTargetLinkMarkup()
        {
                click(TargetLocatorContextMenuElements.SELECT_TARGET_FOR_LINK);
        }
}
