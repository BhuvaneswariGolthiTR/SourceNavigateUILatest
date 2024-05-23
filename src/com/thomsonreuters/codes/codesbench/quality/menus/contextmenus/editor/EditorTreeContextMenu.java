package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.editor;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTreeContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DocumentPreviewPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditorTreeContextMenu extends ContextMenu
{
        private WebDriver driver;

        @Autowired
        public EditorTreeContextMenu(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, EditorTreeContextMenuElements.class);
        }

        public boolean preview()
        {
                click(EditorTreeContextMenuElements.PREVIEW);
                waitForPageLoaded();
                return switchToWindow(DocumentPreviewPageElements.PAGE_TITLE);
        }

        public void expandSixLevels()
        {
                click(EditorTreeContextMenuElements.EXPAND_SIX_LEVELS);
        }

        public void closeAll()
        {
                click(EditorTreeContextMenuElements.CLOSE_ALL);
        }

        public void copy()
        {
                click(EditorTreeContextMenuElements.COPY);
        }

        public void promote()
        {
                click(EditorTreeContextMenuElements.PROMOTE);
        }

        public void demote()
        {
                click(EditorTreeContextMenuElements.DEMOTE);
        }
}
