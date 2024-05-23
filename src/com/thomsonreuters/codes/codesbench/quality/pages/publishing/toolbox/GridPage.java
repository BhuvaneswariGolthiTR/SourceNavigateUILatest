package com.thomsonreuters.codes.codesbench.quality.pages.publishing.toolbox;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class GridPage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public GridPage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, GridElements.class);
        }

        public void waitForGridLoaded()
        {
                longWaitForElement(GridElements.HIDDEN_OVERLAY_PANEL_XPATH);
        }

        public String getLastRowValue()
        {
                return getElementsText(GridElements.LAST_ROW_VALUE_COLUMN_XPATH);
        }

        public void rightClickFirstSectionNode()
        {
                click(GridElements.FIRST_SECTION_NODE_XPATH);
                rightClick(GridElements.FIRST_SECTION_NODE_XPATH);
                waitForPageLoaded();
        }

        public void rightClickLastRow()
        {
                click(GridElements.LAST_ROW_XPATH);
                rightClick(GridElements.LAST_ROW_XPATH);
                waitForPageLoaded();
        }

        public void selectNodebyHierarchyColumnValue(String nodeHierarchyColumnValue)
        {
//                click(String.format(GridElements.NODE_HIERARCHY_COLUMN_VALUE_XPATH, nodeHierarchyColumnValue));
                click(String.format(GridElements.NODE_TARGET_VALUE_ONLY_DIV_XPATH, nodeHierarchyColumnValue));
        }

        public void selectByNodeTargetValue(String nodeTargetValue)
        {
                click(String.format(GridElements.NODE_TARGET_VALUE_XPATH, nodeTargetValue));
        }

        public void selectByNodeTargetValueOnlyDiv(String nodeTargetValue)
        {
                click(String.format(GridElements.NODE_TARGET_VALUE_ONLY_DIV_XPATH, nodeTargetValue));
        }

        public void rightClickByNodeTargetValue(String nodeTargetValue)
        {
                click(String.format(GridElements.NODE_TARGET_VALUE_XPATH, nodeTargetValue));
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                rightClick(String.format(GridElements.NODE_TARGET_VALUE_XPATH, nodeTargetValue));
        }

        public void rightClickByNodeTargetValueOnlyDiv(String nodeTargetValue)
        {
                click(String.format(GridElements.NODE_TARGET_VALUE_ONLY_DIV_XPATH, nodeTargetValue));
                rightClick(String.format(GridElements.NODE_TARGET_VALUE_ONLY_DIV_XPATH, nodeTargetValue));
        }

        public void rightClickMultipleSelected()
        {
                rightClick(GridElements.selectedNodesInGrid);
                waitForPageLoaded();
        }

        public void rightClickByNodeHierarchyColumnValue(String nodeHierarchyColumnValue)
        {
                click(getElement(String.format(GridElements.NODE_HIERARCHY_COLUMN_VALUE_XPATH, nodeHierarchyColumnValue)));
                rightClick(String.format(GridElements.NODE_HIERARCHY_COLUMN_VALUE_XPATH, nodeHierarchyColumnValue));
        }

        @Deprecated
        public void clickByNodeHierarchyColumnValue(String nodeHierarchyColumnValue)
        {
                click(String.format(GridElements.NODE_HIERARCHY_COLUMN_VALUE_XPATH, nodeHierarchyColumnValue));
        }

        public void selectMultipleNodesByNodeHierarchyColumnValues(String... nodeHierarchyColumnValues)
        {
                click(String.format(GridElements.NODE_HIERARCHY_COLUMN_VALUE_XPATH, nodeHierarchyColumnValues[0]));
                try
                {
                        RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
                        for (int i = 0; i < nodeHierarchyColumnValues.length; i++)
                        {
                                if(i == 0)
                                {
                                        continue;
                                }
                                click(String.format(GridElements.NODE_HIERARCHY_COLUMN_VALUE_XPATH, nodeHierarchyColumnValues[i]));
                        }
                        RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
                }
                finally
                {
                        RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
                }
        }

        public void selectAllNodesBetweenTwoNodeHierarchyColumnValues(String nodeHierarchyColumnValue1, String nodeHierarchyColumnValue2)
        {
                try
                {
                        click(getElement(String.format(GridElements.NODE_HIERARCHY_COLUMN_VALUE_XPATH, nodeHierarchyColumnValue1)));
                        RobotUtils.getRobot().keyPress(KeyEvent.VK_SHIFT);
                        click(getElement(String.format(GridElements.NODE_HIERARCHY_COLUMN_VALUE_XPATH, nodeHierarchyColumnValue2)));
                        RobotUtils.getRobot().keyRelease(KeyEvent.VK_SHIFT);
                }
                finally
                {
                        RobotUtils.getRobot().keyRelease(KeyEvent.VK_SHIFT);
                }
        }

        public void selectAllnodesBetweenTwoNodeTargetValueOnlyDiv(String nodeTargetValue1,String nodeTargetValue2)
        {
                        Actions action = new Actions(driver);
                        action.click(getElement(String.format(GridElements.NODE_TARGET_VALUE_ONLY_DIV_XPATH, nodeTargetValue1)));
                        action.pause(Duration.ofSeconds(1));
                        action.keyDown(Keys.SHIFT);
                        action.pause(Duration.ofSeconds(1));
                        action.click(getElement(String.format(GridElements.NODE_TARGET_VALUE_ONLY_DIV_XPATH, nodeTargetValue2)));
                        action.pause(Duration.ofSeconds(1));
                        action.keyUp(Keys.SHIFT).build().perform();
        }

        public void selectAllNodesBetweenTwoNodeValueColumnValues(String nodeTargetValue1, String nodeTargetValue2)
        {
                Actions action = new Actions(driver);
                action.click(getElement(String.format(GridElements.NODE_HIERARCHY_VALUE_COLUMN_XPATH, nodeTargetValue1)));
                action.pause(Duration.ofSeconds(1));
                action.keyDown(Keys.SHIFT);
                action.pause(Duration.ofSeconds(1));
                action.click(getElement(String.format(GridElements.NODE_HIERARCHY_VALUE_COLUMN_XPATH, nodeTargetValue2)));
                action.pause(Duration.ofSeconds(1));
                action.keyUp(Keys.SHIFT).build().perform();
        }

        public void rightClickMultipleSelectedNodes()
        {
                rightClick(GridElements.selectedNodesInGrid);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
                waitForPageLoaded();
        }

        //NOD Publishing Only
        public String getSelectedNodesPublishingStatusValue()
        {
                return getElementsText(GridElements.SELECTED_NODE_PUBLISHING_STATUS_ID_XPATH);
        }

        //Pub Navigate Evaluation - Issues After Pub Complete
        public String getSelectedNodesPublishingStatus()
        {
                return getElementsText(GridElements.SELECTED_NODE_PUBLISHING_STATUS_XPATH);
        }

        public String getSelectedNodesStatusSetDate()
        {
                return getElementsText(GridElements.SELECTED_NODE_PUBLISHING_STATUS_XPATH);
        }

        public String getSelectedNodesWorkflowId()
        {
                return getElementsText(GridElements.SELECTED_NODE_WORKFLOW_ID_XPATH);
        }

        public String getSelectedNodesStatus()
        {
                return getElementsText(GridElements.SELECTED_NODE_STATUS_XPATH);
        }


        public void selectFirstSectionNode()
        {
                click(GridElements.FIRST_SECTION_NODE_XPATH);
        }

        public String getFirstSectionNodeValue()
        {
                return getElementsText(GridElements.FIRST_SECTION_NODE_VALUE_XPATH);
        }

        public boolean isBackgroundGreenForSelectedLastRow()
        {
                return GridElements.SELECTED_LAST_ROW_COLOR.equals(getBackgroundColor(GridElements.LAST_ROW_XPATH));
        }

        public boolean isBackgroundGreenForDeselectedLastRow()
        {
                return GridElements.DESELECTED_LAST_ROW_COLOR.equals(getBackgroundColor(GridElements.LAST_ROW_XPATH));
        }

        public boolean isBackgroundGreenForSelectedRow()
        {
                return GridElements.SELECTED_LAST_ROW_COLOR.equals(getBackgroundColor(GridElements.SELECTED_ROW_XPATH));
        }

        public boolean isBackgroundGreenForRow(String value)
        {
                return GridElements.DESELECTED_LAST_ROW_COLOR.equals(getBackgroundColor(String.format(GridElements.ROW_XPATH, value)));
        }

        public void waitForToastMessageToAppear()
        {
                waitForElement(GridElements.TOAST_MESSAGE_XPATH);
        }

        public boolean checkUpdatedStatusToastMessage()
        {
                return GridElements.UPDATED_STATUS_MESSAGE.equals(getElementsText(GridElements.TOAST_MESSAGE_XPATH));
        }

        public boolean isNodeHierarchyColumnValueInGrid(String nodeHierarchyColumnValue)
        {
                return doesElementExist(String.format(GridElements.NODE_HIERARCHY_COLUMN_VALUE_XPATH, nodeHierarchyColumnValue));
        }

        public String getValidationOfSelectedNode()
        {
                return getElementsText(GridElements.SELECTED_NODE_VALIDATION_STATUS_XPATH);
        }

        public void selectByNodeHierarchyColumnValue(String nodeHierarchyColumnValue)
        {
                click(String.format(GridElements.NODE_HIERARCHY_COLUMN_VALUE_XPATH, nodeHierarchyColumnValue));
        }

        public String getSelectedNodesLawTrackingStatus()
        {
                return getElementsText(GridElements.SELECTED_NODE_LAW_TRACKING_STATUS_XPATH);
        }

        public String getSelectedNodesStartDate()
        {
                return getElementsText(GridElements.SELECTED_NODE_START_DATE_XPATH);
        }

        public String getSelectedNodesEndDate()
        {
                return getElementsText(GridElements.SELECTED_NODE_END_DATE_XPATH);
        }

        public String getSelectedNodesPublishingStatusId()
        {
                return getElementsText(GridElements.SELECTED_NODE_PUBLISHING_STATUS_ID_XPATH);
        }

        public String getSelectedNodesModifiedByUsername()
        {
                return getElementsText(GridElements.SELECTED_NODE_MODIFIED_BY_COLUMN_VALUE_XPATH);
        }

        public boolean isSelectedNodesReadyUserColumnEmpty()
        {
                return getElementsText(GridElements.SELECTED_NODE_READY_USER_COLUMN_VALUE_XPATH).isEmpty();
        }

        public boolean isSelectedNodesReadyDateColumnEmpty()
        {
                return getElementsText(GridElements.SELECTED_NODE_READY_DATE_COLUMN_VALUE_XPATH).isEmpty();
        }

        public String getVolumeOfSelectedNode()
        {
                return getElementsText(GridElements.SELECTED_NODE_VOLUME_XPATH);
        }

        public String getSelectedNodesCode()
        {
                return getElementsText(GridElements.SELECTED_NODE_CODE_XPATH);
        }

        public String getSelectedNodesKeyword()
        {
                return getElementsText(GridElements.SELECTED_NODE_KEYWORD_XPATH);
        }

        public String getSelectedNodesValue()
        {
                return getElementsText(GridElements.SELECTED_NODE_VALUE_XPATH);
        }

        public String getSelectedNodeWlLoadCompleteDateValue()
        {
                String selectedNodesWlLoadCompleteDate = "";
                try
                {
                        String selectedNodeStartDate = getElementsText(GridElements.SELECTED_NODE_WL_LOAD_COMPLETE_DATE);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
                        Date myDate = dateFormat.parse(selectedNodeStartDate);
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
                        selectedNodesWlLoadCompleteDate = dateFormat2.format(myDate);
                }

                catch (Exception e)
                {
                        Assertions.fail("There was an error converting the Publishing date to the specific format");
                }
                return selectedNodesWlLoadCompleteDate;
        }

        public String getSelectedNodesStartDateWithLeadingZeros()
        {
                String selectedNodeStartDateWithLeadingZeros = "";

                try
                {
                        String selectedNodeStartDate = getElementsText(GridElements.SELECTED_NODE_START_DATE_XPATH);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
                        Date myDate = dateFormat.parse(selectedNodeStartDate);
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
                        selectedNodeStartDateWithLeadingZeros = dateFormat2.format(myDate);
                }

                catch (Exception e)
                {
                        Assertions.fail("There was an error converting the Publishing date to the specific format");
                }

                return selectedNodeStartDateWithLeadingZeros;
        }

        public String getSelectedNodesPubApproveUser()
        {
                return getElementsText(GridElements.SELECTED_NODE_PUBLISH_APPROVE_USER_XPATH);
        }

        public String getSelectedNodesPubCompleteDate()
        {
                return getElementsText(GridElements.SELECTED_NODE_PUBLISH_COMPLETE_DATE_XPATH);
        }

        public boolean isSelectedNodeDeleted()
        {
                return doesElementExist(GridElements.SELECTED_NODE_DELETED_XPATH);
        }

        public boolean isMultipleNodesSelected(String... nodeValues) //Searches by value column
        {
                int count = 0;
                for (String nodeValue : nodeValues)
                {
                        if (doesElementExist(String.format(GridElements.SELECTED_NODES_BY_VALUE_COLUMN, nodeValue)))
                        {
                                count++;
                        }
                }
                return count >= nodeValues.length;
        }

        public boolean isChildFromBothParentsSelected(String nodeValue)
        {
                boolean selectedBoth = false;
                int count =0;

                List<WebElement> listOfNodesByValue = getElements(String.format(GridElements.SELECTED_NODES_BY_VALUE_COLUMN, nodeValue));
                for(int i=0; i<=listOfNodesByValue.size(); i++)
                {
                        count+=1;
                }
                if(count >=1)
                {
                        selectedBoth = true;
                }
                return selectedBoth;
        }

        public boolean isChildFromBothParentsHighlightedGreen(String nodeValue)
        {
                boolean selectedBoth = false;
                int count =0;

                List<WebElement> listOfNodesByValue = getElements(String.format(GridElements.SELECTED_NODES_BY_HIERARCHY_COLUMN_READY_STATUS, nodeValue));
                for(int i=0; i<=listOfNodesByValue.size(); i++)
                {
                        count+=1;
                }
                if(count >=1)
                {
                        selectedBoth = true;
                }
                return selectedBoth;
        }
}
