package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AddAssignedScriptsContextMenuElements
{
    @FindBy(how = How.XPATH, using = "//div[@id='assignedScriptsGridContextMenuContainer']//a[text()='Assign Single']")
    public static WebElement assignSingle;

    @FindBy(how = How.XPATH, using = "//div[@id='assignedScriptsGridContextMenuContainer']//a[text()='Assign With Descendants']")
    public static WebElement assignWithDescendants;
}
