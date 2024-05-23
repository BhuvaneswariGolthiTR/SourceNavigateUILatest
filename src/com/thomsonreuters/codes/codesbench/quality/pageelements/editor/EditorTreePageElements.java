package com.thomsonreuters.codes.codesbench.quality.pageelements.editor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditorTreePageElements
{
	public static final String EDITOR_TREE_EFFECTIVE_DATE = "//div[@class='dtree']//div[@treeNodeName='effective.date.block']//a[text()='effective.date.block' and @class='nodeSel']";
	public static final String TREE_NODE_LINK = "//div[@class='dTreeNode']/a[@class='node']";
	public static final String TREE_NODE_WITH_TEXT_LINK = "//div[@class='dTreeNode']/a[text()='%s']";
	public static final String CHILD_TREE_NODE_WITH_TEXT_LINK = "./div[@class='dTreeNode']/a[text()='%s']";
	public static final String TREE_NODE_WITH_TEXT = "//div[@class='dTreeNode' and @treeNodeName='%s']";
	public static final String TREE_NODE_CONTAINS_TEXT = "//div[@class='dTreeNode']/a[contains(text(),'%s')]";
	public static final String TREE_NODE_CONTAINS_TEXT_IN_SECOND_TEXT_NODE = "//div[@class='dTreeNode']/a[text()[contains(.,'%s')]]";
	public static final String TREE_NODE_CONTAINS_TEXT_IN_TWO_NODES = "//div[@class='dTreeNode']/a[text()[contains(.,'%s',..,'%s')]]";
	public static final String PRECEDING_EXPAND_NODE_LINK = "/preceding-sibling::a[img[@src='/cwbEditorWeb/resources/dtree/img/plus.gif' or @src='/cwbEditorWeb/resources/dtree/img/plusbottom.gif']]";
	public static final String SELECTED_NODE_WITH_NAME = "//a[@class='nodeSel' and text()='%s']";
	public static final String SELECTED_NODE_CONTAINING_TEXT_AND_POSITION = "//a[@class='nodeSel' and text()[contains('%s')]][%d]";
	public static final String SELECTED_NODE = "//a[@class='nodeSel']";
	public static final String PARATEXT_NODE_WITH_TEXT_SELECTED = "//a[@class='nodeSel' and contains(text(), 'paratext\"%s')]";
	public static final String PARATEXT_WITH_TEXT_GIVEN = "paratext\"%s\"";
	public static final String SELECTED_PARATEXT_NODE = "//a[@class='nodeSel' and contains(text(),'paratext')]";
	public static final String XAMPEX_TABLE = "//a[text()='xampex.table']";
	public static final String TREE_ROOT = "//div[@id='navWindow2']//div[@treeIndex='0']//a";
	public static final String CODES_HEAD_NODE_SELECTED = "//a[@class='nodeSel' and text()='codes.head \"%s\"']";
	public static final String NAV_WINDOW = "//div[@id='navWindow2']";
	public static final String CORNERPIECE_CP = "//a[text()='cornerpiece \"CP\"']";
	public static final String COLLAPSE_BUTTON_WITH_TEXT_LINK = "//a[./img[@src='/cwbEditorWeb/resources/dtree/img/minus.gif']/following::a[text()='%s']]";
	public static final String PRECEDING_COLLAPSE_NODE_LINK = "/preceding-sibling::a[img[@src='/cwbEditorWeb/resources/dtree/img/minus.gif']]";
	public static final String FOLLOWING_TREE_NODE_WITH_TEXT_LINK = "/following-sibling::div[@class='dTreeNode']/a[text()='%s']";

	@FindBy(how = How.XPATH, using = "//para")
	public static WebElement para;

	@FindBy(how = How.XPATH, using = "//div[@class='dtree']//a[text()='cornerpiece \"CP\"']")
	public static WebElement cornerpieceCP;

	@FindBy(how = How.XPATH, using = "//div[@class='dtree']//a[text()='codes.head \"SNL\"']")
	public static WebElement codesHeadSNL;

	@FindBy(how = How.XPATH, using = "//div[@class='dtree']//a[text()='body']")
	public static WebElement body;
	
	public static final String PARA_UNDER_NUMBER_XPATH = "(//para)[%d]";
	public static final String PARA_UNDER_NUMBER_XPATH_WITH_CHUNK = "(//div[@id='loaded_chunk_%d']//para)[%d]";

	public static final String ED_NOTE = "//div[@treeNodeName='delta.feature \"Ed.Note\"']";
	public static final String OFFICIAL_NOTE = "//div[@treeNodeName='delta.feature \"Official.Note\"']";
	public static final String ADMIN_CODE_REFERENCE = "//div[@treeNodeName='delta.feature \"Admin.Code.Reference\"']";
	public static final String CROSS_REFERENCE = "//div[@treeNodeName='delta.feature \"Cross.Reference\"']";
	public static final String LIBRARY_REFERENCE = "//div[@treeNodeName='delta.feature \"Library.Reference\"']";
	public static final String RESEARCH_REFERENCE = "//div[@treeNodeName='delta.feature \"Research.Reference\"']";
	public static final String US_SUP_CT_REFERENCE = "//div[@treeNodeName='delta.feature \"Us.Sup.Ct.Reference\"']";
	public static final String UNIFORM_LAW_REFERENCE = "//div[@treeNodeName='delta.feature \"Uniform.Law.Reference\"']";
	public static final String HIST_NOTE = "//div[@treeNodeName='delta.feature \"Hist.Note\"']";
	public static final String USCA_REFERENCE = "//div[@treeNodeName='delta.feature \"Usca.Reference\"']";



	public enum treeElements
	{
		ADMIN_CODE_REFERENCE_BLOCK("admin.code.reference.block"),
		AMENDMENT("hist.note \"Amendment\""),
		CODES_HEAD_GNPC("codes.head \"GNPC\""),
		CODES_HEAD_XUSA("codes.head \"XUSA\""),
		CORNERPIECE_CP("cornerpiece \"CP\""),
		CORNERPIECE_CPL1("cornerpiece \"CPL1\""),
		CORNERPIECE_CPR1("cornerpiece \"CPR1\""),
		CORNERPIECE_TEXT("cornerpiece.text"),
		CROSS_REFERENCE_BLOCK("cross.reference.block"),
		DELTA("(delta note.classified)&nbsp;"),
		DELTA_FEATURE("delta.feature"),
		EDITORIAL_NOTE("ed.note \"KITN\""),
		ED_NOTE_REFERENCE("ed.note \"Reference\""),
		FIGURE("figure"),
		HEAD_INFO("head.info"),
		HEAD_BLOCK("head.block"),
		HIST_NOTE_BODY("hist.note.body"),
		HIST_NOTE_LAW_NOTE("hist.note \"Law.Note\""),
		LEGEND_EVEN("legend.even"),
		LEGEND_ODD("legend.odd"),
		LIBRARY_REFERENCE_BLOCK("library.reference.block"),
		OFFICIAL_NOTE_BLOCK("official.note.block"),
		PARATEXT("paratext"),
		PARATEXT_INSERT_TEXT("paratext\"Insert text\""),
		PARA_CE("para \"CE\""),
		PARA_GNP("para \"GNP\""),
		PARA_SMP("para \"SMP\""),
		PARA_WISC("para \"WISC\""),
		PART_TEXT("Part Text"),
		PLACEHOLDER_CE("placeholder \"CE\""),
		PLACEHOLDER_ANGEN("placeholder \"ANGEN\""),
		PLACEHOLDER_TEXT("placeholder.text\"\""),
		PLACEHOLDER_TEXT_INSERT_TEXT("placeholder.text\"Insert text\""),
		PLACEHOLDER_TEXT_SA("placeholder.text\"SA\""),
		PLACEHOLDER_TOPT("placeholder \"TOPT\""),
		PLACEHOLDER_TODD("placeholder \"TODD\""),
		PLACEHOLDER_TOP("placeholder \"TOP\""),
		PLACEHOLDER_PRMS("placeholder \"PRMS\""),
		RESEARCH_REFERENCE_BLOCK("research.reference.block"),
		RUNHEAD_BLOCK("runhead.block"),
		RUNHEAD_EVEN("runhead.even"),
		RUNHEAD_ODD("runhead.odd"),
		SECTION_UNLOCKED("section (unlocked)"),
		SIGNATURE_BLOCK("signature.block"),
		SIGNATURE_LINE("signature.line"),
		SIGNATURE_ADD_TEXT("signature\"add text; also add additional sl paras b\""),
		SOURCE_BODY("source.body"),
		SOURCE_NOTE("source.note"),
		SOURCE_SECTION_1("source.section \"Sec. 1\""),
		TEXT("text\"%s\""),
		UNIFORM_LAW_REFERENCE_BLOCK("uniform.law.reference.block"),
		US_SUP_CT_REFERENCE_BLOCK("us.sup.ct.reference.block"),
		USCA_REFERENCE_BLOCK("usca.reference.block"),
		USCA_REFERENCE_CRL("usca.reference \"CRL\"");

		treeElements(String nodeName)
		{
			this.nodeName = nodeName;
		}
		public String getNodeName()
		{
			return this.nodeName;
		}

		public String getXpath()
		{
			return String.format(xpath, getNodeName());
		}

		private String nodeName;
		private final String xpath = "//div[@id='navWindow2']//div[@treeNodeName='%s']";
	}

	public enum codesHead
	{
		SRNL("SRNL"),
		RRNL("RRNL"),
		HGNL1("HGNL1"),
		HG3("HG3"),
		HG3C("HG3C"),
		RNL("RNL"),
		CE("CE")
		;

		codesHead(String mnemonic)
		{
			this.mnemonic = mnemonic;
		}
		public String getMnemonic()
		{
			return this.mnemonic;
		}

		public String getXpath()
		{
			return String.format(xpath, getMnemonic());
		}

		private String mnemonic = "";
		private final String xpath = "//div[@treeNodeName='codes.head \"%s\"']";
	}
}
