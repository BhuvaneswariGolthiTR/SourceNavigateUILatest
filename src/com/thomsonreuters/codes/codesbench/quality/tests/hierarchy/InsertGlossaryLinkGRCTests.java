package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits.AuditsDataMocking.datapodObject;

public class InsertGlossaryLinkGRCTests extends TestService
{
    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that a newly inserted glossary term has the correct attributes associated to it <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void insertGlossaryLinkGRCTest()
    {
        datapodObject = TargetDataMockingNew.FCAHandbook.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        TargetDataMockingNew.moveDatapodUnderNodeUUID(datapodObject, "I2133A990FC9511E7A9C880000BA47767");
        String glossaryTerm = "account";
        String contentSet = "FCA Handbook";
        String phraseToInsert = "account";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        //Replace text with glossary term and glossary link
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorPage().switchDirectlyToTextFrame();
        editorTextPage().deleteAllTextFromFirstTextParagraph();
        editorTextPage().insertPhraseAndHighlight(phraseToInsert);
        editorPage().insertGlossaryLinkViaContextMenu();

        //Check that the glossary term has the correct attributes
        editorPage().switchDirectlyToTextFrame();
        String refType = editorTextPage().getRefTypeOfGivenCiteReference(glossaryTerm);
        boolean refTypeIsCorrect = refType.equals("GM");

        String manualEditAttribute = editorTextPage().getManualEditAttributeOfGivenCiteReference(glossaryTerm);
        boolean manualEditAttributeIsCorrect = manualEditAttribute.equals("true");

        String pinpointPageAttribute = editorTextPage().getPinpointPageAttributeOfGivenCiteReference(glossaryTerm);
        boolean pinpointPageAttributeIsCorrect = pinpointPageAttribute.equals(glossaryTerm);
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(refTypeIsCorrect,"The glossary term has an incorrect ref type"),
                () -> Assertions.assertTrue(manualEditAttributeIsCorrect,"The glossary term has an incorrect manual edit attribute"),
                () -> Assertions.assertTrue(pinpointPageAttributeIsCorrect,"The glossary term has an incorrect pinpoint page attribute")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies after inserting a new node, that the primary term attribute matches the node value that was set <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void insertGlossaryLinkCreatePrimaryTermGRCTest()
    {
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        String nodeValue = "New Primary Term";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Set data for new node and create it
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInsertInHierarchy();
        insertNewNodesPage().setNewNodeDepthByRowNumber("0", 1);//This method has starting index of 1, used by EPAM so won't refactor
        insertNewNodesPage().setWestlawFormatStatus("Default");
        insertNewNodesPage().setNodeTypeofGivenRow("GLOSSARY", 0);
        insertNewNodesPage().setKeywordOfGivenRow("=", 0);
        insertNewNodesPage().setValueToGivenRow(nodeValue, 0);
        insertNewNodesPage().setEffectiveStartDateOfGivenRow("01/05/2017", 1);//This method has starting index of 1, used by EPAM so won't refactor
        insertNewNodesPage().setEffectiveEndDateOfGivenRow("", 0);
        insertNewNodesPage().clickQuickLoad();
        insertNewNodesPage().clickOk();

        //Check that primary term is correct
        siblingMetadataPage().selectNodesByValue(nodeValue);
        Assertions.assertEquals(siblingMetadataPage().getSelectedNodeValue(), nodeValue, "Selected node is not the new node created. Failing the test to prevent incorrect data from being deleted");
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean glossaryTermPageAppears = siblingMetadataContextMenu().manageGlossaryTerms();
        Assertions.assertTrue(glossaryTermPageAppears, "The Glossary Terms page did not appear when it should");

        String primaryTerm = glossaryTermPage().getTextOfPrimaryTerm();
        boolean primaryTermIsCorrect = primaryTerm.equals(nodeValue);
        glossaryTermPage().clickCancel();

        Assertions.assertTrue(primaryTermIsCorrect, "The primary term is not the correct value");
    }

    @AfterEach
    public void cleanup()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}