package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.BUTTON;

public class SourceNavigateAngularPopUpPageElements
{
    public static final String HEADER = "//h5";
    public static final String UI_TITLE_PATTERN = HEADER + "[text()='%s']";
    public static final String UI_HEADING = HEADER + "/ancestor::div[contains(@class,'modal-header')]";
    public static final String UI_HEADING_SECOND_LEVEL = UI_TITLE_PATTERN + "/following::h5[text()='%s']";
    public static final String CLOSE_UI_BUTTON = UI_TITLE_PATTERN + "/following::i[contains(@class,'close-x cross')]";
    public static final String CONFIRM_BUTTON = BUTTON + "[text()='Confirm']";
    public static final String CANCEL_BUTTON = BUTTON + "[text()='Cancel']";
    public static String Overwrite_Currently_Selected_View_RadBtn = "//label[contains(text(), 'Overwrite Currently Selected View')]/input";
    public static String Save_And_Select_New_View = "//label[contains(text(), 'Save and Select New View')]/input";

    // ---------- UI Titles ----------
    public static final String INDEXING = "Indexing";
    public static final String SPELLCHECK = "Spellcheck";

    // ---------- Miscellaneous ----------
    public static final String WORKFLOW_ID_HYPERLINK_PATTERN = "//a[contains(@href,'workflowId=%s')]";
    public static final String WORKFLOW_HYPERLINK = "//a[contains(@href,'workflowId')]";
    public static final String MISSPELLED_WORD_CONTAINER = "//div[@class='misspelled-words-container']/span";
    public static final String SPELLCHECK_ITEM = "//span[contains(@class,'misspelled')]";
    public static final String SPELLCHECK_INFO = "//div[@class='modal-body']";
    public static final String EFFECTIVE_DATE = "Effective Date";

    //-----------------Difficulty Level window elements -----------------------
    public static final String DIFFICULTY_LEVEL_MODAL = "//source-nav-difficulty-level";
    public static final String DIFFICULTY_LEVEL_DROPDOWN = DIFFICULTY_LEVEL_MODAL + "//select[@id='difficultyLevel']";
    public static final String DIFFICULTY_LEVEL_DROPDOWN_OPTION = "//option[text()='%s']";
    public static final String ANY_BUTTON = DIFFICULTY_LEVEL_MODAL + "//button[contains(text(),'%s')]";
    public static final String SOURCE_NAV_ASSIGN_USER_CANCEL = "//source-nav-confirmation" + CANCEL_BUTTON;
}
