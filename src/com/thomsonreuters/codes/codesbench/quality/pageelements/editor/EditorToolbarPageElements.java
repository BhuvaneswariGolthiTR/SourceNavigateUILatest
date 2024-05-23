package com.thomsonreuters.codes.codesbench.quality.pageelements.editor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditorToolbarPageElements

{
    private static final String TOOLBAR_ID_XPATH = "//div[@class='toolbar']";
	// Top Buttons
	@FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Save']")
	public static WebElement toolbarSaveButton;
	
	@FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Check Spelling']")
	public static WebElement toolbarCheckSpellingButton;

	@FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Close Doc']")
	public static WebElement toolbarCloseDocButton;

	public static final String TOOLBAR_CLOSE_DOC = TOOLBAR_ID_XPATH + "//a[@title='Close Doc']";
	
	@FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Validate']")
	public static WebElement toolbarValidateButton;
	
	@FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Undo']")
	public static WebElement toolbarUndoButton;
	
	@FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Redo']")
	public static WebElement toolbarRedoButton;
	
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Rebuild']")
    public static WebElement toolbarRebuildButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Remove Add/Delete']")
    public static WebElement toolbarRemoveAddDeleteButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Pubtag Refresh']")
    public static WebElement toolbarPubtagRefreshButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Subsection Generation']")
    public static WebElement toolbarSubsectionGenerationButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title = 'Subsection Validation']")
    public static WebElement toolbarSubsectionValidationButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title = 'Credit Generation']")
    public static WebElement toolbarCreditGenerationButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title = 'Footnote Reorder']")
    public static WebElement toolbarFootnoteReorderButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title = 'Insert Stat Pages']")
    public static WebElement toolbarInsertStatPagesButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Advanced SAR']")
    public static WebElement toolbarAdvancedSearchAndReplaceButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Cut']")
    public static WebElement toolbarCutButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title = 'Copy']")
    public static WebElement toolbarCopyButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Paste']")
    public static WebElement toolbarPasteButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title = 'Stocknote Manager']")
    public static WebElement toolbarStocknoteManagerButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Configure Editor Session Preferences']")
    public static WebElement toolbarConfigureEditorSessionPreferencesButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Insert Table']")
    public static WebElement toolbarInsertTableButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Insert Special Character']")
    public static WebElement toolbarInsertSpecialCharacterButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Insert Markup']")
    public static WebElement toolbarInsertMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Promote']")
    public static WebElement toolbarPromoteButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Demote']")
    public static WebElement toolbarDemoteButton;
    
    // toolbar lower line buttons
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Add']")
    public static WebElement toolbarAddMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Delete']")
    public static WebElement toolbarDeleteMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Bold']")
    public static WebElement toolbarBoldMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Italic']")
    public static WebElement toolbarItalicMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Cap conditional']")
    public static WebElement toolbarCapConditionalMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Small caps']")
    public static WebElement toolbarSmallCapsMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Paragraph id include']")
    public static WebElement toolbarParagraphIdIncludedMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Paragraph id ignore']")
    public static WebElement toolbarParagraphIdIgnoreMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Words & Phrases']")
    public static WebElement toolbarWordsAndPhrasesMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Character fill']")
    public static WebElement toolbarCharacterFillMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Character generate']")
    public static WebElement toolbarCharacterGenerateMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Print suppress']")
    public static WebElement toolbarPrintSuppressMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Westlaw suppress']")
    public static WebElement toolbarWestlawSupressMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Vendor include']")
    public static WebElement toolbarVendorIncludeMarkupButton;
   
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Case history id']")
    public static WebElement toolbarCaseHistoryIdMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='End left']")
    public static WebElement toolbarEndLeftMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='End center']")
    public static WebElement toolbarEndCenterMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='End right']")
    public static WebElement toolbarEndRightMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='End justify']")
    public static WebElement toolbarEndJustifyMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Westlaw Table Code']")
    public static WebElement toolbarWestlawTableCodeMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Cite reference']")
    public static WebElement toolbarCiteReferenceMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title='Confirm Link Markup']")
    public static WebElement toolbarConfirmLinkMarkupButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title = 'Print']")
    public static WebElement toolbarPrintButton;
    
    @FindBy(how = How.XPATH, using = TOOLBAR_ID_XPATH + "//a[@title = 'Help']")
    public static WebElement toolbarHelpButton;

    public static final String BUTTON_WITH_TEXT = TOOLBAR_ID_XPATH + "//a[@title='%s']";
    public static final String CLOSE_DOC = TOOLBAR_ID_XPATH + "//a[@title='Close Doc']";
    public static final String SUBSECTION_GENERATION = "//a[@title='Subsection Generation']";

    public static final String UNDO = TOOLBAR_ID_XPATH + "//a[@title='Undo']";
    public static final String REDO = TOOLBAR_ID_XPATH + "//a[@title='Redo']";
}