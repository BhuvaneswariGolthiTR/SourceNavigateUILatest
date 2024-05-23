package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InsertOpinionPopupElementsAngular
{
    public static final String PAGE_TAG = "//app-administrative-opinion-insert-modal";
    public static final String HEADER = PAGE_TAG + "//h5";
    public static final String TYPE_DROPDOWN = "//select[@id='opinionType']";
    public static final String CONTENT_SET_TEXT = "//span[@id='contentSetText']";
    public static final String OPINION_NUMBER_INPUT = "//input[@id='opinionName']";
    public static final String OPINION_DATE_INPUT = "//input[@id='opinionDate']";
    public static final String EDITOR_NAME = "//label[text() = 'Editor']/following-sibling::span";
    public static final String OPINION_TEXT = "//textarea[@id = 'opinionText']";
    public static final String OPINION_CITATION = "//input[@id = 'opinionCitation']";
    public static final String WL_NUMBER = "//input[@id = 'westlawNumber']";
    public static final String INSERT_BUTTON = "//button[text() = 'Insert']";
    public static final String CANCEL_BUTTON = "//button[text() = 'Cancel']";
    public static final String SAVE_AND_EDIT_CLASSIFY_BUTTON = "//button[text() = 'Save and Edit/Classify']";

    @FindBy(how = How.XPATH, using = TYPE_DROPDOWN)
    public static WebElement typeDropdown;

    @FindBy(how = How.XPATH, using = CONTENT_SET_TEXT)
    public static WebElement contentSetText;

    @FindBy(how = How.XPATH, using = OPINION_NUMBER_INPUT)
    public static WebElement opinionNumberInput;

    @FindBy(how = How.XPATH, using = OPINION_DATE_INPUT)
    public static WebElement opinionDateInput;

    @FindBy(how = How.XPATH, using = EDITOR_NAME)
    public static WebElement editorName;

    @FindBy(how = How.XPATH, using = OPINION_TEXT)
    public static WebElement opinionText;

    @FindBy(how = How.XPATH, using = OPINION_CITATION)
    public static WebElement opinionCitation;

    @FindBy(how = How.XPATH, using = WL_NUMBER)
    public static WebElement westlawNumber;

    @FindBy(how = How.XPATH, using = INSERT_BUTTON)
    public static WebElement insertButton;

    @FindBy(how = How.XPATH, using = CANCEL_BUTTON)
    public static WebElement cancelButton;

    @FindBy(how = How.XPATH, using = SAVE_AND_EDIT_CLASSIFY_BUTTON)
    public static WebElement saveAndEditClassifyButton;
}
