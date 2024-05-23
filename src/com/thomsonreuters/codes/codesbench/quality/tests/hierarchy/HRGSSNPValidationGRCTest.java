package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import java.sql.Connection;
public class HRGSSNPValidationGRCTest extends TestService
{
    HierarchyDatapodObject datapodObject;
    Connection connection;
    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that this specific node with invalid cite references still successfully validates subsection
     * and carries the correct ref type for the cite reference. <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void snpValidateDARefTypeGRCTest()
    {
        datapodObject = TargetDataMockingNew.RegGuidanceSummaryUS.Small.InvalidCite.insert(1);
        String contentSet = "Reg Guidance Summary US";
        String nodeUUID = datapodObject.getSections().get(1).getNodeUUID();
        String citeReferenceText = "Alta. Reg. 28/2015, s. 1";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        //Open editor page and Generate Subsection
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        String refType = editorTextPage().getRefTypeOfGivenCiteReference(citeReferenceText);
        boolean refTypeIsCorrect = refType.equals("DA");
        editorToolbarPage().clickSubsectionGeneration();
        //Check validations were successful and ref type is correct
        boolean validationSuccessful = editorPage().validateSubsectionGeneration();
        editorPage().closeDocumentAndDiscardChanges();
        editorPage().waitForEditorToClose();
        Assertions.assertAll
          (
              () -> Assertions.assertTrue(validationSuccessful,"The subsections were generated and the validations were unsuccessful"),
              () -> Assertions.assertTrue(refTypeIsCorrect,"The ref type of the cite reference is not as expected")
          );
    }

    @AfterEach
    public void cleanup()
    {
        if(datapodObject !=null)
        {
            datapodObject.delete();
        }
    }
}
