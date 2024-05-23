package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.sql.Connection;

public class CiteReferencesAndTreatmentsCopyAndPasteTests extends TestService
{
    String renditionUuid;
    String username;
    /**
     * STORY: 41000 <br>
     * SUMMARY: Checks the References and treatement added with a copy <br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void citeReferencesAndTreatmentsCopyAndPasteTest()
    {
        username = user().getUsername().toUpperCase();
        String contentSet = "Iowa (Development)";
        String renditionStatus = "ENR";
        String contentType = "BILL";
        String docType = "HF";
        String docNumber = "126";

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared);

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuid = sourceNavigateGridPage().getUUID();

        sourceNavigateGridPage().firstRenditionEditContent();
        editorTreePage().expandEditorsTreeAndClickNode("source.body","source.section \"1\"");
        editorPage().switchDirectlyToTextFrame();
        WebElement firstValidCite = editorTextPage().getValidCite(1,1);

        String firstCiteTitle = firstValidCite.getAttribute("title");

        String firstCiteContentSet = firstCiteTitle.substring(firstCiteTitle.indexOf("Content Set("));
        firstCiteContentSet = firstCiteContentSet.substring(0,firstCiteContentSet.indexOf(")"));

        String firstCiteCitation = firstCiteTitle.substring(firstCiteTitle.indexOf("Citation("));
        firstCiteCitation = firstCiteCitation.substring(0,firstCiteCitation.indexOf(")"));

        String firstCiteWestLawDisplay = firstCiteTitle.substring(firstCiteTitle.indexOf("Westlaw Display("));
        firstCiteWestLawDisplay = firstCiteWestLawDisplay.substring(0,firstCiteWestLawDisplay.indexOf(")"));

        editorTextPage().click(firstValidCite);
        RobotUtils.ctrlCUsingRobot();
        editorTextPage().sendKeys(Keys.END);
        RobotUtils.ctrlVUsingRobot();
        boolean secondValidCiteAppears = editorTextPage().doesValidCiteExist(1,2);

        WebElement secondValidCite = editorTextPage().getValidCite(1,2);
        String secondCiteTitle = secondValidCite.getAttribute("title");
        boolean secondValidCiteTitleCorrectBeforeValidationAndLinkUpdate = (secondCiteTitle.contains(firstCiteCitation) && secondCiteTitle.contains(firstCiteContentSet) && secondCiteTitle.contains(firstCiteWestLawDisplay));
        editorToolbarPage().clickValidate();
       boolean correctErrorsReceived = editorMessagePage().checkMessage("error: Document has 1 Data error(s) and 0 Data warning(s).", "error: Data error id[12]: MISSING_TREATMENT", "error: CLICK HERE to find the error in the document.", "error: Location: Cite query is not preceeded by a citator.treatment node");

        editorPage().switchDirectlyToTextFrame();
        WebElement firstCiteTreatment = editorTextPage().getValidTreatment(1,1);
        editorTextPage().click(firstCiteTreatment);
        RobotUtils.ctrlCUsingRobot();

        editorTextPage().click(secondValidCite);
        editorTextPage().sendKeys(Keys.ARROW_LEFT);
        RobotUtils.ctrlVUsingRobot();
        boolean secondTreatmentAppears = editorTextPage().doesValidTreatmentExist(1,2);

        editorToolbarPage().clickValidate();
        boolean validationSuccessful = editorMessagePage().checkMessage("info: The document validates by DTD validations.\ninfo: The document validates by Data validations.");

        editorToolbarPage().clickSave();
        editorPage().closeAndCheckInChanges();
        AutoITUtils.verifyAlertTextAndAccept(true,"");

        sourcePage().switchToSourceNavigatePage();
        sourcePage().waitForGridRefresh();
        boolean blueLinkFlagAppearsAfterValidation = sourceNavigateGridPage().isABlueLinkFlagPresent();

        sourcePage().waitForGridRefresh();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().validateValidateAndUpdateLinks();
        sourcePage().waitForGridRefresh();
        sourceNavigateGridPage().waitUntilFirstDocBeUnlocked();

        boolean blueLinkFlagDisappearsAfterUpdatingLinks = !sourceNavigateGridPage().isABlueLinkFlagPresent();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchDirectlyToTextFrame();
        secondValidCite = editorTextPage().getValidCite(1,2);
        secondCiteTitle = secondValidCite.getAttribute("title");
        boolean secondValidCiteTitleCorrectAfterValidationAndLinkUpdate = (secondCiteTitle.contains(firstCiteCitation) && secondCiteTitle.contains(firstCiteContentSet) && secondCiteTitle.contains(firstCiteWestLawDisplay) && secondCiteTitle.contains("ID("));

        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(secondValidCiteAppears,"The second cite did appear."),
            () -> Assertions.assertTrue(secondValidCiteTitleCorrectBeforeValidationAndLinkUpdate, "Hover text of second cite is correct before validation and link update."),
            () -> Assertions.assertTrue(secondValidCiteTitleCorrectAfterValidationAndLinkUpdate,"Hover text of second cite is correct after validation and link update"),
            () -> Assertions.assertTrue(secondTreatmentAppears, "The second treatment did appear"),
            () -> Assertions.assertTrue(correctErrorsReceived, "Correct errors received after validation attempt."),
            () -> Assertions.assertTrue(validationSuccessful,"Validation was successful after adding the second treatment"),
            () -> Assertions.assertTrue(blueLinkFlagAppearsAfterValidation,"Blue link flag did appear after validation."),
            () -> Assertions.assertTrue(blueLinkFlagDisappearsAfterUpdatingLinks,"Blue link flag did disappear after updating links.")
        );
    }

    @AfterEach
    public void restoreBaselineToPreviousOne()
    {
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        SourceDatabaseUtils.restoreBaselineToAPreviousBaseline(connection, renditionUuid, username, 3);
    }
}
