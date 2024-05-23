package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AddHighAssignedScriptsContextMenuElements
{

    @FindBy(how = How.XPATH, using = "//div[@id='assignedScriptsGridContextMenuContainer']//a[text()='Assign(High) Single']")
    public static WebElement assignHighSingle;

    @FindBy(how = How.XPATH, using = "//div[@id='assignedScriptsGridContextMenuContainer']//a[text()='Assign(High) With Descendants']")
    public static WebElement assignHighWithDescendants;
}
