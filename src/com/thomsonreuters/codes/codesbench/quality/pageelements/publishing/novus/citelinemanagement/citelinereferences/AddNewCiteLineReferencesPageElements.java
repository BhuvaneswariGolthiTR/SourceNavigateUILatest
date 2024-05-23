package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.citelinereferences;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AddNewCiteLineReferencesPageElements  extends CiteLineManagementsCommonPageElements
{
    public static final String GENERAL_XPATH = "//div[@class='mat-dialog-content centerContent']";
    public static final String CT1_ERROR = GENERAL_XPATH + "//label[text()='CT1:']/following-sibling::mat-form-field//mat-error[contains(text(),'%s')]";
    public static final String ORG_FIRST_LINE_CITE_ERROR = GENERAL_XPATH + "//label[text()='Original First Line Cite:']/following-sibling::mat-form-field//mat-error[contains(text(),'%s')]";
    public static final String FIRST_LINE_CITE_ERROR  = GENERAL_XPATH + "//label[text()='First Line Cite:']/following-sibling::mat-form-field//mat-error[contains(text(),'%s')]";

    public static final String DUPLICATE_CT1_ERROR= "CT1 already exists for this content set ";
    public static final String CT1_IS_REQUIRED = "CT1 is ";
    public static final String ORG_FIRST_LINE_CITE_REQUIRED = "Original First Line Cite is ";
    public static final String FIRST_LINE_CITE_REQUIRED = "First Line Cite is ";

    public static final String CT1_INPUT_XPATH = GENERAL_XPATH + "//input[@id='inputCt1Num']";
    public static final String ORIGINAL_FIRST_LINE_INPUT_XPATH = GENERAL_XPATH + "//input[@id='inputOriginalFirstLineCite']";
    public static final String FIRST_LINE_INPUT_XPATH = GENERAL_XPATH + "//input[@id='inputFirstLineCite']";
    public static final String SECOND_LINE_PRE_INPUT_XPATH = GENERAL_XPATH + "//input[@id='inputSecondLineCitePre']";
    public static final String SECOND_LINE_APP_INPUT_XPATH = GENERAL_XPATH + "//input[@id='inputSecondLineCiteApp']";
    public static final String EXPANDED_LINE_PRE_INPUT_XPATH = GENERAL_XPATH + "//input[@id='inputExpandedCitePre']";
    public static final String EXPANDED_LINE_APP_INPUT_XPATH = GENERAL_XPATH + "//input[@id='inputExpandedCiteApp']";
    public static final String FORMER_CITE_INPUT_XPATH = GENERAL_XPATH + "//input[@id='inputFormerCite']";
    public static final String COMMENT_INPUT_XPATH = GENERAL_XPATH + "//input[@id='inputComments']";
    public static final String UDPATE_VALUES_BUTTON_XPATH = "//button[@id='updateCitelineButton']";
    public static final String CREATE_BUTTON_XPATH = "//button[@id='createCitelineButton']";
    public static final String CREATE_ANOTHER_BUTTON_XPATH = "//button[@id='createAnotherCitelineButton']";

    @FindBy(how = How.ID, using = "createCitelineButton")
    public static WebElement createButton;

    @FindBy(how = How.ID, using = "createAnotherCitelineButton")
    public static WebElement createAnotherButton;

    @FindBy(how = How.ID, using = "updateCitelineButton")
    public static WebElement updateValues;

    @FindBy(how = How.ID, using = "createCitelineButton")
    public static WebElement create;

    @FindBy(how = How.ID, using ="inputCt1Num")
    public static WebElement ct1InputField;

    @FindBy(how = How.ID, using ="inputOriginalFirstLineCite")
    public static WebElement orignalFirstLineCiteInputField;

    @FindBy(how = How.ID, using ="inputFirstLineCite")
    public static WebElement firstLineCiteInputField;

    @FindBy(how = How.ID, using ="inputSecondLineCitePre")
    public static WebElement secondLineCitePreInputField;

    @FindBy(how = How.ID, using ="inputSecondLineCiteApp")
    public static WebElement secondLineCiteAppInputField;

    @FindBy(how = How.ID, using ="inputExpandedCitePre")
    public static WebElement expandedCitePreInputField;

    @FindBy(how = How.ID, using ="inputExpandedCiteApp")
    public static WebElement expandedCiteAppInputField;

    @FindBy(how = How.ID, using ="inputFormerCite")
    public static WebElement formerCiteInputField;

    @FindBy(how = How.ID, using ="inputComments")
    public static WebElement commentsInputField;
}
