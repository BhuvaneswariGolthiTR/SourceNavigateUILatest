package com.thomsonreuters.codes.codesbench.quality.pageelements.editor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditorHomePageElements
{
	public static final String PAGE_TITLE = "Editor Home Page";

	// target part
	@FindBy(how = How.ID, using = "targetForm:nodeUuid")
	public static WebElement targetNodeUUidTextbox;
	
	@FindBy(how = How.ID, using = "targetChunk")
	public static WebElement targetNodeChunkCheckbox;
	
	@FindBy(how = How.ID, using = "editNodeButton")
	public static WebElement targetNodeEditButton;
	
	@FindBy(how = How.ID, using = "viewNodeButton")
	public static WebElement targetNodeViewButton;
	
	// source c2012 part
	@FindBy(how = How.ID, using = "sourceC2012Form:sourceC2012Uuid")
	public static WebElement sourceRenditionUuidTextbox;
	
	@FindBy(how = How.ID, using = "sourceC2012Chunk")
	public static WebElement sourceRenditionChunkCheckbox;
	
	@FindBy(how = How.ID, using = "editSourceC2012Button")
	public static WebElement sourceRenditionEditButton;
	
}
