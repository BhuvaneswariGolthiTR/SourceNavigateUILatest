package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class FindTemplatesPageElements
{
    public static final String FIND_TEMPLATES_PAGE_TITLE = "Find Templates";

    @FindBy(how = How.XPATH, using = "//td[contains(text(), 'IA ST §')]/input[@type='submit']")
    public static WebElement goButton;

    @FindBy(how = How.XPATH, using = "//td[contains(text(), 'IA ST §')]/input[@type='text']")
    public static WebElement searchField;
}