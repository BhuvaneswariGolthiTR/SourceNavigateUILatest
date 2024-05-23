package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

public class SourceNavigateAngularContextMenuItemsPageElements
{
    public static final String CMI_ANCESTOR_DIV = "/ancestor::div[contains(@class,'ag-menu-option')]";

    public static final String INTEGRATION_RESULT = "//span[text()='Integration Results']";

    public static String CONTEXT_MENU_OPTION = "//span[text()='%s']";

    // ---------- RENDITION/LINEAGE TAB CONTEXT MENU ITEMS ----------
    // *** Edit ***
    public static final String EDIT = "//span[text()='Edit']";
    public static final String EDIT_INDEX_ENTRY_FEATURES = "//span[text()='Index Entry Features']";
    public static final String RENDITION_NOTES = "//span[text()='Rendition Notes']";
    public static final String SECTION_NOTES = "//span[text()='Section Notes']";
    public static final String DELTA_NOTES = "//span[text()='Delta Notes']";
    public static final String TAX_TYPE_ADD = "(//div[@class='ag-menu ag-ltr ag-popup-child']//span[text()='Tax Type Add'])";


    // *** View ***
    public static final String VIEW = "//span[text()='View']";
    public static final String VIEW_MULTIPLE_AND_DUPLICATE_RENDITIONS = "//span[text()='Multiple and Duplicate Renditions']";

    // *** Modify ***
    public static final String MODIFY = "//span[text()='Modify']";
    public static final String INTEGRATE ="(//div[@class='ag-menu ag-ltr ag-popup-child']//span[text()='Integrate'])";
    public static final String MARK_AS_NON_DUPLICATE ="(//div[@class='ag-menu ag-ltr ag-popup-child']//span[text()='Mark as Non-Duplicate'])";
    // *** Rendition Properties ***
    public static final String RENDITION_PROPERTIES = "//span[text()='Rendition Properties']";

    public static final String RENDITION_BASELINE = "//span[text()='Rendition Baselines']";

    // *** Rendition ***
    public static final String RENDITION = "//span[text()='Rendition']";

    // *** Create Preparation Document ***
    public static final String CREATE_PREPARATION_DOCUMENT = "//span[text()='Create Preparation Document']";

    // *** Create Preparation Document/Auto-Integrate ***
    public static final String CREATE_PREPARATION_DOCUMENT_AUTO_INTEGRATE = "//span[text()='Create Preparation Document/Auto-Integrate']";

    // *** Add Content ***
    public static final String ADD_CONTENT = "//span[text()='Add Content']";

    // *** Source Copy ***
    public static final String SOURCE_COPY = "//span[text()='Source Copy']";

    // *** Validate ***

    public static final String VIEW_VALIDATIONS = "//span[text()='View Validations']";
    public static final String VALIDATE = "//span[text()='Validate']";

    // *** Track ***
    public static final String TRACK = "//span[text()='Track']";

    // *** Report ***
    public static final String REPORT = "//span[text()='Report']";
    public static final String RUN_COMPARE_REPORT = "//span[text()='Run Compare Report']";
    public static final String RUN_COMPARE_AND_MARKUP_REPORT = "//span[text()='Run Compare and Markup Report']";

    // *** Transfer ***
    public static final String TRANSFER = "//span[text()='Transfer']";
    public static final String CINDEX = "(//div[@class='ag-menu ag-ltr ag-popup-child']//span[text()='Transfer to Cindex'])";

    // *** Sync ***
    public static final String SYNC = "//span[text()='Sync']";

    // *** Create Bookmark ***
    public static final String CREATE_BOOKMARK = "//span[text()='Create Bookmark']";

    // *** Debug ***
    public static final String DEBUG = "//span[text()='Debug']";

    // *** Export to Excel ***
    public static final String EXPORT_TO_EXCEL = "//span[text()='Export to Excel']";

    // *** Export to Excel selected rows ***
    public static final String EXPORT_TO_EXCEL_SELECTED_ROWS = "//span[text()='Export to Excel selected rows']";

    //Difficulty Level
    public static final String DIFFICULTY_LEVEL = "//div[@class='ag-menu ag-ltr ag-popup-child']//span[text()='Difficulty Level']";
    public static final String ASSIGN_USER = "//div[@class='ag-menu ag-ltr ag-popup-child']//span[text()='Assign User']";
    public static final String ASSIGN_USER_USER = "//source-nav-assign-user-selector//input[@id='combobox']";
    public static final String ASSIGN_USER_DROP_DOWN = "//source-nav-assign-user-selector//*[@class='bento-combobox-dropdown-button-icon bento-icon-caret-down-filled']";

    public static final String BLANK_USER="//*[@id='bui-combobox-list-7-0']";
    public static final String ASSIGN_USER_GETDATE = "assignedToDatePicker";
    public static final String ASSIGNED_TO_DATE = "//input[@id='assignedToDatePicker']";
    public static final String CONTEXT_MENU_EFFECTIVE_DATE = "//div[@class='ag-menu-option']/span[contains(text(),'Effective Date')]";
    public static final String EFFECTIVE_DATE_INPUT = "//input[@id='effectiveDatePicker' or @id='effectiveDate']";
    public static final String SUBMIT = "//button[text()=' Submit ']";
    public static final String USER_SUBMIT = "//button[text()='Submit']";
    public static final String CANCEL = "//button[text()='Cancel']";
    public static final String CONFIRM = "//button[text()='Confirm']";
    public static final String EDIT_EFFECTIVE_DATE = "//div[@class='ag-menu ag-ltr ag-popup-child']//span[text()='Effective Date']";
    public static final String GENERAL_EFFECTIVE_DATE_TOGGLE_BUTTON = "(//div[contains(@class, 'bento-toggle-nob')])[2]";
    public static final String  RUN_CITE_LOCATE_INTEGRATION_STATUS_TOGGLE_BUTTON = "(//bento-toggle[contains(@role,switch)])[3]";
    public static final String  TOOL_BAR_MORE_OPTION = "//div[@class='dropdown']//button[@class='dropdown-toggle btn-toolbar']";
    public static final String RENDITION_CONTENT_MENU = "//div[@class='ag-menu-option']/span[text()='Rendition' or text()=' Rendition ']";
    public static final String  CLASS_NUMBER="//label[text()='Class Number ']";
    public static final String  CLASS_NAME = "//label[text()='Class Name ']";
    public static final String RENDITION_TAB = "//a[text()='Rendition properties']";
    public static final String PROPOSED_TAB = "//a[text()='Proposed/Approved Tracking Information']";
    public static final String PREP_TAB = "//a[text()='PREP Tracking Information']";
    public static final String SYSTEM_PROPERTIES_TAB = "//a[text()='System Properties']";
    public static final String APPROVAL_DATE = "//label[text()='Approval Date']";
    public static final String  CLOSE_ICON =   "//h5/following::button[@aria-label='Close']";
    public static final String DOCUMENT_TYPE = "//label[text()='Document Type ']";
    public static final String DOCUMENT_NUMBER =  "//label[text()= 'Document Number ']";
    public static final String DELTA_PROPERTIES = "//span[text()='Delta Properties']";
}
