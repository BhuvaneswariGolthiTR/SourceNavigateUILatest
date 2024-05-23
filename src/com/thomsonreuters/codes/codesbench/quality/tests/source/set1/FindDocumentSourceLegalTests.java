package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class FindDocumentSourceLegalTests extends TestService
{

    List<SourceDatapodObject> datapodObjects = new ArrayList<>();
    Connection connection;

    /**
     * STORY: N/A <br>
     * SUMMARY: Tests the cancel button on the find document search filter page <br>
     * USER: LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findDocumentCancelLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFooterToolsPage().clickMagnifyingGlass();
        //The assertion for validating the find document window closes is built into clickCancel().
        documentSearchFilterPage().clickCancel();
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Finds a rendition using correlation ID <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findDocumentCorrelationIdLegalTest()
    {
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        connection = CommonDataMocking.connectToDatabase(environmentTag);

        String correlationId = SourceDatabaseUtils.getCorrelationUuidFromLineageUuid(connection, datapodObjects.get(0).getLineages().get(0).getLineageUUID());

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        int beforeFilter = sourceNavigateGridPage().getDocumentCount();
        sourceNavigateFooterToolsPage().clickMagnifyingGlass();
        documentSearchFilterPage().setCorIdFilter(correlationId);
        documentSearchFilterPage().clickCorIdFilterButton();

        int renditionNumber = sourceNavigateGridPage().getNumberOfRenditions();
        boolean numberOfRenditionChanged = renditionNumber != beforeFilter;
        boolean renditionsPresent = renditionNumber != 0;

        List<String> correlationIds = sourceNavigateGridPage().getAllCorrelationIDs();
        boolean correlationPassed = correlationIds.stream().allMatch(text -> text.equals(correlationId));
        Assertions.assertAll
        (
                () -> Assertions.assertTrue(numberOfRenditionChanged, "Rendition number changed"),
                () -> Assertions.assertTrue(renditionsPresent, "There is at least one rendition"),
                () -> Assertions.assertTrue(correlationPassed,"The correlation was correct")
        );
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Finds a rendition using billId <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findDocumentBillIdGridTest()
    {
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        connection = CommonDataMocking.connectToDatabase(environmentTag);

        String billId = SourceDatabaseUtils.getBillId(connection, datapodObjects.get(0).getRenditions().get(0).getRenditionUUID());
        String expectedDocNumber = datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        int beforeFilter = sourceNavigateGridPage().getNumberOfRenditions();
        sourceNavigateFooterToolsPage().clickMagnifyingGlass();
        documentSearchFilterPage().setBillIdFilter(billId);
        documentSearchFilterPage().clickBillIdFilterButton();
        int renditionNumber = sourceNavigateGridPage().getNumberOfRenditions();
        boolean numberOfRenditionChanged = renditionNumber != beforeFilter;
        boolean renditionsPresent = renditionNumber != 0;

        List<String> docNumbers = sourceNavigateGridPage().getAllDocNumbers();

        boolean docNumberPassed = docNumbers.stream().allMatch(text -> text.equals(expectedDocNumber));
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(numberOfRenditionChanged, "The number of rendition changed"),
            () -> Assertions.assertTrue(renditionsPresent, "At least one rendition is present"),
            () -> Assertions.assertTrue(docNumberPassed,"The document numbers were correct")
        );
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Finds a rendition using document uuid <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findDocumentDocUUIdGridTest()
    {
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        connection = CommonDataMocking.connectToDatabase(environmentTag);

        String docUuid = SourceDatabaseUtils.getDocumentUuidFromRenditionUuid(connection, datapodObjects.get(0).getRenditions().get(0).getRenditionUUID());
        String expectedDocNumber = datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        int beforeFilter = sourceNavigateGridPage().getNumberOfRenditions();
        sourceNavigateFooterToolsPage().clickMagnifyingGlass();
        documentSearchFilterPage().setDocUuidFilter(docUuid);
        documentSearchFilterPage().clickDocUuidFilterButton();
        int renditionNumber = sourceNavigateGridPage().getNumberOfRenditions();
        boolean numberOfRenditionChanged = renditionNumber != beforeFilter;
        boolean renditionsPresent = renditionNumber != 0;

        List<String> docNumbers = sourceNavigateGridPage().getAllDocNumbers();

        boolean docNumberPassed = docNumbers.stream().allMatch(text -> text.equals(expectedDocNumber));
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(numberOfRenditionChanged, "The number of rendition changed"),
            () -> Assertions.assertTrue(renditionsPresent, "At least one rendition is present"),
            () -> Assertions.assertTrue(docNumberPassed, "The document numbers were correct")
        );
    }

    //Search for the rendition itself.
    /**
     * STORY: N/A <br>
     * SUMMARY: Finds a rendition using Assigned User's name <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findDocumentAssignedUserGridTest()
    {
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        connection = CommonDataMocking.connectToDatabase(environmentTag);

        String expectedDocNumber1 = datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "";
        String expectedDocNumber2 = datapodObjects.get(1).getRenditions().get(0).getDocNumber() + "";
        String userID = user().getUsername();
        String firstRenditionUUID = datapodObjects.get(0).getRenditions().get(0).getRenditionUUID();
        String secondRenditionUUID = datapodObjects.get(1).getRenditions().get(0).getRenditionUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        SourceDatabaseUtils.assignUser(connection,userID,firstRenditionUUID);
        SourceDatabaseUtils.assignUser(connection,userID,secondRenditionUUID);

        sourceMenu().goToSourceC2012Navigate();

        int beforeFilter = sourceNavigateGridPage().getNumberOfRenditions();
        sourceNavigateFooterToolsPage().clickMagnifyingGlass();
        documentSearchFilterPage().setAssignedUserFilter(userID);
        documentSearchFilterPage().clickAssignedUserFilterButton();
        sourcePage().waitForGridRefresh();
        int renditionNumber = sourceNavigateGridPage().getNumberOfRenditions();
        Assertions.assertTrue(renditionNumber > 0, "There are no renditions that match.");
        boolean numberOfRenditionChanged = renditionNumber != beforeFilter;
        boolean renditionsPresent = renditionNumber == 2;

        List<String> docNumbers = sourceNavigateGridPage().getAllDocNumbers();

        boolean docNumberPassed = docNumbers.stream().allMatch(text -> text.equals(expectedDocNumber1) || text.equals(expectedDocNumber2));
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(numberOfRenditionChanged, "The number of rendition changed"),
            () -> Assertions.assertTrue(renditionsPresent, "At least one rendition is present"),
            () -> Assertions.assertTrue(docNumberPassed, "The document numbers were correct")
        );
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Finds a rendition using rendition uuid <br>
     * USER: LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findDocumentRenditionUUIdGridTest()
    {
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        String renditionUuid = datapodObjects.get(0).getRenditions().get(0).getRenditionUUID();
        String expectedDocNumber = datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        int beforeFilter = sourceNavigateGridPage().getNumberOfRenditions();
        sourceNavigateFooterToolsPage().clickMagnifyingGlass();
        documentSearchFilterPage().setRenditionUuidFilter(renditionUuid);
        documentSearchFilterPage().clickRenditionUuidFilterButton();
        int renditionNumber = sourceNavigateGridPage().getNumberOfRenditions();
        boolean numberOfRenditionChanged = renditionNumber != beforeFilter;
        boolean renditionsPresent = sourceNavigateGridPage().firstRenditionExists();

        List<String> docNumbers = sourceNavigateGridPage().getAllDocNumbers();

        boolean docNumberPassed = docNumbers.stream().allMatch(text -> text.equals(expectedDocNumber));
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(numberOfRenditionChanged, "The number of rendition changed"),
            () -> Assertions.assertTrue(renditionsPresent, "At least one rendition is present"),
            () -> Assertions.assertTrue(docNumberPassed, "The document numbers were correct")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        disconnect(connection);
        for(SourceDatapodObject datapodObject : datapodObjects)
        {
            datapodObject.delete();
        }
        datapodObjects.clear();
    }
}