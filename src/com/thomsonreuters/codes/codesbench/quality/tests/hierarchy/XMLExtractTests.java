package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.XmlExtractPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.landingStrips.LandingStripsFTPClient;
import org.junit.jupiter.api.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

public class XMLExtractTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    HierarchyDatapodObject datapodObject;

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }

    /**
     * STORY: HALCYONST-3134 <br>
     * SUMMARY: This test finds a SET node in Hierarchy Navigate and attempts to run XML Extract on it, verifying that the expected alert appears<br>
     * USER: Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void xmlExtractSETLimitsLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().quickSearch("SET", "IAS");

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean xmlExtractDisabled = siblingMetadataContextMenu().xmlExtractUnableToPerform();
        Assertions.assertTrue(xmlExtractDisabled, "We were able to run xml Extract on the Set Node");
    }

    /**
     * STORY: HALCYONST-3134 <br>
     * SUMMARY: This test finds a Blue line node in Hierarchy Navigate and attempts to run XML Extract on it, verifying that the expected alert appears<br>
     * USER: Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void xmlExtractBlueLineLimitsLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getAllNodes().get(7).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean xmlExtractDisabled = siblingMetadataContextMenu().xmlExtractUnableToPerform();
        Assertions.assertTrue(xmlExtractDisabled, "We were able to run xml Extract on the Blue Line Node");
    }

    /**
     * STORY: HALCYONST-3134 <br>
     * SUMMARY: This test finds a title node in Hierarchy Navigate and attempts to run XML Extract on it, verifying that the expected alert appears<br>
     * USER: Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void xmlExtractTitleLimitsLegalTest()
    {
        // This points to the node right under the SET level node
        String nodeUuid = "I26831DF014EE11DA8AC5CD53670E6B4E";

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean xmlExtractDisabled = siblingMetadataContextMenu().xmlExtractUnableToPerform();
        Assertions.assertTrue(xmlExtractDisabled, "We were able to run xml Extract on the Top Level Title Node");
    }

    /**
     * STORY: HALCYONST-2666,3238, 8930 <br>
     * SUMMARY: On a node in Hierarchy open xml extract, after changing the file name and clicking submit
     * we expect a workflow to be kicked off with that new file name. <br>
     * USER: Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void changeXmlExtractNameAndViewWorkflow()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String contentUuid = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid, uatConnection);
        String fileName = "=_" +  HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetIowa, uatConnection);

        LandingStripsFTPClient lsFtpClient = null;

        try {
            homePage().goToHomePage();
            loginPage().logIn();

            hierarchyMenu().goToNavigate();
            hierarchySearchPage().searchNodeUuid(nodeUuid);
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            boolean xmlExtractPageAppeared = siblingMetadataContextMenu().xmlExtract();
            Assertions.assertTrue(xmlExtractPageAppeared, "Xml Extract page appears");

            //verify keyword and value appears
            boolean doesFileNameAppear = xmlExtractHierarchyPage().isElementDisplayed(String.format(XmlExtractPageElements.FILE_NAME_VALUE, fileName));
            Assertions.assertTrue(doesFileNameAppear, "the File Name does not appear as expected upon opening of document");

            //change value to have _test on it
            xmlExtractHierarchyPage().setFileName(fileName + "_test");
            xmlExtractHierarchyPage().clickSubmit();

            yourWorkflowHasBeenCreatedPage().clickLink();
            boolean workflowFinished = workflowDetailsPage().verifyWorkflowFinished();
            Assertions.assertTrue(workflowFinished, "the workflow Finished");

            workflowDetailsPage().expandWorkflowProperties();
            String userEmail = workflowPropertiesPage().getWorkflowPropertyValueByName("userEmail");
            String userFileName = workflowPropertiesPage().getWorkflowPropertyValueByName("userFilename");
            String xmlExtractFilename = workflowPropertiesPage().getXmlExtractValue("xmlExtractFilename");
            int lastIndexof = xmlExtractFilename.lastIndexOf("/");
            String direcotryFilename = xmlExtractFilename.substring(0, lastIndexof);
            String filename = xmlExtractFilename.substring(lastIndexof + 1);
            String xmlText = HierarchyDatabaseUtils.getXmlTextOfNodeWithContentUuid(contentUuid, uatConnection);

            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIFTEEN_SECONDS);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIFTEEN_SECONDS);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIFTEEN_SECONDS);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIFTEEN_SECONDS);
            lsFtpClient = new LandingStripsFTPClient();
            lsFtpClient.connectToServer();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
            lsFtpClient.getNodeContentFromFile(direcotryFilename, xmlExtractFilename, xmlText);
            //DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
            boolean doesXmlExtractFileContainXmlText = lsFtpClient.checkFileForString(direcotryFilename, filename, xmlText);

            Assertions.assertAll
            (
                () -> Assertions.assertEquals(userEmail, user().getFirstname() + "." + user().getLastname() + "@thomsonreuters.com", "the userEmail is the user's email in the properties page"),
                () -> Assertions.assertEquals(userFileName, fileName + "_test", "the userfileName should reflect the fileName we entered"),
                () -> Assertions.assertTrue(doesXmlExtractFileContainXmlText, "The xmlExtractFile should contain the xml text")
            );
        }
        finally
        {
            lsFtpClient.disconnectFromServer();
        }
    }
}



