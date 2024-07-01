package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.FIRST_RENDITION_ROW;

public class SourceNavigateAngularRenditionPageElements
{
    //lock icon
    public static final String lockIcon = "//source-nav-lock-renderer/em[contains(@class,'custom-grid-cell-icon bento-icon-lock')]";
    public static final String EDIT_RENDITION_INSTRUCTION_NOTES = "Edit Rendition Instruction Notes";
    public static final String RENDITION_LOCKED_BY ="//p[contains(text(),' This Rendition is currently locked by ')]";
    public static final String VIEW_RENDITION_INSTRUCTION_NOTES = "View Rendition Instruction Notes";
    public static final String IMAGES_SENT_OUT = "//div[@class='ag-menu-option']/span[contains(text(),'Images Sent Out')]";
    public static final String CLEAN = "//div[@class='ag-menu-option']/span[contains(text(),'Clean')]";
  //  public static final String FIRST_RENDITION_TRACKING_STATUS = FIRST_RENDITION_ROW + "//div[@col-id='renditionTrackingStatus']";
    public static final String FIRST_RENDITION_TRACKING_STATUS = "(//div[contains(@class, 'selected')]//div[@col-id='renditionTrackingStatus'])";
    public static final String CLEANED_DATE = "(//div[contains(@class, 'selected')]//div[@col-id='cleanedDate'])[1]";
    @FindBy(how = How.ID, using = "rend-note-text-area")
    public static WebElement renditionLevelinstructions;

    @FindBy(how = How.XPATH, using = "//button[@id='save-note-btn']")
    public static WebElement submit;

    public static final String INTEGRATION_EFFECTIVE_DATE = "//label[text()=' Effective Date ']";
    public static final String EFFECTIVE_DATE_PICKER = "//input[@id='effectiveDatePicker']";
    public static final String CALENDER = "//button[@aria-label='Open calendar']";
    public static final String GENERAL_EFFECTIVE_DATE = "//label[@class='my-2 ng-star-inserted']/bento-toggle";
    public static final String SUPPRESS_DATE_ON_WESLAW = "//div[text()='Suppress Date on Westlaw:']";
    public static final String RADIO_BUTTON = "//label[text()='%s']";
    public static final String INSTRUCTIONS_NOTE = "//label[text()='Instruction Note']";
    public static final String ADD_BUTTON= "//button[text()='Add ']";
    public static final String SUBMIT_BUTTON= "//div[@class='modal-footer py-1']/button[text()=' Submit ']";
    public static final String INTEGRATION_CANCEL_BUTTON="//div[@class='modal-footer py-1']/button[text()='Cancel']";
    public static final String INTEGRATION_CLOSE_ICON="//button[@class='close-btn']";
    public static final String ERROR_MESSAGE= "//div[@class='invalid-tooltip ng-star-inserted']";
    public static final String TEMPLATE_NOTES= "//div[@class='btn btn-icon ng-star-inserted']";
    public static final String INSTRUCTIONS_NOTES_DROPDOWN_VALUE="//div[@class='ng-star-inserted']/div[text()='%s']";
    public static final String GENERAL_EFFECTIVE_TOGGLE="//bento-toggle[@class='mr-2 bento-toggle bui-menu-item ng-untouched ng-pristine ng-valid']";




}
