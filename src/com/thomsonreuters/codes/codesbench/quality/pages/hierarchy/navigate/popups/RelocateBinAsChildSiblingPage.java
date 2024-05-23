package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.RelocateBinAsChildSiblingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class RelocateBinAsChildSiblingPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public RelocateBinAsChildSiblingPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RelocateBinAsChildSiblingPageElements.class);
    }

    public void clickCancelButton()
    {
        click(RelocateBinAsChildSiblingPageElements.cancelButton);
    }

    public void clickOkButton()
    {
        click(RelocateBinAsChildSiblingPageElements.okButton);
        switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }

    public void setVolumes(String vols)
    {
        sendKeysToElement(RelocateBinAsChildSiblingPageElements.volumeNumber, vols);
    }

    public void setNewValue(String newValue)
    {
        sendKeysToElement(RelocateBinAsChildSiblingPageElements.newValueTextBox, newValue);
    }

    public void unCheckCombineSameNewValues()
    {
        uncheckCheckbox(RelocateBinAsChildSiblingPageElements.combineSameValuesCheckbox);
    }

    public void setEffectiveStartDate(String startDate)
    {
        sendTextToTextbox(RelocateBinAsChildSiblingPageElements.effectiveStartDateTextbox, startDate);
    }

    public void setEffectiveStartDateToToday()
    {
        setEffectiveStartDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
    }

    public void clickCreateTransferNodeAndDisDerivLinksRadioButton()
    {
        click(RelocateBinAsChildSiblingPageElements.createTransferNodeAndDisDerivlinks);
    }

    public void setQuickLoad()
    {
        click(RelocateBinAsChildSiblingPageElements.setLawTrackingQuickload);
    }

    public void clickUpdateWithDefaultSourceTagRadioButton()
    {
        click(RelocateBinAsChildSiblingPageElements.updateWithDefaultSourceTag);
    }

    public void clickLeaveSourceTagAsIsRadioButton()
    {
        click(RelocateBinAsChildSiblingPageElements.leaveSourceTagAsIs);
    }

    public void clickUpdateSourceTagAsIsRadioButtonAndSet(String sourceTag)
    {
        click(RelocateBinAsChildSiblingPageElements.updateWithDefaultSourceTag);
        sendTextToTextBoxAuto(RelocateBinAsChildSiblingPageElements.sourceTagSelectionTextbox, sourceTag);
    }

    public void clickMoveNodsFowardRadioButton()
    {
        click(RelocateBinAsChildSiblingPageElements.moveNodsfoward);
    }

    public void clickShareNodsForwardRadioButton()
    {
        click(RelocateBinAsChildSiblingPageElements.shareNodsFoward);
    }

    public void checkAddCeParagraphToNewTextWithASourceTagCheckBox()
    {
        checkCheckbox(RelocateBinAsChildSiblingPageElements.addCeParagraphCheckBox);
    }

    public void clickCreateDispDerivLinkOnlyNoTransferNodeRadioButton()
    {
        click(RelocateBinAsChildSiblingPageElements.createDisDerivLinkOnlyNoTransferNode);
    }

    public void clickRetainNodsOnlyAtPriorNodeRadioButton()
    {
        click(RelocateBinAsChildSiblingPageElements.retainNodsOnlyAtPriorNode);
    }

    public void clickCopyNodsForwardRadioButton()
    {
        click(RelocateBinAsChildSiblingPageElements.copyNodsFoward);
    }
}
