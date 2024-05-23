package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source.reports;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.reports.LockReportContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.Menu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LockReportContextMenu extends Menu
{
    private WebDriver driver;

    @Autowired
    public LockReportContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, LockReportContextMenuElements.class);
    }

    public boolean isViewContentEnabled()
    {
        return isElementEnabled(LockReportContextMenuElements.viewContent);
    }

    public boolean isUnlockEnabled()
    {
        return isElementEnabled(LockReportContextMenuElements.unlock);
    }

    public boolean isForceUnlockEnabled()
    {
        return isElementEnabled(LockReportContextMenuElements.forceUnlock);
    }

    public boolean isTransferUnlockEnabled()
    {
        return isElementEnabled(LockReportContextMenuElements.transferUnlock);
    }

    public boolean isTransferLockEnabled()
    {
        return isElementEnabled(LockReportContextMenuElements.transferLock);
    }

    public void clickViewContent()
    {
        sendEnterToElement(LockReportContextMenuElements.viewContent);
        editorPage().switchToEditor();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        editorPage().switchToEditorTextFrame();
    }

    public void clickUnlock()
    {
        sendEnterToElement(LockReportContextMenuElements.unlock);
        waitForGridRefresh();
        waitForPageLoaded();
    }

    public void clickForceUnlock()
    {
        sendEnterToElement(LockReportContextMenuElements.forceUnlock);
    }

    public void clickTransferUnlock()
    {
        sendEnterToElement(LockReportContextMenuElements.transferUnlock);
        waitForGridRefresh();
        waitForPageLoaded();
    }

    public void clickTransferLock()
    {
        sendEnterToElement(LockReportContextMenuElements.transferLock);
        waitForGridRefresh();
        waitForPageLoaded();
    }
}
