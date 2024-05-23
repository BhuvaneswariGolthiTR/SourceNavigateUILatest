package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.publishing.toolbox;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GridContextMenu extends ContextMenu
{
        private WebDriver driver;

        @Autowired
        public GridContextMenu(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, GridContextMenuElements.class);
        }

        public void readyStatus()
        {
                click(GridContextMenuElements.readyStatus);
                waitForPageLoaded();
        }

        public void approvedStatus()
        {
                click(GridContextMenuElements.approvedStatus);
                waitForPageLoaded();
        }

        public void notPublishedStatus()
        {
                click(GridContextMenuElements.notPublishedStatus);
                waitForPageLoaded();
        }

        public void removeApprovedStatus()
        {
                click(GridContextMenuElements.removeApprovedStatus);
        }

        public void selectForUpdatedStatus()
        {
                click(GridContextMenuElements.selectForUpdatedStatus);
                waitForPageLoaded();
        }

        public void selectForNotPublishedStatus()
        {
                click(GridContextMenuElements.notPublishedStatus);
                waitForPageLoaded();
        }

        public void findDocumentInWip()
        {
            click(GridContextMenuElements.findDocumentInWip);
            hierarchyNavigatePage().switchToHierarchyEditPage();
            maximizeCurrentWindow();
        }

        public void findDocumentInPub()
        {
                click(GridContextMenuElements.findDocumentInPub);
                hierarchyPubNavigatePage().switchToPubNavigatePage();
                maximizeCurrentWindow();
        }

        public boolean findDocumentInHierarchy()
        {
                click(GridContextMenuElements.findDocumentInHierarchy);
                waitForWindowByTitle(HierarchyPageElements.PAGE_TITLE);
                boolean windowAppeared = switchToWindow(HierarchyPageElements.PAGE_TITLE);
                maximizeCurrentWindow();
                DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
                return windowAppeared;
        }

        public boolean findPublishingWorkflow()
        {
                click(GridContextMenuElements.findPublishingWorkflow);
                boolean windowAppeared = switchToWindow(WorkflowDetailsPageElements.PAGE_TITLE);
                maximizeCurrentWindow();
                return windowAppeared;
        }

        public void massSelectionReadyStatusAllDocumentsInSameVolume()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_READY_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_IN_SAME_VOLUME_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionReadyStatusAllDocumentsWithSameCode()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_READY_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_WITH_SAME_CODE_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionReadyStatusAllDocumentsModifiedBySameUser()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_READY_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_MODIFIED_BY_SAME_USER_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionReadyStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_READY_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_THAT_INCLUDE_LAW_TRACKING_VALUE_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_THAT_INCLUDE_LAW_TRACKING_VALUE_QUICK_LOAD_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionRemoveReadyStatusAllDocumentsInSameVolume()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_REMOVE_READY_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_IN_SAME_VOLUME_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionRemoveReadyStatusAllDocumentsWithSameCode()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_REMOVE_READY_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_WITH_SAME_CODE_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionRemoveReadyStatusAllDocumentsModifiedBySameUser()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_REMOVE_READY_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_MODIFIED_BY_SAME_USER_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionRemoveReadyStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_REMOVE_READY_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_THAT_INCLUDE_LAW_TRACKING_VALUE_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_THAT_INCLUDE_LAW_TRACKING_VALUE_QUICK_LOAD_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionApprovedStatusAllDocumentsInSameVolume()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_APPROVED_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_IN_SAME_VOLUME_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionApprovedStatusAllDocumentsWithSameCode()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_APPROVED_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_WITH_SAME_CODE_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionApprovedStatusAllDocumentsModifiedBySameUser()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_APPROVED_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_MODIFIED_BY_SAME_USER_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionApprovedStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_APPROVED_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_THAT_INCLUDE_LAW_TRACKING_VALUE_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_THAT_INCLUDE_LAW_TRACKING_VALUE_QUICK_LOAD_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionRemoveApprovedStatusAllDocumentsInSameVolume()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_REMOVE_APPROVED_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_IN_SAME_VOLUME_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionRemoveApprovedStatusAllDocumentsWithSameCode()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_REMOVE_APPROVED_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_WITH_SAME_CODE_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionRemoveApprovedStatusAllDocumentsModifiedBySameUser()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_REMOVE_APPROVED_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_MODIFIED_BY_SAME_USER_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionRemoveApprovedStatusAllDocumentsThatIncludeLawTrackingValueQuickLoad()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_REMOVE_APPROVED_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_THAT_INCLUDE_LAW_TRACKING_VALUE_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_THAT_INCLUDE_LAW_TRACKING_VALUE_QUICK_LOAD_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionSelectForUpdatedStatusAllDocumentsInSameVolume()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_SELECT_FOR_UPDATED_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_IN_SAME_VOLUME_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionSelectForUpdatedStatusAllDocumentsWithSameCode()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_SELECT_FOR_UPDATED_STATUS_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_WITH_SAME_CODE_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionRemoveSelectionAllDocumentsInSameVolume()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_REMOVE_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_IN_SAME_VOLUME_XPATH);
                waitForGridRefresh();
        }

        public void massSelectionRemoveSelectionAllDocumentsWithSameCode()
        {
                click(GridContextMenuElements.MASS_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.MASS_SELECTION_REMOVE_SELECTION_XPATH);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                click(GridContextMenuElements.ALL_DOCUMENTS_WITH_SAME_CODE_XPATH);
                waitForGridRefresh();
        }

        @Deprecated
        public boolean isNotPublishedStatusDisplayed()
        {
                return isElementDisplayed(GridContextMenuElements.NOT_PUBLISHED_STATUS_XPATH);
        }

        @Deprecated
        public boolean isRemoveNotPublishedStatusDisplayed()
        {
                return isElementDisplayed(GridContextMenuElements.REMOVE_NOT_PUBLISHED_STATUS_XPATH);
        }

        @Deprecated
        public boolean isFindDocumentInPubDisplayed()
        {
                return isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_PUB_XPATH);
        }

        @Deprecated
        public boolean isFindDocumentInWipDisplayed()
        {
                return isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_WIP_XPATH);
        }

        @Deprecated
        public boolean isCopyDisplayed()
        {
                return isElementDisplayed(GridContextMenuElements.COPY_XPATH);
        }

        @Deprecated
        public boolean isCopyWithHeadersDisplayed()
        {
                return isElementDisplayed(GridContextMenuElements.COPY_WITH_HEADERS_XPATH);
        }

        @Deprecated
        public boolean isExportDisplayed()
        {
                return isElementDisplayed(GridContextMenuElements.EXPORT_XPATH);
        }
}
