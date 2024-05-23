package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class InsertDeltaWithFeatureTests extends TestService
{
    public static Object[][] insertDeltaWithFeature()
    {
        String section = EditorTextPageElements.SOURCE_SECTION;
        return new Object[][]
                {
                        {
                                new String[] {//menu items
                                        EditorTextContextMenuElements.INSERT_DELTA_WITH_FEATURE,
                                        EditorTextContextMenuElements.EDITORIAL_NOTE,
                                        EditorTextContextMenuElements.REFERENCE_XMISR
                                },
                                new Object[] {//tree
                                        EditorTreePageElements.treeElements.ED_NOTE_REFERENCE
                                },
                                new Object[] {//text editor
                                        new String[] {"Reference Editorial Note", EditorTextPageElements.REFERENCE_EDITORIAL_NOTE},//block
                                        new String[] {"Miscellaneous Reference Heading", EditorTextPageElements.MISCELLANEOUS_REFERENCE_HEADING},//heading
                                        EditorTextPageElements.mnemonics.XMISR,
                                        new String[][] {//additional checks
                                                {"Editor Note Line should be inserted",
                                                        section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + EditorTextPageElements.REFERENCE_EDITORIAL_NOTE +
                                                                EditorTextPageElements.EDITOR_NOTE_LINE, "true"},
                                                {"Editor Note Paragraph should be inserted",
                                                        section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + EditorTextPageElements.REFERENCE_EDITORIAL_NOTE +
                                                                EditorTextPageElements.EDITOR_NOTE_PARAGRAPH, "true"},
                                        }
                                },
                                EditorPageElements.deltaFeature.ED_NOTE
                        }
                        ,
                        {
                                new String[] {//menu items
                                        EditorTextContextMenuElements.INSERT_DELTA_WITH_FEATURE,
                                        EditorTextContextMenuElements.OFFICIAL_NOTE,
                                        EditorTextContextMenuElements.OFFICIAL_NOTE_COMMENT
                                },
                                new Object[] {//tree
                                        EditorTreePageElements.treeElements.OFFICIAL_NOTE_BLOCK
                                },
                                new Object[] {//text editor
                                        new String[] {"Official Note Block", EditorTextPageElements.OFFICIAL_NOTE_BLOCK},//block
                                        new String[] {"Official Commentary Note Heading", EditorTextPageElements.OFFICIAL_COMMENTARY_NOTE_HEADING},//heading
                                        EditorTextPageElements.mnemonics.XCOM,
                                        new String[][] {//additional checks
                                                {"Official Note Body should be inserted",
                                                        section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + EditorTextPageElements.OFFICIAL_NOTE_BLOCK +
                                                                EditorTextPageElements.OFFICIAL_NOTE_BODY, "true"},
                                        }
                                },
                                EditorPageElements.deltaFeature.OFFICIAL_NOTE
                        },
                        {
                                new String[] {//menu items
                                        EditorTextContextMenuElements.INSERT_DELTA_WITH_FEATURE,
                                        EditorTextContextMenuElements.REFERENCE,
                                        EditorTextContextMenuElements.ADMINISTRATIVE_CODE_REFERENCE,
                                        EditorTextContextMenuElements.ADMINISTRATIVE_CODE_REFERENCE_XACR_CR1
                                },
                                new Object[] {//tree
                                        EditorTreePageElements.treeElements.ADMIN_CODE_REFERENCE_BLOCK
                                },
                                new Object[] {//text editor
                                        new String[] {"Administrative Code Reference Block", EditorTextPageElements.ADMINISTRATIVE_CODE_REFERENCE_BLOCK},//block
                                        new String[] {"Administrative Code Reference Heading", EditorTextPageElements.ADMINISTRATIVE_CODE_REFERENCE_HEADING},//heading
                                        EditorTextPageElements.mnemonics.XACR,
                                        new String[][] {//additional checks}
                                        }
                                },
                                EditorPageElements.deltaFeature.ADMIN_CODE_REFERENCE
                        },
                        {
                                new String[] {//menu items
                                        EditorTextContextMenuElements.INSERT_DELTA_WITH_FEATURE,
                                        EditorTextContextMenuElements.REFERENCE,
                                        EditorTextContextMenuElements.CROSS_REFERENCE,
                                        EditorTextContextMenuElements.CROSS_REFERENCE_CRL
                                },
                                new Object[] {//tree
                                        EditorTreePageElements.treeElements.CROSS_REFERENCE_BLOCK
                                },
                                new Object[] {//text editor
                                        new String[] {"Cross Reference Block", EditorTextPageElements.CROSS_REFERENCE_BLOCK},//block
                                        new String[] {"Cross Reference Heading", EditorTextPageElements.CROSS_REFERENCE_HEADING},//heading
                                        EditorTextPageElements.mnemonics.XCR,
                                        new String[][] {//additional checks
                                                {"Cross Reference Body",
                                                        section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + EditorTextPageElements.CROSS_REFERENCE_BLOCK +
                                                                EditorTextPageElements.CROSS_REFERENCE_BODY, "true"},
                                                {"Cross Reference line",
                                                        section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + EditorTextPageElements.CROSS_REFERENCE_BLOCK + EditorTextPageElements.CROSS_REFERENCE_BODY +
                                                                EditorTextPageElements.CROSS_REFERENCE_LINE, "true"},
                                        }
                                },
                                EditorPageElements.deltaFeature.CROSS_REFERENCE
                        },
                        {
                                new String[] {//menu items
                                        EditorTextContextMenuElements.INSERT_DELTA_WITH_FEATURE,
                                        EditorTextContextMenuElements.REFERENCE,
                                        EditorTextContextMenuElements.LIBRARY_REFERENCE,
                                        EditorTextContextMenuElements.LIBRARY_REFERENCE_CJSREFERENCE
                                },
                                new Object[] {//tree
                                        EditorTreePageElements.treeElements.LIBRARY_REFERENCE_BLOCK
                                },
                                new Object[] {//text editor
                                        new String[] {"Library Reference Block", EditorTextPageElements.LIBRARY_REFERENCE_BLOCK},//block
                                        new String[] {"Library Reference Heading", EditorTextPageElements.LIBRARY_REFERENCE_HEADING},//heading
                                        EditorTextPageElements.mnemonics.XLR,
                                        new String[][] {//additional checks
                                                {"Library Reference Body",
                                                        section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + EditorTextPageElements.LIBRARY_REFERENCE_BLOCK +
                                                                EditorTextPageElements.LIBRARY_REFERENCE_BODY, "true"},
                                        }
                                },
                                EditorPageElements.deltaFeature.LIBRARY_REFERENCE
                        },
                        {
                                new String[] {//menu items
                                        EditorTextContextMenuElements.INSERT_DELTA_WITH_FEATURE,
                                        EditorTextContextMenuElements.REFERENCE,
                                        EditorTextContextMenuElements.RESEARCH_REFERENCE,
                                        EditorTextContextMenuElements.RESEARCH_REFERENCE_ALR
                                },
                                new Object[] {//tree
                                        EditorTreePageElements.treeElements.RESEARCH_REFERENCE_BLOCK
                                },
                                new Object[] {//text editor
                                        new String[] {"Research Reference Block", EditorTextPageElements.RESEARCH_REFERENCE_BLOCK},//block
                                        new String[] {"Research Reference Heading", EditorTextPageElements.RESEARCH_REFERENCE_HEADING},//heading
                                        EditorTextPageElements.mnemonics.XAAR,
                                        new String[][] {//additional checks
                                                {"Research Reference Body",
                                                        section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + EditorTextPageElements.RESEARCH_REFERENCE_BLOCK +
                                                                EditorTextPageElements.RESEARCH_REFERENCE_BODY, "true"},
                                                {"Research Reference Subheading",
                                                        section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + EditorTextPageElements.RESEARCH_REFERENCE_BLOCK +
                                                                EditorTextPageElements.RESEARCH_REFERENCE_BODY + EditorTextPageElements.RESEARCH_REFERENCE_SUBHEADING, "true"},
                                                {"American Law Reports Reference",
                                                        section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + EditorTextPageElements.RESEARCH_REFERENCE_BLOCK +
                                                                EditorTextPageElements.RESEARCH_REFERENCE_BODY + EditorTextPageElements.AMERICAN_LAW_REPORTS_REFERENCE, "true"},
                                        }
                                },
                                EditorPageElements.deltaFeature.RESEARCH_REFERENCE
                        },
                        {
                                new String[] {//menu items
                                        EditorTextContextMenuElements.INSERT_DELTA_WITH_FEATURE,
                                        EditorTextContextMenuElements.REFERENCE,
                                        EditorTextContextMenuElements.SUPREME_COURT_REFERENCE,
                                        EditorTextContextMenuElements.SUPREME_COURT_REFERENCE_SUPCTGNP
                                },
                                new Object[] {//tree
                                        EditorTreePageElements.treeElements.US_SUP_CT_REFERENCE_BLOCK
                                },
                                new Object[] {//text editor
                                        new String[] {"US Supreme Court Reference Block", EditorTextPageElements.US_SUPREME_COURT_REFERENCE_BLOCK},//block
                                        new String[] {"Supreme Court Reference Heading", EditorTextPageElements.SUPREME_COURT_REFERENCE_HEADING},//heading
                                        EditorTextPageElements.mnemonics.XUSS,
                                        new String[][] {//additional checks
                                                {"Supreme Court Reference",
                                                        section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + EditorTextPageElements.US_SUPREME_COURT_REFERENCE_BLOCK +
                                                                EditorTextPageElements.SUPREME_COURT_REFERENCE, "true"},
                                        }
                                },
                                EditorPageElements.deltaFeature.US_SUP_CT_REFERENCE
                        },
                        {
                                new String[] {//menu items
                                        EditorTextContextMenuElements.INSERT_DELTA_WITH_FEATURE,
                                        EditorTextContextMenuElements.REFERENCE,
                                        EditorTextContextMenuElements.UNIFORM_LAW_REFERENCE,
                                        EditorTextContextMenuElements.UNIFORM_LAW_REFERENCE_XUL
                                },
                                new Object[] {//tree
                                        EditorTreePageElements.treeElements.UNIFORM_LAW_REFERENCE_BLOCK
                                },
                                new Object[] {//text editor
                                        new String[] {"Uniform Law Reference Block", EditorTextPageElements.UNIFORM_LAW_REFERENCE_BLOCK},//block
                                        new String[] {"Uniform Law Table Heading", EditorTextPageElements.UNIFORM_LAW_TABLE_HEADING},//heading
                                        EditorTextPageElements.mnemonics.XUL,
                                        new String[][] {//additional checks
                                        }
                                },
                                EditorPageElements.deltaFeature.UNIFORM_LAW_REFERENCE
                        }
                };
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("insertDeltaWithFeature")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertDeltaWithFeatureSourceTest(String[] menuItems, Object[] treeChecks, Object[] textChecks, EditorPageElements.deltaFeature deltaFeature)
    {
        String uuid = "I2B6D1EE043EA11E8B306BDCAB99DF270";
        int targetChunkNumber = 1;

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(targetChunkNumber);

        // mark Section
        editorTextPage().scrollToElement(EditorTextPageElements.SOURCE_SECTION);

        //add delta
        editorTextPage().click( EditorTextPageElements.SOURCE_SECTION_SPAN );
        editorTextPage().rightClick( EditorTextPageElements.SOURCE_SECTION_SPAN );
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(menuItems);

        //checks in the tree
        EditorTreePageElements.treeElements treeDeltaFeature = (EditorTreePageElements.treeElements)treeChecks[0];

        boolean deltaNoteExistsInTree = editorPage().doesElementExist(
                EditorTreePageElements.treeElements.DELTA.getXpath());

        boolean deltaFeaturesExistsInTree = editorPage().doesElementExist(deltaFeature.getXpath());
        editorPage().click(deltaFeature.getXpath());

        boolean deltaFeatureExistsInTree = editorPage().doesElementExist(treeDeltaFeature.getXpath());

        //checks in text
        String[] block = (String[]) textChecks[0];
        String blockDescription = block[0];
        String blockXpath = block[1];

        String[] heading = (String[]) textChecks[1];
        String headingDescription = heading[0];
        String headingXpath = heading[1];

        EditorTextPageElements.mnemonics mnemonic = (EditorTextPageElements.mnemonics)textChecks[2];

        editorTextPage().switchToEditorTextArea();
        String section = EditorTextPageElements.SOURCE_SECTION;

        boolean deltaNoteExists = editorTextPage().doesElementExist(section + EditorTextPageElements.DELTA_NOTE);

        boolean targetLocationExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.TARGET_LOCATION);

        boolean deltaFeatureExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE);

        boolean blockExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + blockXpath);

        boolean headingExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + blockXpath +
                        headingXpath);

        boolean mnemonicExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + blockXpath + headingXpath +
                        mnemonic.xpath());

        String modifiedByActual = editorTextPage().getElement(
                section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + blockXpath +	headingXpath +
                        EditorTextPageElements.MODIFIED_BY_MNEMONIC).getText();

        boolean headtextExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + blockXpath + headingXpath +
                        EditorTextPageElements.HEADTEXT);

        boolean nopubExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + blockXpath + headingXpath +
                        EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.DELTA_NOTE + EditorTextPageElements.DELTA_FEATURE + blockXpath + headingXpath +
                        EditorTextPageElements.SOURCE_TAG);

        editorPage().switchToEditor();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(deltaNoteExistsInTree, EditorTreePageElements.treeElements.DELTA + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(deltaFeaturesExistsInTree, deltaFeature.getFeatureName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(deltaFeatureExistsInTree, treeDeltaFeature.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(deltaNoteExists, "Delta Note should be inserted"),
                        () -> Assertions.assertTrue(targetLocationExists, "Target Location Add should be inserted"),
                        () -> Assertions.assertTrue(deltaFeatureExists, "Delta Feature Add should be inserted"),
                        () -> Assertions.assertTrue(blockExists, blockDescription + " should be inserted"),
                        () -> Assertions.assertTrue(headingExists, headingDescription + " should be inserted"),
                        () -> Assertions.assertTrue(mnemonicExists, headingDescription + " should have a mnemonic of " + mnemonic.name()),
                        () -> Assertions.assertEquals(modifiedByExpected, modifiedByActual, String.format("A modified by tag is '%s' instead of '%s'",modifiedByExpected,modifiedByActual)),
                        () -> Assertions.assertTrue(headtextExists, headingDescription + " should have content of heading text markup"),
                        () -> Assertions.assertFalse(nopubExists, headingDescription + " should not have a pub tag of NOPUB"),
                        () -> Assertions.assertFalse(sourcetagExists, headingDescription + " should not have a default source tag")
                );
    }
}
