package com.thomsonreuters.codes.codesbench.quality.pageelements.editor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static java.lang.String.format;

public class EditorTextPageElements
{
	public static final String CLASS_HIGHLIGHTED_POSTFIX = "[contains(@class,'highlighted')]";
	public static final String CLASS_PATTERN_POSTFIX = "[@class='%s']";
	public static final String CLASS_CONTAINS_POSTFIX = "[contains(@class,'%s')]";
	public static final String CONTAINS_TEXT_POSTFIX = "[contains(text(),'%s')]";
	public static final String ENTITY_PATTERN = "[@entity='%s']";

	public static final String SECTION = "//section";
	public static final String SUBSECTION = "//subsection";
	public static final String SUBSECTION_UNDER_NUMBER = "//subsection[%d]";
	public static final String SUBSECTION_SPAN = "//subsection/span[contains(text(),'Subsection')]";
	public static final String SUBSECTION_SPAN_UNDER_NUMBER = "(//subsection/span[contains(text(),'Subsection')])[%d]";
	public static final String SUBSECTION_PARA_UNDER_NUMBER = "//subsection[@display-name='Subsection']//paratext[%d]";
	public static final String BODYS_UPPER_LEVEL_CHILD_SUBSECTION_LABELS = "//body/subsection/span[contains(text(),'Subsection')]";

	public static final String HINT = "//hint";
	public static final String HINT_BY_TEXT = "//hint[text()[contains(.,'%s')]]";
	public static final String SPAN = "//span";
	public static final String SPAN_BY_TEXT = "//span[text()[contains(.,'%s')]]";
	public static final String SPAN_CONTAINS_TEXT = SPAN + CONTAINS_TEXT_POSTFIX;
	public static final String TEXT_PARAGRAPH_SPAN = String.format(SPAN_BY_TEXT, "Text Paragraph");
	public static final String TEXT_PARAGRAPH_SPAN_UNDER_NUMBER = "(" + TEXT_PARAGRAPH_SPAN + ")[%d]";
	public static final String PARA = "//para";
	public static final String PARA_BY_DISPLAY_NAME = PARA + "[@display-name='%s']";
	public static final String PART = "//part";
	public static final String PART_TEXT_BY_DISPLAY_NAME = PART + "[@display-name='Part Text']";
	public static final String PARATEXT = "//paratext";
	public static final String BODY_PARA = "//body/para";
	public static final String FIRST_PARA_IN_BODY = BODY_PARA + "/paratext";
	public static final String SOURCE_BODY = "//source.body";
	public static final String SOURCE_BODY_SOURCE_SECTION = SOURCE_BODY + "/source.section";
	public static final String SOURCE_SECTION = "//source.section";
	public static final String HEADTEXT = "//headtext";
	public static final String ANALYSIS_TEXT = "//analysis.text";
	public static final String LABEL_DESIGNATOR = "//label.designator";
	public static final String METADATA_DESIGNATOR_LABEL = "//md.label.designator";
	public static final String CORNERPIECE = "//cornerpiece";
	public static final String PLACEHOLDER = "//placeholder";
	public static final String PLACEHOLDER_BY_DISPLAY_NAME = PLACEHOLDER + "[@display-name='%s']";
	public static final String PLACEHOLDER_TEXT = "/placeholder.text";
	public static final String METADATA_BLOCK = "//metadata.block";
	public static final String CODES_HEAD = "//codes.head";
	public static final String CODES_TEXT = "//codes.text";
	public static final String ED_NOTE = "//ed.note";

	public static final String SECTION_SPAN = SECTION + "/span";
	public static final String SOURCE_SECTION_SPAN = SOURCE_SECTION + "/span";
	public static final String PARA_SPAN = PARA + "/span";
	public static final String ENGLISH_LABEL = "//%s/span";

	public static final String HINT_WITH_TEXT = HINT + CONTAINS_TEXT_POSTFIX;

	public static final String METADATA_LABEL_DESIGNATOR = METADATA_BLOCK + METADATA_DESIGNATOR_LABEL;
	public static final String SUBSECTION_LABEL_DESIGNATOR_RIGHT_ARROW = SUBSECTION + METADATA_LABEL_DESIGNATOR + "//img[contains(@src,'label_designator_right_arrow.png')]";
    public static final String SUBSECTION_LABEL_DESIGNATOR_LEFT_ARROW = SUBSECTION + METADATA_LABEL_DESIGNATOR + "//img[contains(@src,'label_designator_left_arrow.png')]";

	public static final String BODY_LABEL = SOURCE_BODY + "[@full-display-name='Body']/span";

    public static final String SOURCE_SECTION_LABEL_UNDER_NUMBER_GIVEN = "(//source.section[@full-display-name='Source Section'])[%d]/span";
    public static final String SOURCE_SECTION_EFFECTIVE_DATE_ENGLISH_LABEL = SOURCE_SECTION + "//effective.date.block//span[contains(text(),'Effective Date')]";

    public static final String EFFECTIVE_DATE_UNDER_ENGLISH_LABEL = "/..//date" + CONTAINS_TEXT_POSTFIX;

    public static final String TAX_TYPE_ADD_ENGLISH_LABEL = "//%s/tax.type.add.block";
	public static final String HIGHLIGHTED_TAX_TYPE_ADD_ENGLISH_LABEL = TAX_TYPE_ADD_ENGLISH_LABEL+ CLASS_HIGHLIGHTED_POSTFIX;
	public static final String HIGHLIGHTED_TAX_TYPE_ADD_ENGLISH_LABEL_VALUE = HIGHLIGHTED_TAX_TYPE_ADD_ENGLISH_LABEL + "/tax.types";

    public static final String SOURCE_FRONT_SPAN = "//source.front/span";
    public static final String SOURCE_FRONT_HIDDEN_CHILDREN = "//source.front/*[contains(@class,'hiddenToggle')]";
    public static final String SOURCE_END_SPAN = "//source.end/span";
    public static final String SOURCE_END_HIDDEN_CHILDREN = "//source.end/*[contains(@class,'hiddenToggle')]";

    public static final String TEXT_LABEL = SPAN + "[contains(text(),'Text')]";
	public static final String TEXT_PARAGRAPH_LABEL = SPAN + "[contains(text(),'Text Paragraph')]";
    public static final String SOURCE_SPAN = SPAN + "[text()='Section ']";
    public static final String TEXT_PARAGRAPH_WITH_NUMBER = "(" + TEXT_PARAGRAPH_LABEL + ")" + "[%s]";
	public static final String TEXT_PARAGRAPH_WITH_NUMBER_PARA = "(//para[@display-name='Text Paragraph'])[%d]";
    public static final String VOLUME_NUMBER_SPAN = SPAN + "[contains(text(),'Volume Number ')]/following-sibling::placeholder.text";
    public static final String TEXT_PARAGRAPH_FROM_CHUNK = "(//div[@id='loaded_chunk_%s']//para[@display-name='Text Paragraph'])[%s]";
	public static final String HIGHLIGHTED_PARA_FROM_CHUNK = "//div[@id='loaded_chunk_%s']//para[contains(@class,'highlighted')][%s]";

