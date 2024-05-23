package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RelocateBinAsChildSiblingPageElements
{
    public static final String RELOCATE_BIN_AS_CHILD_SIBLING_TITLE_PAGE = "Relocate Bin as Child/Sibling";

    @FindBy(how = How.ID, using = "pageForm:combineSameValues")
    public static WebElement combineSameValuesCheckbox;

    @FindBy(how = How.ID, using = "pageForm:j_idt84:0")
    public static WebElement moveNodsfoward;

    @FindBy(how = How.ID, using = "pageForm:j_idt84:1")
    public static WebElement shareNodsFoward;

    @FindBy(how = How.ID, using = "pageForm:j_idt84:2")
    public static WebElement copyNodsFoward;

    @FindBy(how = How.ID, using = "pageForm:j_idt84:3")
    public static WebElement retainNodsOnlyAtPriorNode;

    @FindBy(how = How.ID, using = "pageForm:valueTable:0:newval")
    public static WebElement newValueTextBox;

    @FindBy(how = How.ID, using = "pageForm:volumeNumber")
    public static WebElement volumeNumber;

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement setLawTrackingQuickload;

    @FindBy(how = How.ID, using = "pageForm:j_idt48:0")
    public static WebElement createTransferNodeAndDisDerivlinks;

    @FindBy(how = How.ID, using = "pageForm:j_idt48:1")
    public static WebElement createDisDerivLinkOnlyNoTransferNode;

    @FindBy(how = How.ID, using = "pageForm:j_idt48:2")
    public static WebElement doNotCreatTransferNodeOrDispDerivLink;

    @FindBy(how = How.ID, using = "pageForm:effStartDate")
    public static WebElement effectiveStartDateTextbox;

    @FindBy(how = How.ID, using = "pageForm:j_idt65:0")
    public static WebElement updateWithDefaultSourceTag;

    @FindBy(how = How.ID, using = "pageForm:j_idt65:1")
    public static WebElement leaveSourceTagAsIs;

    @FindBy(how = How.ID, using = "pageForm:sourceTagSelectionText")
    public static WebElement sourceTagSelectionTextbox;

    @FindBy(how = How.ID, using = "pageForm:ceParaStyleRadioSelector:0")
    public static WebElement ceTextStyleTitle;

    @FindBy(how = How.ID, using = "pageForm:ceParaStyleRadioSelector:1")
    public static WebElement ceTextStyleCode;

    @FindBy(how = How.ID, using = "pageForm:ceParaStyleRadioSelector:2")
    public static WebElement WebElementceTextStyleAdc;

    @FindBy(how = How.ID, using = "pageForm:altCiteRadioSelector")
    public static WebElement altCiteRadio;

    @FindBy(how = How.ID, using = "pageForm:ceText")
    public static WebElement ceText;

    @FindBy(how = How.ID, using = "pageForm:ok")
    public static WebElement okButton;

    @FindBy(how = How.ID, using = "pageForm:cancelButton")
    public static WebElement cancelButton;

    @FindBy(how = How.ID, using = "pageForm:addCePara")
    public static WebElement addCeParagraphCheckBox;
}
