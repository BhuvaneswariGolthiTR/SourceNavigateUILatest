package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CourtsPageElementsAngular
{
    public static final String PAGE_TITLE = "NodClassifyUi";
    public static final String URL_MODIFIER = "courts";
    public static final String PAGE_TAG = "//body/app-root/app-courts";

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Add Court Routing')]")
    public static WebElement addCourtRouting;

    public static final String CELL_BY_TEXT = "//div[@role='gridcell' and contains(text(),'%s')]";

    public static final String DELETE_COURT_ROUTING = "//span[contains(text(),'Delete Court Routing')]";

}
