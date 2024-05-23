package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HeadnoteDetailsPopupElementsAngular
{

    public static final String PAGE_TAG = "//app-headnote-details";
    public static final String HEADER = PAGE_TAG + "//h5";
    public static final String HEADNOTE = "//dt[text() = 'Headnote']";
    public static final String TOPIC_AND_KEY_NUMBER = "//dt[text() = 'Topic & Key Number']";
    public static final String CONTENT_SET = "//dt[text() = 'Content Set']";
    public static final String CLASSIFICATION = "//dt[text() = 'Classification']";
    public static final String FULL_TEXT = "//dt[text() = 'Full Text']";
    public static final String HEADNOTE_VALUE = "//dd[1]";
    public static final String TOPIC_AND_KEY_NUMBER_VALUE = "//dd[2]";
    public static final String CONTENT_SET_VALUE = "//dd[3]";
    public static final String CLASSIFICATION_VALUE = "//dd[4]/table";
    public static final String FULL_TEXT_VALUE = "//dd[5]";
    public static final String TEXTAREA = "//app-headnote-details//textarea";
    public static final String DIGESTS = "//dt[text() = 'Digests']";
    public static final String DIGESTS_VALUE = "//dd[2]";
    public static final String PAGINATION = "//ul[@class='pagination']";
    public static final String ARROW_TO_THE_NEXT = PAGINATION + "/li[last()-1]";
    public static final String ARROW_TO_THE_LAST = PAGINATION + "/li[last()]";
    public static final String ARROW_TO_PREVIOUS = PAGINATION + "/li[2]";
    public static final String ARROW_TO_THE_FIRST = PAGINATION + "/li[1]";
    public static final String GO_BY_NUMBER = PAGINATION + "/li[text()='%s']";
    public static final String PAGE_NUMBERS = PAGINATION + "/li[position() >= 3 and position() <= last() -2]";
    public static final String BLUELINE_BY_NUMBER = PAGE_NUMBERS + "/a[contains(text(), '%s')]";
    public static final String SAVE_BUTTON = "//app-headnote-details//button[text() = 'Save']";
    public static final String CANCEL_BUTTON = "//app-headnote-details//button[text() = 'Cancel']";
    public static final String ALL_INFORMATION = "//dl[@class='details-content' and .//text()]";

    @FindBy(how = How.XPATH, using = HEADER)
    public static WebElement header;

    @FindBy(how = How.XPATH, using = SAVE_BUTTON)
    public static WebElement saveButton;

    @FindBy(how = How.XPATH, using = TEXTAREA)
    public static WebElement textArea;



}
