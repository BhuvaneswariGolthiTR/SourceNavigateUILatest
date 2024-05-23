package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ClassifyDescendantsWorkflowPageElements
{
    public static final String CLASSIFY_DESCENDANTS_WORKFLOW_PAGE_TITLE = "Classify Descendants Workflow";

    @FindBy(how = How.ID, using = "pageForm:totalCount")
    public static WebElement totalNodeCount;

    @FindBy(how = How.ID, using = "pageForm:submit")
    public static WebElement submitButton;
}
