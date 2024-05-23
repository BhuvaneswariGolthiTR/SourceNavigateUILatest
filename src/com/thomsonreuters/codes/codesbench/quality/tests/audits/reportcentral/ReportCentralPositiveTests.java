package com.thomsonreuters.codes.codesbench.quality.tests.audits.reportcentral;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.audits.AuditsDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ReportCentralPositiveTests extends TestService
{
    /**
     * STORY/BUG - HALCYONST-8797 Report Central wildcard filtering <br>
     * SUMMARY - In the report central page, this verifies that the use of wildcard characters (eg '*') on the
     * 	 'Report Identifier' column filters the reports as expected <br>
     * USER - Legal <br
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void reportCentralWildcardFilteringTest()
    {
        String reportName1 = "888Test IA 2017 1RG CHAPTER 1 HF 50";
        String reportName2 = "888Test IA 2017 2RG CHAPTER 2 HF 70";
        String reportName3 = "888Test IA 2019 1RG CHAPTER 1 HF 70";
        String reportName4 = "888Test IA 2019 2RG CHAPTER 2 HF 50";

        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        //Add reports to report central via database, grab their report uuids
        String reportUuid1 = AuditsDatabaseUtils.insertReportIntoReportCentral(uatConnection, user().getEditorModifiedByUsername(), reportName1, 106, user().getWorkflowUsername());
        String reportUuid2 = AuditsDatabaseUtils.insertReportIntoReportCentral(uatConnection, user().getEditorModifiedByUsername(), reportName2, 106, user().getWorkflowUsername());
        String reportUuid3 = AuditsDatabaseUtils.insertReportIntoReportCentral(uatConnection, user().getEditorModifiedByUsername(), reportName3, 106, user().getWorkflowUsername());
        String reportUuid4 = AuditsDatabaseUtils.insertReportIntoReportCentral(uatConnection, user().getEditorModifiedByUsername(), reportName4, 106, user().getWorkflowUsername());

        try
        {
            //navigate to report central
            homePage().goToHomePage();
            loginPage().logIn();
            auditsMenu().goToReportCentral();

            //filter for various wildcard uses, verify the filter works as expected
            reportCentralFiltersPage().setReportIdentifier("888Test IA * 1RG *");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            boolean wildcardFilterTest1 = reportCentralPage().isReportInTable(reportName1);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);

            reportCentralFiltersPage().setReportIdentifier("888Test IA *17 * 50");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            boolean wildcardFilterTest12 = reportCentralPage().isReportInTable(reportName1);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);

            reportCentralFiltersPage().setReportIdentifier("888Test IA 20* CHAPTER 2*");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            boolean wildcardFilterTest13 = reportCentralPage().isReportInTable(reportName1);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(wildcardFilterTest1, "report1 should appear"),
                () -> Assertions.assertTrue(wildcardFilterTest12, "report1 should appear"),
                () -> Assertions.assertFalse(wildcardFilterTest13, "report1 should not appear")
            );
        }
        finally
        {
            //cleanup, remove all created reports
            AuditsDatabaseUtils.deleteReportFromReportCentral(uatConnection, reportUuid1);
            AuditsDatabaseUtils.deleteReportFromReportCentral(uatConnection, reportUuid2);
            AuditsDatabaseUtils.deleteReportFromReportCentral(uatConnection, reportUuid3);
            AuditsDatabaseUtils.deleteReportFromReportCentral(uatConnection, reportUuid4);
            BaseDatabaseUtils.disconnect(uatConnection);
        }
    }
}
