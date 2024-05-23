package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ImportLtcNovusLoadFilePopupsElementsAngular
{
    public static final String PAGE_TITLE = "//div/h5[contains(text(), 'Import LTC/Novus Load File')]";

    public static final String FILE_BY_NAME = "//div[@class='bento-tree-branch-base']/bento-tree-item/div[div/div/label[contains(text(),'%s')]]/bento-checkbox/input";

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Load Canada Files')]")
    public static WebElement loadCanadaFiles;

    @FindBy(how = How.XPATH, using = "//button[contains(@class,'close')]")
    public static WebElement close;

}
