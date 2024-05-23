package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.AbstractRawXmlDocumentClosurePageElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SourceRawXmlDocumentClosurePageElements extends AbstractRawXmlDocumentClosurePageElements
{
    @FindBy(how = How.ID, using = "b_checkin")
    public static WebElement checkInButton;

    @FindBy(how = How.ID, using = "b_cancel")
    public static WebElement cancelButton;
}
