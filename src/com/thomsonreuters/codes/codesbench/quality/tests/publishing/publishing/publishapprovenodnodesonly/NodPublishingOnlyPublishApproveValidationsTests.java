package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovenodnodesonly;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class NodPublishingOnlyPublishApproveValidationsTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-6100, HALCYONST-7874 <br>
     * SUMMARY - This test verifies that if a parent node has not been published at any point, after setting the child node to ready status in the Publishing UI,
     * a warning message appears saying a node was added because they are required parent nodes.
     * This test then verifies that the validation of the added parent node is set to 'P'. <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void parentageValidationTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        HierarchyDatapodConfiguration.getConfig().setSectionCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String sectionNodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String chapterNodeUuid = datapodObject.getAllNodes().get(2).getNodeUUID();

        String sectionNodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUuid, contentSetIowa, uatConnection);
        String chapterNodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(chapterNodeUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

//        publishingMenu().goToPublishApproveNodNodesOnly();
        publishingMenu().goToPublishingPublishApproveTextNodesOnly();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(sectionNodeValue);

        gridPage().rightClickByNodeTargetValue(sectionNodeValue);
        gridContextMenu().approvedStatus();
        toolbarPage().clickNext();

        gridPage().selectByNodeTargetValue(chapterNodeValue);
        String validationText = gridPage().getValidationOfSelectedNode();
        boolean pValidationAppears = validationText.equals("P");
        boolean pWarningMessageAppears = toolbarPage().isElementDisplayed(ToolbarElements.P_WARNING_MESSAGE_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(pValidationAppears,"The selected chapter node should have a 'P' symbol in the validation column but does not"),
            () -> Assertions.assertTrue(pWarningMessageAppears,"The 'Warning: P' message does not appear at the top of the Publishing UI when it should")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
        if(uatConnection != null)
        {
            BaseDatabaseUtils.disconnect(uatConnection);
        }
    }
}
