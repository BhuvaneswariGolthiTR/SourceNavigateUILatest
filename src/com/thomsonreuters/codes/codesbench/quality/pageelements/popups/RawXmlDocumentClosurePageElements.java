package com.thomsonreuters.codes.codesbench.quality.pageelements.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RawXmlDocumentClosurePageElements {

    @FindBy(how = How.ID, using = "b_checkin")
    public static WebElement checkInButton;

    public static final String CANADA_RADIO = "//input[@id='rb_canadaLoad']";

    @FindBy(how = How.ID, using = "rb_quickLoad")
    public static WebElement quickloadRadioButton;
}
