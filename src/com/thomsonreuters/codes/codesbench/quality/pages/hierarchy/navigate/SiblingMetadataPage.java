package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.activity.ActivityUserLogElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements.SIBLING_METADATA_SELECTED_GRID_ROW_XPATH;

@Component
public class SiblingMetadataPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SiblingMetadataPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SiblingMetadataElements.class);
        PageFactory.initElements(driver, SetLawTrackingPageElements.class);
        PageFactory.initElements(driver, EditorPageElements.class);
        PageFactory.initElements(driver, SiblingMetadataContextMenuElements.class);
        PageFactory.initElements(driver, SourceNavigatePageElements.class);
        PageFactory.initElements(driver, HierarchyPageElements.class);
        PageFactory.initElements(driver, CommonPageElements.class);
    }

    public void selectNodes(String... values)
    {
        click(String.format(SiblingMetadataElements.SIBLING_METADATA_GRID_VALUECOLUMN_VALUE_XPATH, values[0]));
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            for (int i = 0; i < values.length; i++)
            {
                if(i == 0)
                {
                    continue;
                }
                click(String.format(SiblingMetadataElements.SIBLING_METADATA_GRID_VALUECOLUMN_VALUE_XPATH, values[i]));
            }
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
   }

    public void selectNodes(List<WebElement> nodes)
    {
        click(nodes.get(0));
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            for (int i = 1; i < nodes.size(); i++)
            {
                click(nodes.get(i));
            }
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public boolean isSelectedNodeStatusNotPublished()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_WIP_NOT_PUBLISHED");
    }

    public void setNotPublishedStatusForSelectedNode()
    {
        if (!siblingMetadataPage().isSelectedNodeStatusNotPublished())
        {
            openContextMenuForElement(SiblingMetadataElements.SELECTED_NODE_ROW,
                    SiblingMetadataContextMenuElements.PUBLISHING_STATUS,
                    SiblingMetadataContextMenuElements.PUBLISHING_STATUS_NOT_PUBLISHED);
            if(TestSetupEdge.getBrowserTag().equals(CustomAnnotations.BrowserAnnotations.EDGE))
            {
                checkAlertTextMatchesGivenText("Publishing status was updated.");
            }
            else
            {
                AutoITUtils.verifyAlertTextAndAccept(true, "Publishing status was updated.");
            }
            switchToWindow(HierarchyPageElements.PAGE_TITLE);
            waitForGridRefresh();
        }
    }

    public String getSelectedNodeValue()
    {
        return getElementsText(SiblingMetadataElements.SELECTED_NODE_VALUE);
    }

    public String getSelectedNodeVols()
    {
        return getElementsText(SiblingMetadataElements.SELECTED_NODE_VOLS);
    }

    public String getSelectedNodeHID()
    {
        return getElementsText(SiblingMetadataElements.SELECTED_NODE_HID);
    }

    public String getSelectedNodeStartDate()
    {
        return getElementsText(SiblingMetadataElements.SELECTED_NODE_START_DATE);
    }

    public String getSelectedNodeTaxType()
    {
        return getElementsText(SiblingMetadataElements.SELECTED_NODE_TAX_TYPE);
    }

    public String getSelectedNodeProductTag()
    {
        return getElementsText(SiblingMetadataElements.SELECTED_NODE_PRODUCT_TAG);
    }

    public String getSelectedNodeModifiedDateMMDDYYYY()
    {
        return getElementsText(SiblingMetadataElements.SELECTED_NODE_MODIFIED_DATE_XPATH).substring(0, 10);
    }

    public String getSelectedNodeEndDate()
    {
        return getElementsText(SiblingMetadataElements.SELECTED_NODE_END_DATE);
    }

   public boolean checkSelectedNodesEndDateEqualsNoDate()
   {
      return doesElementExist(String.format(SiblingMetadataElements.SIBLING_METADATA_GRID_END_DATE_XPATH,"no date"));
   }

    public String getYesterdaysDateWithoutLeadingZeros()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/YYYY");
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return sdf.format(cal.getTime());
    }

    public boolean checkOutSelectedNode()
    {
        click(getElement(SiblingMetadataElements.SELECTED_NODE_ROW));
        rightClick(getElement(SiblingMetadataElements.SELECTED_NODE_ROW));
        sendEnterToElement(HierarchyContextMenuPageElements.CHECK_OUT);
        return switchToWindow(ActivityUserLogElements.ACTIVITY_USER_LOG_PAGE_TITLE);
    }

    
    public void selectedSiblingMetadataEditContent()
    {
        click(SiblingMetadataElements.SELECTED_NODE_ROW);
        rightClick(SiblingMetadataElements.SELECTED_NODE_ROW);
        siblingMetadataContextMenu().openContextMenu(SiblingMetadataContextMenuElements.editContent);
        switchToWindow(EditorPageElements.PAGE_TITLE);
        waitForPageLoaded();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        waitForElement(EditorToolbarPageElements.CLOSE_DOC);
    }

    public void selectedSiblingMetadataViewContent()
    {
        click(SiblingMetadataElements.SELECTED_NODE_ROW);
        rightClick(SiblingMetadataElements.SELECTED_NODE_ROW);
        siblingMetadataContextMenu().openContextMenu(SiblingMetadataContextMenuElements.viewContent);
        switchToWindow(EditorPageElements.PAGE_TITLE);
        waitForPageLoaded();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
    }

    public void rightClickSelectedNode()
    {
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        click(SiblingMetadataElements.SELECTED_NODE_ROW);
        rightClick(SiblingMetadataElements.SELECTED_NODE_ROW);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
    }

    public void setStatusTo(String status)
    {
        String actualStatus = HierarchyPageElements.SIBLING_METADATA_SELECTED_GRID_ROW_STAT;
        String statusXpath = HierarchyPageElements.METADATA_STATUS + "//option[text()='%s']";
        if (!actualStatus.equals(status))
        {
            // [Live, DLU Delete, Reserve, Transfer, Repeal]
            updateSelectedSiblingMetadata();
            click(String.format(statusXpath, status));
            quickLoadOk();
        }
    }

    public void updateSelectedSiblingMetadata()
    {
        rightClickSelectedSiblingMetadata();
        updateSiblingMetadata();
    }

    
    public void rightClickSelectedSiblingMetadata()
    {
        click(HierarchyPageElements.selectedNodesInSiblingMetadataGrid);
        rightClick(HierarchyPageElements.selectedNodesInSiblingMetadataGrid);
        waitForPageLoaded();
    }

    public void selectFirstFewNodes(int number)
    {
        int size = getElements(SiblingMetadataElements.SIBLING_METADATA_GRID_ROWS).size();
        if(!(number>size))
        {
            try
            {
                RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
                if(doesElementExist(SiblingMetadataElements.SELECTED_NODE_ROW))
                {
                    click(SiblingMetadataElements.SELECTED_NODE_ROW);
                }
                while (number>0)
                {
                    click(String.format(SiblingMetadataElements.SIBLING_METADATA_GRID_ROW, number));
                    number = number-1;
                }
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            }
            finally
            {
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            }
        }
    }

    public void rightClickSiblingMetadata()
    {
        rightClick(HierarchyPageElements.selectedNodesInSiblingMetadataGrid);
    }

    private void updateSiblingMetadata()
    {
        sendEnterToElement(HierarchyContextMenuPageElements.UPDATE_METADATA);
        waitForPageLoaded();
        switchToWindow("Update Metadata");
        waitForPageLoaded();
        switchToInnerIFrameByIndex(0);
    }

    public void quickLoadOk()
    {
        enterTheInnerFrame();
        click(SetLawTrackingPageElements.quickLoadTrackingButtonXpath);
        waitForPageLoaded();
        click(CommonPageElements.OK_BUTTON);
        waitForGridRefresh();
        switchToWindow("Hierarchy Edit");
    }

    public boolean isSelectedRowStatusRepeal()
    {
        //return "Repeal".equals(HierarchyPageElements.SIBLING_METADATA_SELECTED_GRID_ROW_STAT);
        return getElementsText(HierarchyPageElements.SIBLING_METADATA_SELECTED_GRID_ROW_STAT).equals("Repeal");
    }

    public boolean isSelectedRowStatusTransfer()
    {
        return getElementsText(HierarchyPageElements.SIBLING_METADATA_SELECTED_GRID_ROW_STAT).equals("Transfer");
    }

    public String getSelectedNodeStatus()
    {
        return getElementsText(HierarchyPageElements.SIBLING_METADATA_SELECTED_GRID_ROW_STAT);
    }

    public String getSelectedGridRowKeyword()
    {
        return getElementsText(HierarchyPageElements.siblingMetadataSelectedGridRowKeyword);
    }

    public String getSelectedGridRowValue()
    {
        return getElementsText(HierarchyPageElements.siblingMetadataSelectedGridRowValueXpath);
    }

    public String getSelectedGridRowVolumes()
    {
        return getElementsText(HierarchyPageElements.siblingMetadataSelectedGridRowVols);
    }

    public String getSelectedGridRowHID()
    {
        return getElementsText(HierarchyPageElements.siblingMetadataSelectedGridRowHid);
    }

    public void setSelectedRowSatusToLive()
    {
        setStatusTo("Live");
    }

    public boolean isSelectedRowStatusLive()
    {
        return getElementsText(HierarchyPageElements.SIBLING_METADATA_SELECTED_GRID_ROW_STAT).equals("Live");
    }

    public String getValidationFlagOfSelectedNode()
    {
        String selectedNodeSrcValue = getElementsAttribute(SiblingMetadataElements.selectedNodeRowFlag, "src");
        return selectedNodeSrcValue.substring(selectedNodeSrcValue.lastIndexOf('/') + 1, selectedNodeSrcValue.lastIndexOf('.'));
    }

    public void editSelectedSiblingMetadataOld()
    {
        click(SIBLING_METADATA_SELECTED_GRID_ROW_XPATH);
        rightClick(SIBLING_METADATA_SELECTED_GRID_ROW_XPATH);
        openContextMenu(HierarchyContextMenuPageElements.EDIT_CONTENT_OLD);
        waitForPageLoaded();
        editorPage().switchToEditor();
    }

    public boolean isOnlineProductAssigmentMenuDisplayed()
    {
        return isElementDisplayed(HierarchyContextMenuPageElements.ONLINE_PRODUCT_ASSIGNMENTS);
    }

    public boolean isMangaeglossaryTermsDisplayed()
    {
        return isElementDisplayed(HierarchyContextMenuPageElements.MANAGE_GLOSSARY_TERMS);
    }

    public boolean isPublishingWorkflowsDisplayed()
    {
        return isElementDisplayed(HierarchyContextMenuPageElements.PUBLISHING_WORKFLOWS);
    }

    public void getPublishingWorkflows()
    {
        getElement(HierarchyContextMenuPageElements.PUBLISHING_WORKFLOWS).sendKeys(Keys.ARROW_RIGHT);
    }

    public boolean isPublishingWorkflowsBulkPublishByHierarchyDisplayed()
    {
        return isElementDisplayed(HierarchyContextMenuPageElements.PUBLISHING_WORKFLOWS_BULK_PUBLISH_BY_HIERARCHY);
    }

    public boolean isPublishingWorkflowsBulkPublishByVolumeDisplayed()
    {
        return isElementDisplayed(HierarchyContextMenuPageElements.PUBLISHING_WORKFLOWS_BULK_PUBLISH_BY_VOLUME);
    }

    public boolean isSelectedNodeReadyStatus()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_WIP_PUBLISH_READY");
    }

    public boolean isSelectedNodeApprovedStatus()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_WIP_PUBLISH_APPROVED");
    }

    public boolean isSelectedNodeWestlawLoadedStatus()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_WIP_LOADED_TO_WESTLAW");
    }

    public boolean isSelectedNodeWestlawLoadedStatusInPub()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_PUB_LOADED_TO_WESTLAW");
    }

    public boolean isSelectedNodeCompleteStatus()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_WIP_PUBLISH_COMPLETE");
    }

    public boolean isSelectedNodePublishedToPubInHierarchyPubNavigate()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_PUB_PUBLISHED_TO_PUB");
    }

    public boolean isSelectedNodePublishedToPubInHierarchyNavigate()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_WIP_PUBLISHED_TO_PUB");
    }

    public boolean isSelectedNodeHoldNodeStatus()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_PUB_HOLD_NODE");
    }

    public boolean isSelectedNodeLtcFailureStatus()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_PUB_LTC_FAILURE");
    }

    public boolean isSelectedNodeCodesbenchFailureStatus()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_PUB_CODESBENCH_FAILURE");
    }

    public boolean isSelectedNodePublishingStatus()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_PUB_PUBLISHING");
    }

    public boolean isSelectedNodeErrorStatus()
    {
        return getElementsAttribute(
                SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains("Status_WIP_UNDEFINED");
    }

    public boolean areSelectedNodesNotPublished()
    {
        for (WebElement node : SiblingMetadataElements.selectedNodesPublishingStatuses)
        {
            if (!getElementsAttribute(node, "src").contains("Status_WIP_NOT_PUBLISHED"))
            {
                return false;
            }
        }
        return true;
    }

    public boolean areSelectedNodesPublishReady()
    {
        for (WebElement node : SiblingMetadataElements.selectedNodesPublishingStatuses)
        {
            if (!getElementsAttribute(node, "src").contains("Status_WIP_PUBLISH_READY"))
            {
                return false;
            }
        }
        return true;
    }

    public void selectNodesByValue(String value)
    {
        List<WebElement> listOfNodesByValue = getElements(String.format(SiblingMetadataElements.SIBLING_METADATA_GRID_VALUECOLUMN_VALUE_XPATH, value));
        selectNodes(listOfNodesByValue);
    }

    public boolean openPublishingWorkflowsSubmenu()
    {
        return isElementDisplayed(HierarchyContextMenuPageElements.ONLINE_PRODUCT_ASSIGNMENTS);
    }

    public void selectNodeByValueAndStartDate(String value, String date)
    {
        click(String.format(SiblingMetadataElements.NODE_BY_VALUE_AND_START_DATE, value, date));
    }

    public void selectNodeByHid(String hid)
    {
        click(String.format(SiblingMetadataElements.NODE_BY_HID, hid));
    }

    public void selectDeletedNodeByValue(String value)
    {
        click(String.format(SiblingMetadataElements.DELETED_NODE_BY_VALUE_XPATH, value));
    }

    public void selectDeletedNodeByValueAndModifiedDate(String value, String modifiedDate)
    {
        click(String.format(SiblingMetadataElements.DELETED_NODE_BY_VALUE_AND_MODIFIED_DATE_XPATH, value, modifiedDate));
    }

    public void selectSelectedNodesNextNode()
    {
        click(getElementsNextSibling(SiblingMetadataElements.selectedNodeRow));
    }

    public void selectAllNodes()
    {
        try
        {
            int size = getElements(SiblingMetadataElements.SIBLING_METADATA_GRID_ROWS).size();
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            if(doesElementExist(SiblingMetadataElements.SELECTED_NODE_ROW))
            {
                click(SiblingMetadataElements.SELECTED_NODE_ROW);
            }
            while (size>0)
            {
                click(String.format(SiblingMetadataElements.SIBLING_METADATA_GRID_ROW, size));
                size = size-1;
            }
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public void selectFirstNode()
    {
        click(String.format(SiblingMetadataElements.SIBLING_METADATA_GRID_ROW, 0));
    }

    public void selectNodeByRow(String number)
    {
        click(String.format(SiblingMetadataElements.SIBLING_METADATA_GRID_ROW_BY_STRING, number));
    }

    public boolean isNodeDisplayedWithValue(String nodeValue)
    {
        return doesElementExist(String.format(SiblingMetadataElements.SIBLING_METADATA_GRID_VALUECOLUMN_VALUE_XPATH, nodeValue));
    }

    public void rightClickMultipleSelectedSiblingMetadata()
    {
       rightClick(HierarchyPageElements.selectedNodesInSiblingMetadataGrid);
       waitForPageLoaded();
    }

    public String getNodeValueBelowSelectedNode()
    {
        return getElementsText(SiblingMetadataElements.NODE_BELOW_SELECTED_NODE_ROW_VALUE);
    }

    public String getNodeProductTagBelowSelectedNode()
    {
        return getElementsText(SiblingMetadataElements.NODE_BELOW_SELECTED_NODE_ROW_PRODUCT_TAG);
    }

    public String getNodeTaxTypeBelowSelectedNode()
    {
        return getElementsText(SiblingMetadataElements.NODE_BELOW_SELECTED_NODE_ROW_TAX_TYPE);
    }

    public String getNodeVolsBelowSelectedNode()
    {
        return getElementsText(SiblingMetadataElements.NODE_BELOW_SELECTED_NODE_ROW_VOLS);
    }

    public String getNodeHIDBelowSelectedNode()
    {
        return getElementsText(SiblingMetadataElements.NODE_BELOW_SELECTED_NODE_ROW_HID);
    }

    public boolean isSelectedNodeNotPublished()
    {
        return getElementsAttribute(SiblingMetadataElements.SELECTED_NODE_PUBLISHING_STATUS, "src").contains("Status_WIP_NOT_PUBLISHED");
    }

    public boolean isNodeBelowSelectedNodeNotPublished()
    {
        return getElementsAttribute(SiblingMetadataElements.NODE_BELOW_SELECTED_NODE_ROW_PUBLISHING_STATUS, "src").contains("Status_WIP_NOT_PUBLISHED");
    }

    public List<String> getAllNodeValuesInSiblingMetadataGrid()
    {
      return getElements(SiblingMetadataElements.SIBLING_METADATA_GRID).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<WebElement> getListOfNodesWithGivenValue(String nodeValue)
    {
       waitForGridRefresh();
       return getElements(String.format(SiblingMetadataElements.SIBLING_METADATA_GRID_VALUECOLUMN_VALUE_XPATH, nodeValue));
    }

    public boolean isSelectedNodeInARange()
    {
        return doesElementExist(String.format(SiblingMetadataElements.RM_SYMBOL_OF_SELECTED_NODE, "range.gif"));
    }

    public boolean isSelectedNodeTheStartOfARange()
    {
        return doesElementExist(String.format(SiblingMetadataElements.RM_SYMBOL_OF_SELECTED_NODE, "rangeStart.gif"));
    }

   public boolean isSelectedRowStatusReserve()
   {
      return getElementsText(HierarchyPageElements.SIBLING_METADATA_SELECTED_GRID_ROW_STAT).equals("Reserve");
   }

   public boolean isSelectedNodeVolsNumberEqualTo(String expectedVolsNumber)
   {
      return getElementsText(SiblingMetadataElements.SELECTED_NODE_VOLS_NUMBER_XPATH).trim().equals(expectedVolsNumber);
   }

   public String getNodeKeywordBelowSelectedNode()
   {
       return getElementsText(SiblingMetadataElements.NODE_BELOW_SELECTED_NODE_ROW_KEYWORD);
   }

    public boolean isTaxTypeColumnDisplayed()
    {
        if (doesElementExist(SiblingMetadataElements.TAX_TYPE_COLUMN_XPATH))
        {
            return isElementDisplayed(SiblingMetadataElements.TAX_TYPE_COLUMN_XPATH);
        }
        return false;
    }

   public String getHidOfSelectedNode()
   {
        return getElementsText(SiblingMetadataElements.selectedNodeHID);
   }

   public String getEndDateOfSelectedNode()
   {
       return getElementsText(SiblingMetadataElements.SELECTED_NODE_END_DATE);
   }

   public boolean doesSelectedNodeHaveWarningValidationFlag()
   {
       return isElementDisplayed(String.format(SiblingMetadataElements.VALIDATION_FLAG_OF_SELECTED_NODE, "warn"));
   }

   public boolean doesSelectedNodeHaveGreenCheckValidationFlag()
   {
       return isElementDisplayed(String.format(SiblingMetadataElements.VALIDATION_FLAG_OF_SELECTED_NODE, "check"));
   }

    public boolean doesSelectedNodeHaveErrorValidationFlag()
    {
        return isElementDisplayed(String.format(SiblingMetadataElements.VALIDATION_FLAG_OF_SELECTED_NODE, "error"));
    }

    public boolean doesSelectedNodeHaveInfoValidationFlag()
    {
        return isElementDisplayed(String.format(SiblingMetadataElements.VALIDATION_FLAG_OF_SELECTED_NODE, "info"));
    }

   public boolean areNodesByValuesXPublishingStatus(String[] values, String status)
   {
       if(values.length <= 0)
       {
           return false;
       }
       boolean nodesXStatus = true;
       for(String s: values)
       {
           WebElement row = getElement(String.format(SiblingMetadataElements.SIBLING_METADATA_GRID_VALUE_PUBLISHING_STATUS, s));
           boolean isNodeWithSValueXStatus = getElementsAttribute(row, "src").contains(status);
           if(!isNodeWithSValueXStatus)
           {
               nodesXStatus = false;
           }
       }
       return nodesXStatus;
   }

   public boolean areNodesByValuesNotPublished(String... values)
   {
       return areNodesByValuesXPublishingStatus(values, "Status_WIP_NOT_PUBLISHED");
   }

   public boolean areNodesByValuesReadyPublishingStatus(String... values)
   {
       return areNodesByValuesXPublishingStatus(values, "Status_WIP_PUBLISH_READY");
   }

   public boolean areNodesByValuesWestlawLoaded(String... values)
   {
       return areNodesByValuesXPublishingStatus(values, "Status_WIP_LOADED_TO_WESTLAW");
   }

    public boolean doesSelectedNodeHaveAPublishingStatus()
    {
        return isElementDisplayed(SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS);
    }

    public boolean isSelectedNodeSetToGivenPublishingStatus(String publishingStatus)
    {
        return getElementsAttribute(SiblingMetadataElements.SELECTED_NODE_ROW_PUBLISH_STATUS, "src").contains(publishingStatus);
    }

    public boolean isSelectedNodeDeleted()
    {
        return getValidationFlagOfSelectedNode().equals("delete");
    }

    public void deleteSelectedSiblingMetadata()
    {
        if (!getValidationFlagOfSelectedNode().equals("delete"))
        {
            click(SiblingMetadataElements.selectedNodeRow);
            rightClick(SiblingMetadataElements.selectedNodeRow);
            openContextMenu(SiblingMetadataContextMenuElements.deleteFunctions, SiblingMetadataContextMenuElements.deleteFunctionsDelete);
            switchToWindow("Delete"); // TODO look at undeleteSiblingMetadata TODOs
            waitForPageLoaded();
            enterTheInnerFrame();
            setLawTrackingPage().clickQuickLoadTrackingButton();
            click(CommonPageElements.OK_BUTTON);
            //checkAlert();
            switchToWindow(HierarchyPageElements.PAGE_TITLE);
        }
    }

    public void undeleteSiblingMetadata()
    {
        if (getValidationFlagOfSelectedNode().equals("delete"))
        {
            click(SiblingMetadataElements.selectedNodeRow);
            rightClick(SiblingMetadataElements.selectedNodeRow);
            openContextMenu(SiblingMetadataContextMenuElements.deleteFunctions, SiblingMetadataContextMenuElements.deleteFunctionsUndelete);
            setLawTrackingPage().clickQuickLoadTrackingButton();
            setLawTrackingPage().clickOkButton();
            switchToWindow(HierarchyPageElements.PAGE_TITLE);
        }
    }

    public void editRawXmlSelectedSiblingMetadata()
    {
        click(SIBLING_METADATA_SELECTED_GRID_ROW_XPATH);
        rightClick(SIBLING_METADATA_SELECTED_GRID_ROW_XPATH);
        openContextMenu(HierarchyContextMenuElements.EDIT_RAW_XML);
        waitForPageLoaded();
    }

    public String getSelectedNodeUuid()
    {
        return getElement(SiblingMetadataElements.NODE_UUID).getAttribute("innerHTML");
    }
}
