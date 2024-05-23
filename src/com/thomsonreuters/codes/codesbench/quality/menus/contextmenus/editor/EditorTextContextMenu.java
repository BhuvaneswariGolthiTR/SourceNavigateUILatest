package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.editor;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class EditorTextContextMenu extends ContextMenu
{
	private WebDriver driver;

	@Autowired
	public EditorTextContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init() {
		PageFactory.initElements(driver, EditorTextContextMenuElements.class);
	}

	public void insertQuery()
	{
		EditorTextContextMenuElements.insertQuery.click();
		switchToWindow(CreateQueryNotePageElements.QUERY_NOTE_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void insertTextChildCornerpieceCfr()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_CHILD,
				EditorTextContextMenuElements.CORNERPIECE,
				EditorTextContextMenuElements.CORNERPIECE_CFR);
	}

	public void insertTextSiblingGradeContentSection()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.GRADE_CONTENT_SECTION);
	}

	public void insertTextSiblingCornerpieceCfr()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.CORNERPIECE,
				EditorTextContextMenuElements.CORNERPIECE_CFR);
	}

	public void insertTextSiblingCornerpieceRenumbered()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.CORNERPIECE,
				EditorTextContextMenuElements.CPL_CP_CPR1_CPO_CPA_RENUMBERED);
	}

	public void insertTextSiblingCornerpieceRepeal()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.CORNERPIECE,
				EditorTextContextMenuElements.CPL_CP_CPR1_CPO_REPEAL);
	}

	public void insertTextSiblingCornerpieceCpl1()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.CORNERPIECE,
				EditorTextContextMenuElements.CPL1);
	}

	public void insertTextSiblingPageForcerTOPT()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.PAGE_FORCER,
				EditorTextContextMenuElements.PAGE_FORCER_TOPT);
	}

	public void insertTextSiblingPageForcerTODD()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.PAGE_FORCER,
				EditorTextContextMenuElements.PAGE_FORCER_TODD);
	}

	public void insertTextSiblingPageForcerTOP()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.PAGE_FORCER,
				EditorTextContextMenuElements.PAGE_FORCER_TOP);
	}

	public void insertTextSiblingPageForcerPRMS()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.PAGE_FORCER,
				EditorTextContextMenuElements.PAGE_FORCER_PRMS);
	}

	public void insertTextSiblingMiscSCP3()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.MISCELLANEOUS,
				EditorTextContextMenuElements.SOURCE_SECTION_NUMBER);
	}

	public void insertDocumentSetupCopyOfGradeChapter()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.DOCUMENT_SETUP,
				EditorTextContextMenuElements.COPY_OF_GRADE_CHAPTER);
	}

	public void insertImage() {
		openContextMenu(EditorTextContextMenuElements.INSERT_IMAGE);
	}

	public void openLink() {
		openContextMenu(EditorTextContextMenuElements.OPEN_LINK);
	}

	public void insertSubsectionWrapper(WebElement highlightedPara)
	{
		editorTextPage().rightClick(highlightedPara);
		editorTextPage().breakOutOfFrame();
		editorTextPage().openContextSubMenu(EditorTextContextMenuElements.INSERT_WRAPPER, EditorTextContextMenuElements.SUBSECTION);
		editorPage().switchToEditorTextFrame();
	}

	public boolean preview(String labelXpath)
	{
		waitForElement(labelXpath);
		scrollToView(labelXpath);
		rightClick(labelXpath);
		breakOutOfFrame();
		click(EditorTextContextMenuElements.PREVIEW);
		boolean previewAppeared = switchToWindow(DocumentPreviewPageElements.PAGE_TITLE);
		waitForPageLoaded();
		return previewAppeared;
	}

	public void markupInsertTargetLinkMarkup()
	{
		openContextMenu(EditorTextContextMenuElements.MARKUP, EditorTextContextMenuElements.INSERT_TARGET_LINK_MARKUP);
		switchToWindow(InsertTargetCiteReferencePageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void markupApplyLinkMarkup()
	{
		openContextMenu(EditorTextContextMenuElements.MARKUP,
				EditorTextContextMenuElements.APPLY_LINK_MARKUP);
		switchToWindow(ApplyLinkMarkupPageElements.PAGE_TITLE);
	}

	public void insertChildTableTemplate(String parentLabel)
	{
		insertTableTemplate(parentLabel, EditorTextContextMenuElements.INSERT_TABULAR_CHILD);
	}

	public void insertSiblingTableTemplate(String parentLabel)
	{
		insertTableTemplate(parentLabel, EditorTextContextMenuElements.INSERT_TABULAR_SIBLING);
	}

	private void insertTableTemplate(String parentLabel, String submenu)
	{
		click(parentLabel);
		rightClick(parentLabel);
		breakOutOfFrame();
		openContextSubMenu(submenu,
				EditorTextContextMenuElements.INSERT_TABLE_TEMPLATE);

		editorTextPage().createTable("3");
		acceptAlertNoFail();
	}

	public void delete()
	{
		waitForElement(EditorTextContextMenuElements.delete);
		click(EditorTextContextMenuElements.delete);
	}

	public void deleteQueryNote()
	{
		click(EditorTextContextMenuElements.deleteQuery);
	}

	public void insertTextChildTextParagraphSmp()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_CHILD,
				EditorTextContextMenuElements.TEXT_PARAGRAPH,
				EditorTextContextMenuElements.TEXT_PARAGRAPH_SMP);
		waitForPageLoaded();
	}

	public void insertTextChildCornerpieceCornerpiece()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_CHILD,
				EditorTextContextMenuElements.CORNERPIECE,
				EditorTextContextMenuElements.SUB_CORNERPIECE);
		waitForPageLoaded();
	}

	public void insertTextSiblingLocalRulesNamelineRnl()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.LOCAL_RULES,
				EditorTextContextMenuElements.NAMELINE,
				EditorTextContextMenuElements.RNL);
		waitForPageLoaded();
	}

    public void insertTabularSiblingTableTemplate()
    {
    	openContextMenu(EditorTextContextMenuElements.INSERT_TABULAR_SIBLING, EditorTextContextMenuElements.TABLE_TEMPLATE);
    	switchToWindow(InsertTablePageElements.PAGE_TITLE);
    	enterTheInnerFrame();
    }

	public void insertTabularSiblingInsertStart()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TABULAR_SIBLING,
				EditorTextContextMenuElements.INSERT_START);
	}

	public void insertTabularSiblingInsertStop()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TABULAR_SIBLING,
				EditorTextContextMenuElements.INSERT_STOP);
	}

	public void convertToTable()
	{
		openContextMenu(EditorTextContextMenuElements.CONVERT_TO_TABLE);
	}

	public void cutCtrlX()
	{
		openContextMenu(EditorTextContextMenuElements.CUT_CTRL_X);
	}

	public void copyCtrlC()
	{
		openContextMenu(EditorTextContextMenuElements.COPY_CTRL_C);
	}

	public void copy()
	{
		openContextMenu(EditorTextContextMenuElements.COPY);
	}

	public void pasteSiblingCtrlV()
	{
		openContextMenu(EditorTextContextMenuElements.PASTE_SIBLING_CTRL_V);
	}

	public void pasteCtrlV()
	{
		openContextMenu(EditorTextContextMenuElements.PASTE_CTRL_V);
	}

	public void insertWrapperSubsection()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_WRAPPER, EditorTextContextMenuElements.SUBSECTION);
	}

	public void taxTypeAdd()
	{
		openContextMenu(EditorTextContextMenuElements.TAX_TYPE_ADD);
	}

	public void effectiveDate()
	{
		openContextMenu(EditorTextContextMenuElements.EFFECTIVE_DATE);
	}

	public void creditPhrase()
	{
		openContextMenu(EditorTextContextMenuElements.CREDIT_PHRASE);
	}

	public void insertWrapperAnnotations()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_WRAPPER, EditorTextContextMenuElements.ANNOTATIONS);
	}

	public void insertWrapperTextBody()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_WRAPPER, EditorTextContextMenuElements.TEXT_BODY);
	}

   public void markupDeleteMarkup()
   {
	openContextMenu(EditorTextContextMenuElements.MARKUP,
			EditorTextContextMenuElements.DELETE_MARKUP);
   }

   public void markupEditAttributes()
   {
	openContextMenu(EditorTextContextMenuElements.MARKUP,
			EditorTextContextMenuElements.EDIT_ATTRIBUTES);
	switchToWindow(EditAttributesPageElements.PAGE_TITLE);
	enterTheInnerFrame();
   }

    public void insertNewParagraphAltI()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_NEW_PARAGRAPH_ALT_I);
	}

	public void markupInsertMarkup()
	{
		openContextMenu(EditorTextContextMenuElements.MARKUP,
				EditorTextContextMenuElements.INSERT_MARKUP);
		switchToWindow(InsertWestMarkupPageElements.WINDOW_TITLE);
		enterTheInnerFrame();
	}

	public void amend()
	{
		openContextMenu(EditorTextContextMenuElements.AMEND);
	}

	public void pasteChildCtrlAltV()
	{
		openContextMenu(EditorTextContextMenuElements.PASTE_CHILD_CTRL_ALT_V);
	}

    public boolean insertDelta()
    {
		openContextMenu(EditorTextContextMenuElements.INSERT_DELTA);
		boolean windowAppeared = editorPage().switchToWindow(InsertDeltaPageElements.PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
    }

	public void editAttributes()
	{
		openContextMenu(EditorTextContextMenuElements.EDIT_ATTRIBUTES);
		switchToWindow(EditAttributesPageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}

    public boolean insertTextSiblingHeadingNamelineAdditionalNamelinesPopup()
    {
		openContextMenu(
				EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.HEADING_NAMELINE,
				EditorTextContextMenuElements.ADDITIONAL_NAMELINES_POPUP
		);

		boolean windowAppeared = switchToWindow(ContentEditorialSystemPageElements.PAGE_TITLE);
		waitForPageLoaded();
		enterTheInnerFrame();
		return windowAppeared;
    }

	public void insertTextSiblingTextHeadingJurisdictionalTextHeadingNamelineSrnl()
	{
		openContextMenu(
				EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.TEXT_HEADING,
				EditorTextContextMenuElements.JURISDICTIONAL_TEXT_HEADINGS,
				EditorTextContextMenuElements.HEADING__NAMELINE,
				EditorTextContextMenuElements.SRNL
		);
	}

	public void insertTextSiblingTextHeadingJurisdictionalTextHeadingNamelineRrnl()
	{
		openContextMenu(
				EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.TEXT_HEADING,
				EditorTextContextMenuElements.JURISDICTIONAL_TEXT_HEADINGS,
				EditorTextContextMenuElements.HEADING__NAMELINE,
				EditorTextContextMenuElements.RRNL
		);
	}

	public void modifySplitParagraph()
	{
		openContextMenu(EditorTextContextMenuElements.modify, EditorTextContextMenuElements.splitParagraph);
	}

	public void joinParagraph()
	{
		click(EditorTextContextMenuElements.joinParagraph);
	}

	public void insertTextChildIntroductoryParagraph()
	{
		openContextMenu(EditorTextContextMenuElements.insertTextChild, EditorTextContextMenuElements.introductoryParagraph);
	}

	public void importTargetText()
	{
		click("//li[@id='importTargetText']/a");
	}

	public void insertDeltaWithDerivationFeature()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_DELTA_WITH_FEATURE,
				EditorTextContextMenuElements.HISTORICAL_NOTE,
				EditorTextContextMenuElements.DERIVATION);
	}

	public void insertAmendmentPopup()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_FEATURE,
				EditorTextContextMenuElements.HISTORICAL_NOTE,
				EditorTextContextMenuElements.HISTORICAL_NOTE_AMENDMENT_POPUP);
	}

	public void insertItalicNoteWideCentered()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_ITALIC_NOTE,
				EditorTextContextMenuElements.DEFERRED_ITALIC_NOTE_SETUP,
				EditorTextContextMenuElements.SUBSECTION,
				EditorTextContextMenuElements.WITH_ITALIC_NOTES_WIDE_CENTERED);
	}

	public void insertItalicNoteMultipleFullSection()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_ITALIC_NOTE,
				EditorTextContextMenuElements.DEFERRED_ITALIC_NOTE_SETUP,
				EditorTextContextMenuElements.MULTIPLE_FULL_SECTION);
	}

	public void insertImportTargetTextItalicText()
	{
		click(EditorTextContextMenuElements.INSERT_IMPORT_TARGET_TEXT);
	}

	public void pasteAsTextParagraphSmp()
	{
		openContextMenu(EditorTextContextMenuElements.PASTE_AS,
				EditorTextContextMenuElements.TEXT_SIBLING,
				EditorTextContextMenuElements.TEXT_PARAGRAPH,
				EditorTextContextMenuElements.TEXT_PARAGRAPH_SMP);
	}

	public void insertRecodifiedSetup()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_CHILD,
				EditorTextContextMenuElements.DOCUMENT_SETUP,
				EditorTextContextMenuElements.RECODIFIED_SETUP);
	}

	public void pubTagPlus()
	{
		openContextMenu(EditorTextContextMenuElements.pubTagPlus);
	}

	public void pubTagMinus()
	{
		openContextMenu(EditorTextContextMenuElements.pubTagMinus);
	}

	public void pubTagRemove()
	{
		openContextMenu(EditorTextContextMenuElements.pubTagRemove);
	}

	public void pasteAsFeatureTextEditorialNote()
	{
		openContextMenu(EditorTextContextMenuElements.PASTE_AS,
				EditorTextContextMenuElements.FEATURE,
				EditorTextContextMenuElements.EDITORIAL_NOTE,
				EditorTextContextMenuElements.TEXT_EDITORIAL_NOTE);
	}

	public void insertSourceTag()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_SOURCE_TAG);
	}

	public void toggleExcludeInclude()
	{
		openContextMenu(EditorTextContextMenuElements.TOGGLE_EXCLUDE_INCLUDE);
	}

	public void findFootnotesReferences()
	{
		openContextMenu(EditorTextContextMenuElements.FIND_FOOTNOTES_REFERENCES);
	}

	public void insertTextAsPartNamelineSnl()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_AS_PART,
				EditorTextContextMenuElements.NAMELINE,
				EditorTextContextMenuElements.SNL);
	}

	public void insertHeadingNamelineJurisdictionalNamelinesSnl()
	{
		openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
				EditorTextContextMenuElements.HEADING_NAMELINE,
				EditorTextContextMenuElements.JURISDICTIONAL_NAMELINE,
				EditorTextContextMenuElements.SNL);
	}

	public void importTargetTextTXRepeals()
	{
		openContextMenu(EditorTextContextMenuElements.importTargetTextTXRepeals);
	}

	public List<String> getAllAvailableContextMenuOptions()
	{
		return getElementsTextList(EditorTextContextMenuElements.CONTEXT_MENU + "//li/a");
	}

	public void insertGivenInstructionTemplateNote(String instructionToInsert)
	{
		openContextMenu(instructionToInsert, EditorTextContextMenuElements.TEMPLATE_NOTE);
	}
}

