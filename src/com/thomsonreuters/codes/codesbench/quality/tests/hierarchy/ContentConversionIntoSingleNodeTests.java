package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.InputDocumentContentPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.xml.XmlUtils;
import org.custommonkey.xmlunit.Difference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ContentConversionIntoSingleNodeTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - JETS-16696 <br>
     * SUMMARY - This test verifies after inputting a xml file into the node, writing that xml text to a file, comparing it to a preexisting file,
     * and counting the number of differences <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void userInitiatedContentConversionIntoSingleNodeGRCTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();

        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String contentUUID = datapodObject.getSections().get(0).getContentUUID();

        String fileName = "Sample Keyed Guernsey File (Section) Short.xml";
        String goldDataFileName = "DatapodGoldDataForContentConversion.xml";
        String datapodGoldDataFileName = "TempGoldData.xml";
        String testFilePath = new File("commonFiles\\TestFiles\\Temp XML Doc For Comparisons.xml").getAbsolutePath();
        String goldDataFilePath = new File("commonFiles\\TestFiles\\Datapods\\" + goldDataFileName).getAbsolutePath();
        String datapodGoldDataFilePath = new File("commonFiles\\TestFiles\\Datapods\\" + datapodGoldDataFileName).getAbsolutePath();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Input doc content into node
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInputDocumentContent();
        inputDocumentContentPage().findAndSelectFile(fileName);
        inputDocumentContentPage().clickOk();

        //Get xml text from node, delete latest wip version, and update previous wip version to latest
        Connection uatHierarchyConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String nodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, ContentSets.CROWN_DEPENDENCIES.getCode(), uatHierarchyConnection);
        String xmlText = HierarchyDatabaseUtils.getXmlTextOfNodeWithContentUuid(contentUUID, uatHierarchyConnection);
        HierarchyDatabaseUtils.deleteLatestWipVersion(contentUUID, uatHierarchyConnection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUID, uatHierarchyConnection);
        BaseDatabaseUtils.disconnect(uatHierarchyConnection);

        Path path = Paths.get(goldDataFilePath);
        Path newPath = Paths.get(datapodGoldDataFilePath);
        Charset charset = StandardCharsets.UTF_8;

        boolean noDifferencesFound = false;
        List<Difference> differences = new ArrayList<>();

        String content;
        try
        {
            content = Files.readString(path, charset);
            content = content.replace("IABCDEFE429A9DCB4A46F87E1C5FA00011.990", nodeValue);
            Files.write(newPath, content.getBytes(charset));
            //Write xml text to a file to compare
            XmlUtils.writeXmlToFile(xmlText);
            File document = new File(datapodGoldDataFilePath);
            File newDocument = new File(testFilePath);

            //Compare xml text
            differences = XmlUtils.compare(document, newDocument);
            noDifferencesFound = differences.size() == 0;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            FileUtils.deleteFile(datapodGoldDataFilePath);
        }

        //Check number of differences
        Assertions.assertTrue(noDifferencesFound, String.format("There were %s differences found between the two files when there should be none", differences.size()));
    }

    /**
     * STORY/BUG - JETS-16696 <br>
     * SUMMARY - This test verifies that an error message occurs when you try to input document content of a pdf file
     * instead of a xml file into a node and when you try to input document content into a gradehead node <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void inputDocumentContentErrorMessageTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String initialNodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String gradeHeadNodeUuid = datapodObject.getAllNodes().get(0).getNodeUUID();
        String incorrectFileName = "EO102.pdf";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check error message displays when trying to input a pdf file
        hierarchySearchPage().searchNodeUuid(initialNodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInputDocumentContent();
        inputDocumentContentPage().findAndSelectFile(incorrectFileName);
        boolean isErrorDisplayed = inputDocumentContentPage().isElementDisplayed(InputDocumentContentPageElements.FILE_MUST_BE_XML_ERROR_MESSAGE);
        inputDocumentContentPage().clickCancel();
        boolean isWindowClosed = inputDocumentContentPage().isWindowClosed();

        //Check error message displays after trying to input content into a grade node
        hierarchySearchPage().searchNodeUuid(gradeHeadNodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInputDocumentContent();
        boolean isErrorDisplayed2 = inputDocumentContentPage().isElementDisplayed(InputDocumentContentPageElements.FUNCTION_ONLY_AVAILABLE_ON_SECTION_LEVEL_NODE_ERROR_MESSAGE);
        inputDocumentContentPage().clickCancel();
        boolean isWindowClosed2 = inputDocumentContentPage().isWindowClosed();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isErrorDisplayed, "Error message not displayed when it should"),
            () -> Assertions.assertTrue(isWindowClosed, "Window didn't close when it should"),
            () -> Assertions.assertTrue(isErrorDisplayed2, "Error message not displayed when it should"),
            () -> Assertions.assertTrue(isWindowClosed2, "Window didn't close when it should")
        );
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
