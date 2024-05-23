package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractPasteAsMultipleParagraphsTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class PasteAsMultipleParagraphsTests extends AbstractPasteAsMultipleParagraphsTests
{
    private static final String IOWA_UUID = "I7A9E71D09D1F11E6A1AD844B0F56AEAE";
    private static final String USCA_UUID = "I00590861E97011E283739FEC27DDDA55";
    private static final String UNIFORM_LAWS_ANNOTATED_UUID = "IFA54F0B22E9B11E9963EED6F277401D2";

    public static Object[][] uuids()
    {
        return new Object[][]
                {
                        {IOWA_UUID},
                        {USCA_UUID},
                        {UNIFORM_LAWS_ANNOTATED_UUID}
                };
    }

    /**
     * STORY/BUG - HALCYONST-9375/9378 <br>
     * SUMMARY - Copy of a single para pastes as multiple paras (Source) <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("uuids")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyOfASingleParaPastesAsMultipleParasSourceLegalTest(String uuid)
    {
        //Open source rendition with specified uuid in DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        sourceNavigateGridPage().firstRenditionEditContent();

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
