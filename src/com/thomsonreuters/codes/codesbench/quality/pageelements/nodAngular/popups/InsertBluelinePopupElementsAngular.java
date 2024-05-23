package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InsertBluelinePopupElementsAngular
{
    public static final String PAGE_TAG = "//app-blueline-properties";
    public static final String HEADER = PAGE_TAG + "//h5";
    public static final String SELECTED_STATUTE = PAGE_TAG + "//span[text() = 'Selected Statute: ']";
    public static final String BREADCRUMBS = SELECTED_STATUTE + "/following-sibling::span";
    public static final String BLUELINE_NUMBER_HEADER = PAGE_TAG + "//span[text() = 'Blueline Number: ']";
    public static final String BLUELINE_NUMBER_INPUT = BLUELINE_NUMBER_HEADER + "/following-sibling::span/input";
    public static final String NEXT_BUTTON = PAGE_TAG + "//button[text() = ' Next']";
    public static final String ERROR_MESSAGE_BY_TEXT = PAGE_TAG + "//bento-alert-item//span[contains(text(), '%s')]";
    public static final String ERROR_MESSAGE = PAGE_TAG + "//bento-alert-item//span";

    public static final String CANCEL_BUTTON = PAGE_TAG + "//button[text() = 'Cancel']";
    public static final String SAVE_BUTTON = PAGE_TAG + "//button[text() = 'Save']";
    public static final String INSERT_BUTTON = PAGE_TAG + "//button[text() = 'Insert']";
    public static final String DELETE_BUTTON = PAGE_TAG + "//button[text() = 'Delete']";
    public static final String BLUELINE_TYPE = PAGE_TAG + "//span[contains(@class, 'blueline-type')]";
    public static final String FLUSH_CHECKBOX = BLUELINE_TYPE + "/label[text() = 'Flush']/preceding-sibling::input";
    public static final String INDENT_CHECKBOX = BLUELINE_TYPE + "/label[text() = 'Indent']/preceding-sibling::input";
    public static final String START_OF_INDENT_CHECKBOX = BLUELINE_TYPE + "/label[text() = 'Start of Indent']/preceding-sibling::input";
    public static final String TEXT_INPUT = "//span[text() = 'Text: ']/following-sibling::span/input";
    public static final String SECOND_TEXT_INPUT = "//span[text() = 'Text: ']/ancestor::div[@class='mb-2']/label[2]//input";
    public static final String TEXT_INPUT_DROPDOWN = "//ngb-typeahead-window[contains(@class, 'dropdown-menu')]";
    public static final String TEXT_INPUT_DROPDOWN_BUTTON_BY_TEXT = TEXT_INPUT_DROPDOWN + "//ngb-highlight[normalize-space()='%s']/parent::button";
    public static final String FLUSH_ANALYSIS_INPUT = PAGE_TAG + "//span[text() = 'Flush Analysis (NAL): ']/following-sibling::input";
    public static final String INDENT_ANALYSIS_INPUT = PAGE_TAG + "//span[text() = 'Indent Analysis (NAL2): ']/following-sibling::input";
    public static final String FLUSH_ANALYSIS_INDEX_ALSO_INPUT = PAGE_TAG + "//span[text() = 'Flush Analysis (NAL) - Index Also: ']/following-sibling::div//input";
    public static final String TAG = "//div[contains(@class, 'bento-tags-input-pill-label')]/ancestor::bento-tags-input-pill";
    public static final String TAG_BY_TEXT = "//div[contains(@class, 'bento-tags-input-pill-label') and normalize-space() = '%s']/ancestor::bento-tags-input-pill";
    public static final String TAG_CLOSE_BUTTON_BY_TAG_TEXT = "//button[contains(@aria-label, '%s')]/i";

    @FindBy(how = How.XPATH, using = INSERT_BUTTON)
    public static WebElement insertButton;

    @FindBy(how = How.XPATH, using = DELETE_BUTTON)
    public static WebElement deleteButton;

    @FindBy(how = How.XPATH, using = SAVE_BUTTON)
    public static WebElement saveButton;

    @FindBy(how = How.XPATH, using = CANCEL_BUTTON)
    public static WebElement cancelButton;

    @FindBy(how = How.XPATH, using = FLUSH_CHECKBOX)
    public static WebElement flushCheckbox;

    @FindBy(how = How.XPATH, using = INDENT_CHECKBOX)
    public static WebElement indentCheckbox;

    @FindBy(how = How.XPATH, using = START_OF_INDENT_CHECKBOX)
    public static WebElement startOFIndentCheckbox;


    //TEXTS
    public static final String HEADER_TEXT = "Insert Blueline - Step %s of 2";
    public static final String ERROR_BLUELINE_NUMBER_ALREADY_EXISTS = "The blueline number specified is not valid. " +
            "A blueline already exists with this number under the analysis node.";
    public static final String GAP_WARNING = "Gap Warning: The last blueline number before this one was: '%s'. " +
            "Press 'Next' button again to ignore this warning.";

}
