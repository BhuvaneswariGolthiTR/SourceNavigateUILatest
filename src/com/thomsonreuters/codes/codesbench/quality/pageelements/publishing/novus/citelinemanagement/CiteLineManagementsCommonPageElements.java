package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CiteLineManagementsCommonPageElements
{
    public static final String PAGE_TITLE_URL = "cite-line";

    public static final String FIRST_CITE_IN_GRID = "//div[@class='ag-center-cols-container']//div[@row-index='0']";
    public static final String DELETE = "//div[@row-id='%s']//button[@title='Delete']";
    public static final String EDIT = "//div[@row-id='%s']//button[@title='Edit']";
    public static final String CITE_LINE_ADD = "//div[@class='cdk-overlay-pane']//h1[contains(text(),'Cite Line Add')]";
    public static final String CITE_LINE_EDIT = "//div[@class='cdk-overlay-pane']//h1[contains(text(),'Cite Line Edit')]";
    public static final String CANCEL_BUTTON_XPATH = "//button[@id='cancel']";
    public static final String REQUIRED = "/descendant::strong[contains(text(), 'required')]";
    private static final String GENERAL_TAB_XPATH = "//toolbox-cite-line//div[contains(@class,'mat-tab-label-container')]";
    public static final String CREATE_BUTTON_XPATH = "//button[@id='createCitelineButton']";
    public static final String CREATE_ANOTHER_BUTTON_XPATH = "//button[@id='createAnotherCitelineButton']";
    public static final String UPDATE_VALUES_BUTTON_XPATH = "//button[@id='updateCitelineButton']";

    @FindBy(how = How.XPATH, using = GENERAL_TAB_XPATH + "//div[contains(text(),'Normalized Cite')]")
    public static WebElement normalizedCite;

    @FindBy(how = How.XPATH, using = GENERAL_TAB_XPATH + "//div[contains(text(),'Cite Line References')]")
    public static WebElement citeLineReferences;

    @FindBy(how = How.ID, using = "cancel")
    public static WebElement cancel;

    @FindBy(how = How.ID, using = "yesButton")
    public static WebElement yes;

    @FindBy(how = How.ID, using = "noButton")
    public static WebElement no;

    @FindBy(how = How.ID, using = "createCitelineButton")
    public static WebElement createButton;

    @FindBy(how = How.ID, using = "createAnotherCitelineButton")
    public static WebElement createAnotherButton;

    @FindBy(how = How.ID, using = "updateCitelineButton")
    public static WebElement updateValues;
}
