package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HierarchyClassifyInCHCTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test to verify that a Risk user can go to Hierarchy -> Navigate,
     * right click a selected document, and see the 'Classify in CHC' option <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void classifyInCHCGRCTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check if the node has the Classify In CHC option enabled
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean isClassifyInCHCOptionEnabled = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.CLASSIFY_IN_CHC);
        Assertions.assertTrue(isClassifyInCHCOptionEnabled,"The Classify In CHC option is disabled when it should be enabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test to verify that a Risk user can go to Hierarchy -> Navigate,
     * right click a selected document, and see the 'Classify in CHC' option is not shown <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void classifyInCHCCodesbenchTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check if the node has the Classify In CHC option enabled
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean isClassifyInCHCOptionEnabled = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.CLASSIFY_IN_CHC);
        Assertions.assertFalse(isClassifyInCHCOptionEnabled,"The Classify In CHC option is enabled when it should be disabled");
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
