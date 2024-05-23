package com.thomsonreuters.codes.codesbench.quality.pageelements.home;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ContentSetPublishingConfigurationPageElements
{
    public static final String ENABLED_CONTENT_SET_XPATH = "//select[@id='pageForm:enabledContentSets']//option[text()='%s']";
    public static final String DISABLED_CONTENT_SET_XPATH = "//select[@id='pageForm:disabledContentSets']//option[text()='%s']";

    @FindBy(how = How.XPATH, using = "//input[@value='>']")
    public static WebElement rightArrowButton;

    @FindBy(how = How.XPATH, using = "//input[@value='<']")
    public static WebElement leftArrowButton;

    @FindBy(how = How.XPATH, using = "//input[@value='Submit']")
    public static WebElement submitButton;
}

