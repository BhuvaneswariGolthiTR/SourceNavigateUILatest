package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HeadnotesContextMenuElementsAngular
{

    public static final String CONTEXT_MENU = "//section[contains(@class, 'context-menu')]";
    public static final String CONTEXT_MENU_OPTION = CONTEXT_MENU + "//div";
    public static final String CONTEXT_MENU_OPTION_WITH_TEXT = CONTEXT_MENU + "//div[normalize-space()='%s']";

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//div[text()='Update Metadata']")
    public static WebElement updateMetadata;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//div[text()='Insert Blueline']")
    public static WebElement insertBlueline;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//div[text()='View Content']")
    public static WebElement viewContent;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//div[text()='View Content Raw Xml']")
    public static WebElement viewContentRawXml;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//div[text()='Edit Content']")
    public static WebElement editContent;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//div[text()='Edit Content Raw Xml']")
    public static WebElement editContentRawXml;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//div[text()='Find In Hierarchy']")
    public static WebElement findInHierarchy;
}
