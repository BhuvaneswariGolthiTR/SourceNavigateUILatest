package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceNavigateGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.switchEnums.DocumentLevels;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SourceNavigateGridPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SourceNavigateGridPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateGridPageElements.class);
    }

    private void clickFirstRow()
    {
        click(SourceNavigateGridPageElements.FIRST_ROW);
    }

    private void rightClickFirstRow()
    {
        clickFirstRow();
        rightClick(SourceNavigateGridPageElements.FIRST_ROW);
    }

    public void onlyRightClickFirstItem()
    {
        rightClick(SourceNavigateGridPageElements.FIRST_ROW);
    }

    public void clickFirstRendition()
    {
        clickFirstRow();
    }

    public void rightClickFirstRendition()
    {
        rightClickFirstRow();
    }

    public void clickFirstSection()
    {
        clickFirstRow();
    }

    public void rightClickFirstSection()
    {
        rightClickFirstRow();
    }

    public void clickFirstDelta()
    {
        clickFirstRow();
    }

    public void rightClickFirstDelta()
    {
        rightClickFirstRow();
    }

    public String getFirstItemTaxTypeAddValue()
    {
        return getElementsText(SourceNavigateGridPageElements.FIRST_ITEM_TAX_TYPE_ADD);
    }

    public void selectAllRows()
    {
        try
        {
            int size = getElements(SourceNavigateGridPageElements.ROWS).size();
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            while (size>0)
            {
                click(String.format(SourceNavigateGridPageElements.ROW, size));
                size = size-1;
            }
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public int getRowsNumber()
    {
        return getElements(SourceNavigateGridPageElements.ROWS).size();
    }

    public void rightClickFirstItem()
    {
        waitForElement(SourceNavigateGridPageElements.FIRST_ROW);
        rightClickFirstRow();
    }

    public void clickFirstItem()
    {
        waitForElement(SourceNavigateGridPageElements.FIRST_ROW);
        clickFirstRow();
    }

    public void clickRowByIndex(int index)
    {
        waitForElement(String.format(SourceNavigateGridPageElements.ROW, index));
        click(String.format(SourceNavigateGridPageElements.ROW, index));
    }

    public void rightClickRowByIndex(int index)
    {
        waitForElement(String.format(SourceNavigateGridPageElements.ROW, index));
        click(String.format(SourceNavigateGridPageElements.ROW, index));
        rightClick(String.format(SourceNavigateGridPageElements.ROW, index));
    }

    public void clearFilters()
    {
        getElement(SourceNavigatePageElements.CLEAR_BUTTON_XPATH).click();
        getElement(SourceNavigatePageElements.CLEAR_DROPDOWN_CLEAR_FILTERS).click();
        waitForGridRefresh();
        waitForPageLoaded();
        waitForGridRefresh();
    }

    public void firstRenditionEditContent()
    {
        clickFirstItem();
        rightClickFirstRendition();
        renditionContextMenu().editRendition();
        switchToWindow(EditorPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        waitForPageLoaded();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        waitForElement(EditorToolbarPageElements.CLOSE_DOC);
    }

    public void sectionEditContentByIndex(int index)
    {
        clickRowByIndex(index);
        rightClickRowByIndex(index);
        sectionContextMenu().editSection();
        switchToWindow(EditorPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        waitForPageLoaded();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
    }

    public void deltaEditContentByIndex(int index)
    {
        clickRowByIndex(index);
        rightClickRowByIndex(index);
        deltaContextMenu().editDelta();
        switchToWindow(EditorPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        waitForPageLoaded();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
    }

    public boolean isFirstRenditionLockedByYou()
    {
        return doesElementExist(SourceNavigatePageElements.LOCK_IMG_FIRST_REND_XPATH);
    }

    public boolean isFirstRenditionLocked()
    {
        return doesElementExist(SourceNavigatePageElements.LOCK_IMG_FIRST_REND_XPATH);
    }

    public void firstRenditionViewRendition()
    {
        rightClickFirstRendition();
        getElement(RenditionContextMenuElements.VIEW).sendKeys(Keys.ARROW_RIGHT);
        waitForElement(RenditionContextMenuElements.RENDITION);
        sendKeys(Keys.ARROW_DOWN);
        sendKeys(Keys.ENTER);
        switchToWindow(EditorPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        waitForPageLoaded();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
    }

    public void firstSectionEditContent()
    {
        rightClickFirstRendition();
        sectionContextMenu().editSection();
        switchToWindow(EditorPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        waitForPageLoaded();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
    }

    public void sectionGroupEditContent()
    {
        sectionGroupContextMenu().editSectionGroup();
        switchToWindow(EditorPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        waitForPageLoaded();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
    }

    //Delta Grid Page

    public boolean isDeltaWithLocationIntegrationStatusExist(String status)
    {
        return doesElementExist(String.format(SourceNavigateGridPageElements.ITEM_BY_LOCATION_INTEGRATION_STATUS, status));
    }

    public void selectFirstDeltaWithLocationIntegrationStatus(String status)
    {
        if (doesElementExist(String.format(SourceNavigateGridPageElements.ITEM_BY_LOCATION_INTEGRATION_STATUS, status)))
        {
            click(String.format(SourceNavigateGridPageElements.ITEM_BY_LOCATION_INTEGRATION_STATUS, status));
            rightClick(String.format(SourceNavigateGridPageElements.ITEM_BY_LOCATION_INTEGRATION_STATUS, status));
        }
    }

    public String getFirstDeltaColorWithLocationIntegrationStatus(String status)
    {
        return getBackgroundColor(String.format(SourceNavigateGridPageElements.ITEM_BY_LOCATION_INTEGRATION_STATUS, status));
    }

    public void switchToDeltaGridPage()
    {
        switchToWindow(SourceNavigatePageElements.DELTA_NAVIGATE_PAGE_TITLE);
    }

    public int getDeltaCountOfFirstItem()
    {
        return Integer.parseInt(getElementsText(SourceNavigateGridPageElements.FIRST_ROW_DELTA_COUNT));
    }

    public List<String> getTaxTypeAddColumnValues()
    {
        List<String> taxTypeAddColumnValues = new ArrayList<>();
        for (WebElement row: SourceNavigateGridPageElements.taxTypeAddColumnValue)
        {
            taxTypeAddColumnValues.add(row.getText());
        }
        return taxTypeAddColumnValues;
    }

    public String getItemLocationIntegrationStatusByIndexRow(int index)
    {
        return getElementsText(String.format(SourceNavigateGridPageElements.ITEM_LOCATION_INTEGRATION_STATUS,index));
    }

    public String getItemTargetStatusMessageByIndexRow(int index)
    {
        return getElementsText(String.format(SourceNavigateGridPageElements.ITEM_TARGET_STATUS_MESSAGE,index));
    }

    public List<String> getTaxTypeAddColumnValuesInRange(int firstDelta, int lastDelta)
    {
        List<String> taxTypeAddColumnValues = new ArrayList<>();
        for (int i = firstDelta - 1; i < lastDelta; i++)
        {
            taxTypeAddColumnValues.add(SourceNavigateGridPageElements.taxTypeAddColumnValue.get(i).getText());
        }
        return taxTypeAddColumnValues;
    }

    public String getTaxTypeAddColumnValuesByIndexRow(int index)
    {
        return SourceNavigateGridPageElements.taxTypeAddColumnValue.get(index).getText();
    }

    public int getNumberOfTaxTypeDeltas()
    {
        return SourceNavigateGridPageElements.taxTypeAddColumnValue.size();
    }

    //Delta Group Grid Page
    public void clickItemByDeltaGroupName(String name)
    {
        click(String.format(SourceNavigateGridPageElements.ITEM_BY_DELTA_GROUP_NAME, name));
    }

    public void rightClickItemByDeltaGroupName(String name)
    {
        rightClick(String.format(SourceNavigateGridPageElements.ITEM_BY_DELTA_GROUP_NAME, name));
    }

    public String getTaxTypeAddValueByDeltaGroupName(String name)
    {
        return getElementsText(String.format(SourceNavigateGridPageElements.TAX_TYPE_ADD_VALUE_BY_DELTA_GROUP_NAME, name));
    }

    //Delta Grouping Delta Grid Page
    public int getNumberOfDeltaGridRows()
    {
        return SourceNavigateGridPageElements.deltaGridRows.size();
    }

    public List<WebElement> getDeltaGridRows()
    {
        return SourceNavigateGridPageElements.deltaGridRows;
    }

    public String getSectionNumberTextUnderGivenRow(String rowNum)
    {
        return getElementsText(String.format(SourceNavigateGridPageElements.SECTION_NUMBER_UNDER_NUMBER_XPATH, rowNum));
    }

    public String getSubSectionNumberTextUnderGivenRow(String rowNum)
    {
        return getElementsText(String.format(SourceNavigateGridPageElements.SUBSECTION_NUMBER_UNDER_NUMBER_XPATH, rowNum));
    }

    public String getDeltaLevelTextUnderGivenRow(String rowNum)
    {
        return getElementsText(String.format(SourceNavigateGridPageElements.DELTA_LEVEL_UNDER_NUMBER_XPATH, rowNum));
    }

    public String getActionTextUnderGivenRow(String rowNum)
    {
        return getElementsText(String.format(SourceNavigateGridPageElements.ACTION_UNDER_NUMBER_XPATH, rowNum));
    }

    public void selectDeltasInRange(int firstDelta, int lastDelta)
    {
        //decrement the numberOfDocumentsToSelect because we are clicking the first
        //document before entering the for loop to select the other documents
        firstDelta--;
        List<WebElement> deltas = getDeltaGridRows();
        try
        {
            if((lastDelta - firstDelta) > 0 && (lastDelta - firstDelta) < deltas.size())
            {
                RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
                RobotUtils.getRobot().delay(500);

                for (int i = firstDelta; i < lastDelta; i++)
                {
                    click(deltas.get(i));
                }
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            }
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public void moveSelectedDeltasToGroup(String groupName)
    {
        rightClick(SourceNavigateGridPageElements.selectedDelta);
        openContextMenu(SourceNavigateGridPageElements.moveToGroup, getElement(String.format(SourceNavigateGridPageElements.MOVE_TO_GROUP_WITH_GIVEN_NAME_XPATH, groupName)));
    }

    public enum SortOrder
    {
        ASC,
        DESC
    }

    public boolean checkDeltaGroupingDeltaSectionNumberFilter(String filter)
    {
        waitForElement(SourceNavigateGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return SourceNavigateGridPageElements.deltaSectionNumbers.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkDeltaGroupingDeltaLevelFilter(String filter)
    {
        waitForElement(SourceNavigateGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return SourceNavigateGridPageElements.deltaLevels.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkDeltaGroupingDeltaActionFilter(String filter)
    {
        waitForElement(SourceNavigateGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return SourceNavigateGridPageElements.actions.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkDeltaGroupingDeltaGroupNameFilter(String filter)
    {
        waitForElement(SourceNavigateGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return SourceNavigateGridPageElements.groupNames.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkDeltaGroupingDeltaSectionNumberSort(SourceNavigateGridPage.SortOrder sortOrder)
    {
        return checkSort(SourceNavigateGridPageElements.deltaSectionNumbers, (String s1, String s2) -> SourceNavigateGridPage.SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
    }

    public boolean checkDeltaGroupingDeltaLevelSort(SourceNavigateGridPage.SortOrder sortOrder)
    {
        return checkSort(SourceNavigateGridPageElements.deltaLevels, (String s1, String s2) -> SourceNavigateGridPage.SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
    }

    public boolean checkDeltaGroupingDeltaActionSort(SourceNavigateGridPage.SortOrder sortOrder)
    {
        return checkSort(SourceNavigateGridPageElements.actions, (String s1, String s2) -> SourceNavigateGridPage.SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
    }

    protected boolean checkSort(List<WebElement> column, Comparator<String> comparator)
    {
        List<String> values = new ArrayList<>();
        column.forEach(val -> values.add(val.getText()));
        for (int i = 0; i < values.size() - 1; i++)
        {
            if (comparator.compare(values.get(i), values.get(i+1)) > 0)
            {
                return false;
            }
        }
        return true;
    }

    //Delta Grouping Group Grid Page
    public boolean doesDeltaGroupingGroupExist(String groupName)
    {
        return isElementDisplayed(String.format(SourceNavigateGridPageElements.DELTA_GROUPING_GROUP_WITH_GIVEN_NAME_XPATH, groupName));
    }

    public void clickDeltaGroupingGroup(String groupName)
    {
        doubleClick(String.format(SourceNavigateGridPageElements.DELTA_GROUPING_GROUP_WITH_GIVEN_NAME_XPATH, groupName));
    }

    //Rendition Grid Page
    public void deleteRenditions()
    {
        int renditionCount = getNumberOfRenditions();
        if (renditionCount > 1)
        {
            clickFirstXRenditions(renditionCount);
            onlyRightClickFirstItem();
            renditionContextMenu().modifyDeleteRenditions();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
            breakOutOfFrame();
            switchToWindow("Navigate");
            waitForGridRefresh();
            waitForPageLoaded();
        }
    }

    public void deleteFirstRendition()
    {
        if(firstRenditionExists()) {
            unlockFirstRendition();
            rightClickFirstRendition();
            renditionContextMenu().modifyDeleteRendition();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
            switchToWindow("Navigate");
            waitForGridRefresh();
            waitForPageLoaded();
        }
    }

    public boolean checkIfAllValuesInEffectiveDateColumnAreEqualTo(String date)
    {
        List<String> effectiveDateList = getEffectiveDateColumnTextList();
        //return !effectiveDateList.isEmpty() && effectiveDateList.stream().allMatch(effectiveDate -> effectiveDate.equals(date));
        return effectiveDateList.stream().allMatch(effectiveDate -> effectiveDate.equals(date));
    }

    public boolean verifyContentSetExists(String contentSet)
    {
        return tableTestingPage().doesCellWithTextExist(TableTestingPage.RenditionColumns.CONTENT_SET, contentSet);
    }

    public String getFirstRowColor()
    {
        return getBackgroundColor(String.format(SourceNavigateGridPageElements.ROW,1));
    }

    public String getRenditionStatusFirstRow()
    {
        return getElementsText(SourceNavigateGridPageElements.RENDITION_STATUS_COLUMN_FIRST_ROW);
    }

    public String getRenditionTrackingStatus()
    {
        return getElementsText(SourceNavigateGridPageElements.SELECTED_RENDITION_TRACKING_STATUS);
    }

    public String getEffectiveCommentsInGrid()
    {
        return getElementsText(SourceNavigateGridPageElements.EFFECTIVE_COMMENTS_IN_GRID);
    }

    public String getInstructionNoteInGrid()
    {
        return getElementsText(SourceNavigateGridPageElements.INSTRUCTION_NOTE_IN_GRID);
    }

    public String getMiscellaneousInGrid()
    {
        return getElementsText(SourceNavigateGridPageElements.MISCELLANEOUS_IN_GRID);
    }

    public String getQueryNoteInGrid()
    {
        return getElementsText(SourceNavigateGridPageElements.QUERY_NOTE_IN_GRID);
    }

    public String getQueryDateInGrid()
    {
        return getElementsText(SourceNavigateGridPageElements.QUERY_DATE_IN_GRID);
    }

    public String getEffectiveDateProvisionInGrid()
    {
        return getElementsText(SourceNavigateGridPageElements.EFFECTIVE_DATE_PROVISION_IN_GRID);
    }

    public String getDeltaCountInRenditionGrid()
    {
        return getElementsText(SourceNavigateGridPageElements.DELTA_COUNT_COLUMN_FIRST_ROW);
    }

    public String getFileSize()
    {
        return getElementsText(SourceNavigateGridPageElements.SOURCE_FILE_SIZE_IN_GRID);
    }

    public String getInternalEnactmentStatus()
    {
        return getElementsText(SourceNavigateGridPageElements.INTERNAL_ENACTMENT_IN_GRID);
    }

    public String getClassNumber()
    {
        return getElementsText(SourceNavigateGridPageElements.CLASS_NUMBER_IN_GRID);
    }

    public String getWestId()
    {
        return getElementsText(SourceNavigateGridPageElements.WEST_ID_COLUMN_FIRST_ROW);
    }

    public int getResultsCount()
    {
        String[] parts = getElementsAttribute(SourceNavigateGridPageElements.RENDITION_RESULT, "innerHTML").split("\\s+");
        return Integer.parseInt(parts[0]);
    }

    //Section Grid Page
    public int getNumberOfSections()
    {
        return SourceNavigateGridPageElements.taxTypeAddColumnValue.size();
    }

   //Section Group Grid Page
   public void clickItemBySectionGroupName(String name)
   {
       click(String.format(SourceNavigateGridPageElements.ITEM_BY_SECTION_GROUP_NAME, name));
   }

    public void rightClickItemBySectionGroupName(String name)
    {
        clickItemBySectionGroupName(name);
        rightClick(String.format(SourceNavigateGridPageElements.ITEM_BY_SECTION_GROUP_NAME, name));
    }

    public String getTaxTypeAddValueBySectionGroupName(String name)
    {
        return getElementsText(String.format(SourceNavigateGridPageElements.TAX_TYPE_ADD_VALUE_BY_SECTION_GROUP_NAME, name));
    }

    //Section Grouping Group Grid Page
    public boolean doesSectionGroupingGroupExist(String groupName)
    {
        return isElementDisplayed(String.format(SourceNavigateGridPageElements.SECTION_GROUPING_GROUP_WITH_GIVEN_NAME_XPATH, groupName));
    }

    public void clickSectionGroupingGroup(String groupName)
    {
        click(String.format(SourceNavigateGridPageElements.SECTION_GROUPING_GROUP_WITH_GIVEN_NAME_XPATH, groupName));
        rightClick(String.format(SourceNavigateGridPageElements.SECTION_GROUPING_GROUP_WITH_GIVEN_NAME_XPATH, groupName));
    }

    //Section Grouping Section Grid Page
    public int getNumberOfSectionGridRows()
    {
        return SourceNavigateGridPageElements.sectionGridRows.size();
    }

    public List<WebElement> getSectionGridRows()
    {
        return SourceNavigateGridPageElements.sectionGridRows;
    }

    public void selectSectionsInRange(int firstSection, int lastSection)
    {
        //decrement the numberOfDocumentsToSelect because we are clicking the first
        //document before entering the for loop to select the other documents
        firstSection--;
        List<WebElement> sections = getSectionGridRows();
        try
        {
            if((lastSection - firstSection) > 0 && (lastSection - firstSection) < sections.size())
            {
                RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
                RobotUtils.getRobot().delay(500);

                for (int i = firstSection; i < lastSection; i++)
                {
                    click(sections.get(i));
                }
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            }
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public void moveSelectedSectionsToGroup(String groupName)
    {
        click(SourceNavigateGridPageElements.selectedSection);
        rightClick(SourceNavigateGridPageElements.selectedSection);
        openContextMenu(SourceNavigateGridPageElements.moveToGroup, getElement(String.format(SourceNavigateGridPageElements.MOVE_TO_GROUP_WITH_GIVEN_NAME_XPATH, groupName)));
    }

    public boolean checkSectionGroupingSectionNumberFilter(String filter)
    {
        waitForElement(SourceNavigateGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return SourceNavigateGridPageElements.sectionNumbers.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkSectionGroupingSectionGroupNameFilter(String filter)
    {
        waitForElement(SourceNavigateGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return SourceNavigateGridPageElements.groupNames.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkSectionGroupingSectionSubSectionNumberFilter(String filter)
    {
        waitForElement(SourceNavigateGridPageElements.FIRST_SUBSECTION_NUMBER_XPATH);
        return SourceNavigateGridPageElements.subSectionNumbers.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkSectionGroupingSectionDeltaCountFilter(String filter)
    {
        waitForElement(SourceNavigateGridPageElements.FIRST_DELTA_COUNT_XPATH);
        return SourceNavigateGridPageElements.deltaCounts.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkSectionGroupingSectionNumberSort(SourceNavigateGridPage.SortOrder sortOrder)
    {
        return checkSort(SourceNavigateGridPageElements.sectionNumbers, (String s1, String s2) -> SourceNavigateGridPage.SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
    }

    public boolean checkSectionGroupingSectionSubSectionNumberSort(SourceNavigateGridPage.SortOrder sortOrder)
    {
        return checkSort(SourceNavigateGridPageElements.subSectionNumbers, (String s1, String s2) -> SourceNavigateGridPage.SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
    }

    public boolean checkSectionGroupingSectionDeltaCountSort(SourceNavigateGridPage.SortOrder sortOrder)
    {
        return checkSort(SourceNavigateGridPageElements.deltaCounts, (String s1, String s2) -> SourceNavigateGridPage.SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
    }

    public void firstDeltaEditContent()
    {
        rightClickFirstRendition();
        deltaContextMenu().editDelta();
        switchToWindow(EditorPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        waitForPageLoaded();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        waitForElement(EditorToolbarPageElements.CLOSE_DOC);
    }

    public void firstDeltaViewContent()
    {
        rightClickFirstRendition();
        deltaContextMenu().viewDelta();
        switchToWindow(EditorPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        waitForPageLoaded();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        waitForElement(EditorToolbarPageElements.CLOSE_DOC);
    }

    public void deltaGroupEditContent()
    {
        deltaGroupContextMenu().editDeltaGroup();
        switchToWindow(EditorPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        waitForPageLoaded();
        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        waitForElement(EditorToolbarPageElements.CLOSE_DOC);
    }

    public List<String> checkIfOptionsAreVisible(String Element)
    {
        boolean optionNotSupported = false;
        List<String> fails = new ArrayList<>();

        for (WebElement ele : getElements(Element))
        {
            String id = ele.getAttribute("id");
            String optionText = ele.getText();
            optionNotSupported = clamshellPage().verifyClamshellOptionNotSupported(id, optionText);
            if (!optionNotSupported)
            {
                if (optionText.contains("\n"))
                {
                    optionText = optionText.replace("\n", " ");
                }
                fails.add(optionText);
            }
        }
        return fails;
    }

    public void firstRenditionEditContent(DocumentLevels documentLevel)
    {
        rightClickFirstRendition();
        switch (documentLevel)
        {
            case SECTION:
                sectionContextMenu().editSection();
                break;
            case SECTION_GROUP:
                sectionGroupContextMenu().editSectionGroup();
                break;
            case DELTA:
                deltaContextMenu().editDelta();
                break;
//			case DELTA_GROUP:
//				renditionContextMenu().editDeltaGroupDynamicScrolling(); // TODO create this method
//				break;
            default:
                throw new RuntimeException("Illegal argument! Use enum from DocumentLevels class");
        }
        switchToWindow(EditorPageElements.PAGE_TITLE);
        waitForPageLoaded();
    }

    public boolean waitUntilFirstDocBeUnlocked()
    {
        return waitUntilFirstDocBeUnlocked(60000);
    }

    public boolean waitUntilFirstDocBeUnlocked(long timeToWait)
    {
        boolean isLocked = true;
        long start = new Date().getTime();
        while( (isLocked = doesElementExist(SourceNavigatePageElements.LOCK_IMG_FIRST_REND_XPATH)) && new  Date().getTime() - start < timeToWait)
        {
            sourceNavigateFooterToolsPage().refreshTheGrid();
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                break;
            }
        }
        return isLocked;
    }

    public boolean isABlueLinkFlagPresent()
    {
        return doesElementExist(SourceNavigatePageElements.BLUE_FLAG_ON_RENDITION);
    }

    public void rightClickRenditions(){
        rightClick(SourceNavigateGridPageElements.RENDITIONS_LIST_XPATH);
    }

    public boolean isRenditionDeleted(int row)
    {
        return tableTestingPage().doesCellContainElement( TableTestingPage.RenditionColumns.DELETE, row, SourceNavigatePageElements.DELETED_IMG_XPATH );
    }

    public boolean isRenditionLocked(int row)
    {
        return tableTestingPage().doesCellContainElement(TableTestingPage.RenditionColumns.LOCK,row, SourceNavigatePageElements.LOCK_IMG_XPATH);
    }

    public int getNumberOfRenditions()
    {
        return getElements(SourceNavigateGridPageElements.RENDITIONS_LIST_XPATH).size();
    }

    public int getNumberOfDeltas()
    {
        return getElements(SourceNavigateGridPageElements.RENDITIONS_LIST_XPATH).size();
    }

    public int getNumberOfMultipleFlaggedRenditions()
    {
        return getElements(SourceNavigatePageElements.MULTI_FLAG_XPATH).size();
    }

    public int getNumberOfDeletedFlaggedRenditions()
    {
        return getElements(SourceNavigatePageElements.DELETE_FLAG_XPATH).size();
    }

    public void clickFirstXRenditions(int numberOfRenditions)
    {
        try
        {
            click(String.format(SourceNavigateGridPageElements.X_RENDITION, 1));
            RobotUtils.getRobot().keyPress(KeyEvent.VK_SHIFT);
            click(String.format(SourceNavigateGridPageElements.X_RENDITION,numberOfRenditions));
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_SHIFT);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_SHIFT);
        }
    }

    public boolean firstRenditionDeleted()
    {
        return doesElementExist(SourceNavigatePageElements.DELETE_IMG);
    }

    public boolean firstRenditionExists()
    {
        return doesElementExist(SourceNavigateGridPageElements.FIRST_ROW);
    }

    public String getUUID()
    {
        return getElement(SourceNavigatePageElements.ID).getAttribute("innerHTML");
    }

    public String getSelectedUuid()
    {
        return getElement(SourceNavigatePageElements.SELECTED_ID).getAttribute("innerHTML");
    }

    public void unlockFirstRendition()
    {
        if(isFirstRenditionLocked())
        {
            String renditionUuid = sourceNavigateGridPage().getUUID();
            Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
            SourceDatabaseUtils.deleteLocks(connection, renditionUuid);
            BaseDatabaseUtils.disconnect(connection);
            sourceNavigateFooterToolsPage().refreshTheGrid();
        }
    }

    public void unlockRenditionWithUuid(String renditionUuid)
    {
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        SourceDatabaseUtils.deleteLocks(connection, renditionUuid);
        BaseDatabaseUtils.disconnect(connection);
    }

    public void setRenditionDeletedWithDatabase(String renditionUuid, String username)
    {
            Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
            SourceDatabaseUtils.setRenditionDeleted(connection, renditionUuid, username);
            BaseDatabaseUtils.disconnect(connection);
    }

    public void setRenditionUndeletedWithDatabase(String renditionUuid, String username)
    {
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        SourceDatabaseUtils.setRenditionUndeleted(connection, renditionUuid, username);
        BaseDatabaseUtils.disconnect(connection);
    }

    public boolean doesGroupNameExistAtPosition(int row)
    {
        return doesElementExist(String.format(SourceNavigatePageElements.SECTION_DELTA_GROUPING_SECTION_GROUPNAME_ON_POSITION_IN_GRID,row, "Tagalong"));
    }

    public boolean groupExistInGrid(int row, String groupName)
    {
        return getElement(String.format(SourceNavigatePageElements.SECTION_DELTA_GROUPING_SECTION_GROUPNAME_ON_POSITION_IN_GRID,row,groupName)).getText().equals(groupName);
    }

    public void clickRenditionByGroupName(String groupName)
    {
        click(String.format(SourceNavigatePageElements.SECTION_DELTA_GROUPNAME_IN_GRID,groupName));
    }

    public void rightClickRenditionByGroupName(String groupName)
    {
        click(String.format(SourceNavigatePageElements.SECTION_DELTA_GROUPNAME_IN_GRID,groupName));
        rightClick(String.format(SourceNavigatePageElements.SECTION_DELTA_GROUPNAME_IN_GRID,groupName));
    }

    public String getDifficultyLevelOfFirstItem()
    {
        return getElementsText(SourceNavigateGridPageElements.FIRST_ROW_DIFFICULTY_LEVEL);
    }

    public boolean checkSectionDeltaGroupIsLocked(String sectiongroupName)
    {
        return isElementDisplayed(String.format(SourceNavigatePageElements.SECTION_DELTA_GROUPING_SECTION_LOCK,sectiongroupName));
    }

    public boolean checkIfTodaysDateIsOnGroupX(String groupname)
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        return getElementsText(String.format(SourceNavigatePageElements.GROUPING_TAB_EFFECTIVE_DATE_OF_GIVEN_GROUP,groupname)).contains(currentDate);
    }
    public boolean checkPositionOfGroup(int groupPosition, String groupName)
    {
        return getElementsText(String.format(SourceNavigatePageElements.SECTION_DELTA_GROUPING_SECTION_GROUPNAME_ON_POSITION_IN_GRID,groupPosition,groupName)).contains(groupName);
    }

    public boolean checkColumnsInGrid(List<String> columns)
    {
        ArrayList<String> listOfColumns = new ArrayList<>();
        int columnsQuantity = getElements(SourceNavigatePageElements.ALL_COLUMNS_IN_GRID).size();
        for(int i=1; i<=columnsQuantity; i++)
        {
            if(doesElementExist(SourceNavigatePageElements.ALL_COLUMNS_IN_GRID + "[" + i + "]" + "//span/a"))
            {
                String column = getElement(SourceNavigatePageElements.ALL_COLUMNS_IN_GRID + "[" + i + "]" + "//span/a")
                        .getAttribute("innerHTML").replaceAll("<br>", " ");
                listOfColumns.add(column);
            }
        }
        if(!listOfColumns.containsAll(columns))
        {
            System.out.println(listOfColumns);
            return false;
        }
        return true;
    }

    public int numberOfDeltasOfSelectedSections()
    {
        List<String> selectedSectionsDeltaCount = getElements(SourceNavigatePageElements.SELECTED_SECTIONS_DELTA_COUNT).
                stream().map(WebElement::getText).collect(Collectors.toList());
        int deltaCount = 0;
        for (String sectionDeltas : selectedSectionsDeltaCount)
        {
            deltaCount += Integer.parseInt(sectionDeltas);
        }
        return deltaCount;
    }

    public int getDocumentCount()
    {
        int count = 0;
        if(doesElementExist(SourceNavigatePageElements.RENDITIONS_XPATH))
        {
            count = getElements(SourceNavigatePageElements.RENDITIONS_XPATH).size();
        }
        return count;
    }

    public String getDocumentId()
    {
        return getElement(SourceNavigatePageElements.DOCUMENT_ID).getAttribute("innerHTML");
    }
    public String getIntegrationStatusForSelected()
    {
        return getElementsText(SourceNavigatePageElements.LOCATION_INTEGRATION_STATUS_IN_GRID);
    }

    public String getFinishedBookmarkForSelected()
    {
        return getElementsText(SourceNavigatePageElements.FINISHED_BOOKMARK_GRID);
    }

    public String getValidateReportDocNumInGrid()
    {
        return getElementsText(SourceNavigatePageElements.VALIDATE_REPORT_DOC_NUM_IN_GRID);
    }

    public String getInstructionNoteOfFirstItem()
    {
        return getElementsText(SourceNavigateGridPageElements.FIRST_ROW_INSTRUCTION_NOTE);
    }

    public String getEffectiveDateOfFirstRendition()
    {
        return getElementsText(SourceNavigateGridPageElements.RENDITION_FIRST_ROW_EFFECTIVE_DATE);
    }

    public String getEffectiveDateOfFirstItem()
    {
        return getElementsText(SourceNavigateGridPageElements.FIRST_ROW_EFFECTIVE_DATE);
    }

    public String getEffectiveDateProvisionOfFirstItem()
    {
        return getElementsText(SourceNavigateGridPageElements.FIRST_ROW_EFFECTIVE_DATE_PROVISION);
    }

    public String getEffectiveCommentsOfFirstItem()
    {
        return getElementsText(SourceNavigateGridPageElements.FIRST_ROW_EFFECTIVE_COMMENTS);
    }

    public String getQueryDateOfFirstItem()
    {
        return getElementsText(SourceNavigateGridPageElements.FIRST_ROW_QUERY_DATE);
    }

    public boolean wasAddedAsQueryDate()
    {
        return doesElementExist(SourceNavigateGridPageElements.FIRST_ROW_ADDED_AS_QD);
    }

    public String getMiscellaneousOfFirstItem()
    {
        return getElementsText(SourceNavigateGridPageElements.FIRST_ROW_MISCELLANEOUS);
    }

    public List<String> getEffectiveDateColumnTextList()
    {
        return getElementsTextList(SourceNavigatePageElements.EFFECTIVE_DATE_COLUMN_TEXT_LIST);
    }

    public List<String> getAllContentSets()
    {
        return getElementsTextList(SourceNavigateGridPageElements.RENDITION_CONTENT_SET_LIST_XPATH);
    }

    public List<String> getAllYears()
    {
        return getElementsTextList(SourceNavigateGridPageElements.RENDITION_YEAR_LIST_XPATH);
    }

    public List<String> getAllSessions()
    {
        return getElementsTextList(SourceNavigateGridPageElements.RENDITION_SESSION_LIST_XPATH);
    }

    public List<String> getAllDocNumbers()
    {
        return getElementsTextList(SourceNavigateGridPageElements.RENDITION_DOC_NUMBER_LIST_XPATH);
    }

    public List<String> getAllContentTypes()
    {
        return getElementsTextList(SourceNavigateGridPageElements.RENDITION_CONTENT_TYPE_LIST_XPATH);
    }

    public List<String> getAllRenditionStatuses()
    {
        return getElementsTextList(SourceNavigateGridPageElements.RENDITION_STATUS_LIST_XPATH);
    }

    public List<String> getAllTaxTypeAdd()
    {
        return getElementsTextList(SourceNavigateGridPageElements.TAX_TYPE_ADD_LIST);
    }

    public String getDocNumber()
    {
        return getElement(SourceNavigateGridPageElements.RENDITION_DOC_NUMBER_LIST_XPATH).getText();
    }

    public String getFileNumber()
    {
        return getElement(SourceNavigateGridPageElements.RENDITION_FILE_NUMBER).getText();
    }

    public List<String> getAllCorrelationIDs()
    {
        return getElementsTextList(SourceNavigateGridPageElements.RENDITION_CORRELATION_ID_LIST_XPATH);
    }

    public String getCPDDateTime(String groupName)
    {
        return tableTestingPage().getCellValue(TableTestingPage.SectionGroupingColumns.CPD_DATE_TIME, tableTestingPage().getRowByText(TableTestingPage.SectionGroupingColumns.SECTION_GROUP_NAME,groupName)-1);
    }

    public String getDeltaTargetNodeText()
    {
        return getElementsText(SourceNavigateGridPageElements.DELTA_TAB_TARGET_NODE);
    }

    public String getSelectedDeltaTargetNode()
    {
        return getElementsText(SourceNavigateGridPageElements.SELECTED_DELTA_TARGET_NODE);
    }

    public void clickSelectedDeltaTargetNode()
    {
        click(SourceNavigateGridPageElements.SELECTED_DELTA_TARGET_NODE);
    }

    public void clickDeltaTargetNode()
    {
        click(SourceNavigateGridPageElements.DELTA_TAB_TARGET_NODE);
    }

    public void rightClickDeltaTargetNode()
    {
        rightClick(SourceNavigateGridPageElements.DELTA_TAB_TARGET_NODE);
    }

    public String getDeltaTargetSubNodeText()
    {
        return getElementsText(SourceNavigateGridPageElements.DELTA_TAB_TARGET_SUBNODE);
    }

    public boolean selectedDeltaHasOneOfTheIntStatuses(String[] statuses)
    {
        return Arrays.asList(statuses).contains(getElementsText(SourceNavigateGridPageElements.DELTA_TAB_SELECTED_DELTA_INT_STATUS));
    }

    public boolean allDeltasHaveCorrectStatus(String[] statuses)
    {
        List<WebElement> addDeltas = getElements(SourceNavigateGridPageElements.DELTA_TAB_ALL_DELTAS_INT_STATUSES);
        for (int i = 0; i < addDeltas.size(); i++) {
            if (!Arrays.asList(statuses).contains(addDeltas.get(i).getText())) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<ArrayList<String>> getInfoFromSourceNavForReportConsolidatedColumns()
    {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < getElements(SourceNavigateGridPageElements.RENDITIONS_LIST_XPATH).size(); i++)
        {
            ArrayList<String> singleDeltaInfo = new ArrayList<String>();
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.TARGET_CODE, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.TARGET_NODE, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.TARGET_SUB_NODE, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.EFF_DATE, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.TARGET_VOL, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.CLASS_NUMBER, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.WEST_ID, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.DOC_TYPE, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.DOC_NUMBER, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.RENDITION_SECTIONS, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.SECTION_NUMBER, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.SUB_SECTION_NUMBER, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.DELTA_LEVEL, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.ACTION, i));
            result.add(singleDeltaInfo);
        }

        return result;
    }

    public ArrayList<ArrayList<String>> getInfoFromSourceNavForReportAllColumns()
    {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < getElements(SourceNavigateGridPageElements.RENDITIONS_LIST_XPATH).size(); i++)
        {
            ArrayList<String> singleDeltaInfo = new ArrayList<String>();
            if(doesElementExist(String.format(SourceNavigatePageElements.RENDITIONS_VALIDATE_IMAGES, i+1)))
            {
                singleDeltaInfo.add(getElementsAttribute(String.format(SourceNavigatePageElements.RENDITIONS_VALIDATE_IMAGES, i+1),"alt"));
            }
            else
            {
                singleDeltaInfo.add("None");
            }


            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.LOCK, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.INSTRUCTION_NOTES, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.CONTENT_SET, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.RENDITION_STATUS, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.CLASS_NUMBER, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.WEST_ID, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.DOC_TYPE, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.DOC_NUMBER, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.RENDITION_SECTIONS, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.SECTION_NUMBER, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.SUB_SECTION_NUMBER, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.DELTA_LEVEL, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.ACTION, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.TARGET_CODE, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.TARGET_NODE, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.TARGET_SUB_NODE, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.EFF_DATE, i));
            singleDeltaInfo.add(tableTestingPage().getCellValue(TableTestingPage.RenditionColumns.TARGET_VOL, i));
            result.add(singleDeltaInfo);
        }

        return result;
    }

    public boolean errorFlagIsPresent()
    {
        return getElements(SourceNavigateGridPageElements.ERROR_IMG).size() >= 1;
    }

    public boolean isOnlineProductTagOfFirstItemAdded()
    {
        return getElement(SourceNavigateGridPageElements.FIRST_ROW_ONLINE_PRODUCT_TAG).getAttribute("src").contains("check.gif");
    }

    public void sortSourceNavigateGridByYearAscending()
    {
        if(doesElementExist(SourceNavigateGridPageElements.YEAR_COLUMN_HEADER_SORT_ASCENDING))
        {
            click(SourceNavigateGridPageElements.YEAR_COLUMN_HEADER_SORT_ASCENDING);
            waitForGridRefresh();
        }
    }

    public void sortSourceNavigateGridByYearDescending()
    {
        if(doesElementExist(SourceNavigateGridPageElements.YEAR_COLUMN_HEADER_SORT_DESCENDING))
        {
            click(SourceNavigateGridPageElements.YEAR_COLUMN_HEADER_SORT_DESCENDING);
            waitForGridRefresh();
        }
    }

    public boolean isColumnPresent(String... columnNames)
    {
        for(String name : columnNames)
        {
            if(!doesElementExist(String.format(SourceNavigateGridPageElements.SOURCE_NAVIGATE_GRID_PAGE_FILTER_COLUMNS, name)))
            {
                return false;
            }
        }
        return true;
    }
}
