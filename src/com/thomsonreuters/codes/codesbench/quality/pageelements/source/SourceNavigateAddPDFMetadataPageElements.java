package com.thomsonreuters.codes.codesbench.quality.pageelements.source;

public class SourceNavigateAddPDFMetadataPageElements
{
	public static final String ADD_PDF_METADATA_PAGE_TITLE = "Add Shell";
	public static final String CONTENT_SET_DROPDOWN = "//select[@id='pageForm:contentSet']";
	public static final String DOCUMENT_TYPE_DROPDOWN = "//select[@id='pageForm:documentType']";
	public static final String ORGANIZATION_TEXT_BOX = "//input[@id='CombifyInput-mySelect']";
	public static final String DOCUMENT_NUMBER_TEXT_BOX = "//input[@id='pageForm:documentNumber']";
	public static final String CONTENT_TYPE_DROPDOWN = "//select[@id='pageForm:contentType']";
	public static final String YEAR_TEXT_BOX = "//input[@id='pageForm:year']";
	public static final String DOCUMENT_TITLE_TEXT_BOX = "//input[@id='pageForm:title']";
	public static final String SOURCE_PUBLICATION_DATE_TEXT_BOX = "//input[@id='pageForm:sourcePublicationDate']";
	public static final String DOCUMENT_DATE_TEXT_BOX = "//input[@id='pageForm:documentDate']";
	public static final String ADOPTED_DATE_TEXT_BOX = "//input[@id='pageForm:adoptedDate']";
	public static final String FILING_DATE_TEXT_BOX = "//input[@id='pageForm:filingDate']";
	public static final String APPROVAL_DATE_TEXT_BOX = "//input[@id='pageForm:approvalDate']";
	public static final String EXPIRATION_DATE_TEXT_BOX = "//input[@id='pageForm:expirationDate']";
	public static final String SECTORS_INSURANCE_CHECK_BOX = "//input[@id='pageForm:sectorsID:0']";
	public static final String SECTORS_HEALTHCARE_CHECK_BOX = "//input[@id='pageForm:sectorsID:1']";
	public static final String SECTORS_MORTGAGE_BANKING_CHECK_BOX = "//input[@id='pageForm:sectorsID:2']";
	public static final String SECTORS_CONSUMER_BANKING_CHECK_BOX = "//input[@id='pageForm:sectorsID:3']";
	public static final String SECTORS_BANKING_CHECK_BOX = "//input[@id='pageForm:sectorsID:4']";
	public static final String SECTORS_BLUE_SKY_CHECK_BOX = "//input[@id='pageForm:sectorsID:5']";
	public static final String SECTORS_SECURITIES_CHECK_BOX = "//input[@id='pageForm:sectorsID:6']";
	public static final String SELECTED_FILE_TEXT_BOX = "//input[@id='pageForm:selectedFile']";
	public static final String REMOTE_FILE_URL_TEXT_BOX = "//input[@id='pageForm:url']";
	public static final String SKIP_BPMS_NOTIFICATION_CHECK_BOX = "//input[@id='pageForm:skipBPMS']";
	public static final String DO_NOT_DOWNLOAD_CHECK_BOX = "//input[@id='pageForm:noDownload']";
	public static final String INITIATE_VENDOR_KEYING_CHECK_BOX = "//input[@id='pageForm:keyingUpload']";
		
	public static final String FILE_NUMBER_TEXT_BOX = "//input[@id='pageForm:dtFileNumbers:0:inputText']";
	public static final String FILE_NUMBER_ADD_BUTTON = "//input[contains(@id,'pageForm:dtFileNumbers') and contains(@value,'Add')]";
	public static final String ALTERNATE_DOCUMENT_NUMBER_TEXT_BOX = "//input[@id='pageForm:dtAltDoc:0:inputText']";
	public static final String ALTERNATE_DOCUMENT_NUMBER_ADD_BUTTON = "//input[contains(@id,'pageForm:dtAltDoc') and contains(@value,'Add')]";
	public static final String CITES_AFFECTED_TEXT_BOX = "//input[@id='pageForm:dtCites:0:inputText']";
	public static final String CITES_AFFECTED_ADD_BUTTON = "//input[contains(@id,'pageForm:dtCites') and contains(@value,'Add')]";
	public static final String DELETE_FILE_BUTTON = "//input[contains(@id, 'pageForm:dtFileNumbers:1') and contains(@value,'Delete')]";
	public static final String SUBMIT_BUTTON = "//input[@id='pageForm:addContentButton']";
	public static final String EDIT_FILENAME_MESSAGE = "//span[@id='pageForm:filenameMessageEdit']";
	public static final String ADD_FILENAME_MESSAGE = "//span[@id='pageForm:filenameMessage']";
	public static final String FORMAT_NOT_FOUND_ERROR_MESSAGE = "//SPAN[@class='errorMessage' and text()='Format not found for null']";
	
	//These objects only exist in the edit pdf window
	
    public static final String JURUSDICTION_XPATH = "//option[@value='Alaska']";
    public static final String CONTENT_TYPE_XPATH = "//option[@value='BILL']";
    public static final String YEAR_XPATH = "//*[@name='pageForm:year']";
    public static final String DOCUMENT_TYPE_XPATH = "//option[@value='H']";
    public static final String SESSION_XPATH = "//option[@value='1RG']";
    public static final String DOCUMENT_NUMBER_XPATH = "//input[@name='pageForm:documentNumber']";
    public static final String CHAMBER_XPATH = "//option[@value='HOUSE']";
    public static final String LEGISLATION_TYPE_XPATH = "//select[@name='pageForm:legislationType']";
    public static final String TITLE_XPATH = "//input[@name='pageForm:title']";
    public static final String SPONSORS_XPATH = "//input[@name='pageForm:sponsors']";
    public static final String SUMMARY_XPATH = "//input[@name='pageForm:summary']";
    public static final String ADD_CONTENT_BUTTON_XPATH = "//input[@name='pageForm:addContentButton']";

    //Manual data stuff
    public static final String MANUAL_BILL_TRACK_DATA_ENTRY_CHECKBOX_XPATH = "//input[@id='pageForm:manualBillTrackDataEntryCheckBox']";
    public static final String MANUAL_DATE_START_XPATH = "//td/input[@name='pageForm:billTrackAction";
    public static final String MANUAL_DATE_END_XPATH = "Date']";
    public static final String MANUAL_ACTION_START_XPATH = "//input[@name='pageForm:billTrackAction";
    public static final String MANUAL_ACTION_END_XPATH = "']";
    public static final String MANUAL_STAGE_START_XPATH = "//select[@name='pageForm:billTrackStage";
    public static final String MANUAL_STAGE_END_XPATH = "']";
}
