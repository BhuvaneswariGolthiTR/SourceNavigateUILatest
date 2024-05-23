package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RemoveAssignedScriptsContextMenuElements
{
    @FindBy(how = How.XPATH, using = "//div[@id='assignedScriptsGridContextMenuContainer']//a[text()='Remove With Descendants']")
    public static WebElement removeWithDescendants;

    @FindBy(how = How.XPATH, using = "//a[text()='Remove Single']")
    public static WebElement removeSingle;
}
