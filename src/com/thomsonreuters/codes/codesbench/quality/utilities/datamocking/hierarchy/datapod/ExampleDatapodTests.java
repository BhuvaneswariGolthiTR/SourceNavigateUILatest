package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ExampleDatapodTests extends TestService
{

    private HierarchyDatapodObject datapodObject;

    @Test
    @CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertDefaultDatapodTest()
    {
        // Insert the default Small hierarchy datapod
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        // The default hierarchy datapod has 1 Section-level node
        String sectionNodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        // Get the value of the section via the database
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);

        // Go to hierarchy navigate
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        // Verify the node was found
        hierarchySearchPage().searchNodeUuid(sectionNodeUUID);
        boolean nodeWasInserted = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(nodeValue);
        Assertions.assertTrue(nodeWasInserted, "Selected node did not have the expected value!");
    }

    @Test
    @LEGAL
    @LOG
    public void insertMediumDatapodTest()
    {
        // Insert the default Medium hierarchy datapod
        datapodObject = TargetDataMockingNew.Iowa.Medium.insert();

        // The default hierarchy datapod has 1 Section-level node
        String sectionNodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        // Get the value of the section via the database
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);

        // Go to hierarchy navigate
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        // Verify the node was found
        hierarchySearchPage().searchNodeUuid(sectionNodeUUID);
        boolean nodeWasInserted = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(nodeValue);
        Assertions.assertTrue(nodeWasInserted, "Selected node did not have the expected value!");
    }

    @Test
    @LEGAL
    @LOG
    public void insertDatapodWithMultipleSectionsAndBluelinesTest()
    {
        // Give each section 1 blueline
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        // Give each chapter 2 sections
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);

        // Insert the hierarchy datapod
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        // Two sections each have 1 Blueline, so there are 2 Bluelines
        String bluelineNodeUUID = datapodObject.getBluelines().get(1).getNodeUUID();

        // Get the value of the blueline via the database
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(bluelineNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);

        // Go to hierarchy navigate
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        // Verify the node was found
        hierarchySearchPage().searchNodeUuid(bluelineNodeUUID);
        boolean nodeWasInserted = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(nodeValue);
        Assertions.assertTrue(nodeWasInserted, "Selected node did not have the expected value!");
    }

    @Test
    @RISK
    @LOG
    public void insertDatapodInCrownDependenciesTest()
    {
        // Load the default config for the datapod to have Risk content
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();

        // Insert the hierarchy datapod
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();

        // The default Risk datapod has 1 Part in it
        String partNodeUUID = datapodObject.getParts().get(0).getNodeUUID();

        // Get the value of the blueline via the database
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(partNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);

        // Go to hierarchy navigate
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        // Verify the node was found
        hierarchySearchPage().searchNodeUuid(partNodeUUID);
        boolean nodeWasInserted = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(nodeValue);
        Assertions.assertTrue(nodeWasInserted, "Selected node did not have the expected value!");
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
