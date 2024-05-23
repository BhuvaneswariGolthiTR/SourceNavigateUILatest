package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class BermudaTreatmentEngineTests extends TestService
{
    String renditionUuid;
    String username;
    /**
     * STORY: ranorex 29000<br>
     * SUMMARY:  Checks that the bermuda treatment can be applied to a rendition<br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void bermudaTreatmentEngineTest()
    {
        String contentSet = "Minnesota (Development)";
        String year = "2011";
        String docType = "HF";
        String docNumber = "1003";
        username = user().getUsername().toUpperCase();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        sourcePage().waitForGridRefresh();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuid = sourceNavigateGridPage().getUUID();

        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().scrollToChunk(2);
        boolean sectionOneExists = editorTextPage().sectionXExists(1);
        boolean sectionOneStartsWithTreatment = editorTextPage().sectionXHasTreatment(1);
        boolean sectionOneStartsWithReference = editorTextPage().sectionXHasReference(1);

        boolean sectionTwoExists = editorTextPage().sectionXExists(2);
        boolean sectionTwoStartsWithTreatment = editorTextPage().sectionXHasTreatment(2);
        boolean sectionTwoStartsWithReference = editorTextPage().sectionXHasReference(2);

        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().validateValidateAndUpdateLinks();

        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().waitUntilFirstDocBeUnlocked();

        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().scrollToChunk(2);
        boolean sectionOneHasReferenceAfterValidation = editorTextPage().sectionXHasReference(1);
        boolean sectionTwoHasTreatmentAfterValidation = editorTextPage().sectionXHasTreatment(2);

        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(sectionOneExists,"Section 1 does exist."),
            () -> Assertions.assertTrue(sectionOneStartsWithTreatment, "Section 1 does start with a citation treatment"),
            () -> Assertions.assertTrue(sectionOneHasReferenceAfterValidation, "Section 1 does have a citation reference after validation and link updating."),
            () -> Assertions.assertTrue(sectionTwoExists, "Section 2 does exist"),
            () -> Assertions.assertTrue(sectionTwoStartsWithReference, "Section 2 does start with a citation reference."),
            () -> Assertions.assertTrue(sectionTwoHasTreatmentAfterValidation, "Section 2 does have a citation  treatment after validation and link updating"),
            () -> Assertions.assertTrue(sectionOneStartsWithReference, "Section 1 starts with a citation reference."),
            () -> Assertions.assertTrue(sectionTwoStartsWithTreatment, "Section 2 starts with a citation treatment.")
        );
    }

    @AfterEach
    public void restoreBaseline()
    {
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        SourceDatabaseUtils.restoreBaselineToAPreviousBaseline(connection, renditionUuid, username, 2);
    }
}