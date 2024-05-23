package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.ViewBaselinesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ViewBaselinesNavigatePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ViewBaselinesNavigatePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ViewBaselinesPageElements.class);
    }

    public void clickNumberSort()
    {
        click(ViewBaselinesPageElements.numberSort);
        waitForGridRefresh();
    }

    public void rightClickOriginalBaseline()
    {
        scrollToView(ViewBaselinesPageElements.originalBaseline);
        rightClick(ViewBaselinesPageElements.originalBaseline);
    }

    public boolean switchToViewBaselinesPage()
    {
        boolean windowAppears = switchToWindow(ViewBaselinesPageElements.PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        waitForGridRefresh();
        return windowAppears;
    }

    public void clickFirstTableRow()
    {
        tableTestingPage().click(String.format(SourceNavigatePageElements.BASELINE_ON_A_ROW_WITH_NUMBER_GIVEN, 1));
    }

    public void rightClickTableNumber(int row) {
        tableTestingPage().rightClickByCell(TableTestingPage.ViewBaselines.NUMBER, row);
    }

        public String getCurrentBaselineDescription()
    {
        return getElementsText(ViewBaselinesPageElements.currentBaselineDescription);
    }

    public void rightClickWipVersionByIndex(String versionIndex)
    {
        click(String.format(ViewBaselinesPageElements.VERSION_BY_INDEX, versionIndex));
        rightClick(String.format(ViewBaselinesPageElements.VERSION_BY_INDEX, versionIndex));
    }

    public void closeWindow()
    {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", ViewBaselinesPageElements.close);
    }

    public void rightClickRowX(String rowX)
    {
        rightClick(String.format(SourceNavigatePageElements.BASELINE_ON_A_ROW_WITH_NUMBER_GIVEN,rowX));
    }

    public void openRestoreBaseLineContextMenu()
    {
        selectRestoreBaseline();
        checkAlert();
    }

    public void selectRestoreBaseline()
    {
        openContextMenu(SourceNavigatePageElements.RESTORE_BASELINE_CONTEXT_MENU_XPATH);
    }

    public boolean baselineWasRestoredFromX(String baseline)
    {
        return getElementsText(SourceNavigatePageElements.CURRENT_BASELINE_DESCRIPTION_XPATH).contains("Restored from " + baseline);
    }

    public boolean checkBaselineTableDescriptionValue(int row, String description)
    {
        return tableTestingPage().checkCellValue(TableTestingPage.ViewBaselines.DESCRIPTION, row, description);
    }
    public boolean checkBaselineTableNumberValue(int row, String number)
    {
        return tableTestingPage().checkCellValue(TableTestingPage.ViewBaselines.NUMBER, row, number);
    }

    public boolean doesCompareBaselinesExist()
    {
        return doesElementExist(SourceNavigatePageElements.COMPARE_BASELINES);
    }

    public void goToLastBaselinePage()
    {
        click(ViewBaselinesPageElements.lastPageButton);
    }

    public void goToFirstBaselinePage()
    {
        click(ViewBaselinesPageElements.firstPageButton);
    }

    public String getSecondBaseline()
    {
        return getElementsText(String.format(ViewBaselinesPageElements.BASELINE_ON_A_ROW_WITH_NUMBER_GIVEN, "2")+ ViewBaselinesPageElements.NUMBER_OF_GIVEN_BASELINE);
    }

    public boolean acceptGroupingAlert()
    {
        waitForAlert();
        return AutoITUtils.verifyAlertTextContainsAndAccept(true, "This Rendition has existing Section Groups and/or Delta Groups. Restoring a Baseline that existed prior to the assignment of the Groups may remove assignments");
    }

    public boolean restoreFirstRenditionToBaselineInRowWithGivenNumber(String rowNumber)
    {
        boolean restoredFrom;

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewRenditionBaselines();

        if (rowNumber.equals("0"))
        {
            click(ViewBaselinesPageElements.numberSort);
            waitForGridRefresh();
            rightClick(ViewBaselinesPageElements.originalBaseline);
            openContextMenuWithClick(SourceNavigatePageElements.RESTORE_BASELINE_CONTEXT_MENU_XPATH);
            switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
            switchToWindow(ViewBaselinesPageElements.PAGE_TITLE);
            enterTheInnerFrame();
            click(ViewBaselinesPageElements.numberSort);
            waitForGridRefresh();
        }
        else
        {
            rightClick(String.format(SourceNavigatePageElements.BASELINE_ON_A_ROW_WITH_NUMBER_GIVEN, rowNumber));
            openContextMenuWithClick(SourceNavigatePageElements.RESTORE_BASELINE_CONTEXT_MENU_XPATH);
            waitForPageLoaded();
        }

        restoredFrom = getElement(SourceNavigatePageElements.CURRENT_BASELINE_DESCRIPTION_XPATH)
                .getText().contains("Restored from");
        breakOutOfFrame();
        closeViewBaselinesPage();
        switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        waitForPageLoaded();

        return restoredFrom;
    }

    public void closeViewBaselinesPage()
    {
        closeCurrentWindowIgnoreDialogue();
        waitForWindowGoneByTitle(ViewBaselinesPageElements.PAGE_TITLE);
    }
}

