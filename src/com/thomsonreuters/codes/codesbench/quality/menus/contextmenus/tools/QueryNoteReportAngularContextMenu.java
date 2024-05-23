package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.QueryNoteReportAngularContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QueryNoteReportAngularContextMenu extends ContextMenu
{
    WebDriver driver;

    @Autowired
    public QueryNoteReportAngularContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, QueryNoteReportAngularContextMenuElements.class);
    }

    /**
     * Clicks the resolve query note option.
     */
    public boolean resolveQueryNote()
    {
        click(QueryNoteReportAngularContextMenuElements.resolveQueryNote);
        boolean isWindowOpened = switchToWindow(QueryNoteReportAngularResolvePageElements.QUERY_NOTE_RESOLVE_MODAL_HEADER);
        waitForElementGone(QueryNoteReportAngularExportPageElements.PAGE_LOADER);
        return isWindowOpened;
    }

    /**
     * Clicks the Delete query note option.
     */
    public boolean deleteQueryNote()
    {
        click(QueryNoteReportAngularContextMenuElements.deleteQueryNote);
        boolean isWindowOpened = switchToWindow(QueryNoteReportAngularDeletePageElements.QUERY_NOTE_DELETE_MODAL_HEADER);
        waitForElementGone(QueryNoteReportAngularExportPageElements.PAGE_LOADER);
        return isWindowOpened;
    }

    /**
     * Clicks the Edit query note option.
     */
    public boolean editQueryNote()
    {
        click(QueryNoteReportAngularContextMenuElements.editQueryNote);
        boolean isWindowOpened = switchToWindow(QueryNoteReportAngularEditPageElements.QUERY_NOTE_EDIT_MODAL_HEADER);
        waitForElementGone(QueryNoteReportAngularExportPageElements.PAGE_LOADER);
        return isWindowOpened;
    }

    /**
     * Clicks the Export to Excel option.
     */
    public boolean exportToExcel()
    {
        click(QueryNoteReportAngularContextMenuElements.exportToExcel);
        boolean isWindowOpened = switchToWindow(QueryNoteReportAngularExportPageElements.QUERY_NOTE_EXPORT_TO_EXCEL_MODAL_HEADER);
        waitForElementGone(QueryNoteReportAngularExportPageElements.PAGE_LOADER);
        return isWindowOpened;
    }

    /**
     * Clicks the Edit Action Dates option.
     */
    public boolean editActionDates()
    {
        click(QueryNoteReportAngularContextMenuElements.editActionDates);
        boolean isWindowOpened = switchToWindow(QueryNoteReportAngularEditActionDatesPageElements.QUERY_NOTE_EDIT_ACTION_DATES_MODAL_HEADER);
        waitForElementGone(QueryNoteReportAngularExportPageElements.PAGE_LOADER);
        return isWindowOpened;
    }

    /**
     * Clicks the Find Document in Hierarchy option.
     */
    public boolean findDocumentInHierarchy()
    {
        click(QueryNoteReportAngularContextMenuElements.findDocumentInHierarchy);
        return switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
