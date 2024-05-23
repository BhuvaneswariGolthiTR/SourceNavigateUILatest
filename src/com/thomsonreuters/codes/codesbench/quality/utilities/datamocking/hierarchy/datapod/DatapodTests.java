package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes.HierarchyNode;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class DatapodTests extends TestService
{

    HierarchyDatapodObject datapodObject;
    Connection connection;
    private  final String FCA_HANDBOOK = "FCA Handbook";

    @Test
    @RISK
    @LOG
    public void setDatapodToPublishReady()
    {
        // Insert the default hierarchy datapods
        //HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();

        // Go to hierarchy navigate
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        for(HierarchyNode node : datapodObject.getAllNodes())
        {
            String nodeUUID = node.getNodeUUID();
            hierarchySearchPage().searchNodeUuid(nodeUUID);
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setReadyPublishingStatus();
            boolean nodeIsReadyStatus = siblingMetadataPage().isSelectedNodeReadyStatus();
            Assertions.assertTrue(nodeIsReadyStatus, "Node (" + node.getType().toString() + ") is set to ready status");
        }

        System.out.println("This is a good spot to put a breakpoint");
    }

    @Test
    @LEGAL
    @LOG
    public void exampleDatapodTest()
    {
        // Insert the default hierarchy datapods
        datapodObject = TargetDataMockingNew.Iowa.Large.insert();

        // The default hierarchy datapod has 1 Section-level node
        String sectionNodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String contentUUID = datapodObject.getSections().get(0).getContentUUID();

        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String dbXML = HierarchyDatabaseUtils.getXmlTextOfNodeWithContentUuid(contentUUID, connection);
        BaseDatabaseUtils.disconnect(connection);

        System.out.println("dbXML:");
        System.out.println(dbXML);
        System.out.println("(" + dbXML.length() + " characters)");


        // Go to hierarchy navigate
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        // Verify the node was found
        hierarchySearchPage().searchNodeUuid(sectionNodeUUID);
        boolean nodeWasInserted = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(nodeValue);
        Assertions.assertTrue(nodeWasInserted, "Selected node did not have the expected value!");
        // Continue testing as you would normally

    }

    @Test
    @LEGAL
    @LOG
    public void exampleDatapodTest2()
    {
        // Insert the default hierarchy datapods
        datapodObject = TargetDataMockingNew.Iowa.Large.insert();

        // The default hierarchy datapod has 1 Section-level node
        String sectionNodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);

        // I7A35F76014EE11DA8AC5CD53670E6B4E

        // Go to hierarchy navigate
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        // Verify the node was found
        hierarchySearchPage().searchNodeUuid(sectionNodeUUID);
        boolean nodeWasInserted = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(nodeValue);
        Assertions.assertTrue(nodeWasInserted, "Selected node did not have the expected value!");

        String newParentUUID = "I7A35F76014EE11DA8AC5CD53670E6B4E";
        TargetDataMockingNew.moveDatapodUnderNodeUUID(datapodObject, newParentUUID);
        // Continue testing as you would normally
        hierarchySearchPage().searchNodeUuid(sectionNodeUUID);
        nodeWasInserted = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(nodeValue);
        Assertions.assertTrue(nodeWasInserted, "Selected node did not have the expected value!");
    }

    @Test
    @LEGAL
    @LOG
    public void exampleDatapodTest3()
    {
        // Insert the default hierarchy datapod
        datapodObject = TargetDataMockingNew.USCA.Large.insert();

        // The default hierarchy datapod has 1 Section-level node
        String sectionNodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        // Sections in hierarchy datapods are numbered 999.990, 999.991, 999.992... 999.999, 999.9910, 999.9911...
        String nodeValue = "999.990";

        // Go to hierarchy navigate in USCA content set
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet("USCA(Development)");
        hierarchyMenu().goToNavigate();

        // Verify the node was found
        hierarchySearchPage().searchNodeUuid(sectionNodeUUID);
        boolean nodeWasInserted = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(nodeValue);
        Assertions.assertTrue(nodeWasInserted, "Selected node did not have the expected value!");
        // Continue testing as you would normally
    }

    @Test
    @RISK
    @LOG
    public void exampleDatapodTest4()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.RegGuidanceSummaryUS.Small.insert();
        String partNodeUUID = datapodObject.getParts().get(0).getNodeUUID();
        String nodeValue = "1";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet("Reg Guidance Summary US");
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(partNodeUUID);
        boolean nodeWasInserted = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(nodeValue);
        Assertions.assertTrue(nodeWasInserted, "Selected node did not have the expected value!");
    }

    @Test
    @LEGAL
    @LOG
    public void exampleDatapodTest5()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultFedRegsConfig();
        datapodObject = TargetDataMockingNew.CodeOfFedRegs.Small.insert();
        String partNodeUUID = datapodObject.getParts().get(0).getNodeUUID();
        String nodeValue = "1";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet("Code of Fed Regs (Development)");
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(partNodeUUID);
        boolean nodeWasInserted = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(nodeValue);
        Assertions.assertTrue(nodeWasInserted, "Selected node did not have the expected value!");
    }
    @Test
    @LEGAL
    @LOG
    public void exampleTestRange()
    {
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        ArrayList<String> sectionNodes = new ArrayList<>();
        sectionNodes.add(datapodObject.getSections().get(0).getNodeUUID());
        sectionNodes.add(datapodObject.getSections().get(1).getNodeUUID());

        HierarchyDatapodConfiguration.getConfig().setRange(sectionNodes ,connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(datapodObject.getSections().get(0).getNodeUUID());
    }

    @Test
    @EDGE
    @RISK
    @LOG
    public void exampleGlossaryDataPodTest()
    {
        HierarchyDatapodConfiguration.getConfig().setChapterCount(1);
        HierarchyDatapodConfiguration.getConfig().setSubtitleCount(1);
        HierarchyDatapodConfiguration.getConfig().setContentType(HierarchyDatapodContentType.GLOSSARY);
        HierarchyDatapodConfiguration.getConfig().setSectionCount(1);
        datapodObject = TargetDataMockingNew.FCAHandbook.Small.insert();
        TargetDataMockingNew.moveDatapodUnderNodeUUID(datapodObject, "I2133D0A1FC9511E7A9C880000BA47767");
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(FCA_HANDBOOK);
        hierarchyMenu().goToNavigate();

        closeBrowser();
        openBrowser();

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(FCA_HANDBOOK);
        System.out.println("yo");
    }

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void sectionHistoricalTests()
    {
        /*
        When future and past nodes are inserted into both chapters and sections the last
        Chapter inserted will not have the historical sections underneath them.It appears that
        another chapter gets the sections instead.
         */
        HierarchyDatapodConfiguration.getConfig().setNumberOfPastSectionVersions(3);
//        HierarchyDatapodConfiguration.getConfig().setNumberOfFutureSectionVersions(3);
//        HierarchyDatapodConfiguration.getConfig().setNumberOfFutureChapterVersions(3);
        HierarchyDatapodConfiguration.getConfig().setNumberOfPastChapterVersions(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        connection = HierarchyDatabaseUtils.connectToDatabaseUAT();


        homePage().goToHomePage();
        loginPage().logIn();
        System.out.println("Yo");
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