	public static final String NDP_TEXT = PARA + "[" + SPAN + "[contains(text(),'Note of Decision Paragraph')]" + "]" + PARATEXT;
	public static final String NOD_NOTE = "//nod.note";
	public static final String NDP_BY_USER = NOD_NOTE + "[para/metadata.block//modified.by[contains(text(),'%s')]]//span[@class='englishLabel' and contains(text(),'Note of Decision')]";
	public static final String NOD_NOTE_SPAN = NOD_NOTE + "/span";
	public static final String NOD_PARA_XPATH = "//span[contains(text(), 'Note of Decision Paragraph')]";
	public static final String NOD_PARATEXT_WORDPHRASE = "//wordphrase/ancestor::paratext";

	public static final String HINT_START_TABLE_MARKER = SOURCE_BODY+ HINT + "[text()='Start Table Marker']";
    public static final String HINT_START_AS_PRECEDING_SIBLING = "/preceding-sibling::hint[text()='Start Table Marker']";
    public static final String HINT_STOP_TABLE_MARKER = SOURCE_BODY+ HINT + "[text()='Stop Table Marker']";
    public static final String HINT_STOP_AS_FOLLOWING_SIBLING = "/following-sibling::hint[text()='Stop Table Marker']";
    public static final String TABULAR_ENTRIES_IN_BODY_PART = "//*[self::source.body or self::body]//span[contains(text(),'Tabular Entry ')]";
    public static final String XAMPEX_TABLE_IN_BODY_PART = SOURCE_BODY + "//xampex.table";
	public static final String TABLE_PARAS_MODIFIED_BY = XAMPEX_TABLE_IN_BODY_PART + "/para/metadata.block/modified.by";
	public static final String TABLE_PARAS_MNEMONIC = "//xampex.table/para/metadata.block/md.mnem";
    public static final String TABLE_PARAS_PARATEXT_BY_NUMBER = "(//xampex.table/para/paratext)[%d]";

    public static final String DELTA_AMEND_SUBSECTION = "//delta[@full-display-name='Delta Amend Subsection' "
			+ "and @level='subsection' and @action='amend']";
    public static final String DELTA_AMEND_SUBSECTION_LABEL = DELTA_AMEND_SUBSECTION + "/span";
    public static final String HIGHLIGHTED_DELTA = "//delta[contains(@class,'highlighted')]";
	public static final String DELTA = "//delta";
	public static final String DELTA_ADD_NOTE = "//delta[@full-display-name='Delta Add Note']";
	public static final String DELTA_ADD_NOTE_SPAN = DELTA_ADD_NOTE + "/span";
	public static final String DELTA_INSTRUCTION_TEXT = "//source.note[@full-display-name='Delta Instruction']/text";
	public static final String DELTA_BY_NUMBER = "//delta[%d]";
    public static final String DELTA_BY_NUMBER_SPAN = DELTA_BY_NUMBER + "/span";
    public static final String DATE_BY_TEXT = "//date[text()[contains(.,'%s')]]";
	public static final String DELTA_BY_CLASS_PATTERN = DELTA + CLASS_PATTERN_POSTFIX;
	public static final String MULTIPLE_FULL_SECTION_ITALIC_NOTE = DELTA_BY_CLASS_PATTERN + PART + ED_NOTE + PARA;
	public static final String MULTIPLE_FULL_SECTION_ITALIC_NOTE_TEXT = PARATEXT + format(CLASS_CONTAINS_POSTFIX, "italic") +
			CONTAINS_TEXT_POSTFIX;

    // query note
    public static final String QUERY_NOTE_BLOCK = "//md.sn.info";
    public static final String QUERY_NOTE_OF_ANY_TYPE = QUERY_NOTE_BLOCK + "/*[@class]";
    public static final String QUERY_NOTE_TYPE_DATE = "//md.sn.query.date";
    public static final String RESOLVED_QUERY_NOTE_XPATH = QUERY_NOTE_TYPE_DATE + ".resolved";
    public static final String QUERY_NOTE_TYPE_REVISOR = "//md.sn.query.revisor";
    public static final String QUERY_NOTE_TYPE_CONTINGENCY = "//md.sn.query.comment";
    public static final String QUERY_NOTE_TYPE_CRITICAL_ISSUE = "//md.sn.query.issue";
    public static final String QUERY_NOTE_TYPE_TABULAR = "//md.sn.query.table";

    // hvs information
	public static final String HVS_SERIAL_NUMBER = "//metadata.block//md.serial.no";
	public static final String HVS_REPORTER_VOLUME = "//metadata.block//md.reporter.volume";
	public static final String HVS_REPORTER_NUMBER = "//metadata.block//md.reporter.number";
	public static final String HVS_REPORTER_PAGE = "//metadata.block//md.reporter.page";
	public static final String HVS_HEADNOTE_NUMBER = "//metadata.block//md.headnote.no";

	// Bill Track Block
	public static final String BILL_TRACK_BLOCK_WITH_MANUAL_INSERT_MARK = "//bill.track.block//span[text() = ' MANUAL INSERT']//ancestor::bill.track.block";
	public static final String SUMMARY_LINE_TEXT_OF_GIVEN_BILL_TRACK_BLOCK = BILL_TRACK_BLOCK_WITH_MANUAL_INSERT_MARK + "//summary.line/text";
	public static final String MODIFIED_BY_TAG_OF_GIVEN_BILL_TRACK_BLOCK_SUMMARY_LINE = BILL_TRACK_BLOCK_WITH_MANUAL_INSERT_MARK + "//*[contains(text(),'Modified')]/..";
	public static final String TRACK_STAGE_TEXT_OF_GIVEN_BILL_TRACK_BLOCK = BILL_TRACK_BLOCK_WITH_MANUAL_INSERT_MARK + "//track.stage/text";

	public static final String BILL_TRACK_BLOCK_MANUAL_INSERT_XPATH = "//span[contains(text(),'MANUAL INSERT')]"; //works
	public static final String BILL_TRACK_BLOCK_SUMMARY_LINE_XPATH = "//*[contains(@full-display-name,'Summary Line')]";
	public static final String BILL_TRACK_BLOCK_SUMMARY_MODIFIED_BY_XPATH_HEAD = "//*[contains(.,'"; //works
	public static final String BILL_TRACK_BLOCK_SUMMARY_TEXT_BEFORE_XPATH = "//hint[contains(.,'Insert')][1]"; //works
	public static final String BILL_TRACK_BLOCK_TRACKING_SPAN_XPATH = "//*[contains(@full-display-name,'Tracking Stage')]"; //?
	public static final String BILL_TRACK_BLOCK_TRACK_STAGE_TEXT_XPATH = "//bill.track.block/track.stage/text";
	public static final String BILL_TRACK_BLOCK_TRACK_STAGE_MODIFIIED_XPATH = "//bill.track.block/track.stage/metadata.block/modified.by";

