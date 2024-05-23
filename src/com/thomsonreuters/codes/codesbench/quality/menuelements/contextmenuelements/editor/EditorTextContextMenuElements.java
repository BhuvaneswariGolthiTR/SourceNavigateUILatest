package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditorTextContextMenuElements
{
	public static final String CONTEXT_MENU = "//div[@id='yuiContextMenu']";
	public static final String INSERT_TEXT_SIBLING = CONTEXT_MENU + "//a[text()='Insert Text (sibling)']";
	public static final String CORNERPIECE = CONTEXT_MENU + "//a[text()='Cornerpiece']";
	public static final String LOCAL_RULES = CONTEXT_MENU + "//a[text()='Local Rules']";
	public static final String SUB_CORNERPIECE = CONTEXT_MENU + "//a[text()='cornerpiece']";
	public static final String TEXT_SIBLING = CONTEXT_MENU + "//a[text()='Text (sibling)']";
	public static final String TEXT_PARAGRAPH = CONTEXT_MENU + "//a[text()='Text Paragraph']";
	public static final String TEXT_PARAGRAPH_SMP = CONTEXT_MENU + "//a[text()='Text Paragraph (smp)']";
	public static final String FEATURE = CONTEXT_MENU + "//a[text()='Feature']";
	public static final String NAMELINE = CONTEXT_MENU + "//a[text()='Nameline']";
	public static final String RNL = CONTEXT_MENU + "//a[text()='RNL']";
	public static final String CORNERPIECE_CFR = CONTEXT_MENU + "//a[text()='cornerpiece cfr']";
	public static final String CPL1 = CONTEXT_MENU + "//a[text()='cpl1']";
	public static final String CPL_CP_CPR1_CPO_CPA_RENUMBERED = CONTEXT_MENU + "//a[text()='CPL-CP-CPR1-CPO-CPA Renumbered']";
	public static final String CPL_CP_CPR1_CPO_REPEAL = CONTEXT_MENU + "//a[text()='CPL-CP-CPR1-CPO REPEAL']";
	public static final String INSERT_TEXT_CHILD = CONTEXT_MENU + "//a[text()='Insert Text (child)']";
	public static final String DOCUMENT_SETUP = CONTEXT_MENU + "//a[text()='Document Setup']";
	public static final String RECODIFIED_SETUP = CONTEXT_MENU + "//a[text()='Recodified - setup']";
	public static final String COPY_OF_GRADE_CHAPTER = CONTEXT_MENU + "//a[text()='Copy of grade - Chapter']";
	public static final String INSERT_IMAGE = CONTEXT_MENU + "//li/a[text()='Insert Image']";
	public static final String OPEN_LINK = CONTEXT_MENU + "//li/a[text()='Open Link']";
	public static final String MISCELLANEOUS = CONTEXT_MENU + "//a[text()='Miscellaneous']";
	public static final String ADDITIOANL_MISC_PARAGRAPHS_POPUP = "//a[text()='Additional Misc Paragraphs Popup']";
	public static final String ITALIC_NOTE_WISC = CONTEXT_MENU + "//a[text()='Italic Note (wisc)']";
	public static final String INSERT_ITALIC_NOTE = CONTEXT_MENU + "//a[text()='Insert Italic Note']";
	public static final String INSERT_IMPORT_TARGET_TEXT = CONTEXT_MENU+"//a[text()='Import Target Text - Italic Text']";
	public static final String DEFERRED_ITALIC_NOTE_SETUP = CONTEXT_MENU + "//a[text()='Deferred Italic Note Setup']";
	public static final String MULTIPLE_FULL_SECTION = CONTEXT_MENU + "//a[text()='Multiple Full Section']";
	public static final String WITH_ITALIC_NOTES_WIDE_CENTERED = CONTEXT_MENU + "//a[text()='With Italic Notes Wide Centered (wisc)']";
	public static final String NOTE_PARAGRAPH_GNP = CONTEXT_MENU + "//a[text()='Note Paragraph (gnp)']";
	public static final String PREVIEW = "//div[@id='yuiContextMenu' or @id='treeContextMenu']//li[@id='preview']";
	public static final String MARKUP = CONTEXT_MENU + "//a[text()='Markup']";
	public static final String INSERT_TARGET_LINK_MARKUP = CONTEXT_MENU + "//li[@id='insertLinkMarkup']/a";
	public static final String INSERT_GLOSSARY_LINK_MARKUP = CONTEXT_MENU + "//a[contains(text(),'Insert Glossary Link')]";
	public static final String APPLY_LINK_MARKUP = CONTEXT_MENU + "//li[@id='applyLinkMarkup']/a";
	public static final String DELETE_MARKUP = CONTEXT_MENU + "//li[@id='deleteMarkup']/a";
	public static final String EDIT_ATTRIBUTES = CONTEXT_MENU + "//li[@id='editAttributes']/a";
	public static final String PAGE_FORCER = CONTEXT_MENU +  "//a[text()='Page Forcer']";
	public static final String PAGE_FORCER_TOPT = CONTEXT_MENU + "//a[text()='Page Forcer In Text Next Page With Running Heads (topt)']";
	public static final String PAGE_FORCER_TODD = CONTEXT_MENU + "//a[text()='Page Forcer Next Odd Page Without Running Heads (todd)']";
	public static final String PAGE_FORCER_TOP = CONTEXT_MENU + "//a[text()='Page Forcer Next Page With Running Heads (top)']";
	public static final String PAGE_FORCER_PRMS = CONTEXT_MENU + "//a[text()='Page Forcer Next Page Without Running Heads (prms)']";
	public static final String SIGNATURE_BLOCK_SL = "//a[text()='Signature Block (sl)']";
	public static final String SOURCE_SECTION_NUMBER = "//a[text()='Source Section Number (scp3)']";
	public static final String TOGGLE_EXCLUDE_INCLUDE = CONTEXT_MENU + "//a[text()='Toggle Exclude / Include']";
	public static final String FIND_FOOTNOTES_REFERENCES = CONTEXT_MENU + "//a[text()='Find Footnotes/References']";
	public static final String GRADE_CONTENT_SECTION = CONTEXT_MENU + "//a[text()='Grade Content Section']";

	public static final String TEXT_HEADING = "//a[text()='Text Heading']";
	public static final String JURISDICTIONAL_TEXT_HEADINGS = "//a[text()='Jurisdictional Text Headings']";
	public static final String HEADING__NAMELINE = "//a[text()='Heading - Nameline']";
	public static final String SRNL = "//a[text()='srnl']";
	public static final String TEXT_HEADING_NARROW_CENTERED_CLP = "//a[text()='Text Heading Narrow Centered (clp)']";

	public static final String INSERT_SECTION_INSTRUCTION = CONTEXT_MENU + "//a[text()='Insert Section Instruction']";
	public static final String TEMPLATE_NOTE = CONTEXT_MENU + "//a[text()='Template Note']";

	public static final String DELETE = "//DIV[@id='yuiContextMenu']//li[@id='delete']/a";
	public static final String EDIT_CREDIT = "//a[text()='Edit Credit']";
	public static final String DELETE_CREDIT = "//a[text()='Delete Credit']";

	// First Level Child
	public static final String INSERT_TABULAR_CHILD = CONTEXT_MENU + "//a[@href='#tabularChildSubMenu']";
	public static final String INSERT_TEXT_AS_CHILD_SUBMENU =CONTEXT_MENU + "//a[text()='Insert Text (child)']";

	// First Level Sibling
	public static final String INSERT_TEXT_AS_SIBLING_SUBMENU = CONTEXT_MENU + "//a[@href='#insertTextAsSiblingSubMenu']";
	public static final String INSERT_TABULAR_SIBLING = CONTEXT_MENU + "//li/a[text()='Insert Tabular (sibling)']";
	public static final String TABLE_TEMPLATE = CONTEXT_MENU + "//li/a[text()='Table Template']";
	//First Level
	public static final String TAX_TYPE_ADD = CONTEXT_MENU + "//li/a[text()='Tax Type Add']";
	// First Level Other
	public static final String INSERT_WRAPPER = CONTEXT_MENU + "//li/a[text()='Insert Wrapper']";
	//second level Sibling
	public static final String RUNNING_HEAD_SIBLING_SUBMENU = CONTEXT_MENU + "//li/a[@href='#runningHeadWithBlockSiblingSubMenu']";
	// second level Insert Wrapper
	public static final String SUBSECTION = CONTEXT_MENU + "//li/a[text()='Subsection']";
	public static final String ANNOTATIONS = CONTEXT_MENU + "//li/a[text()='Annotations']";
	public static final String TEXT_BODY = CONTEXT_MENU + "//li/a[text()='Text (body)']";
	// second level Tabular sibling
	public static final String INSERT_TABLE_TEMPLATE = CONTEXT_MENU + "//li[@id='insertTableAsSibling' or @id='insertTableAsChild']/a";
	public static final String INSERT_START = CONTEXT_MENU + "//li/a[text()='Insert Start']";
	public static final String INSERT_STOP = CONTEXT_MENU + "//li/a[text()='Insert Stop']";
	public static final String CONVERT_TO_TABLE = CONTEXT_MENU + "//li/a[text()='Convert to Table']";
	public static final String CUT_CTRL_X = CONTEXT_MENU + "//li/a[text()='Cut [Ctrl + X]']";
	public static final String COPY_CTRL_C = CONTEXT_MENU + "//li/a[text()='Copy [Ctrl + C]']";
	public static final String COPY = CONTEXT_MENU + "//li/a[text()='Copy']";
	public static final String PASTE_SIBLING_CTRL_V = CONTEXT_MENU + "//li/a[text()='Paste (sibling) [Ctrl + V]']";
	public static final String PASTE_CTRL_V = CONTEXT_MENU + "//li/a[text()='Paste [Ctrl + V]']";
    public static final String INSERT_NEW_PARAGRAPH_ALT_I = CONTEXT_MENU + "//li/a[text()='Insert New Paragraph [Alt + I]']";
	public static final String PASTE_CHILD_CTRL_ALT_V = CONTEXT_MENU + "//li/a[text()='Paste (child) [Ctrl + Alt + V]']";
    public static final String INSERT_MARKUP = CONTEXT_MENU + "//li[@id='insertMarkup']/a";

	public static final String AMEND =  "//a[text()='Amend']";
    public static final String INSERT_FEATURE = CONTEXT_MENU + "//a[text()='Insert Feature']";
	public static final String REFERENCE = CONTEXT_MENU + "//a[text()='Reference']";
	public static final String RESEARCH_REFERENCE = "//a[text()='Research Reference']";
	public static final String RESEARCH_REFERENCE_ALR = "//a[text()='alr']";
	public static final String USCA_REFERENCE = CONTEXT_MENU + "//a[text()='USCA Reference']";
	public static final String USCA_REFERENCES = CONTEXT_MENU + "//a[text()='usca.references']";
	public static final String HISTORICAL_NOTE = CONTEXT_MENU + "//a[text()='Historical Note']";
	public static final String HISTORICAL_NOTE_AMENDMENT_POPUP = CONTEXT_MENU + "//a[text()='Historical Note - Amendment Popup']";
	public static final String FEATURE_CHILD = CONTEXT_MENU + "//a[text()='Feature (child)']";
	public static final String CHANGE_OF_NAME = CONTEXT_MENU + "//a[text()='Change Of Name - Change of Name Paste As']";
	public static final String LAW_NOTE = CONTEXT_MENU + "//a[text()='Law Note - law.note']";
	public static final String INSERT_DELTA_WITH_FEATURE = "//a[text()='Insert Delta with Feature']";
	public static final String INSERT_DELTA_INSTRUCTION = CONTEXT_MENU + "//a[text()='Insert Delta Instruction']";

	public static final String INSERT_DELTA = CONTEXT_MENU + "//a[text()='Insert Delta']";
	public static final String SHOW_HIDE = "//li[@id='toggleDisplay']/a";

	public static final String EDITORIAL_NOTE = "//a[text()='Editorial Note']";
	public static final String TEXT_EDITORIAL_NOTE = "//a[text()='Text - editorial.note']";
	public static final String REFERENCE_XMISR = "//a[text()='Reference - xmisr']";

	public static final String OFFICIAL_NOTE = "//a[text()='Official Note']";
	public static final String OFFICIAL_NOTE_COMMENT = "//a[text()='Comment - comment']";

	public static final String ADMINISTRATIVE_CODE_REFERENCE = "//a[text()='Administrative Code Reference']";
	public static final String ADMINISTRATIVE_CODE_REFERENCE_XACR_CR1 = "//a[text()='xacr.cr1']";

	public static final String CROSS_REFERENCE = "//a[text()='Cross Reference']";
	public static final String CROSS_REFERENCE_CRL = "//a[text()='crl']";

	public static final String LIBRARY_REFERENCE = "//a[text()='Library Reference']";
	public static final String LIBRARY_REFERENCE_CJSREFERENCE = "//a[text()='cjs.reference']";

	public static final String SUPREME_COURT_REFERENCE = "//a[text()='Supreme Court Reference']";
	public static final String SUPREME_COURT_REFERENCE_SUPCTGNP = "//a[text()='sup.ct.gnp']";

	public static final String UNIFORM_LAW_REFERENCE = "//a[text()='Uniform Law Reference']";
	public static final String UNIFORM_LAW_REFERENCE_XUL = "//a[text()='xul']";
    public static final String INSERT_GLOSSARY_LINK = CONTEXT_MENU + "//li[@id='insertGlossaryLink']/a";
	public static final String HEADING_NAMELINE = "//a[text()='Heading Nameline']";
	public static final String ADDITIONAL_NAMELINES_POPUP = "//a[text()='Additional Namelines Popup']";

	public static final String EFFECTIVE_DATE = "//a[text()='Effective Date']";
	public static final String CREDIT_PHRASE = "//a[text()='Credit Phrase']";
    public static final String STATE_RULES = CONTEXT_MENU + "//a[text()='State Rules']";
	public static final String STATE_RULES_HEADING_NAMELINE = "//li/a[@href='#headingNamelineStateRulesSiblingGenSubMenu']";

	public static final String RUNNING_HEAD_LEGEND_LINE = CONTEXT_MENU + "//li/a[text()='Legend Line' and @href='#']";
	public static final String RUNNING_HEAD_CHILD_SUBMENU = CONTEXT_MENU + "//li/a[@href='#runningHeadWithBlockChildSubMenu']";
	public static final String RUNNING_HEAD_RUNNING_HEAD = CONTEXT_MENU + "//li/a[text()='Running Head' and @href='#']";
    public static final String JURISDICTIONAL_NAMELINE = CONTEXT_MENU + "//a[text()='Jurisdictional Namelines']";
	public static final String RRNL = CONTEXT_MENU + "//a[translate(text(), 'RNL', 'rnl')='rrnl']";
	public static final String GNH_RESEARCH_REFERENCE_SUBHEADING = "//a[text()='gnh']";

	public static final String INSERT_SOURCE_LINK_MARKUP = "//li[@id='insertSourceLinkMarkup']/a";

	public static final String CORNERPIECE_SIBLING_SUBMENU = "//li/a[@href='#cornerpieceSiblingGenSubMenu']";
	public static final String CORNERPIECE_SIBLING_SUBMENU_BY_NAME = "//div[@id='cornerpieceSiblingGenSubMenu']//a[contains(text(), 'cpl1')]";
	public static final String TEXT_PARAGRAPH_SIBLING_SUBMENU = "//li/a[@href='#textParagraphSiblingSubMenu']";

	public static final String JOIN_PARAGRAPHS = "//li/a[text()='Join Paragraphs [Ctrl + J]']";

	public static final String ITALIC_NOTE = "//a[text()='Italic Note']";
	public static final String ITALIC_NOTE_WIDE_CENTERED = "//a[text()='Italic Note Wide Centered (wisc)']";
	public static final String JURISDICTIONAL_ITALIC_NOTES = "//a[text()='Jurisdictional Italic Notes']";
	public static final String KITN_DEFERRED_SUBSECTION_ADD = "//a[text()='kitn.deferred.subsection.add']";
	public static final String INSERT_TEXT_AS_PART = "//a[text()='Insert Text As Part']";
	public static final String SNL = "//a[text()='snl']";
	public static final String PASTE_AS = CONTEXT_MENU + "//a[text()='Paste As']";
	public static final String PART_TEXT = CONTEXT_MENU + "//a[text()='Part Text']";
	public static final String SPLIT_PARAGRAPH = CONTEXT_MENU + "//a[text()='Split Paragraph [Shift + Enter]']";
	public static final String MODIFY = CONTEXT_MENU + "//a[text()='Modify']";
	public static final String DERIVATION = CONTEXT_MENU + "//a[text()='Derivation - derivation']";
	public static final String PREVIEW_OPTION = CONTEXT_MENU + "//a[text()='Preview']";
	public static final String INSERT_SOURCE_TAG = CONTEXT_MENU + "//li/a[text()='Insert Source Tag']";

	@FindBy(how = How.XPATH, using = "//li[@id='plusTag']/a")
	public static WebElement pubTagPlus;
	
	@FindBy(how = How.XPATH, using = "//li[@id='minusTag']/a")
	public static WebElement pubTagMinus;
	
	@FindBy(how = How.XPATH, using = "//li[@id='removeTag']/a")
	public static WebElement pubTagRemove;
	
	@FindBy(how = How.XPATH, using = "//li[@id='insertQueryNote']/a")
	public static WebElement insertQuery;

	@FindBy(how = How.XPATH, using = "//li[@id='deleteQueryNote']/a")
	public static WebElement deleteQuery;

	@FindBy(how = How.XPATH, using = "//li[@id='delete']/a")
	public static WebElement delete;

	@FindBy(how = How.XPATH, using = "//li/a[@href='#withinTextModifySubMenu']")
	public static WebElement modify;

	@FindBy(how = How.XPATH, using = "//li[@id = 'splitParagraph']/a")
	public static WebElement splitParagraph;

	@FindBy(how = How.XPATH, using = "//a[text() = 'Join Paragraphs [Ctrl + J]']")
	public static WebElement joinParagraph;

	@FindBy(how = How.XPATH, using = "//a[text()='Insert Text (child)']")
	public static WebElement insertTextChild;

	@FindBy(how = How.XPATH, using = "//a[text()='Introductory Paragraph']")
	public static WebElement introductoryParagraph;

	@FindBy(how = How.XPATH, using = "//a[text()='Import Target Text']")
	public static WebElement importTargetText;

	@FindBy(how = How.XPATH, using = "//a[text()='Import Target Text TX Repeals']")
	public static WebElement importTargetTextTXRepeals;
}
