package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LineageContextMenuElements
{
	public static final String contextMenu = "//div[@id='contextMenu']";
	public static final String contextMenuView = "//div[@id='contextMenu']//div[@id='view']";
	
	public static final String VIEW_RENDITION_XML = "//div/span[contains(@id,'view') and text()='Rendition XML']";
	public static final String VIEW_XML_EXTRACT = "//div/span[contains(@id,'view') and text()='XML Extract']";
	public static final String RAW_XML_EDITOR = "Raw XML Editor";
	public static final String XML_EXTRACT_TITLE = "Xml Extract";
	public static final String LINEAGE_NAVIGATE_TITLE = "Lineage Navigate";
	public static final String VIEW = contextMenu + "//a[text()='View']";
	public static final String HEADER_IMAGE = "//img[@class='headerImage']";
	
	
	@FindBy(how = How.XPATH, using = "//div/span[contains(@id,'view') and text()='Rendition(s)']")
	public static WebElement viewRenditions;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Edit']")
	public static WebElement edit;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='View']")
	public static WebElement view;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Modify']")
	public static WebElement modify;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Track']")
	public static WebElement track;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Report']")
	public static WebElement report;

	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Validate']")
	public static WebElement validate;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Sync']")
	public static WebElement sync;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Rendition Properties']")
	public static WebElement renditionProperties;
	
	@FindBy(how = How.XPATH, using = "//div/span[contains(@id,'view5Text')]")
	public static WebElement sidebarMultipleAndDuplicate;
	
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Rendition' or text()='Renditions']")
	public static WebElement viewRendition;

	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Rendition Notes']")
	public static WebElement viewRenditionNotes;
	
	@FindBy(how = How.XPATH, using = "//img[@class='headerImage']")
	public static WebElement sideBarHeaderImage;
	
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Source Front']")
	public static WebElement viewSourceFront;
	
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Rendition XML']")
	public static WebElement viewRenditionXml;
	
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Source End']")
	public static WebElement viewSourceEnd;
		
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Deltas Affecting Same Target']")
	public static WebElement viewDeltasAffectingSameTarget;
	
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='XML Extract']")
	public static WebElement viewXmlExtract;
	
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Multiple and Duplicate Renditions']")
	public static WebElement viewMultipleAndDuplicateRenditions;
	
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Rendition Print Preview']")
	public static WebElement viewRenditionPrintPreview;
	
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Tabular Mainframe Print Preview']")
	public static WebElement viewTabularMainframePrintPreview;
	
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Tabular Print Preview']")
	public static WebElement viewTabularPrintPreview;
	
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Tabular WL Preview']")
	public static WebElement viewTabularWlPreview;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Classify in CHC']")
	public static WebElement classifyInChc;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Send to Vendor for Capture']")
	public static WebElement sendToVendorForCapture;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Add Content']")
	public static WebElement viewAddContent;
	
	@FindBy(how = How.XPATH, using = "//div[@id='contextMenu']//a[text()='Create Preparation Document']")
	public static WebElement createPreparationDocument;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Transfer']")
	public static WebElement transfer;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Create Bookmark']")
	public static WebElement createBookmark;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Create Target Document']")
	public static WebElement createTargetDocument;
	
	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Move Task']")
	public static WebElement moveTask;

	@FindBy(how = How.XPATH, using = contextMenu + "//a[text()='Add Content']")
	public static WebElement addContent;
	
	@FindBy(how = How.XPATH, using = contextMenuView + "//a[text()='Rendition Baselines']")
	public static WebElement viewRenditionBaselines;
	
	
	

	
}
