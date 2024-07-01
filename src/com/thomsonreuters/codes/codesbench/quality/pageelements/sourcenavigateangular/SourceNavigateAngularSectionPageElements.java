package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaPageElements.SECTION_PROPERTIES_INPUT_FIELD;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;

public class SourceNavigateAngularSectionPageElements {
    public static final String SOURCE_NAV_SECTIONS_AG_GRID = "";
    public static final String FIRST_RENDITION_ROW = "(" + SOURCE_NAV_SECTIONS_AG_GRID + "//div[@row-id='0'])[2]";
    public static final String TOTAL_SECTIONS_NUMBERS = "//span[contains(text(),'Total sections:')]";
    public static final String TOTAL_SECTIONS = "//span[contains(text(),'Total sections: %s')]";
    public static final String TOTAL_SECETION_NUMBERS = "//span[contains(text(),'Total sections: ')]";
    public static final String CLEAR_FILTERS_SECTION_TAB = SOURCE_NAV_SECTION + CLEAR_FILTERS;
    public static final String EDIT_SECTION_INSTRUCTION_NOTES = "Edit Section Instruction Notes";
    public static final String SECTION_LOCKED_BY = "//p[contains(text(),' This Section is currently locked by ')]";
    public static final String VIEW_SECTION_INSTRUCTION_NOTES = "View Section Instruction Notes";
    public static final String NOTES_VALUE_OF_SECTION = "(//button[@class='grid-btn'])[2]";

    @FindBy(how = How.ID, using = "sect-note-text-area")
    public static WebElement sectionLevelinstructions;
    public static final String SECTION_PROPERTIES = "//span[text()='Section Properties']";
    public static String SECTION_PROPERTIES_BOX_LABEL = "//label[text()='%s']";
    public static String SECTION_PROPERTIES_TEXT_FIELD = SECTION_PROPERTIES_BOX_LABEL + "/following::input[1]";
    public static String SECTION_INSTRUCTIONS = SECTION_PROPERTIES_BOX_LABEL + "/textarea";
    public static String SECTION_PROPERTIES_DIFFICULTY_LEVEL_DROPDOWN = "//source-nav-single-select-combobox[@id='difficultyLevels']";
    public static String LOCKED_ALERT_MESSAGE = "//div[@class='alert bento-alert alert-danger']/span";
    public static String SECTION_PROPERTIES_INPUT_FIELDS = "//div[@class='tab-content']//input";
    public static String SYSTEM_PROPERTIES_INPUT_FIELD = "//source-nav-section-properties" + SECTION_PROPERTIES_INPUT_FIELD;
    public static String INSTRUCTION_NOTE_INPUT_FIELD = "//textarea[@id='instructionNote']";
    public static String SECTION_INSTRUCTION_NOTE_INPUT_FIELD = "//input[@id='%s']";

    public static String SUPPRESS_DATE_ON_WESTLAW_RADIOBUTTON= "//label[text()='%s']";
            //"//input[@class='ng-pristine ng-valid ng-touched'][@type='radio']";

}
