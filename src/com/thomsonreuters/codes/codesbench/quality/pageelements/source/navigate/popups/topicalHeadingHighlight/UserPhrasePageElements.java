package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.topicalHeadingHighlight;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UserPhrasePageElements
{
    @FindBy(how = How.ID, using = "userPhraseInputText")
    public static WebElement userPhraseTextBox;

    @FindBy(how = How.XPATH, using = "//span[@class='button-group']/button[contains(text(), 'Submit')]")
    public static WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//span[@class='button-group']/button[contains(text(), 'Cancel')]")
    public static WebElement cancelButton;
}
