package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
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

public class AddRuleBookToSeriesRiskTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - JETS-23127 <br>
     * SUMMARY - This test logs in as the risk user and verifies that Add to Series is an option in the hierarchy navigate
     *           context menu but that it is disabled for the risk user on a chapter level node  <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void verifyAddToSeriesIsDisabledForChapterLevelNodeRiskTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String chapterNodeUuid = datapodObject.getChapters().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(chapterNodeUuid);
        siblingMetadataPage().rightClickSelectedNode();

        boolean isAddToSeriesDisabled = siblingMetadataContextMenu().isElementDisabled(SiblingMetadataContextMenuElements.addToSeries);
        Assertions.assertTrue(isAddToSeriesDisabled, "Add To Series was enabled in the context menu when it should have been disabled");
    }

    /**
     * STORY/BUG - JETS-23127 <br>
     * SUMMARY - This test logs in as the risk user and verifies that Add to Series is an option in the hierarchy navigate
     *           context menu and that it is enabled for the risk user on a section level node  <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void verifyAddToSeriesIsEnabledForSectionLevelNodeRiskTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String sectionNodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(sectionNodeUuid);
        siblingMetadataPage().rightClickSelectedNode();
        boolean didTheGRCNewsAndAlertsPageAppear = siblingMetadataContextMenu().addToSeries();
        Assertions.assertTrue(didTheGRCNewsAndAlertsPageAppear, "The GRC News and Alerts page did not appear when it should have");
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
