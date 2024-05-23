package com.thomsonreuters.codes.codesbench.quality.pageelements.nortextractor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NortExtractorPageElements
{
    public static final String PAGE_URL = "http://c524scecpub1.int.thomsonreuters.com:9244/nortExtractor/";
    public static final String PAGE_TITLE = "Nort Extractor";

    public static final String PAGE_HEADER_XPATH  = "//h1[text()='Nort Extractor']";
    public static final String ID_LIST_INPUT_XPATH  = "//input[@id='idList']";

    private static final String CONVERSION_TYPE_SWITCH_XPATH = "//div[@id='conversion-type-switch']";
    public static final String TARGET_CONVESION_RADIO_BUTTON_XPATH = CONVERSION_TYPE_SWITCH_XPATH + "//input[@value='TARGET']";
    public static final String STATUTE_HISTORY_IDS_RADIO_BUTTON_XPATH = CONVERSION_TYPE_SWITCH_XPATH + "//input[@value='STATUTE_HISTORY_IDS']";
    public static final String SOURCE_CONVERSION_TAX_TYPE_RADIO_BUTTON_XPATH = CONVERSION_TYPE_SWITCH_XPATH + "//input[@value='SOURCE_TAX_TYPE']";
    public static final String SOURCE_CONVERSION_AMENDMENT_NOTES_RADIO_BUTTON_XPATH = CONVERSION_TYPE_SWITCH_XPATH + "//input[@value='SOURCE_AMENDMENT_NOTES']";

    //home page hidden menus
    private static final String HIDDEN_MENUS_XPATH = "//div[@id='all-hidden-menus']";
    public static final String SOURCE_CONVERSION_TAX_TYPE_HIDDEN_MENU_XPATH = HIDDEN_MENUS_XPATH + "//div[@id='source-conversion-inputs']";
    public static final String SOURCE_CONVERSION_AMENDMENT_NOTES_HIDDEN_MENU_XPATH = HIDDEN_MENUS_XPATH + "//div[@id='source-amendment-notes-menu']";
    public static final String AMENDMENT_NOTES_ENVIRONMENT_DROPDOWN_XPATH = HIDDEN_MENUS_XPATH + "//select[@id='source-amendment-notes-environments']";
    public static final String AMENDMENT_NOTES_CONTENT_SET_DROPDOWN_XPATH = HIDDEN_MENUS_XPATH + "//select[contains(@id,'source-amendment-notes-content-set-names')]";
    public static final String TAX_TYPE_ENVIRONMENT_DROPDOWN_XPATH = HIDDEN_MENUS_XPATH + "//select[@id='source-conversion-environments-input']";
    public static final String TAX_TYPE_CONTENT_SET_DROPDOWN_XPATH = HIDDEN_MENUS_XPATH + "//select[contains(@id,'source-conversion-content-set-names-select')]";
    public static final String DOC_NAME_INPUT_XPATH = HIDDEN_MENUS_XPATH + "//input[@id='source-conversion-doc-name-input']";
    public static final String ATTORNEY_TOPIC_GAP_CHECKBOX_XPATH = HIDDEN_MENUS_XPATH + "//input[@id='attorney-topic-gap-checkbox']";
    public static final String REPEAL_DOC_GAP_CHECKBOX_XPATH = HIDDEN_MENUS_XPATH + "//input[@id='repeal-doc-gap-checkbox']";
    public static final String NEW_CODE_GAP_CHECKBOX_XPATH = HIDDEN_MENUS_XPATH + "//input[@id='new-code-gap-checkbox']";
    public static final String ATTORNEY_TOPIC_GAP_DOC_NAME_INPUT_XPATH = HIDDEN_MENUS_XPATH + "//input[@id='attorney-topic-gap-doc-name']";
    public static final String REPEAL_DOC_GAP_DOC_NAME_INPUT_XPATH = HIDDEN_MENUS_XPATH + "//input[@id='repeal-doc-gap-doc-name']";
    public static final String NEW_CODE_GAP_DOC_NAME_INPUT_XPATH = HIDDEN_MENUS_XPATH + "//input[@id='new-code-gap-doc-name']";
    public static final String ATTORNEY_TOPIC_GAP_CHOOSE_FILE_XPATH = HIDDEN_MENUS_XPATH + "//input[@id='attorney-topic-gap-file']";
    public static final String REPEAL_DOC_GAP_CHOOSE_FILE_XPATH = HIDDEN_MENUS_XPATH + "//input[@id='repeal-doc-gap-file']";
    public static final String NEW_CODE_GAP_CHOOSE_FILE_XPATH = HIDDEN_MENUS_XPATH + "//input[@id='new-code-gap-file']";
    //public static final String CONTENT_SETS_LIST_XPATH = HIDDEN_MENUS_XPATH + "//";

    public static final String EXTRACTED_FILE_PARAGRAPH_XPATH = "//p[contains(text(),'Extracted File:')]";
    public static final String CONVERTED_FILES_PARAGRAPH_XPATH = "//p[contains(text(),'Converted Files:')]";
    public static final String CHECK_WORKFLOWS_PARAGRAPH_XPATH =  "//p[contains(text(),'Check workflows:')]";
    public static final String EXTRACTED_FILE_URL_XPATH = EXTRACTED_FILE_PARAGRAPH_XPATH +  "/following-sibling::a";
    public static final String CONVERTED_FILES_URL_XPATH = "//div[@id='converted-file-names']/a";
    public static final String CHECK_WORKFLOWS_URL_XPATH = "//div[@id='workflows']/a";

    @FindBy(how = How.ID, using = "submitButton")
    public static WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//input[@value='Reset']")
    public static WebElement resetButton;

    @FindBy(how = How.XPATH, using = "//input[@value='Log Out']")
    public static WebElement logoutButton;

    @FindBy(how = How.XPATH, using = "//button[text()='Back']")
    public static WebElement backButton;
}
