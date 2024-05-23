package com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PublishingMenuElements extends CommonMenuElements
{
	private final static String GENERIC_PUBLISHING_MENU_XPATH = "//div[@id='baseNavBarpublishing']";
	private final static String GENERIC_VIEW_EDIT_DATA_XPATH = "//a[text()='View/Edit Data']";
	private final static String GENERIC_VIEW_DELETED_ROWS_XPATH = "//a[text()='View Deleted Rows']";
	private final static String GENERIC_RECOMP_XPATH = "//a[text()='Recomp']";
	private final static String GENERIC_ANP_PP_MANAGEMENT_XPATH = "//a[text()='AnP/PP Management']";
	private final static String GENERIC_CUSTOM_XPATH = "//a[text()='Custom']";
	private final static String GENERIC_TOC_XPATH = "//a[text()='TOC']";
	private final static String GENERIC_WIP_EXTRACTS_XPATH = "//div[@id='c2012_printc2012_WIPExtracts']";
	private final static String GENERIC_PUB_EXTRACTS_XPATH = "//div[@id='c2012_printc2012_PUBExtracts']";
	private final static String GENERIC_NOVUS_XPATH = "//div[@id='publishingc2012_novus']";
	private final static String GENERIC_REPORTS_XPATH = "//div[@id='publishingc2012_publishing_reports']";
	private final static String GENERIC_RANKING_PREFERENCES_XPATH = "//div[@id='c2012_novusc2012rankingPrefs']";
	private final static String GENERIC_XFEATURE_PREFERENCES_XPATH = "//div[@id='c2012_novusc2012xfeaturePrefs']";
	private final static String GENERIC_COPYRIGHT_MESSAGE_CODES = "//div[@id='c2012_novusc2012copyrightMsgCodes']";
	private final static String GENERIC_CURRENCY_MESSAGE_CODES = "//div[@id='c2012_novusc2012currencyMsgCodes']";
	private final static String GENERIC_DELETE_VOLUMES_XPATH = "//a[text()='Delete Volumes']";
	private final static String GENERIC_ADD_NEW_VOLUMES_XPATH = "//a[text()='Add New Volumes']";
	private final static String GENERIC_MESSAGE_CODES_XPATH = "//div[@id='c2012_novusc2012msgCodeVolumes']";
	private final static String GENERIC_PRINT_XPATH = GENERIC_PUBLISHING_MENU_XPATH + "//div[@id='publishingc2012_print']";
	public static final  String NOVUS_CITLINE_MANAGAMENT = GENERIC_NOVUS_XPATH + "//a[text()='Citeline Management']";

	public static final String PUBLISHING_MENU_XPATH = "//ul[@class='first-of-type']//a[text()='Publishing']";

	@FindBy(how = How.XPATH, using = PUBLISHING_MENU_XPATH)
	public static WebElement publishingMenu;

	@FindBy(how = How.XPATH, using = GENERIC_PUBLISHING_MENU_XPATH + "//a[text()='Print']")
	public static WebElement print;
	
	@FindBy(how = How.XPATH, using = GENERIC_PUBLISHING_MENU_XPATH + "//a[text()='Novus']")
	public static WebElement novus;
	
	@FindBy(how = How.XPATH, using = GENERIC_PUBLISHING_MENU_XPATH + "//a[text()='Reports']")
	public static WebElement reports;
	
	@FindBy(how = How.XPATH, using = GENERIC_PUBLISHING_MENU_XPATH + "//a[text()='Publishing']")
	public static WebElement publishing;

	@FindBy(how = How.XPATH, using = GENERIC_PUBLISHING_MENU_XPATH + "//a[text()='Publishing Status Reports']")
	public static WebElement publishingStatusReports;
	
	@FindBy(how = How.XPATH, using = GENERIC_PRINT_XPATH + "//a[text()='PUB Extracts']")
	public static WebElement printPUBExtracts;
	
	@FindBy(how = How.XPATH, using = GENERIC_PRINT_XPATH + "//a[text()='WIP Extracts']")
	public static WebElement printWIPExtracts;
	
	@FindBy(how = How.XPATH, using = GENERIC_WIP_EXTRACTS_XPATH + GENERIC_CUSTOM_XPATH)
	public static WebElement printWIPExtractsCustom;
	
	@FindBy(how = How.XPATH, using = GENERIC_WIP_EXTRACTS_XPATH + GENERIC_RECOMP_XPATH)
	public static WebElement printWIPExtractsRecomp;
	
	@FindBy(how = How.XPATH, using = GENERIC_WIP_EXTRACTS_XPATH + GENERIC_TOC_XPATH)
	public static WebElement printWIPExtractsTOC;
	
	@FindBy(how = How.XPATH, using = GENERIC_WIP_EXTRACTS_XPATH + GENERIC_ANP_PP_MANAGEMENT_XPATH)
	public static WebElement printWIPExtractsAnpPPManagement;
	
	@FindBy(how = How.XPATH, using = GENERIC_PUB_EXTRACTS_XPATH + GENERIC_CUSTOM_XPATH)
	public static WebElement printPUBExtractsCustom;
	
	@FindBy(how = How.XPATH, using = GENERIC_PUB_EXTRACTS_XPATH + GENERIC_RECOMP_XPATH)
	public static WebElement printPUBExtractsRecomp;
	
	@FindBy(how = How.XPATH, using = GENERIC_PUB_EXTRACTS_XPATH + GENERIC_TOC_XPATH)
	public static WebElement printPUBExtractsTOC;
	
	@FindBy(how = How.XPATH, using = GENERIC_PUB_EXTRACTS_XPATH + GENERIC_ANP_PP_MANAGEMENT_XPATH)
	public static WebElement printPUBExtractsAnpPPManagement;

	public final static String DLU_UPLOAD_XPATH = GENERIC_PRINT_XPATH + "//a[text()='DLU Upload']";

	@FindBy(how = How.XPATH, using = DLU_UPLOAD_XPATH)
	public static WebElement printDLUUpload;

	@FindBy(how = How.XPATH, using = GENERIC_PRINT_XPATH + "//a[text()='Run Pubtag Refresh by VOLS']")
	public static WebElement printRunPUBTagRefreshByVols;

	@FindBy(how = How.XPATH, using = GENERIC_PRINT_XPATH + "//a[text()='View Scripts for Content Set']")
	public static WebElement printViewScriptsForContentSet;

	@FindBy(how = How.XPATH, using = GENERIC_NOVUS_XPATH + "//a[text()='Ranking Preferences']")
	public static WebElement novusRankingPreferences;

	@FindBy(how = How.XPATH, using = GENERIC_NOVUS_XPATH + "//a[text()='XFeature Preferences']")
	public static WebElement novusXFeaturePreferences;

	@FindBy(how = How.XPATH, using = GENERIC_NOVUS_XPATH + "//a[text()='Copyright Message Codes']")
	public static WebElement novusCopyrightMessageCodes;

	@FindBy(how = How.XPATH, using = GENERIC_NOVUS_XPATH + "//a[text()='Currency Message Codes']")
	public static WebElement novusCurrencyMessageCodes;

	@FindBy(how = How.XPATH, using = GENERIC_NOVUS_XPATH + "//a[text()='Message Code Volumes']")
	public static WebElement novusMessageCodeVolumes;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOVUS_XPATH + GENERIC_MESSAGE_CODES_XPATH + GENERIC_DELETE_VOLUMES_XPATH)
	public static WebElement novusMessageCodeVolumesDeleteVolumes;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOVUS_XPATH + GENERIC_MESSAGE_CODES_XPATH + GENERIC_ADD_NEW_VOLUMES_XPATH)
	public static WebElement novusMessageCodeVolumesAddNewVolumes;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOVUS_XPATH + "//a[text()='Feature Source Heading Message Code']")
	public static WebElement novusFeatureSourceHeadingMessageCode;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOVUS_XPATH + GENERIC_CURRENCY_MESSAGE_CODES + GENERIC_VIEW_DELETED_ROWS_XPATH)
	public static WebElement novusCurrencyMessageCodesViewDeletedRows;
	
	@FindBy(how = How.XPATH, using = GENERIC_COPYRIGHT_MESSAGE_CODES + GENERIC_VIEW_DELETED_ROWS_XPATH)
	public static WebElement novusCopyrightMessageCodesViewDeletedRows;
	
	@FindBy(how = How.XPATH, using = GENERIC_XFEATURE_PREFERENCES_XPATH + GENERIC_VIEW_DELETED_ROWS_XPATH)
	public static WebElement novusXFeaturePreferencesViewDeletedRows;
	
	@FindBy(how = How.XPATH, using = GENERIC_RANKING_PREFERENCES_XPATH + GENERIC_VIEW_DELETED_ROWS_XPATH)
	public static WebElement novusRankingPreferencesViewDeletedRows;
	
	@FindBy(how = How.XPATH, using = GENERIC_CURRENCY_MESSAGE_CODES + GENERIC_VIEW_EDIT_DATA_XPATH)
	public static WebElement novusCurrencyMessageCodesViewEditData;
	
	@FindBy(how = How.XPATH, using = GENERIC_COPYRIGHT_MESSAGE_CODES + GENERIC_VIEW_EDIT_DATA_XPATH)
	public static WebElement novusCopyrightMessageCodesViewEditData;
	
	@FindBy(how = How.XPATH, using = GENERIC_XFEATURE_PREFERENCES_XPATH + GENERIC_VIEW_EDIT_DATA_XPATH)
	public static WebElement novusXFeaturePreferencesViewEditData;
	
	@FindBy(how = How.XPATH, using = GENERIC_RANKING_PREFERENCES_XPATH + GENERIC_VIEW_EDIT_DATA_XPATH)
	public static WebElement novusRankingPreferencesViewEditData;
	
	@FindBy(how = How.XPATH, using = GENERIC_REPORTS_XPATH + "//a[text()='CT1 Report']")
	public static WebElement reportsCT1Report;

	@FindBy(how = How.XPATH, using = "//div[@id='publishingc2012_publishing_reports']//a[text()='PubId Report']")
	public static WebElement reportsPubIdReport;

	//Publishing > Publishing Selection
	private static final String PUBLISHING_PUBLISHING_SELECTION_XPATH = "//div[@id='publishingpublishingSelection']//li";

	@FindBy(how = How.XPATH, using = PUBLISHING_PUBLISHING_SELECTION_XPATH + "/a[text()='Publish Ready-Text nodes only']")
	public static WebElement publishReadyTextNodesOnly;

	@FindBy(how = How.XPATH, using = PUBLISHING_PUBLISHING_SELECTION_XPATH + "/a[text()='2-Step Publishing - Publish Approve']")
	public static WebElement publish2StepPublishingPublishApproveSelection;

	@FindBy(how = How.XPATH, using = PUBLISHING_PUBLISHING_SELECTION_XPATH + "/a[text()='Publish Approve-Text nodes only']")
	public static WebElement publishApproveTextNodesOnly;

	@FindBy(how = How.XPATH, using = PUBLISHING_PUBLISHING_SELECTION_XPATH + "/a[text()='Publish Approve-Text and NOD nodes by Volume']")
	public static WebElement publishApproveTextAndNodNodesByVolume;

	@FindBy(how = How.XPATH, using = PUBLISHING_PUBLISHING_SELECTION_XPATH + "/a[text()='Publish Approve-NOD nodes only']")
	public static WebElement publishApproveNodNodesOnly;

	//Publishing > Publishing Status Reports
	private static final String PUBLISHING_STATUS_REPORTS_XPATH = "//div[@id='publishingpublishingTroubleshooting']//li";

	@FindBy(how = How.XPATH, using = PUBLISHING_STATUS_REPORTS_XPATH + "/a[text()='Pub Navigate Evaluation']")
	public static WebElement publishingStatusReportsPubNavigateEvaluation;

	@FindBy(how = How.XPATH, using = PUBLISHING_STATUS_REPORTS_XPATH + "/a[text()='WIP to PUB Upload Issues']")
	public static WebElement publishingStatusReportsWipToPubUploadIssues;

	@FindBy(how = How.XPATH, using = PUBLISHING_STATUS_REPORTS_XPATH + "/a[text()='ERROR Statuses']")
	public static WebElement publishingStatusReportsErrorStatuses;

	@FindBy(how = How.XPATH, using = PUBLISHING_STATUS_REPORTS_XPATH + "/a[text()='------------------------']")
	public static WebElement publishingStatusReportsBreakpoint;

	@FindBy(how = How.XPATH, using = PUBLISHING_STATUS_REPORTS_XPATH + "/a[text()='NOD-Only Pub Navigate Evaluation']")
	public static WebElement publishingStatusReportsNodOnlyPubNavigateEvaluation;

	@FindBy(how = How.XPATH, using = PUBLISHING_STATUS_REPORTS_XPATH + "/a[text()='NOD-Only WIP to PUB Upload Issues']")
	public static WebElement publishingStatusReportsNodOnlyWipToPubUploadIssues;

	@FindBy(how = How.XPATH, using = PUBLISHING_STATUS_REPORTS_XPATH + "/a[text()='NOD-Only ERROR Statuses']")
	public static WebElement publishingStatusReportsNodOnlyErrorStatuses;

	@FindBy(how = How.XPATH, using = PUBLISHING_STATUS_REPORTS_XPATH + "/a[text()='Westlaw Load Complete']")
	public static WebElement publishingStatusReportsWestlawLoadComplete;

	@FindBy(how = How.XPATH, using = "(//div[@id='publishingpublishingTroubleshooting']//li/a[text()='------------------------'])[2]")
	public static WebElement publishingStatusReportsSecondBreakpoint;
}