    // Text
    public static final String TEXT_SUBNODES = "//body[@display-name='Text']/*";
    public static final String DIV_PARATEXT = "//div[@class='paratext']";

    public static final String LAW_BLOCK_PARATEXT = "//pending.law.block[%d]/para/paratext";
	public static final String LAW_BLOCK_SPAN = "//pending.law.block[%d]/para/span";

    public static final String TEXT_PARAGRAPH_PARA = PARA + "[@display-name='Text Paragraph']";
	public static final String TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN = TEXT_PARAGRAPH_PARA+ "[%d]";
	public static final String FIRST_TEXT_PARAGRAPH = TEXT_PARAGRAPH_PARA +"/span";
	public static final String TEXT_PARAGRAPH_PARATEXT = TEXT_PARAGRAPH_PARA + PARATEXT;
	public static final String TEXT_PARAGRAPH_PARATEXT_UNDER_NUMBER_GIVEN = TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN + PARATEXT;
    public static final String HIGHLIGHTED_PARA = PARA + CLASS_HIGHLIGHTED_POSTFIX;
	public static final String TEXT_PARAGRAPH_LABEL_XPATH = "//span[contains(text(),'Text Paragraph')]";
	public static final String CORNERPIECE_SPAN_BY_VALUE_XPATH = "//cornerpiece.text/span[contains(text(), '%s')]/../../span";
	public static final String FIRST_END_OF_TEXT_SPAN_XPATH = "//placeholder[@full-display-name='End of Text']/span";

    public static final String PARATEXT_SPAN = PARATEXT + "/span";
    public static final String PARATEXT_ITALIC = PARATEXT + "[contains(@class, 'italic')]";
    public static final String TEXT_PARAGRAPH_STARTING_WITH_TEXT_GIVEN = PARATEXT + "[starts-with(text(),'%s')]";
    public static final String TEXT_PARAGRAPH_CONTAINS_TEXT_GIVEN = PARATEXT + CONTAINS_TEXT_POSTFIX;
    public static final String TEXT_PARAGRAPH_IMAGE = "//body[@display-name='Text']//para//span[contains(text(),'Text Paragraph')]//..//..//figure//image.block//image//span[contains(text(),'Image')]";
    public static final String PARATEXT_WITH_TEXT = "//paratext[text()='%s']";

	public static final String CHILD_PARATEXT = "./paratext";
    public static final String HIGHLIGHTED_CORNERPIECE = CORNERPIECE + CLASS_HIGHLIGHTED_POSTFIX;
    public static final String HIGHLIGHTED_CORNERPIECE_TEXT = HIGHLIGHTED_CORNERPIECE + "/cornerpiece.text";
    public static final String HIGHLIGHTED_PLACEHOLDER = PLACEHOLDER + CLASS_HIGHLIGHTED_POSTFIX;

    public static final String HIGHLIGHTED_SUBSECTION_BY_LABEL = SUBSECTION + CLASS_HIGHLIGHTED_POSTFIX + "//md.label.designator[contains(text(), '%s')]";

    public static final String CORNERPIECE_TEXT = CORNERPIECE + "//cornerpiece.text";
    public static final String CORNERPIECE_SPAN = CORNERPIECE + "/span";

	public static final String END_OF_TEXT_BLOCK_XPATH = "//placeholder.text/span";
	public static final String FIRST_TEXT_SPAN_XPATH = "//body[@full-display-name='Text']/span";
    public static final String CORNERPIECE_FROM_SOURCE_FRONT = "//source.front/para/span[contains(text(),'Cornerpiece')]";
    public static final String HIGHLIGHTED_CORNERPIECE_AS_FOLLOWING_SIBLING =
    		"/following-sibling::cornerpiece" + CLASS_HIGHLIGHTED_POSTFIX;

    public static final String TEXT_PARAGRAPH_UNDER_HIGHLIGHTED_SUBSECTION = SUBSECTION + CLASS_HIGHLIGHTED_POSTFIX + "/para";
    public static final String CITATION_IMAGE = "//img[contains(@title, 'Citation(%s)')]";

	public static final String TARGET_LOCATION_SECTION = "//target.location.section";
	public static final String DELTA_AMEND_LABEL = "//span[contains(text(),'Delta Amend %s')]";
	public static final String FIRST_TEXT_PARAGRAPH_XPATH = "//para[@display-name='Text Paragraph']/span";
	public static final String TEXT_PARAGRAPH_LABEL_UNDER_NUMBER_GIVEN = "(//span[contains(text(),'Text Paragraph')])[%d]";
	public static final String TEXT_PARAGRAPH_PARA_UNDER_NUMBER_GIVEN = "(//para[@display-name='Text Paragraph'])[%d]";
	public static final String TEXT_LABEL_XPATH = "//span[contains(text(),'Text')]";
	public static final String INSERT_TEXT_HINT = "//hint[text()='Insert text']";
	public static final String SECTION_INTRO_PARA_TEXT_XPATH = "(//*[local-name() = 'source.section'])[%s]/*[local-name() = 'intro.para']/*[local-name() = 'text']";
	public static final String SECTION_INTRO_PARA_SPAN_XPATH = "(//*[local-name() = 'source.section'])[%s]/*[local-name() = 'intro.para']/span";

