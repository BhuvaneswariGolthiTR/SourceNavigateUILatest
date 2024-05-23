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

}
