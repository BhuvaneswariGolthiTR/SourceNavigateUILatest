package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class GridContextMenuElements
{
        private final static String GENERAL_GRID_MENU_XPATH = "//div[contains(@class, 'ag-menu')]";
        public final static String READY_STATUS_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Ready Status']/..";
        public final static String REMOVE_READY_STATUS_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Remove Ready Status']/..";
        public final static String APPROVED_STATUS_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Approved Status']/..";
        public final static String REMOVE_APPROVED_STATUS_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Remove Approved Status']/..";
        public final static String SELECT_FOR_UPDATED_STATUS_XPATH = GENERAL_GRID_MENU_XPATH +  "//div[span[text()='Select for Updated Status']]";
        public final static String REMOVE_SELECTION_XPATH = GENERAL_GRID_MENU_XPATH +  "//div[span[text()='Remove Selection']]";
        public final static String FIND_DOCUMENT_IN_WIP_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Find document in WIP']";
        public final static String FIND_DOCUMENT_IN_HIERARCHY_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Find document in hierarchy']";
        public final static String FIND_DOCUMENT_IN_PUB_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Find document in PUB']";
        public final static String FIND_PUBLISHING_WORKFLOW_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Find Publishing Workflow']";
        public final static String NOT_PUBLISHED_STATUS_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Not Published Status']";
        public final static String REMOVE_NOT_PUBLISHED_STATUS_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Remove Not Published Status']";
        public final static String COPY_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Copy']";
        public final static String COPY_WITH_HEADERS_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Copy with Headers']";
        public final static String EXPORT_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Export']";
        public final static String  MASS_SELECTION_XPATH = GENERAL_GRID_MENU_XPATH +  "//span[text()='Mass Selection']/../span[@ref='ePopupPointer']";
        public final static String  MASS_SELECTION_REMOVE_READY_STATUS_XPATH = "(//div[contains(@class, 'ag-theme-balham ag-popup')])[2]//span[text()='Remove Ready Status']/../span[@ref='ePopupPointer']";
        public final static String  MASS_SELECTION_READY_STATUS_XPATH = "(//div[contains(@class, 'ag-theme-balham ag-popup')])[2]//span[text()='Ready Status']/../span[@ref='ePopupPointer']";
        public final static String  MASS_SELECTION_REMOVE_APPROVED_STATUS_XPATH = "(//div[contains(@class, 'ag-theme-balham ag-popup')])[2]//span[contains(text(),'Remove Approved Status')]/../span[@ref='ePopupPointer']";
        public final static String  MASS_SELECTION_APPROVED_STATUS_XPATH = "(//div[contains(@class, 'ag-theme-balham ag-popup')])[2]//span[text()='Approved Status']/../span[@ref='ePopupPointer']";
        public final static String  MASS_SELECTION_SELECT_FOR_UPDATED_STATUS_XPATH = "(//div[contains(@class, 'ag-theme-balham ag-popup')])[2]//span[text()='Select for Updated Status']/../span[@ref='ePopupPointer']";
        public final static String  MASS_SELECTION_All_DOCS_SAME_VOLUME_XPATH = "(//div[contains(@class, 'ag-theme-balham ag-popup')])[3]//span[text()='All documents in same volume']/../span[@ref='ePopupPointer']";
        public final static String  MASS_SELECTION_ALL_DOCS_SAME_CODE_XPATH = "(//div[contains(@class, 'ag-theme-balham ag-popup')])[3]//span[text()='All documents with same code']/../span[@ref='ePopupPointer']";

        public final static String  MASS_SELECTION_REMOVE_SELECTION_XPATH = "(//div[contains(@class, 'ag-theme-balham ag-popup')])[2]//span[text()='Remove Selection']/../span[@ref='ePopupPointer']";
        public final static String  ALL_DOCUMENTS_IN_SAME_VOLUME_XPATH = "//div[contains(@class, 'ag-menu')]//span[text()='All documents in same volume']";
        public final static String  ALL_DOCUMENTS_WITH_SAME_CODE_XPATH = "//div[contains(@class, 'ag-menu')]//span[text()='All documents with same code']";
        public final static String  ALL_DOCUMENTS_MODIFIED_BY_SAME_USER_XPATH = "//div[contains(@class, 'ag-menu')]//span[text()='All documents modified by same user']";
        public final static String  ALL_DOCUMENTS_THAT_INCLUDE_LAW_TRACKING_VALUE_XPATH = "//div[contains(@class, 'ag-menu')]//span[text()='All documents that include Law Tracking Value']/../span[@ref='ePopupPointer']";
        public final static String  ALL_DOCUMENTS_THAT_INCLUDE_LAW_TRACKING_VALUE_QUICK_LOAD_XPATH = "//div[contains(@class, 'ag-menu')]//span[text()='Quick Load']";

        @FindBy(xpath = "//div[contains(@class, 'ag-menu')]//span[text()='Ready Status']")
        public static WebElement readyStatus;

        @FindBy(xpath = "//div[contains(@class, 'ag-menu')]//span[text()='Approved Status']")
        public static WebElement approvedStatus;

        @FindBy(xpath = "//div[contains(@class, 'ag-menu')]//span[text()='Remove Approved Status']")
        public static WebElement removeApprovedStatus;

        @FindBy(xpath = "//div[contains(@class, 'ag-menu')]//span[text()='Select for Updated Status']")
        public static WebElement selectForUpdatedStatus;

        @FindBy(xpath = "//div[contains(@class, 'ag-menu')]//span[text()='Find document in WIP']")
        public static WebElement findDocumentInWip;

        @FindBy(xpath = "//div[contains(@class, 'ag-menu')]//span[text()='Find document in hierarchy']")
        public static WebElement findDocumentInHierarchy;

        @FindBy(xpath = "//div[contains(@class, 'ag-menu')]//span[text()='Find Publishing Workflow']")
        public static WebElement findPublishingWorkflow;

        @FindBy(xpath = "//div[contains(@class, 'ag-menu')]//span[text()='Find document in PUB']")
        public static WebElement findDocumentInPub;

        @FindBy(xpath = "//div[contains(@class, 'ag-menu')]//span[text()='Not Published Status']")
        public static WebElement notPublishedStatus;

        @FindBy(xpath = "//div[@class='ag-menu']//span[text()='Remove Not Published Status']")
        public static WebElement removeNotPublishedStatus;

        @FindBy(xpath = "//div[@class='ag-menu']//span[text()='Copy']")
        public static WebElement copy;

        @FindBy(xpath = "//div[@class='ag-menu']//span[text()='Copy with Headers']")
        public static WebElement copyWithHeaders;

        @FindBy(xpath = "//div[@class='ag-menu']//span[text()='Export']")
        public static WebElement export;
}