package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class ForeignLanguageKeywordTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    private static String currentContentSetCode;

    /**
     * STORY/BUG - JETS-12423 <br>
     * SUMMARY - This test verifies that all German language keywords are shown in the update metadata dropdown option. <br>
     * This test also verifies that changing the keyword through the dropdown menu in the update metadata page can be done successfully <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void germanForeignLanguageKeywordGRCTest()
    {
        currentContentSetCode = ContentSets.GERMAN_LEGISLATIVE.getCode();
        HierarchyDatapodConfiguration.getConfig().loadDefaultGermanConfig();
        datapodObject = TargetDataMockingNew.GermanLegislative.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        List<String> germanLanguageKeywords = Arrays.asList("(*)", "=", "@", "ABSCHNITT", "ANHANG", "ANLAGE",
                "ARTIKEL", "BOV", "BUCH", "EOV", "FORMULAR", "GESETZ", "KAPITEL", "REGEL", "SET", "TEIL",
                "TITEL", "UNTERABSCHNITT", "VERORDNUNG");
        String contentSet = "German Legislative";
        String keyword = "ABSCHNITT";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        //Check that all of the German language keywords are displayed
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        List<String> keywordOptions = updateMetadataPage().getListOfKeywordOptions();
        boolean allGermanLanguageKeywordsAppear = germanLanguageKeywords.equals(keywordOptions);
        Assertions.assertTrue(allGermanLanguageKeywordsAppear,"All of the German language keywords did not appear when they should have");

        //Set the keyword to "ABSCHNITT"
        updateMetadataPage().setKeywordDropdown(keyword);
        updateMetadataPage().clickQuickLoadOk();

        //Check that the keyword was changed to "ABSCHNITT"
        String selectedNodeKeyword = siblingMetadataPage().getSelectedGridRowKeyword();
        boolean keywordChangedSuccessfully = keyword.equals(selectedNodeKeyword);
        Assertions.assertTrue(keywordChangedSuccessfully,"The node's keyword did not successfully change to " + keyword);
    }

    /**
     * STORY/BUG - JETS-12423 <br>
     * SUMMARY - This test verifies that all French language keywords are shown in the update metadata dropdown option <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void frenchForeignLanguageKeywordGRCTest()
    {
        currentContentSetCode = ContentSets.FRANCE_REGULATORY.getCode();
        List<String> frenchLanguageKeywords = Arrays.asList("(*)", "=", "@", "ANNEXE", "ARTICLE", "BOV", "CHAPITRE", "EOV",
                "FORMULAIRE", "LIVRE", "LOI", "NORM", "PARTIE", "R»GLE", "RËGLEMENT", "SECTION", "SET", "SOUS-PARTIE",
                "SOUS-SECTION", "SOUS-TITRE", "TITRE");
        String contentSet = "France Regulatory";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        //Check that all of the French language keywords are displayed
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        List<String> keywordOptions = updateMetadataPage().getListOfKeywordOptions();
        boolean allFrenchLanguageKeywordsAppear = frenchLanguageKeywords.equals(keywordOptions);
        updateMetadataPage().clickCancel();

        Assertions.assertTrue(allFrenchLanguageKeywordsAppear,"All of the French language keywords did not appear when they should have");
    }

    /**
     * STORY/BUG - JETS-12423 <br>
     * SUMMARY - This test verifies that all Dutch language keywords are shown in the update metadata dropdown option <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void dutchForeignLanguageKeywordGRCTest()
    {
        currentContentSetCode = ContentSets.NETHERLANDS_LEGISLATIVE.getCode();
        List<String> dutchLanguageKeywords = Arrays.asList("(*)", "=", "@", "AFDELING", "ARTIKEL", "BIJLAGE", "BOV", "DEEL",
                "EOV", "FORMULIER", "HOOFDSTUK", "ONDERDEEL", "PARAGRAAF", "POINT", "REGEL", "REGELGEVING", "SET", "SOUS-CHAPITRE", "TITEL", "WET");
        String contentSet = "Netherlands Legislative";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        //Check that all of the French language keywords are displayed
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        List<String> keywordOptions = updateMetadataPage().getListOfKeywordOptions();
        boolean allDutchLanguageKeywordsAppear = dutchLanguageKeywords.equals(keywordOptions);
        updateMetadataPage().clickCancel();

        Assertions.assertTrue(allDutchLanguageKeywordsAppear,"All of the Dutch language keywords did not appear when they should have");
    }

    /**
     * STORY/BUG - JETS-12423 <br>
     * SUMMARY - This test verifies that all Mexico language keywords are shown in the update metadata dropdown option <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void mexicoForeignLanguageKeywordGRCTest()
    {
        currentContentSetCode = ContentSets.MEXICO_REGULATORY.getCode();
        List<String> mexicoLanguageKeywords = Arrays.asList("(*)", "=", "@", "ACUERDO", "ANEXO", "ARTÕCULO", "AVISO",
                "BOV", "CAPÕTULO", "DECRETO", "DISPOCIONES", "EOV", "FRACCI”N", "LEY", "REGLA", "RESOLUCI”N",
                "SECCI”N", "SET", "TRANSITORIAS", "TRANSITORIOS", "TÕTULO");
        String contentSet = "Mexico Regulatory";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        //Check that all of the Spanish language keywords are displayed
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        List<String> keywordOptions = updateMetadataPage().getListOfKeywordOptions();
        boolean allMexicoLanguageKeywordsAppear = mexicoLanguageKeywords.equals(keywordOptions);
        updateMetadataPage().clickCancel();

        Assertions.assertTrue(allMexicoLanguageKeywordsAppear,"All of the Spanish language keywords did not appear when they should have");
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            if(currentContentSetCode.equals(ContentSets.GERMAN_LEGISLATIVE.getCode()))
            {
                // The datapod doesn't support foreign language keywords so after the test we put it back to SUBTITLE so that datapod can delete.
                String contentUUID = datapodObject.getSections().get(0).getContentUUID();
                Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
                HierarchyDatabaseUtils.setNodeKeywordToSubTitle(contentUUID, currentContentSetCode, connection);
                BaseDatabaseUtils.disconnect(connection);
            }
            datapodObject.delete();
        }
    }
}
