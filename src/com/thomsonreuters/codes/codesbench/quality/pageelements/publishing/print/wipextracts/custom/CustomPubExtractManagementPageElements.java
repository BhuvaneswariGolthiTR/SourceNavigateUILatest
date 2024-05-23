package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CustomPubExtractManagementPageElements
{
    public static final String WIP_EXTRACTS_CUSTOM_PUB_EXTRACT_MANAGEMENT_PAGE_TITLE = "Custom Pub Extract (Management)";

    @FindBy(how = How.ID, using = "pageForm:nextButton")
    public static WebElement newButton;
}