    public static final String CITE_QUERY_UNDER_NUMBER = "(//cite.query)[%d]";
    public static final String ADDED_TEXT = "//added.material[text()='%s']";
    public static final String DELETED_TEXT = "//deleted.material[text()='%s']";
    public static final String BOLD_TEXT = "//bold[text()='%s']";
	public static final String MARKUP_IMAGE_LABEL = "/img";
    public static final String BOLD_TEXT_MARKUP_IMAGE = BOLD_TEXT + MARKUP_IMAGE_LABEL;
    public static final String ITALIC_TEXT = "//ital[text()='%s']";
    public static final String ITALIC_TEXT_MARKUP_IMAGE = ITALIC_TEXT + MARKUP_IMAGE_LABEL;
    public static final String ITALIC_INSIDE_BOLD_TEXT = "//bold" + ITALIC_TEXT;
    public static final String CAP_CONDITIONAL_TEXT = "//uc[text()='%s']";
    public static final String SMALL_CAPS_TEXT = "//csc[text()='%s']";
    public static final String PARAGRAPH_ID_INCLUDE_TEXT = "//wip.flag[text()='%s' and contains(@title,'cent8')]";
    public static final String PARAGRAPH_ID_IGNORE_TEXT= "//wip.flag[text()='%s' and contains(@title,'cent9')]";
    public static final String ELEMENT_WITH_WORDPHRASE_REGEX = "^<\\?xml:namespace prefix = \"tw\" /><tw:wordphrase.*<IMG.*wordphrase\\.png.*</tw:wordphrase>%s<tw:wordphrase.*<IMG.*wordphrase\\.png.*</tw:wordphrase>.*$";
    public static final String CHARACTER_FILL_TEXT= "//leaderchar[text()='%s']";
    public static final String CHARACTER_GENERATE_TEXT= "//charfill[text()='%s']";
    public static final String PRINT_SUPPRESS_TEXT = "//conditional[text()='%s' and contains(@title,'print')]";
    public static final String WL_SUPPRESS_TEXT = "//conditional[text()='%s' and contains(@title,'westlaw')]";
    public static final String VENDOR_INCLUDE_TEXT = "//conditional[text()='%s' and contains(@title,'pocket.part')]";
    public static final String CASE_HISTORY_ID_TEXT = "//wip.flag[text()='%s' and contains(@title,'centh')]";
	public static final String ELEMENT_WITH_END_LEFT_REGEX = "^%s<\\?xml:namespace prefix = \"tw\" /><tw:endline.*title=\" quadding\\(left\\).*$";
	public static final String ELEMENT_WITH_END_CENTER_REGEX = "^%s<\\?xml:namespace prefix = \"tw\" /><tw:endline.*title=\" quadding\\(center\\).*$";
	public static final String ELEMENT_WITH_END_RIGHT_REGEX = "^%s<\\?xml:namespace prefix = \"tw\" /><tw:endline.*title=\" quadding\\(right\\).*$";
	public static final String ELEMENT_WITH_END_JUSTIFY_REGEX = "^%s<\\?xml:namespace prefix = \"tw\" /><tw:endline.*title=\" quadding\\(justify\\).*$";
	public static final String WTC_TEXT= "//wip.flag[text()='%s' and contains(@title,'centdol')]";
    public static final String CITE_REFERENCE_TEXT= "//cite.query[text()='%s']";
    public static final String QUOTE_TEXT = "//quote[text()='%s']";
	public static final String CITE_TREATMENT_TEXT = "//citator.treatment[text()='%s']";
	public static final String CONDITIONAL_CASE_TEXT = "//conditional[text()='%s']";
	public static final String CROSSHATCH_VETOED_TEXT = "//crosshatch[text()='%s']";
	public static final String GLOSSARY_LINK_TEXT = "//cite.query[@w-ref-type='GM' and @manual-edit='true' and text()='%s']";
	public static final String SOURCE_LINK_TEXT = "//cite.query[@w-ref-type='GO' and @manual-edit='true' and @w-docfamily-uuid and text()='%s']";
	public static final String MANUAL_TARGET_LINK_TEXT = "//cite.query[@w-ref-type='GH' and @manual-edit='true' and @w-pub-number and text()='%s']";
	public static final String TARGET_LINK_TEXT = "//cite.query[@w-ref-type='GH' and @w-pub-number and text()='%s']";
	public static final String TARGET_LINK_TEXT_GREEN = "//cite.query[@w-ref-type='GH' and @w-pub-number and contains(@style, 'BACKGROUND-COLOR: #00e600') and text()='%s']";

	public static final String RESEARCH_REFERENCE_SUBHEADING = "//codes.head[@display-name = 'Research Reference Subheading']";
	public static final String MD_MNEM_MNEMONIC = METADATA_BLOCK + "/md.mnem";
	public static final String GNH_MNEMONIC = MD_MNEM_MNEMONIC + "[text()='GNH']";
	public static final String CLP_MNEMONIC = MD_MNEM_MNEMONIC + "[text()='CLP']";
	public static final String STBL_MNEMONIC = MD_MNEM_MNEMONIC + "[text()='STBL']";
	public static final String SOURCE_TAG = METADATA_BLOCK + "/md.source.tag";
	public static final String SOURCE_TAG_BY_TEXT = "//md.source.tag[text()[contains(.,'%s')]]";
	public static final String MODIFIED_BY_MNEMONIC = METADATA_BLOCK + "/modified.by";
	public static final String MODIFIED_BY_MNEMONIC_WITH_TEXT = MODIFIED_BY_MNEMONIC + "[text()='%s']";
	public static final String MERGED_FROM_TAG = METADATA_BLOCK + "//md.merged.from";
	public static final String MERGED_TO_TAG = METADATA_BLOCK + "//md.merged.to";
	public static final String HEAD_INFO = "//head.info";
	public static final String HEADING_TEXT_LABEL = HEAD_INFO + "/headtext";
	public static final String TEXT_HEADING_NARROW_CENTERED_CLP = "//codes.head[@display-name = 'Text Narrow Centered']";

	// pubtags
	public static final String ANY_PUBTAG_IN_METADATA_BLOCK = METADATA_BLOCK + "/md.pub.tag.info/md.pub.tag";
	public static final String NOPUB_MNEMONIC = ANY_PUBTAG_IN_METADATA_BLOCK + "[@class = 'nopub']";
	public static final String PUBTAG_AS_CONDITION_XPATH_PART = "[md.pub.tag[text()='%s']]";

	// exclude tag
	public static final String EXCLUDE_TAG = METADATA_BLOCK + "/md.exclude.tag";

	public static final String TABLE_AS_A_PARENT_NODE_FOLLOWING_SIBLING_POSTFIX = "/../following-sibling::xampex.table/span[contains(text(),'Table')]";

	public static final String FIRST_SUMMARY_LINE_TEXTBOX_XPATH = "(//*[local-name() = 'summary.line'])[1]/*[local-name() = 'text']";
	public static final String FIRST_TRACKING_STAGE_TEXTBOX_XPATH = "(//*[local-name() = 'track.stage'])[1]/*[local-name() = 'text']";
	public static final String SECOND_SUMMARY_LINE_FROM_SPLIT_XPATH = "(//*[local-name() = 'bill.track.block'])[1]//*[local-name() = 'summary.line'][2]";
	public static final String SECOND_TRACKING_STAGE_FROM_SPLIT_XPATH = "(//*[local-name() = 'bill.track.block'])[2]//*[local-name() = 'track.stage'][2]";

	public static final String PARAGRAPH_WITH_TEXT = "//para[./paratext[contains(text(),'%s')]]";

	public static final String LOADED_CHUNK = "//div[@id='loaded_chunk_%s']";
	public static final String CHUNKS = "//div[contains(@id,'loaded_chunk_')]";

