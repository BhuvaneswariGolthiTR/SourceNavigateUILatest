package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractPasteAsMultipleParagraphsTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.FCA_HANDBOOK;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.UNIFORM_LAWS_ANNOTATED;

public class PasteAsMultipleParagraphsTests extends AbstractPasteAsMultipleParagraphsTests
{
    private static final String IOWA_UUID = "I2A7119A014F111DA8AC5CD53670E6B4E";
    private static final String FCA_HANDBOOK_UUID = "I2133D0A2FC9511E7A9C880000BA47767";
    private static final String UNIFORM_LAWS_ANNOTATED_UUID = "I9D39AD2000E011DD93B594B1C5A4C9CA";

    public static Object[][] contentSetsAndUuids()
    {
        return new Object[][]
                {
                        {IOWA_DEVELOPMENT, IOWA_UUID},
                        {FCA_HANDBOOK, FCA_HANDBOOK_UUID},
                        {UNIFORM_LAWS_ANNOTATED, UNIFORM_LAWS_ANNOTATED_UUID}
                };
    }

    /**
     * STORY/BUG - HALCYONST-9375/9378 <br>
     * SUMMARY - Copy of a single para pastes as multiple paras (Target) <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("contentSetsAndUuids")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyOfASingleParaPastesAsMultipleParasTargetLegalTest(ContentSets contentSet, String uuid)
    {
        //Open target node with specified content set and uuid in DS editor
        hierarchyNavigatePage().goToHierarchyPage(contentSet.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //Paste single para as multiple paras
        int parasNumberBeforePasting = getParasNumber();
        pasteContent(SINGLE_PARA_THAT_SHOULD_BE_PASTED_AS_7_PARAS);
        //Assert that single para is pasted as multiple paras correctly
        assertThatParaIsPastedAsMultipleParas(parasNumberBeforePasting + 7);
        assertThatAllPastedContentIsAppearedInTheDocument(SINGLE_PARA_THAT_SHOULD_BE_PASTED_AS_7_PARAS);

        undoChanges();

        //Paste three lines of text with roman numerals
        pasteContent(THREE_LINES_WITH_ROMAN_NUMERALS_THAT_SHOULD_BE_PASTED_AS_3_PARAS);
        //Assert that three lines of text with roman numerals are pasted as multiple paras correctly
        assertThatParaIsPastedAsMultipleParas(parasNumberBeforePasting + 3);
        assertThatAllPastedContentIsAppearedInTheDocument(THREE_LINES_WITH_ROMAN_NUMERALS_THAT_SHOULD_BE_PASTED_AS_3_PARAS);
    }
}
