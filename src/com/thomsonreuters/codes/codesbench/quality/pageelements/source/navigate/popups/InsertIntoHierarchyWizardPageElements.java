package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InsertIntoHierarchyWizardPageElements
{
    public static final String INSERT_INTO_HIERARCHY_WIZARD_TITLE = "Insert Into Hierarchy Wizard";
    public static final String SELECTED_NODE_ON_THE_NODE_TREE = "//table[contains(@class,'highlight1')]//td[contains(@class,'content')]";
    public static final String CLOSE_BUTTON = "//input[@id='pageForm:close' or @id='pageForm:closeButton'] | //button[@id='close-button' or @id='b_close']";

    @FindBy(how = How.ID, using = "pageForm:findButton")
    public static WebElement findButton;

    @FindBy(how = How.ID, using = "pageForm:AddBelowAsSiblingButton")
    public static WebElement addBelowAsSiblingButton;
}
