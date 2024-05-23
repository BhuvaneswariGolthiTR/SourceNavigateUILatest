package com.thomsonreuters.codes.codesbench.quality.pageelements.home.userpreferences;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UserSecuritySettingsPageElements
{
    public static final String USER_SECURITY_SETTINGS_PAGE_TITLE = "User Security Settings";

    @FindBy(how = How.XPATH, using = "//td[contains(text(), 'SWAT')]")
    public static WebElement swatAccess;

    @FindBy(how = How.XPATH, using = "//td[contains(text(), 'Paratech')]")
    public static WebElement paratechAccess;

    @FindBy(how = How.XPATH, using = "//td[contains(text(), 'Westlaw Publisher')]")
    public static WebElement westlawPublisherAccess;

    @FindBy(how = How.XPATH, using = "//td[contains(text(), 'Jetstream Editor SWAT User')]")
    public static WebElement jetstreamEditorSwatUserAccess;

    @FindBy(how = How.XPATH, using = "//td[contains(text(), 'Legal CT1 User')]")
    public static WebElement legalCT1UserAccess;

    @FindBy(how = How.XPATH, using = "//td[contains(text(), 'Risk CT1 User')]")
    public static WebElement riskCT1UserAccess;
}
