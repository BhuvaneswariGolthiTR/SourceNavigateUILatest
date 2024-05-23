package com.thomsonreuters.codes.codesbench.quality.pages.audits;

import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.CommonAuditsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbydocument.AuditByDocumentPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.reportcentral.ReportCentralPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbydocument.AuditByDocumentPageElements.cutOffDateEntry;

@Component
public class AuditByDocumentPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public AuditByDocumentPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AuditByDocumentPageElements.class);
    }

    public void addAllMaterials()
    {
        sendEnterToElement(AuditByDocumentPageElements.addAllButton);
    }

    public void selectVolumeByVolumeNumber(String volumeNumber)
    {
        click(String.format(AuditByDocumentPageElements.VOLUME_ITEM, volumeNumber));
    }

    public void selectDocumentByCodeNameAndLegacyTextAndLegisEffectiveStartDate(String codeName, String legacyText, String legisEffectiveStartDate)
    {
        Date date = DateAndTimeUtils.convertStringToDateObject(legisEffectiveStartDate, DateAndTimeUtils.yyyyDashMMDashdd_HHmmss_DATE_TIME_FORMAT);

        String dateString = DateAndTimeUtils.convertDateToFormat(date, DateAndTimeUtils.MMddyyyy_DATE_FORMAT);

        String titleFormat = String.format("T. %s;", codeName.split(" ")[1]);

        String combinedString =  String.format("%s = %s %s", titleFormat, legacyText, dateString);

        selectDropdownOption(AuditByDocumentPageElements.NON_SELECTED_DOCUMENTS_LIST_XPATH, combinedString);
    }

    public void selectDocumentByKeywordAndValueAndLegisEffectiveStartDate(String keyWord, String value, String legisEffectiveStartDate)
    {
        Date date = DateAndTimeUtils.convertStringToDateObject(legisEffectiveStartDate, DateAndTimeUtils.yyyyDashMMDashdd_HHmmss_DATE_TIME_FORMAT);
        String dateString = DateAndTimeUtils.convertDateToFormat(date, DateAndTimeUtils.MMddyyyy_DATE_FORMAT_NO_LEADING_ZEROS);
        String combinedString =  String.format("%s %s %s", keyWord, value, dateString);

        selectDropdownOption(AuditByDocumentPageElements.NON_SELECTED_DOCUMENTS_LIST_XPATH, combinedString);
    }

    public void clickAddOneDocumentButton()
    {
        sendEnterToElement(AuditByDocumentPageElements.addOneDocumentButton);
        waitForPageLoaded();
    }

    public void clickAddOneVolumeButton()
    {
        sendEnterToElement(AuditByDocumentPageElements.addOneVolumeButton);
        waitForPageLoaded();
    }

    public void setAuditIdentifier(String auditIdentifier)
    {
        clearAndSendKeysToElement(AuditByDocumentPageElements.auditIdentifierTextBox, auditIdentifier);
    }

    public void clickNextButton()
    {
        sendEnterToElement(AuditByDocumentPageElements.nextButton);
        waitForPageLoaded();
        waitForElement(AuditByDocumentPageElements.addAllButton);
    }

    public void clickNextButtonWithoutWaiting()
    {
        sendEnterToElement(AuditByDocumentPageElements.nextButton);
    }

    private void clickListItemByText(String listXpath, String text) throws InterruptedException
    {
        getElement(listXpath + "/option[text()='" + text + "']").click();
    }

    public boolean clickProcessAuditButton()
    {
        sendEnterToElement(AuditByDocumentPageElements.processAudit);
        waitForGridRefresh();

        return switchToWindow(ReportCentralPageElements.REPORT_CENTRAL_PAGE_TITLE);
    }

    public void selectClassificationRadioButton()
    {
        checkCheckbox(CommonAuditsPageElements.classificationRadioButton);
    }

    public void selectShortSameParagraphsRadioButton()
    {
        checkCheckbox(CommonAuditsPageElements.shortSameParagraphsRadioButton);
    }

    public void selectNotShortSameParagraphsRadioButton()
    {
        checkCheckbox(CommonAuditsPageElements.notShortSameParagraphsRadioButton);
    }

    public void selectAllTextRadioButton()
    {
        checkCheckbox(CommonAuditsPageElements.allTextRadioButton);
    }

    public void selectIgnorePubTagChangeRadioButton()
    {
        checkCheckbox(CommonAuditsPageElements.ignorePubtagChangeRadioButton);
    }

    public void uncheckNODReport()
    {
        uncheckCheckbox(CommonAuditsPageElements.nodReportCheckbox);
    }

    public void checkTaxTypeReport()
    {
        checkCheckbox(CommonAuditsPageElements.taxTypeReportCheckbox);
    }

    public void checkNODReport()
    {
        checkCheckbox(CommonAuditsPageElements.nodReportCheckbox);
    }
    public void uncheckSources()
    {
        uncheckCheckbox(CommonAuditsPageElements.sourcesCheckbox);
    }

    public void uncheckValidations()
    {
        uncheckCheckbox(CommonAuditsPageElements.validationsCheckbox);
    }

    public void checkValidations()
    {
        checkCheckbox(CommonAuditsPageElements.validationsCheckbox);
    }

    public void uncheckVersionsAuditStarting()
    {
        uncheckCheckbox(CommonAuditsPageElements.versionsAuditStartingCheckbox);
    }
    public void checkVersionsAuditStarting()
    {
        checkCheckbox(CommonAuditsPageElements.versionsAuditStartingCheckbox);
    }

    public void uncheckHierarchyContext()
    {
        uncheckCheckbox(CommonAuditsPageElements.hierarchyContextCheckbox);
    }

    public void checkContentAudit()
    {
        checkCheckbox(CommonAuditsPageElements.contentAuditCheckbox);
    }

    public void uncheckResearchReferencesCheckbox()
    {
        uncheckCheckbox(AuditByDocumentPageElements.researchReferencesCheckbox);
    }

    public void selectMostRecentWIPDocumentRadioButton()
    {
        checkCheckbox(AuditByDocumentPageElements.mostRecentWIPDocumentRadioButton);
    }

    public void clickAddAllVolumesButton()
    {
        sendEnterToElement(AuditByDocumentPageElements.addAllButton);
    }

    public String getCutOffDate()
    {
        return getElementsAttribute(cutOffDateEntry, "value");
    }

    public void setCutOffDate(String cutOffDate)
    {
        clearAndSendKeysToElement(cutOffDateEntry, cutOffDate);
    }
}
