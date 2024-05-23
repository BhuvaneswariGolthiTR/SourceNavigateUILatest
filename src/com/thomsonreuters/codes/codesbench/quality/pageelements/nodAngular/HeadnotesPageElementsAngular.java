package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HeadnotesPageElementsAngular {

    public static final String PAGE_TAG = "//body/app-root/app-headnotes";
    public static final String LOADING_CIRCLE = "//div[@class='bento-busyloader-blocker md']";
    //RIGHT_PANE
    public static final String RIGHT_PANE = "//bento-splitter-group-main";
    //CASES_NAVIGATION_AREA
    public static final String CASE_BUTTONS = RIGHT_PANE + "//div[contains(@class, 'case-buttons')]";
    public static final String PREVIOUS_CASE_BUTTON = CASE_BUTTONS + "/button[text() = 'Previous Case']";
    public static final String NEXT_CASE_BUTTON = CASE_BUTTONS + "/button[text() = 'Next Case']";
    public static final String UNIGNORE_ALL_BUTTON = CASE_BUTTONS + "/button[text() = 'Unignore All']";
    public static final String IGNORE_ALL_BUTTON = CASE_BUTTONS + "/button[text() = 'Ignore All']";
    public static final String COMPLETED_BY_AND_DATE_BUTTON = CASE_BUTTONS + "/button[text() = 'Completed By and Date']";
    public static final String SIGN_OUT_CASE_BUTTON = CASE_BUTTONS + "/button[text() = 'Sign Out Case']";
    public static final String CLEAR_SIGN_OUT_CASE_BUTTON = CASE_BUTTONS + "/button[text() = 'Clear Sign Out']";
    //CASE_INFORMATION
    public static final String CASE_TITLE = RIGHT_PANE + "//h5";
    public static final String CASE_INFORMATION_TABLE = "//table[@id = 'caseInformation']";
    public static final String CASE_INFORMATION_COLUMN_VALUES = CASE_INFORMATION_TABLE + "/tbody/tr/td";
    public static final String HEADNOTE_HIGHLIGHTING_TEXT = "//em[@data-highlighted = 'true']";
    //BackGround
    public static final String BACKGROUND = "//*[contains(@class,'hierarchy-container')]";

    @FindBy(how = How.XPATH, using = CASE_TITLE)
    public static WebElement caseTitle;
}
