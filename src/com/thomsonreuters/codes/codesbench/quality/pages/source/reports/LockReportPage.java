package com.thomsonreuters.codes.codesbench.quality.pages.source.reports;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.reports.LockReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LockReportPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public LockReportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, LockReportPageElements.class);
    }

    public boolean switchToLockReportPage()
    {
        boolean windowAppears = switchToWindow(LockReportPageElements.LOCK_REPORT_PAGE_TITLE);
        waitForGridRefresh();
        waitForPageLoaded();
        maximizeCurrentWindow();
        return windowAppears;
    }

    public void refreshLockReportGrid()
    {
        sendEnterToElement(LockReportPageElements.contentSetFilter);
        waitForGridRefresh();
        waitForPageLoaded();
    }

    public boolean lockedDocumentExists(String contentSet, String docName, String userName)
    {
        return doesElementExist(String.format(LockReportPageElements.LOCKED_DOCUMENT, contentSet, docName, userName));
    }

    public void setContentSetFilter(String contentSet)
    {
        waitForElement(LockReportPageElements.contentSetFilter);
        sendTextToTextBoxAuto(LockReportPageElements.contentSetFilter, contentSet);
        sendEnterToElement(LockReportPageElements.contentSetFilter);
        waitForGridRefresh();
        waitForPageLoaded();
        waitForGridRefresh();
    }

    public boolean viewContent(String contentSet, String docName, String userName)
    {
        click(String.format(LockReportPageElements.LOCKED_DOCUMENT, contentSet, docName, userName));
        rightClick(String.format(LockReportPageElements.LOCKED_DOCUMENT, contentSet, docName, userName));

        if(lockReportContextMenu().isViewContentEnabled())
        {
            lockReportContextMenu().clickViewContent();
            return true;
        }
        return false;
    }

    public boolean unlockDocument(String contentSet, String docName, String userName)
    {
        click(String.format(LockReportPageElements.LOCKED_DOCUMENT, contentSet, docName, userName));
        rightClick(String.format(LockReportPageElements.LOCKED_DOCUMENT, contentSet, docName, userName));

        if(lockReportContextMenu().isUnlockEnabled())
        {
            lockReportContextMenu().clickUnlock();
            return true;
        }
        return false;
    }

    public boolean forceUnlockDocument(String contentSet, String docName, String userName)
    {
        click(String.format(LockReportPageElements.LOCKED_DOCUMENT, contentSet, docName, userName));
        rightClick(String.format(LockReportPageElements.LOCKED_DOCUMENT, contentSet, docName, userName));

        if(lockReportContextMenu().isForceUnlockEnabled())
        {
            lockReportContextMenu().clickForceUnlock();
            return true;
        }
        return false;
    }

    public boolean transferUnlockDocument(String contentSet, String docName, String userName)
    {
        click(String.format(LockReportPageElements.LOCKED_DOCUMENT, contentSet, docName, userName));
        rightClick(String.format(LockReportPageElements.LOCKED_DOCUMENT, contentSet, docName, userName));

        if(lockReportContextMenu().isTransferUnlockEnabled())
        {
            lockReportContextMenu().clickTransferUnlock();
            return true;
        }
        return false;
    }

    public boolean transferLockDocument(String contentSet, String docName, String userName)
    {
        click(String.format(LockReportPageElements.LOCKED_DOCUMENT, contentSet, docName, userName));
        rightClick(String.format(LockReportPageElements.LOCKED_DOCUMENT, contentSet, docName, userName));

        if(lockReportContextMenu().isTransferLockEnabled())
        {
            lockReportContextMenu().clickTransferLock();
            return true;
        }
        return false;
    }

    public void unlockRendition(String contentSet, String docName, String user)
    {
        if(sourceNavigateGridPage().isFirstRenditionLocked())
        {
            sourceMenu().goToSourceC2012ReportsLockReport();
            lockReportPage().setContentSetFilter(contentSet);

            boolean lockedRenditionAppears = lockReportPage().lockedDocumentExists(contentSet, docName, user);
            Assertions.assertTrue(lockedRenditionAppears, "Specified report appears in Lock Report grid before unlocking");

            boolean unlockEnabled = lockReportPage().unlockDocument(contentSet, docName, user);
            Assertions.assertTrue(unlockEnabled, "Unlock is enabled for the specified rendition");

            boolean unlockedRenditionAppears = lockReportPage().lockedDocumentExists(contentSet, docName, user);
            Assertions.assertFalse(unlockedRenditionAppears, "Specified report appears in Lock Report grid after unlocking");

            closeCurrentWindowIgnoreDialogue();
        }
        sourcePage().switchToSourceNavigatePage();
    }

    public void forceUnlockRendition(String contentSet, String docName, String user)
    {
        if(sourceNavigateGridPage().isFirstRenditionLocked())
        {
            sourceMenu().goToSourceC2012ReportsLockReport();
            lockReportPage().setContentSetFilter(contentSet);

            boolean lockedRenditionAppears = lockReportPage().lockedDocumentExists(contentSet, docName, user);
            Assertions.assertTrue(lockedRenditionAppears, "Specified report appears in Lock Report grid");

            boolean forceUnlockEnabled = lockReportPage().forceUnlockDocument(contentSet, docName, user);
            Assertions.assertTrue(forceUnlockEnabled, "Force Unlock is enabled for the specified rendition");

            boolean unlockedRenditionAppears = lockReportPage().lockedDocumentExists(contentSet, docName, user);
            Assertions.assertFalse(unlockedRenditionAppears, "Specified report appears in Lock Report grid");

            closeCurrentWindowIgnoreDialogue();
        }
        sourcePage().switchToSourceNavigatePage();
    }
}