	public static final String SOURCE_BODY_SPAN = "//source.body/span";
	public static final String SOURCE_BODY_EFFECTIVE_DATE = "//source.body/effective.date.block";
	public static final String SOURCE_BODY_EFFECTIVE_DATE_DATE = "//source.body/effective.date.block/date";
	public static final String SOURCE_SECTION_TEXT = SOURCE_SECTION + "/source.note/text";
	public static final String SOURCE_SECTION_WITH_TEXT = "//source.section[//*[contains(text(), '%s')]]";
	public static final String SOURCE_SECTION_SPAN_WITH_TEXT = SOURCE_SECTION_WITH_TEXT + "/span";
	public static final String SOURCE_SECTION_BY_PARATEXT = "//paratext[text()[contains(.,'%s')]]/ancestor::source.section";
	public static final String DELTA_FEATURE = "//delta.feature";
	public static final String SOURCE_SECTION_DELTA_FEATURE = "//source.section[source.note/text[contains(text(), '%s')]]/delta/delta.feature";
	public static final String TARGET_LOCATION = "//target.location";

	public static final String DELTA_TARGET_LOCATION = "//delta/target.location";
	public static final String DELTA_ADD_SECTION = "//delta[@display-name='Delta Add Section']";
	public static final String DELTA_DELETE_SECTION = "//delta[@display-name='Delta Delete Section']";
	public static final String DELTA_AMEND_SECTION = "//delta[@display-name='Delta Amend Section']";
	public static final String DELTA_ADD_SUBSECTION = "//delta[@display-name='Delta Add Subsection']";
	public static final String DELTA_ADD_GRADE = "//delta[@display-name='Delta Add Grade']";
	public static final String DELTA_REPEAL_SECTION = "//*[contains(text(),'Delta Repeal Section')]";
	public static final String DELTA_IN_TREE = "(//*[contains(text(),'Delta Amend Section')])[%d]";
	public static final String FIRST_DELTA_AMEND_SUBSECTION = "(//*[contains(text(),'Delta Amend Subsection')])[%d]";
	public static final String FIRST_DELTA_ADD_SECTION = "//*[contains(text(),'Delta Add Section')]";
	public static final String FIRST_DELTA_ADD_SUBSECTION = "//*[contains(text(),'Delta Add Subsection')]";
	public static final String FIRST_DELTA_ADD_NOTE = "//*[contains(text(),'Delta Add Note')]";
	public static final String FIRST_DELTA_DELETE_SUBSECTION = "//*[contains(text(),'Delta Delete Subsection')]";
	public static final String FIRST_DELTA_GRADE_SECTION = "//*[contains(text(),'Delta Add Grade')]";

	public static final String SOURCE_SECTION_DELTA_TARGET_LOCATION = "//source.section[source.note/text[contains(text(), '%s')]]/delta/target.location";
	public static final String SOURCE_SECTION_DELTA_ADD_SECTION = "//source.section[source.note/text[contains(text(), '%s')]]/delta/span[text()='Delta Add Section ']";
	public static final String SOURCE_SECTION_DELTA_DELTE_SECTION = "//source.section[source.note/text[contains(text(), '%s')]]/delta/span[text()='Delta Delete Section ']";
	public static final String SOURCE_SECTION_DELTA_AMEND_SECTION = "//source.section[source.note/text[contains(text(), '%s')]]/delta/span[text()='Delta Amend Section ']";

	public static final String USCA_REFERENCE_BLOCK = "//usca.reference.block";
	public static final String USCA_REFERENCE_HEADING = USCA_REFERENCE_BLOCK + "/codes.head[span[text()='USCA Reference Heading ']]";
	public static final String USCA_REFERENCE_LINE = USCA_REFERENCE_BLOCK + "/usca.reference[span[text()=' USCA Reference Line ']]";

	public static final String SECTION_INSTRUCTION = "//*[@display-name='Section Instruction']";
	public static final String SECTION_INSTRUCTION_WITH_TEXT = "//*[@display-name='Section Instruction'][//*[contains(text(), %s)]]";
	public static final String SECTION_INSTRUCTION_TEXT = "//source.note[@full-display-name='Section Instruction']/text";
	
	public static final String USCA_REF = HINT + "[text()='U.S.C.A Ref']";
	public static final String DELTA_NOTE = "//*[@display-name='Delta  Note']";
	public static final String REFERENCE_EDITORIAL_NOTE = "//*[@display-name='Reference Editorial Note']";
	public static final String MISCELLANEOUS_REFERENCE_HEADING = "//*[@display-name='Miscellaneous Reference Heading']";
	public static final String TARGET_LOCATION_ADD = "//*[@display-name='Target Location Add']";
	public static final String EDITOR_NOTE_LINE = "//*[@display-name='Editor Note Line']";
	public static final String EDITOR_NOTE_PARAGRAPH = "//*[@display-name='Editor Note Paragraph']";
	public static final String OFFICIAL_NOTE_BLOCK = "//official.note.block";
	public static final String OFFICIAL_COMMENTARY_NOTE_HEADING = "//*[@display-name='Official Commentary Note Heading']";
	public static final String OFFICIAL_NOTE_BODY = "//official.note.body";
	public static final String ADMINISTRATIVE_CODE_REFERENCE_BLOCK = "//admin.code.reference.block";
	public static final String ADMINISTRATIVE_CODE_REFERENCE_HEADING = "//*[@display-name='Administrative Code Reference Heading']";
	public static final String CROSS_REFERENCE_BLOCK = "//cross.reference.block";
	public static final String CROSS_REFERENCE_HEADING = "//*[@display-name='Cross Reference Heading']";
	public static final String CROSS_REFERENCE_BODY = "//cross.reference.body";
	public static final String CROSS_REFERENCE_LINE = "//*[@display-name='Cross Reference Line']";
	public static final String LIBRARY_REFERENCE_BLOCK = "//library.reference.block";
	public static final String LIBRARY_REFERENCE_HEADING = "//*[@display-name='Library Reference Heading']";
	public static final String LIBRARY_REFERENCE_BODY = "//library.reference.body";
	public static final String RESEARCH_REFERENCE_BLOCK = "//research.reference.block";
	public static final String RESEARCH_REFERENCE_HEADING = "//*[@display-name='Research Reference Heading']";
	public static final String RESEARCH_REFERENCE_BODY = "//research.reference.body";
	public static final String AMERICAN_LAW_REPORTS_REFERENCE = "//*[@display-name='American Law Reports Reference']";
	public static final String US_SUPREME_COURT_REFERENCE_BLOCK = "//*[@display-name='US Supreme Court Reference Block']";
	public static final String SUPREME_COURT_REFERENCE_HEADING = "//*[@display-name='Supreme Court Reference Heading']";
	public static final String SUPREME_COURT_REFERENCE = "//*[@display-name='Supreme Court Reference']";
	public static final String UNIFORM_LAW_REFERENCE_BLOCK = "//*[@display-name='Uniform Law Reference Block']";
	public static final String UNIFORM_LAW_TABLE_HEADING = "//*[@display-name='Uniform Law Table Heading']";
	public static final String EDITORIAL_NOTE = "//*[@display-name=' Editorial Note']";
	public static final String ITALIC_NOTE = "//*[@display-name='Italic Note']";
	public static final String NOTE_PARAGRAPH = "//*[@display-name='Note Paragraph']";
	public static final String GRADE_HEADING_BLOCK = "//*[@display-name='Grade Heading Block']";
	public static final String GRADE_HEADING = "//*[@display-name='Grade Heading']";
	public static final String GRADE_HEADING_CONTINUED = "//*[@display-name='Grade Heading Continued']";
	public static final String CITATION_EQUIVALENT = "//*[@display-name='Citation Equivalent']";
	public static final String ANALYSIS_GENERATION = "//*[@display-name='Analysis Generation']";
	public static final String PAGE_FORCE_PLACEHOLDER_TOPT = "//*[@display-name='Page Forcer In Text Next Page With Running Heads']";
	public static final String PAGE_FORCE_PLACEHOLDER_TODD = "//*[@display-name='Page Forcer Next Odd Page Without Running Heads']";
	public static final String PAGE_FORCE_PLACEHOLDER_TOP = "//*[@display-name='Page Forcer Next Page With Running Heads']";
	public static final String PAGE_FORCE_PLACEHOLDER_PRMS = "//*[@display-name='Page Forcer Next Page Without Running Heads']";
	public static final String SIGNATURE_BLOCK = "//*[@display-name='Signature Block']";
	public static final String SIGNATURE_LINE = "//*[@display-name='Signature Line']";

