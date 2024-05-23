package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class GradeNodeVersioningDatePageElements
{
    public static final String GRADE_NODE_VERSIONING_DATE_TITLE = "Grade Node Versioning Date";

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:cancel']")
    public static WebElement cancelButton;
}
