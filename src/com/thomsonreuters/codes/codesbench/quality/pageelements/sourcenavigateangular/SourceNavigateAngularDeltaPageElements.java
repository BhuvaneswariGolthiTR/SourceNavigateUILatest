package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;

public class SourceNavigateAngularDeltaPageElements
{
    public static final String TOTAL_DELTAS_NUMBERS = "//span[contains(text(),'Total deltas:')]";
    public static final String TOTAL_DELTA_NUMBERS= "//span[contains(text(),' Total deltas: ')]";
    public static final String TOTAL_DELTAS= "//span[contains(text(),' Total deltas: %s')]";
    public static final String CLEAR_FILTERS_DELTA_TAB =  SOURCE_NAV_DELTAS + CLEAR_FILTERS;
    public static final String EDIT_DELTA_INSTRUCTION_NOTES = "Edit Delta Instruction Notes";
    public static final String DELTA_LOCKED_BY ="//p[contains(text(),' This Delta is currently locked by ')]";
    public static final String VIEW_DELTA_INSTRUCTION_NOTES = "View Delta Instruction Notes";

    @FindBy(how = How.ID, using = "delta-note-text-area")
    public static WebElement deltaLevelinstructions;
    public static final String ASSIGNED_USER_DROPDOWN="//source-nav-assign-user-selector[@id='assigned-user']";
    public static final String DELTA_PROPERTIES_HEADER = "//div[@class='modal-header modal-header-override']";
    public static final String DELTA_CANCEL_BUTTON = "//button[text()='Cancel']";
    public static final String DELTA_SUBMIT_BUTTON = "//button[contains(text(),' Submit ')]";
    public static final String DELTA_CLOSE_BUTTON = "//button[@class='close-btn']";
    public static String DELTA_PROPERTIES_DROPDOWN_ELEMENTS ="//div[contains(@class,'group')]/label[text()='%s']";
    public static String DELTA_PROPERTIES_VIEWMODE_ELEMENTS= "//label[text()='%s']//input[@class='form-control']";

    public static final String EFFECTIVE_CALENDER= "//div/input[@id='effectiveDate']/following-sibling::button[@title='Open calendar']";
    public static String DELTA_DIFFICULTY_LEVEL_DROPDOWN="//source-nav-single-select-combobox[@id='difficultyLevels']";
    public static final String ASSIGNED_CALENDER= "//div/input[@id='assignedToDate']/following-sibling::button[@title='Open calendar']";
    public static final String DELTA_PROPERTIES_DESCRIPTION="//textarea[contains(@class,'instructions-area full-width ng-untouched ng-pristine')]";
    public static  String DIFFICULTY_LEVEL_DROPDOWN_VALUE="//div[text()='%s']";
    public static final String DELTA_PROPERTIES_LOCKED_HEADER = "//div[@class='alert bento-alert alert-danger']";
    public static final String ASSIGNED_DATE_PICKER="//input[@id='assignedToDate']";
    public static String SECTION_PROPERTIES_BOX_LABEL = "//label[text()='%s']";
    public static final String LABEL_TEXT_FIELD = SECTION_PROPERTIES_BOX_LABEL + "/following::input[1]";
    public static final String OWNER_DATA = SECTION_PROPERTIES_BOX_LABEL + "/following-sibling::input[1]";
    public static final String SECTION_PROPERTIES_INPUT_FIELD = SECTION_PROPERTIES_BOX_LABEL + "/input";
    public static final String PREP_TRACKING_FIRST_HALF = "//div[@class='properties-container mb-3']/div[1]"+SECTION_PROPERTIES_BOX_LABEL;
    public static final String PREP_TRACKING_SECOND_HALF = "//div[@class='properties-container mb-3']/div[2]"+SECTION_PROPERTIES_BOX_LABEL;
    public static final String DELTA_PROPERTIES_SUBMIT_BUTTON="//source-nav-delta-properties//div/button[text()=' Submit ']";
    public static final String DELTA_PROPERTIES_INPUT_FIELD = "//source-nav-delta-properties" + SECTION_PROPERTIES_INPUT_FIELD;
    public static String CHECKBOX_INPUT_FIELD = "//bento-checkbox/input[@id='%s']";
    public static final String PREP_TRACKING_COMBO_BOX = SECTION_PROPERTIES_BOX_LABEL+"/following::bento-combobox";
    public static String COMBO_BOX_LIST = "//bento-combobox-list//div[@aria-setsize='3']/div/div[text()='%s']";
}
