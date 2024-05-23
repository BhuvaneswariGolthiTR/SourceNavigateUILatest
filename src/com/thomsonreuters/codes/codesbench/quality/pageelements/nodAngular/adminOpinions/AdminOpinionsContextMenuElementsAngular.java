package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.adminOpinions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdminOpinionsContextMenuElementsAngular
{
    public static final String CONTEXT_MENU = "//div[@class = 'ag-menu-list']";
    public static final String CONTEXT_MENU_OPTION = CONTEXT_MENU + "/div";
    public static final String CONTEXT_MENU_OPTION_WITH_TEXT = CONTEXT_MENU + "//span[text()='%s']/parent::div";

    @FindBy(how = How.XPATH, using = "//span[text()='Delete Opinion']")
    public static WebElement deleteOpinion;
}
