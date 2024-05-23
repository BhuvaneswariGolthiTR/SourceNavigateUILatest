package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClassifyDescendantsTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This tests to make sure Classify Descendants is not an option for legal users <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void withChildrenTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Verify Classify Descendants option is disabled
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean isClassifyDescendantsContextMenuOptionDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.CLASSIFY_DESCENDANTS);
        Assertions.assertFalse(isClassifyDescendantsContextMenuOptionDisplayed, "The 'Classify Descendants' context menu option is enabled when it should be disabled");
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}