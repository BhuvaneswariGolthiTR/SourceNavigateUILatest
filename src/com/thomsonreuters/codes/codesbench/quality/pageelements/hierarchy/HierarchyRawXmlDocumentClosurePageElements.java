package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.AbstractRawXmlDocumentClosurePageElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyRawXmlDocumentClosurePageElements extends AbstractRawXmlDocumentClosurePageElements
{
    @FindBy(how = How.ID, using = "rb_quickLoad")
    public static WebElement quickLoadRadioButton;

    @FindBy(how = How.ID, using = "b_checkin")
    public static WebElement checkInButton;
}