	public static final String SECTION_NAMELINE = "//codes.head[@display-name='Section Nameline']";

	public static final String NON_LIVE_SECTION_NAMELINE = "//codes.head[@display-name='Non-Live Section Nameline']";
	public static final String NON_LIVE_SECTION_NAMELINE_HIGHLIGHT = "//codes.head[@display-name='Non-Live Section Nameline'][contains(@class,'highlighted')]";

	public static final String NON_LIVE_RULE_NAMELINE = "//codes.head[@display-name='Non-Live Rule Nameline']";
	public static final String NON_LIVE_RULE_NAMELINE_HIGHLIGHT = "//codes.head[@display-name='Non-Live Rule Nameline'][contains(@class,'highlighted')]";
	
	public static final String RULE_NAMELINE = "//codes.head[@display-name='Rule Nameline']";
	public static final String RULE_NAMELINE_VALUE_WRAPPER = RULE_NAMELINE + "//label.designator";

	@FindBy(how = How.XPATH, using = RULE_NAMELINE + "//label.designator")
	public static WebElement ruleNamelineValueWrapper;

	// running head
	public static final String RUNNING_HEAD_BLOCK = "//runhead.block[@display-name='Running Head Block']";
	public static final String LEGEND_LINE_EVEN = RUNNING_HEAD_BLOCK + "/legend.even";
	public static final String LEGEND_LINE_ODD = RUNNING_HEAD_BLOCK + "/legend.odd";
	public static final String RUNNING_HEAD_EVEN = RUNNING_HEAD_BLOCK + "/runhead.even";
	public static final String RUNNING_HEAD_ODD = RUNNING_HEAD_BLOCK + "/runhead.odd";
	public static final String RUNNING_HEAD_EVEN_TEXT = RUNNING_HEAD_EVEN + "//runhead.even.text";

	public static final String HISTORICAL_NOTE = "//hist.note";
	public static final String HISTORICAL_NOTE_BODY = "//hist.note.body";
	public static final String HISTORICAL_NOTE_BODY_HIGHLIGHTED = "//hist.note.body[contains(@class,'highlighted')]";
	public static final String HISTORICAL_NOTE_BLOCK = "//hist.note.block";
	public static final String HISTORICAL_NOTE_PARATEXT = HISTORICAL_NOTE_BODY + "//hist.note/para/paratext";
	public static final String HISTORICAL_NOTE_SUBHEADING = HISTORICAL_NOTE_BODY + "/codes.head[@display-name='Historical Note Subheading']";
	public static final String LAW_NOTE_HISTORICAL = HISTORICAL_NOTE_BODY + "/hist.note[@display-name='Law.Note Historical Note']";
	
	public static final String ANNOTATIONS = "//annotations";
	public static final String ANNOTATIONS_SPAN = ANNOTATIONS + "/span";

	public static final String EFFECTIVE_DATE_SPAN = "/effective.date.block/span";

	public static final String DELTA_WITH_PART_CORNERPIECE = "//delta[//part[@type='Cornerpiece']]";
	public static final String DELTA_WITH_TEXT = "//delta[//*[contains(text(), '%s')]]";
	public static final String PART_CORNERPIECE = "//part[@type='Cornerpiece']";
	public static final String PART_CORNERPIECE_SPAN = PART_CORNERPIECE + "/span";
	public static final String PART_CORNERPIECE_TEXT_SPAN = PART_CORNERPIECE + "//cornerpiece.text/span";
	public static final String PART_SECTION_NAMELINE = "//part[@type='Section.Name.Line']";
	public static final String PART_CORNERPIECE_HINT_HEADTEXT = "//part[@type='Section.Name.Line']//headtext";
	public static final String CORNERPIECE_TEXT_WITH_TEXT = "//cornerpiece.text[contains(text(), '%s')]";

	// Content Editorial Window
	public static final String CONTENT_EDITORIAL_SYSTEM_WINDOW_TITLE = "Content Editorial System";
	public static final String MNEMONIC_INPUT_SELECT = "//input[@id='mnemonicInput']";
	public static final String MNEMONIC_HINT_LIST = "//div[@id='mnemonicContainer']//ul";
	public static final String MNEMONIC_HINT_EXPECTED = "//div[@id='mnemonicContainer']//li[text()='%s']";
	public static final String MNEMONIC_HIGHLIGHTED_HINT = "//div[@id='mnemonicContainer']//li[contains(@class, 'highlight') and text()='%s']";
	public static final String TARGET_TEXT_PARAGRAPH_SPAN = "(//part[@type='Text']/codes.text/para/span)[1]";
	public static final String PARA_WHERE_MNEMONIC_HAS_BEEN_CHANGED = TARGET_TEXT_PARAGRAPH_SPAN + "/..";
	public static final String MNEMONIC_DROPDOWN_BUTTON = "//span[@id='mnemonicToggle']//button";
	public static final String TARGET_CORNERPIECE_SPAN = "(//part[@type='Cornerpiece']/cornerpiece/span)[1]";
	public static final String CORNERPIECE_WHERE_MNEMONIC_HAS_BEEN_CHANGED = TARGET_CORNERPIECE_SPAN + "/..";


