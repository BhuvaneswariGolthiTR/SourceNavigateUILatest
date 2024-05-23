package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class GlossaryTermPageElements
{
    public static final String GLOSSARY_TERM_PAGE_TITLE = "Glossary Term";

    @FindBy(how = How.ID, using = "pageForm:primaryTerm")
    public static WebElement primaryTerm;

    @FindBy(how = How.ID, using = "pageForm:definitionContent")
    public static WebElement definitionContent;

    @FindBy(how = How.ID, using = "pageForm:selectedTerm")
    public static WebElement selectedTerm;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:cancel' or @id='cancelButton' or @id='pageForm:cancelButton' or @value='Cancel' or @id='inputAsForm:cancelButton'] | //button[@id='cancelButton-button' or @id='b_cancel']")
    public static WebElement cancelButton;
}
