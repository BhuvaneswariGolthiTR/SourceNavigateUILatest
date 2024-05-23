package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BulkPublishPageElements
{
    public static final String BULK_PUBLISH_PAGE_TITLE = "Bulk Publish";

    @FindBy(how = How.ID, using = "pageForm:skipFileCleanup")
    public static WebElement fileCleanupCheckbox;

    @FindBy(how = How.ID, using = "pageForm:skipBermuda")
    public static WebElement bermudaCheckbox;

    @FindBy(how = How.ID, using = "pageForm:skipNovusDocFamily")
    public static WebElement novusDocFamilyCheckbox;

    public static final String NOVUS_DOC_FAMILY_CHECKBOX = "//*[@id='pageForm:skipNovusDocFamily']";

    @FindBy(how = How.XPATH, using = "//input[@value='Publish']")
    public static WebElement publishButton;

    @FindBy(how = How.ID, using = "pageForm:okButton")
    public static WebElement bulkPublishButton;

    @FindBy(how = How.ID, using = "pageForm:skipNovusNorm")
    public static WebElement novusNormCheckbox;
}