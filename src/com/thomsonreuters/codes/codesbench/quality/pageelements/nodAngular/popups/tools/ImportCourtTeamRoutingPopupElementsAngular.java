package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ImportCourtTeamRoutingPopupElementsAngular
{
    public static final String PAGE_TITLE = "//div/h5[contains(text(), 'Import Court Team Routing')]";

    @FindBy(how = How.XPATH, using = "//div[@class='my-2']//span")
    public static WebElement currentUser;

    @FindBy(how = How.XPATH, using = "//button[text()='Cancel']")
    public static WebElement cancel;

    public static final String MESSAGE = "//div[@class='my-2 mx-3']/span";

}
