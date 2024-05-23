package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class GridElements
{
        public static final String HIDDEN_OVERLAY_PANEL_XPATH = "//div[@class='ag-overlay ag-hidden']";
        public static final String LOADING_CANDIDATES_XPATH = "//span[text()='Loading candidates...']";
        public static final String LAST_ROW_XPATH = "//div[contains(@class, 'ag-center-cols-container')]//div[contains(@class, 'ag-row-last')]";
        public static final String MODIFIED_DATE_COLUMN_VALUE_XPATH = "//div[@col-id='modifiedDate']/span[@text='%s']";
        public static final String LAST_ROW_VALUE_COLUMN_XPATH = LAST_ROW_XPATH + "//div[@col-id='value']";
        public static final String NODE_TARGET_VALUE_XPATH = "//div[@col-id='value']//span[text()='%s']";
        public static final String NODE_TARGET_VALUE_ONLY_DIV_XPATH = "//div[@col-id='value' and text()='%s']";
        public static final String SELECTED_LAST_ROW_COLOR = "rgba(54, 179, 54, 1)";
        public static final String DESELECTED_LAST_ROW_COLOR = "rgba(118, 204, 123, 1)";
        public static final String TOAST_MESSAGE_XPATH = "//div[@class='cdk-overlay-container']//span[contains(@class, 'Toast-message')]";
        public static final String UPDATED_STATUS_MESSAGE = "Publishing status updated";
        public static final String FIRST_SECTION_NODE_XPATH = "//div[contains(@class, 'ag-row')]/div[contains(@class, 'ag-cell')]" +
                "/span[contains(@class, ag-row-group)]//span[contains(text(), 'Sec') or contains(text(), '=')]";
        public static final String FIRST_SECTION_NODE_VALUE_XPATH = FIRST_SECTION_NODE_XPATH + "/../../..//div[@col-id='value']";
        public static final String NODE_HIERARCHY_COLUMN_VALUE_XPATH = "//div[@col-id='ag-Grid-AutoColumn']//span[text()='%s']";
        public static final String NODE_HIERARCHY_VALUE_COLUMN_XPATH = "//div[contains(@class, 'ag-center-cols-container')]//div[@col-id='value']//span[contains(text(),'%s')]";
        public static final String SELECTED_NODE_LAW_TRACKING_STATUS_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='lawTracking']";
        public static final String SELECTED_NODE_START_DATE_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='startDate']";
        public static final String SELECTED_NODE_END_DATE_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='endDate']";
        public static final String SELECTED_NODE_VALIDATION_STATUS_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='validationResponse']";
        public static final String SELECTED_NODE_PUBLISHING_STATUS_ID_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='publishStatusId']";
        public static final String SELECTED_NODE_PUBLISHING_STATUS_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='publishingStatus']";
        public static final String SELECTED_NODE_WORKFLOW_ID_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='workflowId']";
        public static final String SELECTED_NODE_STATUS_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='nodeStatus']";
        public static final String SELECTED_NODE_STATUS_SET_DATE_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='statusSetDate']";
        public static final String SELECTED_NODE_WL_LOAD_COMPLETE_DATE = "//div[contains(@class, 'selected')]//div[col-id='wlLoadCompleteDate']";

        public static final String SELECTED_NODE_READY_USER_COLUMN_VALUE_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='publishReadyUser']";
        public static final String SELECTED_NODE_READY_DATE_COLUMN_VALUE_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='publishReadyDate']";
        public static final String SELECTED_NODE_MODIFIED_BY_COLUMN_VALUE_XPATH = "//div[contains(@class, 'selected')]//div[@col-id='modifiedBy']";
        public static final String SELECTED_ROW_XPATH = "//div[contains(@class,'ag-body-viewport')]//div[contains(@class,'ag-row-selected')]";
        public static final String SELECTED_NODE_VOLUME_XPATH = "//div[contains(@class, 'ag-row-selected')]//div[@col-id='volume']";
        public static final String SELECTED_NODE_CODE_XPATH = "//div[contains(@class, 'ag-row-selected')]//div[@col-id='codeName']";
        public static final String SELECTED_NODE_KEYWORD_XPATH = "//div[contains(@class, 'ag-row-selected')]//div[@col-id='keyword']";
        public static final String SELECTED_NODE_VALUE_XPATH = "//div[contains(@class, 'ag-row-selected')]//div[@col-id='value']";
        public static final String SELECTED_NODE_PUBLISH_APPROVE_USER_XPATH = "//div[contains(@class, 'ag-row-selected')]//div[@col-id='publishApprovedUser']";
        public static final String SELECTED_NODE_PUBLISH_COMPLETE_DATE_XPATH = "//div[contains(@class, 'ag-row-selected')]//div[@col-id='publishCompleteDate']";
        public static final String SELECTED_NODE_DELETED_XPATH = "//div[contains(@class, 'ag-row-selected') and contains(@class, 'deleted')]";

        public static final String SELECTED_NODES_BY_HIERARCHY_COLUMN =  "//div[contains(@class, 'selected')]//div[@col-id='ag-Grid-AutoColumn']//span[text()='%s']";
        public static final String SELECTED_NODES_BY_VALUE_COLUMN =  "//div[contains(@class, 'selected')]//div[@col-id='value']//span[text()='%s']";
        public static final String SELECTED_NODES_BY_HIERARCHY_COLUMN_READY_STATUS =  "//div[contains(@class, 'ready-status')]//div[@col-id='ag-Grid-AutoColumn']//span[text()='%s']";
        public static final String VOLUME_SELECTION_IS_REQUIRED_XPATH =  "//div[@class='volume-selection-container']//span[contains(@class,'error')]//b[contains(text(), 'Volume Selection Is Required')]";
        public static final String SELECTED_VOLUMES_TEXT =  "//div[contains(@class, 'volume-selection-container')]//span[contains(@class,'ng-star-inserted') and contains(text(),'%s')]";

        public static final String ROW_XPATH = "//div[contains(@class,'ag-body-viewport')]//span[contains(text(), '%s')]/../../..";

        @FindBy(how = How.XPATH, using = "//div[@ref='eBodyContainer']")
        public static WebElement gridBody;

        @FindBy(how = How.XPATH, using = "//div[contains(@class, 'selected')]//div[@col-id='ag-Grid-AutoColumn']")
        public static WebElement selectedNodesInGrid;

        @FindBy(how = How.XPATH, using = "//div[contains(@class, 'selected')]")
        public static WebElement selectedNodes;

        @FindBy(how = How.XPATH, using = "//div[@class='volume-selection-container']//span[contains(@class,'error')]//b[contains(text(), 'Volume Selection Is Required')]")
        public static WebElement volumeSelectionIsRequired;
}
