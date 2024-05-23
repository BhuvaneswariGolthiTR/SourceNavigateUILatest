package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class KeywordFindPopupElementsAngular
{
    public static final String PAGE_TAG = "//app-keyword-find";
    public static final String HEADER = PAGE_TAG + "//h5";
    public static final String FIRST_SELECT = PAGE_TAG + "//div[1]/select";
    public static final String FIRST_INPUT = PAGE_TAG + "//div[1]/input";
    public static final String SECOND_SELECT = PAGE_TAG + "//div[2]/select";
    public static final String SECOND_INPUT = PAGE_TAG + "//div[2]/input";
    public static final String THIRD_SELECT = PAGE_TAG + "//div[3]/select";
    public static final String THIRD_INPUT = PAGE_TAG + "//div[3]/input";
    public static final String SEARCH_BUTTON = PAGE_TAG + "//span[text() = 'Search']/ancestor::button";
    public static final String CANCEL_BUTTON = PAGE_TAG + "//button[text() = 'Cancel']";

    @FindBy(how = How.XPATH, using = SEARCH_BUTTON)
    public static WebElement searchButton;

    @FindBy(how = How.XPATH, using = CANCEL_BUTTON)
    public static WebElement cancelButton;

    @FindBy(how = How.XPATH, using = FIRST_SELECT)
    public static WebElement firstSelect;

    @FindBy(how = How.XPATH, using = FIRST_INPUT)
    public static WebElement firstInput;

    @FindBy(how = How.XPATH, using = SECOND_SELECT)
    public static WebElement secondSelect;

    @FindBy(how = How.XPATH, using = SECOND_INPUT)
    public static WebElement secondInput;

    @FindBy(how = How.XPATH, using = THIRD_SELECT)
    public static WebElement thirdSelect;

    @FindBy(how = How.XPATH, using = THIRD_INPUT)
    public static WebElement thirdInput;

}