    // Credit
    public static final String CREDIT_SECTION = "//source.section[@num='%s']//credit.section";
    public static final String TEXT_MERGE_FLAG = "//source.section[@num='%s']//text.merge.flags/text";
    public static final String CREDIT_LABEL = "//subsection/metadata.block/md.credit.info/md.credit";

    // View Volume Tests
    public static final String BODY_TAG = "//body";
    public static final String BOV_TAG_IN_EDITOR_TEXT_BODY = BODY_TAG + "/div/bov";
    public static final String EOV_TAG_IN_EDITOR_TEXT_BODY = BODY_TAG + "/div/eov";

    //Section
    public static final String SECTION_SOURCE_SECTION = SOURCE_SECTION_SPAN + "[contains(text(),'Source Section')]";
	public static final String SECTION_SOURCE_SECTION_BYNUM = "//source.section[%s]/span[contains(text(),'Source Section')]";
	public static final String SECTION_HAS_TREATMENT = "//*[local-name() = 'citator.treatment']";
	public static final String SECTION_HAS_REFERENCE = "//*[local-name() = 'cite.query']";
	public static final String SECTION_BY_NUMBER = "//*[local-name() = 'source.section'][para[span[text() = 'Source Section Number ']][paratext[text() = '%s']]]";

	//XML Regex format for Text Paragraphs
	public static final String TEXT_PARAGRAPH_XML_REGEX_FORMAT = "<para\\sID=\"[A-Za-z0-9]*\"><metadata.block\\sowner=\"[A-Za-z0-9]*\"><md.mnem>[A-Za-z0-9]{0,}</md.mnem>(<md.text.id>)*.*(</md.text.id>)*(<md.pub.tag.info>)*(<md.pub.tag>)*.*(</md.pub.tag>)*(</md.pub.tag.info>)*(<md.source.tag>)*.*(</md.source.tag>)*(<modified.by>)*.*(</modified.by>)*</metadata.block><paratext>.*</paratext></para>";
	public static final String SECTION_TEXT_PARAGRAPH_XML_REGEX_FORMAT = "<section>" + TEXT_PARAGRAPH_XML_REGEX_FORMAT+"</section>";

	// credit phrase block
	public static final String CREDIT_PHRASE_OVERRIDE = "//credit.phrase.block[@display-name='Credit Phrase Override']";
	public static final String CREDIT_PHRASE_OVERRIDE_SPAN = CREDIT_PHRASE_OVERRIDE + "/span[contains(text(),'Credit Phrase Override')]";
	public static final String CREDIT_PHRASE_BEGINNING_PHRASE = CREDIT_PHRASE_OVERRIDE + "/beginning.phrase";
	public static final String CREDIT_PHRASE_FIRST_EFF_DATE_PHRASE = CREDIT_PHRASE_OVERRIDE + "/effective.date.phrase";
	public static final String CREDIT_PHRASE_FIRST_EFF_DATE = CREDIT_PHRASE_OVERRIDE + "/effective.date";
	public static final String CREDIT_PHRASE_SECOND_EFF_DATE_PHRASE = CREDIT_PHRASE_OVERRIDE + "/second.effective.date.phrase";
	public static final String CREDIT_PHRASE_SECOND_EFF_DATE = CREDIT_PHRASE_OVERRIDE + "/second.effective.date";
	public static final String CREDIT_PHRASE_CREDIT_SECTIONS = CREDIT_PHRASE_OVERRIDE + "/credit.section";
	
	public static final String FIRST_ELEMENT_UNDER_SOURCE_SECTION_LABEL = SOURCE_SECTION + "/*[2]"; // [1] is a span of Source Section itself
	
	public static final String SECTION_SIGN = SPAN + "[contains(text(), '\u00A7')]"; //§
	public static final String ID = "//*[@id= '%s']";

	public static final String MODIFIED_BY_TEXT_UNDER_NUMBER = "(//span[contains(text(),'Text Paragraph')])[%d]/..//metadata.block/modified.by";

	public static final String PUBTAGS_WITH_TEXT = PARA + "[@display-name='Text Paragraph']//metadata.block/md.pub.tag.info/md.pub.tag[text()='%s']";
	public static final String TEXT_PARAGRAPH_LABEL_UNDER_NUMBER = "(" + TEXT_PARAGRAPH_LABEL + ")" + "[%d]";
	public static final String TEXT_PARAGRAPH_SOURCE_TAG_UNDER_NUMBER = "(//para[@display-name='Text Paragraph']//metadata.block/md.source.tag)[%d]";

	public static final String FOOTNOTE_PARATEXT_WITH_SUPERSCRIPT_AND_HINT = "/paratext[super[text()='%s'] and hint[text()='Insert footnote text here']]";

	//Footnote references in text
	public static final String FOOTNOTE_MNEMONIC = "//footnote//md.mnem[text()='%s']";
	public static final String FOOTNOTE_REFERENCE = "//footnote.reference";
	public static final String SUPER = "//super";
	public static final String FOOTNOTE_REFERENCE_IN_TEXT = PARATEXT + FOOTNOTE_REFERENCE + "//super[text()='%s']";
	public static final String FOOTNOTE_REFERENCE_IN_TEXT_UNDER_NUMBER = "(//paratext//footnote.reference//super)[%s]";
	public static final String FOOTNOTE_REFERENCE_SUPER = FOOTNOTE_REFERENCE + SUPER;
	public static final String FOOTNOTE_REFERENCE_MARKUP = FOOTNOTE_REFERENCE_SUPER + MARKUP_IMAGE_LABEL;
	public static final String IMPORTED_MODIFIED_BY_MNEMONIC_WITH_TEXT = MODIFIED_BY_MNEMONIC +"[text()='Imported by %s']";

