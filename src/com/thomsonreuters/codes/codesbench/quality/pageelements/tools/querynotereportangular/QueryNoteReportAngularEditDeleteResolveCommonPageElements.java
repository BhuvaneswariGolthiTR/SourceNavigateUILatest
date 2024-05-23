package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPageElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.modal.SpellcheckReportModalElements.SPELLCHECK_REPORT_LINK;

public class QueryNoteReportAngularEditDeleteResolveCommonPageElements
{
    /**
     * Values
     */
    public static final String MODAL_BODY = "//div[@class = 'modal-body']";
    public static final String QUERY_NOTE_UUID = MODAL_BODY + "//label[contains(text(), 'Query Note UUID')]/following-sibling::span";
    public static final String TYPE = MODAL_BODY + "//label[contains(text(), 'Type')]/following-sibling::span";
    public static final String CONTENT_SET = MODAL_BODY + "//label[contains(text(), 'Content Set')]/following-sibling::span";
    public static final String VOLS = MODAL_BODY + "//label[contains(text(), 'Vols')]/following-sibling::span";
    public static final String CODE = MODAL_BODY + "//label[contains(text(), 'Code')]/following-sibling::span";
    public static final String KEYWORD = MODAL_BODY + "//label[contains(text(), 'Keyword')]/following-sibling::span";
    public static final String VALUE = MODAL_BODY + "//label[contains(text(), 'Value')]/following-sibling::span";
    public static final String START_DATE = MODAL_BODY + "//label[contains(text(), 'Start Date')]/following-sibling::span";
    public static final String END_DATE = MODAL_BODY + "//label[contains(text(), 'End Date')]/following-sibling::span";
    public static final String ACTION_DATE = MODAL_BODY + "//label[contains(text(), 'Action Date (MM/DD/YYYY)')]/following-sibling::span";
    public static final String CREATED_BY = MODAL_BODY + "//label[contains(text(), 'Created By')]/following-sibling::span";
    public static final String CREATED_DATE = MODAL_BODY + "//label[contains(text(), 'Created Date')]/following-sibling::span";
    public static final String QUERY_NOTE_STATUS = MODAL_BODY + "//label[contains(text(), 'Query Note Status')]/following-sibling::span";
    public static final String RESOLVED_BY = MODAL_BODY + "//label[contains(text(), 'Resolved By')]/following-sibling::span";
    public static final String RESOLVED_DATE = MODAL_BODY + "//label[contains(text(), 'Resolved Date')]/following-sibling::span";
    public static final String RESOLVED_COMMENT = MODAL_BODY + "//label[contains(text(), 'Resolved Comment')]/following-sibling::span";
    public static final String DELETED_BY = MODAL_BODY + "//label[contains(text(), 'Deleted By')]/following-sibling::span";
    public static final String DELETED_DATE = MODAL_BODY + "//label[contains(text(), 'Deleted Date')]/following-sibling::span";
    public static final String PRODUCT_TYPE = MODAL_BODY + "//label[contains(text(), 'Product Type')]/following-sibling::span";
    public static final String VIEW_TAG = MODAL_BODY + "//label[contains(text(), 'View/Tag')]/following-sibling::span";
    public static final String PRODUCT_NAME = MODAL_BODY + "//label[contains(text(), 'Product Name')]/following-sibling::span";

    /**
     *  Buttons/webelements
     */
    @FindBy(how = How.ID, using = "query_note_text")
    public static WebElement queryNoteText;

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Cancel')]")
    public static WebElement cancelButton;

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Submit')]")
    public static WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Delete')]")
    public static WebElement deleteButton;

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Save')]")
    public static WebElement saveButton;

}
