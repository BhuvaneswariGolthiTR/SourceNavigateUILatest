package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CustomPubExtractOptionsPageElements
{
    public static final String WIP_EXTRACTS_CUSTOM_PUB_EXTRACT_OPTIONS_PAGE_TITLE = "Custom Pub Extract (Options)";

    @FindBy(how = How.ID, using = "pageForm:next")
    public static WebElement nextButton;

    @FindBy(how = How.ID, using = "pageForm:extractName")
    public static WebElement extractFileNameInputField;
}
