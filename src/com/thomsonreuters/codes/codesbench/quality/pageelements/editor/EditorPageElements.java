package com.thomsonreuters.codes.codesbench.quality.pageelements.editor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditorPageElements
{
	public static final String PAGE_TITLE = "Editor";
	
	public static final String COMMAND_IN_PROGRESS =
			"//DIV[@id='progressWindow']//TR/TD[text()='Command in progress...']";
	public static final String BLUE_BAR_BY_WIP_VERSION_NUMBER = "//div[contains(text(),'Wip Version # %s')]";

	@FindBy(how = How.XPATH, using = "//para/span")
	public static WebElement firstTextParagraph;

	public static final String LOADED_CHUNK = "//div[@id='loaded_chunk_0']";
	public static final String BLUE_BAR = "//div[@class='clWinHead']";
	public static final String EDITOR_LIST = "//div[@id='editorList']/span/a";
	public static final String EDITOR_LIST_ID = "editorList";

	// Chunk JavaScript
	public static final String SCROLL_TO_CHUNK_JS = "var iframe = document.getElementsByTagName('iframe');" + 
            "iframe[1].contentWindow.document.getElementById('loaded_chunk_%s').scrollIntoView();" +
            "iframe[1].contentWindow.document.getElementById('loaded_chunk_%s').focus();";
	public static final String SCROLL_DOWN = "window.scrollBy(0,%d);";
	
    // Editor window
	public static final String EDITOR_IFRAME = "//DIV[contains(@id, 'outerBody')]//DIV[contains(@id, 'divWindow')]//DIV[contains(@id, 'editor')]//IFRAME";

	public enum deltaFeature
	{
		ED_NOTE("Ed.Note"),
		OFFICIAL_NOTE("Official.Note"),
		ADMIN_CODE_REFERENCE("Admin.Code.Reference"),
		CROSS_REFERENCE("Cross.Reference"),
		LIBRARY_REFERENCE("Library.Reference"),
		RESEARCH_REFERENCE("Research.Reference"),
		US_SUP_CT_REFERENCE("Us.Sup.Ct.Reference"),
		UNIFORM_LAW_REFERENCE("Uniform.Law.Reference"),
		;

		deltaFeature(String featureName)
		{
			this.featureName = featureName;
		}
		public String getFeatureName()
		{
			return this.featureName;
		}

		public String getXpath()
		{
			return String.format(XPATH_PATTERN, getFeatureName());
		}

		private String featureName;
		private final String XPATH_PATTERN = "//div[@treeNodeName='delta.feature \"%s\"']";
	}

}
