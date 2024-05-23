package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.topicalHeadingHighlight;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MarkIndexEntryPageElements
{
    @FindBy(how = How.XPATH, using = "//span[@class='button-group']/button[contains(text(), 'Yes')]")
    public static WebElement yesButton;

    @FindBy(how = How.XPATH, using = "//span[@class='button-group']/button[contains(text(), 'No')]")
    public static WebElement noButton;

    @FindBy(how = How.XPATH, using = "//span[@class='button-group']/button[contains(text(), 'Cancel')]")
    public static WebElement cancelButton;
}
