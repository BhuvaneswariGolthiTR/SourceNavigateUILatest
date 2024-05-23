package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

public class PreviewFromHierarchyGRCTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - JETS-20923 <br>
     * SUMMARY - This test verifies that the correct text is displayed in the 'Preview Document' page <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void previewFromHierarchyGRCTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        String partOfAParagraph = "This Part is used for QED Testing purposes only.";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check that text is shown on Preview Document page
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().preview();
        boolean isTextInPreview = hierarchyPreviewDocumentPage().doesDocumentContainGivenText(partOfAParagraph);
        hierarchyPreviewDocumentPage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertTrue(isTextInPreview,"The given text was not displayed in the preview page");
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}