package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
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

public class RulebookGlossaryConversionTests extends TestService
{
    String FCAHandbook = ContentSets.FCA_HANDBOOK.getCode();
    HierarchyDatapodObject datapodObject;
    Connection connection;

    /**
     * STORY/BUG - JETS-19679 <br>
     * SUMMARY - A test to make sure that all input/text fields in the glossary term page
     * for a rendition are the same as what we see in our document of gold data:
     * â€œGlossary mapping JIRA removed duplicates.xlsxâ€�<br>
     * USER - Risk <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void rulebookGlossaryConversionTest()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        HierarchyDatapodConfiguration.getConfig().setTopNodeTypeWithString("GLOSSARY");
        HierarchyDatapodConfiguration.getConfig().setSubtitleCount(0);
        HierarchyDatapodConfiguration.getConfig().setChapterCount(0);
        HierarchyDatapodConfiguration.getConfig().setSectionCount(0);

        datapodObject = TargetDataMockingNew.FCAHandbook.Small.insert();
        TargetDataMockingNew.moveDatapodUnderNodeUUID(datapodObject, "I213E09E9FC9511E7A9C880000BA47767");
        String glossaryNodeUUID = datapodObject.getAllNodes().get(0).getNodeUUID();
        String contentSet = "FCA Handbook";
        String definition = "QED Glossary Test";

        String primaryTerm = HierarchyDatabaseUtils.getNodeValueByNodeUUID(glossaryNodeUUID, FCAHandbook, connection);
        String alternateTerms = "Glossary QED Test";

        // Change content set and go to Hierarchy Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        // Quick search for our term then right click -> manage glossary terms
        // Due to the current bug 323953 you will not be able to use manage glossary. When using DM glossary node you will get a popup stating the uuid does not match the pattern
        // Once current bug is fixed there must be some reverse engineering in order to dertemine the correct UUID, Document_UUID, and Glossary_Node_UUID for our DM glossary node
        hierarchySearchPage().searchNodeUuid(glossaryNodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().manageGlossaryTerms();

        // Pull the text fields
        String primaryTermFromGlossaryTermPage = glossaryTermPage().getTextOfPrimaryTerm();
        String definitionFromGlossaryTermPage = glossaryTermPage().getTextOfDefinitionContent();
        String alternateTermFromGlossaryTermPage = glossaryTermPage().getTextOfSelectedAlternateTerm();

        Assertions.assertAll
        (
                () -> Assertions.assertEquals(primaryTerm, primaryTermFromGlossaryTermPage, "The primary term was expected to be: " + primaryTerm + " but was: " + primaryTermFromGlossaryTermPage),
                () -> Assertions.assertEquals(definition, definitionFromGlossaryTermPage, "The definition was expected to be: " + definition + " but was: " + definitionFromGlossaryTermPage),
                () -> Assertions.assertEquals(alternateTerms, alternateTermFromGlossaryTermPage, "The alternate term was expected to be: " + alternateTerms + " but was: " + alternateTermFromGlossaryTermPage)
        );

    }
    @AfterEach
    public void cleanup()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
