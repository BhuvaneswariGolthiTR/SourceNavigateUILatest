package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.editor;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorQueryContextMenuElementsPageElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.DeleteQueryNotePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.EditQueryNotePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ResolveQueryNotePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ViewQueryNotePageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditorQueryContextMenu extends ContextMenu
{
    private WebDriver driver;

    @Autowired
    public EditorQueryContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, EditorQueryContextMenuElementsPageElements.class);
    }

    public void view()
    {
       openContextMenu(EditorQueryContextMenuElementsPageElements.view);
       switchToWindow(ViewQueryNotePageElements.PAGE_TITLE);
    }

    public void resolveUnresolve()
    {
        openContextMenu(EditorQueryContextMenuElementsPageElements.resolveUnresolve);
        switchToWindow(ResolveQueryNotePageElements.PAGE_TITLE);
    }

    public void delete()
    {
        breakOutOfFrame();
        openContextMenu(EditorQueryContextMenuElementsPageElements.delete);
        switchToWindow(DeleteQueryNotePageElements.PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
    }

    public void edit()
    {
        openContextMenu(EditorQueryContextMenuElementsPageElements.edit);
        switchToWindow(EditQueryNotePageElements.PAGE_TITLE);
    }
}