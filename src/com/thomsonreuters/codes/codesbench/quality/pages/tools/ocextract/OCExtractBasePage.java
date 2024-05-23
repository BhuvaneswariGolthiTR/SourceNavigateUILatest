package com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.OCExtractBasePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class OCExtractBasePage extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public OCExtractBasePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, OCExtractBasePageElements.class);
    }

    public boolean isPageOpened()
    {
        boolean isPageOpened = doesElementExist(OCExtractBasePageElements.PAGE_TAG);
        boolean isFirstHeaderLineDisplayed = isElementDisplayed(OCExtractBasePageElements.PAGE_FIRST_HEADER_LINE);
        boolean isSecondHeaderLineDisplayed = isElementDisplayed(OCExtractBasePageElements.PAGE_SECOND_HEADER_LINE);
        boolean isSectionHeaderDisplayed = isElementDisplayed(OCExtractBasePageElements.PAGE_SECTION_HEADER);
        return isPageOpened && isFirstHeaderLineDisplayed && isSecondHeaderLineDisplayed && isSectionHeaderDisplayed;
    }

    public void clickPublicationFilesButton()
    {
        click(OCExtractBasePageElements.PUBLICATION_FILES_BUTTON);
        publicationFilesTablePage().waitForHiddenOverlay();
    }

    public boolean isTableNameDisplayed(String tableName)
    {
        return isElementDisplayed(String
                .format(OCExtractBasePageElements.TABLE_NAME_XPATH, tableName));
    }
}
