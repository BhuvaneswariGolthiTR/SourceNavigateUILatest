package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.courts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AddCourtRoutingPopupElementsAngular {

    public static final String PAGE_TITLE = "//h5[text()='Add Court Routing']";

    @FindBy(how = How.XPATH, using = "//input[contains(@class,'form-control')]")
    public static WebElement inputField;

    @FindBy(how = How.XPATH, using = "//button[@class='dropdown-item active']/ngb-highlight")
    public static WebElement option;

    @FindBy(how = How.XPATH, using = "//button[text()='Submit']")
    public static WebElement submit;

    public static final String FIRST_OPTION = "//button[@class='dropdown-item active']/ngb-highlight[contains(text(),'%s')]";

}
