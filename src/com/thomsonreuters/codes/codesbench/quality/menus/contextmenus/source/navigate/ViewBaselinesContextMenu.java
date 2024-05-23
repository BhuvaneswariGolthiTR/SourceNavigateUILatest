package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.ViewBaselinesContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.ViewBaselinesPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ViewBaselinesContextMenu extends ContextMenu
{
    private WebDriver driver;

    @Autowired
    public ViewBaselinesContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ViewBaselinesContextMenuElements.class);
    }

    public void restoreBaseline()
    {
        click(ViewBaselinesContextMenuElements.restoreBaseline);
        checkAlert();
        waitForPageLoaded();
        waitForGridRefresh();
    }

    public boolean clickViewBaseline(Boolean expectedEnabled, Boolean closeWindow)
    {
        boolean expectedWindowAppeared;
        click(ViewBaselinesContextMenuElements.viewBaseline);

        if(!expectedEnabled)
        {
            expectedWindowAppeared = switchToWindow(ViewBaselinesPageElements.PAGE_TITLE);
        }
        else
        {
            expectedWindowAppeared = editorPage().switchToEditorWindow();
            waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
            editorPage().switchToEditorTextFrame();
        }

        sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
        return expectedWindowAppeared;
    }
}
