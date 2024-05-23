package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ParentageGraphPageElements
{
    public static final String PARENTAGE_GRAPH_IMAGE = "//*[@id='parentageForm:parentageDoc']";

    @FindBy(how = How.ID, using = "parentageForm:parentageDoc")
    public static WebElement parentageGraphImage;

    @FindBy(how = How.ID, using = "fullScreenParentButton")
    public static WebElement expandButton;
}
