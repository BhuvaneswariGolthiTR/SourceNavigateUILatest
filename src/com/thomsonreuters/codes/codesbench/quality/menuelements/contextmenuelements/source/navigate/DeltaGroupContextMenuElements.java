package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeltaGroupContextMenuElements {

    @FindBy(how = How.XPATH, using = "//a[text()='Delta Group']")
    public static WebElement deltaGroup;

}
