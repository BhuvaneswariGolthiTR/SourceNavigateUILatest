package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditorQueryContextMenuElementsPageElements
{
    public static final String CONTEXT_MENU = "//div[@id='yuiContextMenu']";

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//a[text()='View']")
    public static WebElement view;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//a[text()='Resolve/Unresolve']")
    public static WebElement resolveUnresolve;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//a[text()='Delete']")
    public static WebElement delete;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//a[text()='Edit']")
    public static WebElement edit;

}