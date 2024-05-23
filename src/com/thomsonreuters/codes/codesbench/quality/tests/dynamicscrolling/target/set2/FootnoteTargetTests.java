package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractFootnoteTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class FootnoteTargetTests extends AbstractFootnoteTests
{
	private static final String NODE_UUID = "I2A903D9014F511DA8AC5CD53670E6B4E";

	@BeforeEach
	public void openTargetNodeInDsEditor()
	{
		hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
		hierarchySearchPage().searchNodeUuid(NODE_UUID);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
	}

	/*
	 * Insert multiple footnotes and renumber
	 * 1. open document
	 * 2. scroll to chunk 2 or 3
	 * 3. Insert text
	 * 4. Insert a footnote (alt + f)
	 * 5. Set the footnote reference type to FP
	 * 6. Set the footnote type to footnote
	 * 7. Set the footnote reference to 1
	 * 8. Click Save
	 * 9. VERIFY: We are brought to the selected, inserted footnote and the footnote appears under the Credit Line
	 * 10. VERIFY: The 'footnote: end text' markup contains the reference set
	 * 11. VERIFY: The footnote contains a hint for the user to 'Insert footnote text here'
	 * 12. Scroll back to the place you inserted the footnote
	 * 13. VERIFY: 'footnote: end text' markup exists where you inserted the footnote and contains the reference set
	 * 14. Follow steps 3 - 13 and set the reference to 3
	 * 15. Follow steps 3 - 13 and set the reference to 2
	 * 16. Click the Footnotes Renumber button in the toolbar
	 * 17. VERIFY: The footnotes after the credit are renumbered in order 1, 2, 3
	 * 18. VERIFY: The footnotes markup in the paragraph are renumbered in order 1, 2, 3
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void insertMultipleFootnotesAndReorderTargetLegalTest()
	{
		// insert first footnote with ref 2
		insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_2, true, true);
		// assert that first footnote is inserted successfully
		assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_2, 1);

		// insert second footnote with ref 3
		insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_3, true, true);
		// assert that second footnote is inserted successfully
		assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_3, 1);

		// insert third footnote with ref 1
		insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, true, true);
		// assert that third footnote is inserted successfully
		assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_1, 1);

		// hit FN reorder
		editorToolbarPage().clickToolbarButton(EditorToolbarPageElements.toolbarFootnoteReorderButton);

		// check reordering of footnote references
		editorPage().switchToEditorTextFrame();

		// assert that footnote reference with value 1 is on the first place after reordering
		assertThatFootnoteReferenceWithSpecificValueIsOnSpecificPlace(FOOTNOTE_REFERENCE_VALUE_1, 1);

		// assert that footnote reference with value 2 is on the second place after reordering
		assertThatFootnoteReferenceWithSpecificValueIsOnSpecificPlace(FOOTNOTE_REFERENCE_VALUE_2, 2);

		// assert that footnote reference with value 3 is on the third place after reordering
		assertThatFootnoteReferenceWithSpecificValueIsOnSpecificPlace(FOOTNOTE_REFERENCE_VALUE_3, 3);

		// assert that footnote with value 1 is on the first place after reordering
		assertThatFootnoteWithSpecificValueIsOnSpecificPlace(FOOTNOTE_REFERENCE_VALUE_1, 1);

		// assert that footnote with value 2 is on the second place after reordering
		assertThatFootnoteWithSpecificValueIsOnSpecificPlace(FOOTNOTE_REFERENCE_VALUE_2, 2);

		// assert that footnote with value 3 is on the third place after reordering
		assertThatFootnoteWithSpecificValueIsOnSpecificPlace(FOOTNOTE_REFERENCE_VALUE_3, 3);

	}

//  ------------- Assistive methods: -------------

	private void assertThatFootnoteReferenceWithSpecificValueIsOnSpecificPlace(String footnoteReferenceValue, int place)
	{
		assertThat(editorTextPage().checkFootnoteReferenceTextUnderNumber(place))
				.as(format("Footnote reference with value %s should be on the %d place after reordering",
						footnoteReferenceValue, place))
				.isEqualTo(footnoteReferenceValue);
	}

	private void assertThatFootnoteWithSpecificValueIsOnSpecificPlace(String footnoteValue, int place)
	{
		assertThat(editorTextPage().getFootnoteTextUnderNumber(place))
				.as(format("Footnote with value %s should be on the %d place after reordering", footnoteValue, place))
				.isEqualTo(footnoteValue);
	}

}