	//Footnotes
	public static final String FOOTNOTE_PARAGRAPH_BOTTOM_OF_TEXT =
			"//para[@display-name='Footnote Paragraph Bottom of Text']";
	public static final String FOOTNOTE_UNDER_CREDIT = "//credit/following-sibling::footnote";
	public static final String FOOTNOTE_PARAGRAPH_WITH_GIVEN_REFERENCE_AND_HINT = FOOTNOTE_PARAGRAPH_BOTTOM_OF_TEXT
					+ FOOTNOTE_PARATEXT_WITH_SUPERSCRIPT_AND_HINT;
	public static final String FOOTNOTE_TEXT = "(" + FOOTNOTE_PARAGRAPH_BOTTOM_OF_TEXT + "/paratext/super)[%s]";
	public static final String FOOTNOTE_WITH_GIVEN_REFERENCE = "//footnote[.//super[text()='%s']]";
	public static final String FOOTNOTE_BY_ID_HINT = "//footnote[@id= '%s']//hint";
	public static final String FOOTNOTE_BY_ID_NOT_HIGHLIGHTED =
			"//footnote[@id= '%s' and not(contains(@class, 'highlighted'))]";
	public static final String HIGHLIGHTED_FOOTNOTE_WITH_GIVEN_REFERENCE_AND_HINT_UNDER_CREDIT =
			"//credit/following-sibling::footnote" + CLASS_HIGHLIGHTED_POSTFIX
					+ "/para[span[text()='Footnote Paragraph Bottom of Text ']]"
					+ FOOTNOTE_PARATEXT_WITH_SUPERSCRIPT_AND_HINT;
	public static final String LOADED_CHUNK_NUMBER = "//div[@id='loaded_chunk_%d']/*";
	public static final String FOOTNOTE = "//footnote";
	public static final String END_OF_DOCUMENT = "//SPAN[contains(text(),'END OF DOCUMENT')]";

	@FindBy(how = How.TAG_NAME, using = "cornerpiece.text")
	public static WebElement cornerpieceText;

	@FindBy(how = How.TAG_NAME, using = "placeholder")
	public static WebElement placeholder;

	@FindBy(how = How.XPATH, using = "//para[@full-display-name='Text Paragraph']/span[contains(text(), 'Text Paragraph')][1]")
	public static WebElement firstTextParagraph;

	@FindBy(how = How.XPATH, using = "//para[@display-name='Text Paragraph']//metadata.block/md.pub.tag.info/md.pub.tag")
	public static WebElement pubtagsInFirstTextParaMetadataBlock;

	@FindBy(how = How.XPATH, using = "//eov//placeholder//metadata.block/md.pub.tag.info/md.pub.tag")
	public static WebElement pubtagsInIndexReferenceLineBlock;

	@FindBy(how = How.XPATH, using = "//para[@display-name='Text Paragraph']//metadata.block/md.pub.tag.info/md.pub.tag")
	public static WebElement pubtagsText;

	// HVS INFORMATION
	@FindBy(how = How.XPATH, using = "//metadata.block//md.serial.no")
	public static WebElement hvsSerialNumber;

	@FindBy(how = How.XPATH, using = "//metadata.block//md.reporter.volume")
	public static WebElement hvsReporterVolume;

	@FindBy(how = How.XPATH, using = "//metadata.block//md.reporter.number")
	public static WebElement hvsReporterNumber;

	@FindBy(how = How.XPATH, using = "//metadata.block//md.reporter.page")
	public static WebElement hvsReporterPage;

	@FindBy(how = How.XPATH, using = "//metadata.block//md.headnote.no")
	public static WebElement hvsHeadnoteNumber;

	@FindBy(how = How.XPATH, using = "//footnote[contains(@class,'highlighted')]//paratext//hint")
	public static WebElement highlightedFootnotesHint;

	@FindBy(how = How.XPATH, using = "//metadata.block//md.neutral")
	public static WebElement hvsNeutralCitation;

	@FindBy(how = How.XPATH, using = "//metadata.block//md.ccdb")
	public static WebElement hvsCcbd;

	@FindBy(how = How.XPATH, using = "//metadata.block//md.nod.type")
	public static WebElement hvsNodeType;

	//OTHER TEXT
	@FindBy(how = How.XPATH, using = "//subsection/span[contains(text(),'Subsection')]")
	public static WebElement subsectionSpan;

	@FindBy(how = How.XPATH, using = "//para/paratext")
	public static WebElement paraText;

	public enum mnemonics
	{
		ANGEN,
		CE,
		CLPP,
		CP,
		CPL1,
		CPM,
		CPR1,
		CRL,
		FP,
		GNP,
		GNPC,
		GNPQ,
		HG3,
		HG3C,
		HG9,
		HG11,
		HGNL1,
		LEGL,
		LEGR,
		PRMS,
		RHL,
		RHR,
		RNL,
		RRNL,
		SL,
		SMP,
		SMPF,
		SNL,
		SRNL,
		TODD,
		TOP,
		TOPT,
		WISC,
		XAAR,
		XACR,
		XCOM,
		XCR,
		XLR,
		XMISR,
		XUL,
		XUSA,
		XUSS,
		SCP3;

		public String xpath()
		{
			return String.format(xpath, this.name() );
		}

		private final String xpath = "//metadata.block/md.mnem[text()='%s']";
	}

	public static final String FOOTNOTE_HINT = "//footnote//paratext//hint";

	public static final String LEGISLATIVE_HISTORY_FOOTNOTE = "//source.end/para[@display-name='Legislative History Footnote']";

	public static final String MODIFIED_BY_TAGS_IN_TEXT_PARAGRAPHS = "//para[@display-name='Text Paragraph']//modified.by";

	public static final String REFERENCE_LABEL = "//reference.text";
	public static final String REFERENCE_TEXT_WITH_THE_PHRASE_GIVEN = REFERENCE_LABEL + "[contains(text(), '%s')]";
	public static final String REFERENCE_DOC_NUMBER = "//md.reference.doc.text[contains(text(), '%s')]";
	public static final String REFERENCE_MODIFIED_BY = "//modified.by[contains(text(), '%s')]";
	public static final String TARGET_LOCATION_SUBSECTION = "//target.location.subsection";

	public static final String SCP_STOCKNOTE_VIA_CONTEXTMENU = "//source.section[@display-name='Source Section']//md.mnem[contains(text(), 'SCP3')]//ancestor::para";
	public static final String STOCKNOTE_NAME_IN_EDITOR = "//cornerpiece//md.mnem[contains(text(), '%s')]//ancestor::cornerpiece";

	public static final String DELTA_IN_EDITOR = "//source.section/delta";
	public static final String SECTION_IN_EDITOR = "//source.section";
	public static final String SUBSECTION_PARA_TEXT_BY_SECTION_AND_SUBSECTION_NUM = SECTION_IN_EDITOR + "[%d]//part[@type='Text']/subsection[%d]/para/paratext";
	public static final String LOG_INFO_XPATH = "//div[@id='outerBody']//span[@class='log_info']";
	public static final String EMBEDDED_HTML = "/embedded.html";
	public static final String FOLLOWING_SIBLING = "/following-sibling::%s";
	public static final String PRECEDING_SIBLING = "/preceding-sibling::%s";
	public static final String ANCESTOR = "/ancestor::%s";
	public static final String PARENT = "/parent::%s";

	public static final String INDEX_REFERENCE_LINE = "//*[@display-name='Index Reference Line']";
}
