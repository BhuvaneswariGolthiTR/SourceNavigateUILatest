package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InitiateNODDataValidationPopupElementsAngular
{
    public static final String PAGE_TITLE = "//div/h5[contains(text(), 'Initiate NOD Data Validation')]";

    @FindBy(how = How.XPATH, using = "//div[@class='pt-2 mx-3 pb-4']/h4")
    public static WebElement status;

    @FindBy(how = How.XPATH, using = "//div[@class='inputs my-2']/label[2]/span")
    public static WebElement currentUser;

    @FindBy(how = How.XPATH, using = "//label[contains(text(),'File name:')]//input")
    public static WebElement fileName;

    public static final String MESSAGE = "//div[@class='pt-2 mx-3 pb-4']/h4";

}
