package com.thomsonreuters.codes.codesbench.quality.menus.mainmenus;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.AuditsMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbydocument.AuditByDocumentPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbysource.AuditBySourcePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.reportcentral.ReportCentralPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.twirlreport.TwirlReportPageElements;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AuditsMenu extends Menu
{
    private WebDriver driver;

    @Autowired
    public AuditsMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AuditsMenuElements.class);
    }

    public boolean goToAuditByDocument()
    {
        openMenu(AuditsMenuElements.auditsMenu);
        sendEnterToElement(AuditsMenuElements.auditByDocument);
        return switchToWindow(AuditByDocumentPageElements.AUDIT_BY_DOCUMENT_PAGE_TITLE);
    }

    //Audits -> Audit by Source
    public boolean goToAuditBySource()
    {
        openMenu(AuditsMenuElements.auditsMenu);
        sendEnterToElement(AuditsMenuElements.auditBySource);
        return switchToWindow(AuditBySourcePageElements.AUDIT_BY_SOURCE_PAGE_TITLE);
    }

    //Audits -> Report Central
    public boolean goToReportCentral()
    {
        openMenu(AuditsMenuElements.auditsMenu);
        sendEnterToElement(AuditsMenuElements.reportCentral);
        boolean windowAppeared = switchToWindow(ReportCentralPageElements.REPORT_CENTRAL_PAGE_TITLE);
        waitForGridRefresh();
        maximizeCurrentWindow();
        return windowAppeared;
    }

    //Audits -> Twirl - Inverted
    public boolean goToTwirlInverted()
    {
        openMenu(AuditsMenuElements.auditsMenu);
        sendEnterToElement(AuditsMenuElements.twirlInverted);
        boolean windowOpened = switchToWindow(TwirlReportPageElements.TWIRL_REPORT_PAGE_TITLE);
        maximizeCurrentWindow();
        return windowOpened;
    }

    //Audits -> Twirl - Publication
    public boolean goToTwirlPublication()
    {
        openMenu(AuditsMenuElements.auditsMenu);
        sendEnterToElement(AuditsMenuElements.twirlPublication);
        boolean windowOpened = switchToWindow(TwirlReportPageElements.TWIRL_REPORT_PAGE_TITLE);
        maximizeCurrentWindow();
        return windowOpened;
    }

    public void openMenu()
    {
        sendKeyToElement(AuditsMenuElements.auditsMenu, Keys.ARROW_DOWN);
    }
}
