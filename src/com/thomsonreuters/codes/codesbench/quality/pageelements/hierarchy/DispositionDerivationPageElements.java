package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DispositionDerivationPageElements
{
    public static final String GIVEN_NODE_VALUE = "//div[@id='dispDerivGrid']//tbody[@class='yui-dt-data']//td[contains(@class,'Value')]/div[text()='%s']";
    public static final String ROW_OF_GRID_BY_NUMBER = "//div[@id='dispDerivGrid']//tbody[@class='yui-dt-data']/tr[%s]";

    @FindBy(how = How.XPATH, using = "//div[@class='yui-dt-bd']//td[contains(@headers, 'Value ')]/div")
    public static WebElement selectedNodeTableValue;

    @FindBy(how = How.ID, using = "fullScreenDispButton")
    public static WebElement expandButton;

    public static final String EXPAND_BUTTON = "//*[@id='fullScreenDispButton']";

    @FindBy(how = How.ID, using = "dispDerivForm:dispDerivDoc")
    public static WebElement selectedNodeImage;

    @FindBy(how = How.ID, using = "dispDerivGridForm:dispDerivGridCommit")
    public static WebElement commit;

    @FindBy(how = How.XPATH, using = "//div[@id='dispDerivGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]")
    public static WebElement selectedNodeValue;

    @FindBy(how = How.XPATH, using = "//div[@id='dispDerivGrid']//tbody[@class='yui-dt-data']/tr[2]")
    public static WebElement secondRowOfGrid;

    @FindBy(how = How.XPATH, using = "//div[@id='dispDerivGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]//td[contains(@class,'yui-dt-col-PublishStatus')]/div/img")
    public static WebElement selectedNodePublishingStatus;

    public static final String SECOND_ROW_OF_GRID = "//div[@id='dispDerivGrid']//tbody[@class='yui-dt-data']/tr[2]";

    @FindBy(how = How.XPATH, using = "//div[@id='dispDerivGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]//td[contains(@class, 'StartDate')]/div/div/input")
    public static WebElement selectedNodeStartDateTextBox;

    @FindBy(how = How.XPATH, using = "//div[@id='dispDerivGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]//td[contains(@class, 'EndDate')]//input")
    public static WebElement selectedNodeEndDateTextBox;
}
