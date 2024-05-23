package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class IntegrationPropertiesPageElements
{
    public static final String PAGE_TITLE = "Integration Properties";

    @FindBy(how = How.ID, using = "pageForm:effectiveDate")
    public static WebElement effectiveDateTextBox;

    @FindBy(how = How.ID, using = "pageForm:effectiveDateProvision")
    public static WebElement effectiveDateProvision;

    @FindBy(how = How.ID, using = "pageForm:effectiveComments")
    public static WebElement effectiveComments;

    @FindBy(how = How.ID, using = "pageForm:queryNote")
    public static WebElement queryNote;

    @FindBy(how = How.ID, using = "pageForm:queryDate")
    public static WebElement queryDateTextbox;

    @FindBy(how = How.ID, using = "pageForm:addAsDateQuery")
    public static WebElement addAsDateQueryCheckbox;

    @FindBy(how = How.ID, using = "pageForm:instructionNote")
    public static WebElement instructionNote;

    @FindBy(how = How.ID, using = "pageForm:miscellaneous")
    public static WebElement miscellaneous;

    @FindBy(how = How.ID,using = "pageForm:saveButton")
    public static WebElement saveButton;

    @FindBy(how = How.ID,using = "pageForm:cancelButton")
    public static WebElement cancelButton;

    @FindBy(how = How.ID, using = "pageForm:queryDate")
    public static WebElement integrationPropertiesQueryDateTextBox;

    @FindBy(how = How.NAME, using = "pageForm:suppressDate")
    public static WebElement integrationPropertiesDefaultCheckbox;

    @FindBy(how = How.ID, using = "pageForm:effectiveDateProvision")
    public static WebElement integrationPropertiesEffectiveDateProvision;

    @FindBy(how = How.ID, using = "pageForm:effectiveComments")
    public static WebElement integrationPropertiesEffectiveComments;

    @FindBy(how = How.ID, using = "pageForm:queryNote")
    public static WebElement integrationPropertiesQueryNote;

    @FindBy(how = How.NAME, using = "pageForm:instructionNote")
    public static WebElement integrationPropertiesInstructionNotes;

    @FindBy(how = How.NAME, using = "pageForm:miscellaneous")
    public static WebElement integrationPropertiesMiscellaneous;
}
