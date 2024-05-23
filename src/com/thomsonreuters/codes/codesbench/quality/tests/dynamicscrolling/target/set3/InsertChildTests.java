package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InsertChildTests extends TestService
{

	HierarchyDatapodObject datapodObject;

	/*Insert as child italic note wide centered
	 * 1. Open document
	 * 2. Scroll to chunk 2 or 3 (or maybe it will be best to scroll to the Text element)
	 * 3. Select and right click the parent element
	 * 4. Go to Insert Text (child) -> Italic Note -> Italic Note Wide Centered (wisc)
	 * VERIFY: Editorial Note element is inserted with an Italic Note child
	 * VERIFY: This is a direct child of the parent element selected before
	 * VERIFY: The Italic Note has a mnemonic of WISC
	 * VERIFY: The Italic Note has a pubtag of NOPUB
	 * VERIFY: The Italic Note has a source tag of the default source tag
	 * VERIFY: The Italic Note has a modified by tag
	 */
	
	/*Insert as child italic note subsection deferred
	 * 1. Open document
	 * 2. Scroll to chunk 2 or 3 (or maybe it will be best to scroll to the Text element)
	 * 3. Select and right click the parent element
	 * 4. Go to Insert Text (child) -> Italic Note -> Jurisdictional Italic Notes -> kitn.deferred.subsection.add
	 * VERIFY: Editorial Note element is inserted with an Italic Note child
	 * VERIFY: This is a direct child of the parent element selected before
	 * VERIFY: The Italic Note has a mnemonic of WISC
	 * VERIFY: The Italic Note has a pubtag of NOPUB
	 * VERIFY: The Italic Note has a source tag of the default source tag
	 * VERIFY: The Italic Note has text "Subsection effective ..."
	 */

	public static Object[][] insertChild()
	{
		return new Object[][]
		{
			{
				new String[] {//menu items
						EditorTextContextMenuElements.INSERT_TEXT_AS_CHILD_SUBMENU,
						EditorTextContextMenuElements.ITALIC_NOTE,
						EditorTextContextMenuElements.ITALIC_NOTE_WIDE_CENTERED
				},
					EditorTextPageElements.mnemonics.WISC,
				new String[][] {
					{"italic text",
					EditorTextPageElements.ITALIC_NOTE + EditorTextPageElements.PARATEXT_ITALIC, "true"}
				}
			},
			{
				new String[] {//menu items
						EditorTextContextMenuElements.INSERT_TEXT_AS_CHILD_SUBMENU,
						EditorTextContextMenuElements.ITALIC_NOTE,
						EditorTextContextMenuElements.JURISDICTIONAL_ITALIC_NOTES,
						EditorTextContextMenuElements.KITN_DEFERRED_SUBSECTION_ADD
				},
					EditorTextPageElements.mnemonics.WISC,
				new String[][] {//additional checks
					{"The Italic Note should have text \"Subsection effective ...\"",
							EditorTextPageElements.SUBSECTION + EditorTextPageElements.EDITORIAL_NOTE + EditorTextPageElements.ITALIC_NOTE +
						String.format(EditorTextPageElements.TEXT_PARAGRAPH_STARTING_WITH_TEXT_GIVEN, "Subsection effective"), "true"},
				}
				
			}		
		};
	}
	
	@ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
	@MethodSource("insertChild")
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	void insertChildTest(String[] menuItems, EditorTextPageElements.mnemonics mnemonic, String[][] textChecks)
	{
		datapodObject = TargetDataMockingNew.Iowa.Medium.insert();
 		String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
		String modifiedByTag = editorTextPage().getUserNameAndCurrentData(this.user());
 		String blockDescription = "Editorial Note";
		String headingDescription = "Italic Note";
		String blockXpath = EditorTextPageElements.EDITORIAL_NOTE;
		String headingXpath = EditorTextPageElements.ITALIC_NOTE;
		String section = EditorTextPageElements.SUBSECTION;

 		int targetChunkNumber = 1;
 		
 		// open DS editor
		homePage().goToHomePage();
		loginPage().logIn();
		hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().selectedSiblingMetadataEditContent();

		editorPage().switchToEditorAndScrollToChunk(targetChunkNumber);
		editorPage().scrollTo(EditorTextPageElements.SUBSECTION);
		
		// Right click and select
		editorPage().click(EditorTextPageElements.subsectionSpan);
		editorPage().rightClick(EditorTextPageElements.subsectionSpan);
		editorPage().breakOutOfFrame();
		editorPage().openContextSubMenu(menuItems);
		
		//checks in the tree
		boolean editorialNoteExistsInTree = editorPage().checkForEditorialNoteInTree();
		boolean paraExistsInTree = editorPage().checkForParaWiscInTree();
		
		editorPage().switchDirectlyToTextFrame();
		
		//checks in text
		boolean blockExists = editorPage().doesElementExist(section + blockXpath);
		boolean headingExists = editorPage().doesElementExist(section + blockXpath + headingXpath);
		boolean mnemonicExists = editorPage().doesElementExist(section + blockXpath + headingXpath + mnemonic.xpath());
		boolean modifiedExists = editorPage().checkForModifiedByTextWithXpathPrefix(section + blockXpath +	headingXpath , modifiedByTag);
		boolean nopubExists = editorPage().checkForSourceTagWithXpathPrefix(section + blockXpath + headingXpath);
		boolean sourcetagExists = editorPage().checkForNoPubWithXpathPrefix(section + blockXpath + headingXpath);
		
		//additional checks
		editorPage().checkAdditionalChecks(textChecks, "");
		
		editorPage().closeDocumentAndDiscardChanges();	
		
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(editorialNoteExistsInTree, EditorTreePageElements.treeElements.EDITORIAL_NOTE.getNodeName() + " should be inserted in the Tree"),
			() -> Assertions.assertTrue(paraExistsInTree, EditorTreePageElements.treeElements.PARA_WISC.getNodeName() + " should be inserted in the Tree"),
			() -> Assertions.assertTrue(blockExists, blockDescription + " should be inserted"),
			() -> Assertions.assertTrue(headingExists, headingDescription + " should be inserted"),
			() -> Assertions.assertTrue(mnemonicExists, headingDescription + " should have a mnemonic of " + mnemonic.name()),
			() -> Assertions.assertTrue(modifiedExists, headingDescription + " should have a modified by tag"),
			() -> Assertions.assertTrue(nopubExists, headingDescription + " should have a pub tag of NOPUB"),
			() -> Assertions.assertTrue(sourcetagExists, headingDescription + " should have a default source tag")
		);	
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
