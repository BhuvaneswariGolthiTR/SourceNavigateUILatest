package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaPageElements.SECTION_PROPERTIES_INPUT_FIELD;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.SECTION_PROPERTIES_BOX_LABEL;

public class SourceNavigateAngularPageElements
{
    // ---------- General Grid Elements ----------
    public static final String PAGE_TITLE = "SourceNavigateUi";
    public static final String AG_GRID_ANGULAR = "/ag-grid-angular";
    public static final String COLUMN_ID_PATTERN = "//div[@col-id='%s']";
    public static final String FILTER_ICON_ON_COLUMN_PATTERN = COLUMN_ID_PATTERN + "//span[@class='ag-icon ag-icon-filter']";
    public static final String LOADING_PLATE = "//span[text()='Loading...']";
    public static final String SPAN_CONTAINS_TEXT = "//span[contains(text(),'%s')]";
    public static final String SPAN_TEXT = "//span[text()='%s']";
    public static final String CLEAR_FILTERS = "//button[text()='Clear Filters']";
    public static final String GRID_CONTAINER = "//div[@class='ag-center-cols-container']/child::div";
    public static final String ACTIVE_VIEW_NAME = "//source-nav-active-view-status-panel/span";
    public static final String TOAST_MESSAGE="//*[contains(@class, 'toast bento-toast-success show in')]";
    public static final String ERROR_TOAST_MESSAGE="(//*[contains(text(), '%s')])";
    public static final String ASSIGNED_USER_MESSAGE="//div[@class='py-3 px-4']";
    public static final String ASSIGNED_USER_BLANK_MESSAGE="//span[@class='ng-star-inserted']";
    public static final String ASSIGNED_DATE_INCORRECT_MESSAGE="//div[@class='invalid-tooltip ng-star-inserted']";
    public static final String Incorrect_Date_Format="//div[@class='invalid-tooltip ng-star-inserted']";
    public static final String NOTES_VALUE_OF_RENDITIONS = "(//Button[@class='grid-btn'])[1]";
    public static final String ADD_TAX_TYPE = "//label[@class='font-weight-normal'and contains(text(),'%s')]/parent::div";
    public static final  String FILTER_LIST = "(//label[@class='transferbox-searchbox-label'and contains(text(),'Filter list')]/preceding-sibling::input)[%s]";
    // ---------- Lineage/Rendition tab ----------
    public static final String SOURCE_NAV_LINEAGE_RENDITIONS = "//source-nav-lineage-renditions";
    public static final String SOURCE_NAV_RENDITIONS = "//source-nav-renditions";
    public static final String SOURCE_NAV_RENDITIONS_AG_GRID = SOURCE_NAV_RENDITIONS + AG_GRID_ANGULAR;
    public static final String RENDITIONS_GRID_CONTAINER = SOURCE_NAV_RENDITIONS_AG_GRID + GRID_CONTAINER;
    public static final String RENDITIONS_ACTIVE_VIEW_NAME = SOURCE_NAV_RENDITIONS + ACTIVE_VIEW_NAME;
    public static final String COLUMN_CELL_VALUE_OF_RENDITION_ROW_PATTERN = RENDITIONS_GRID_CONTAINER + "[@row-id='%s']" + COLUMN_ID_PATTERN;
    public static final String FIRST_RENDITION_ROW = "(" + SOURCE_NAV_RENDITIONS_AG_GRID + "//div[@row-id='0'])[2]";
    public static final String ANY_RENDITION_ROW = "(" + SOURCE_NAV_RENDITIONS_AG_GRID + "//div[@row-id='%s'])[2]";
    public static final String APV_RENDITION_ROW = "(//div[@col-id='renditionStatus' and contains(text(), 'APV')])[%d]";
    public static final String APVDOCUMENT_NUMBER = "(//div[contains(@class, 'selected')]//div[@col-id='docNumber'])[%d]";
    public static final String FIRST_RENDITION_EFFECTIVE_DATE = "(//div[contains(@class, 'selected')]//div[@col-id='effectiveDate'])[1]";
    public static final String FIRST_RENDITION_ASSIGNED_DATE = "(//div[contains(@class, 'selected')]//div[@col-id='assignedToDate'])[1]";
    public static final String FIRST_RENDITION_ASSIGNED_USER = "(//div[contains(@class, 'selected')]//div[@col-id='assignedTo'])[1]";
    public static final String RENDITION_ROW_PATTERN = "(" + SOURCE_NAV_RENDITIONS_AG_GRID + "//div[@row-id='%s'])[2]";
    public static final String TOTAL_RENDITIONS_NUMBER = "//span[text()='Total renditions: %s']";
    public static final String TOTAL_RENDITIONS_NUMBERS = "//span[contains(text(),'Total renditions:')]";
    public static final String CLEAR_FILTERS_RENDITION = SOURCE_NAV_RENDITIONS + CLEAR_FILTERS;
    public static final String REFRESH_TABLE_DATA = "(//button[@ngbtooltip='Reload table data']/em[@class='bento-icon-refresh'])[2]";
    public static final String File_Size ="fileSize";
    public static final String File_Size_Filter ="//input[contains(@class,'ag-input-field-input ag-number-field-input')][1]";
    public static final String Save_View =SOURCE_NAV_RENDITIONS +"//button[text()='Save View']";
    public static final String View_Name = "//input[contains(@Id,'view-name')]";
    public static final String Submit_Button ="//button[contains(text(),'Submit')]";
    public static final String Save_Button ="//button[text()='Save']";
    public static final String Click_Created_View ="//strong[text()='%s']//parent::div//parent::div//preceding-sibling::em[@title='Delete View']";
    public static final String Confirm_Button ="//button[text()='Confirm']";
    public static final String Cancel_Button ="//button[(text()='Cancel')]";
    public static final String FIRST_FILE_SIZE_NUMBER = "(//div[contains(@class, 'selected')]//div[@col-id='fileSize'])[1]";
    public static final String FIRST_ROW_INSTRUCTION_NOTE ="(//div[contains(@class, 'selected')]//div[@col-id='instructionNote'])[1]";
    public static final String WORKFLOW_REPORTING_SYSTEM = "//button[@class='btn-toolbar']//span[text()='Workflow Reporting System']";

    // ---------- Section Group tab ----------
    public static final String SOURCE_NAV_SECTIONGROUPS = "//source-nav-section-groups";
    public static final String SOURCE_NAV_SECTIONGROUPS_AG_GRID = SOURCE_NAV_SECTIONGROUPS+AG_GRID_ANGULAR;
    public static final String SECTIONGROUP_ROW = "(" + SOURCE_NAV_SECTIONGROUPS_AG_GRID + "//div[@row-id='%s'])[2]";

    // ---------- Section tab ----------
    public static final String SOURCE_NAV_SECTION = "//source-nav-sections";
    public static final String SOURCE_NAV_SECTION_AG_GRID = SOURCE_NAV_SECTION+AG_GRID_ANGULAR;
    public static final String SECTION_ROW = "(" + SOURCE_NAV_SECTION_AG_GRID + "//div[@row-id='%s'])[2]";
    public static final String FIRST_SECTION_EFFECTIVE_DATE = "(//div[@row-id='0']//div[@col-id='effectiveDate'])[2]";
    public static final String FIRST_SECTION_ASSIGNED_DATE = "(//div[@col-id='assignedToDate'])[2]";
    public static final String FIRST_SECTION_ASSIGNED_USER = "(//div[@col-id='assignedTo'])[2]";
    public static final String SECOND_SECTION_EFFECTIVE_DATE ="(//div[@col-id='effectiveDate'])[6]";
   public static final String SECTION_ROW_INSTRUCTION_NOTE ="(//div[contains(@class, 'selected')]//div[@col-id='instructionNote'])[2]";

    // ---------- Delta Group tab ----------
    public static final String SOURCE_NAV_DELTAGROUPS = "//source-nav-delta-groups";

    // ---------- Delta tab ----------
    public static final String SOURCE_NAV_DELTAS = "//source-nav-deltas";
    public static final String SOURCE_NAV_DELTAS_AG_GRID = SOURCE_NAV_DELTAS + AG_GRID_ANGULAR;
    public static final String DELTA_ROW = "(" + SOURCE_NAV_DELTAS_AG_GRID + "//div[@row-id='%s'])[2]";
    public static final String FIRST_DELTA_EFFECTIVE_DATE = DELTA_ROW +"//div[@col-id='effectiveDate']";
    public static final String SECOND_DELTA_EFFECTIVE_DATE ="(//div[@col-id='effectiveDate'])[10]";

    // ---------- Cell Values ----------
    public static final String GRID_ROW_MULTIPLE_DUPLICATE_CELL_VALUE = SOURCE_NAV_RENDITIONS_AG_GRID + "//div[@row-id='%s']/div/source-nav-multiple-duplicate-flag-renderer/img";

    // ---------- Column IDs (col-id) ----------
    public static final String CORRELATION_ID = "correlationId";
    public static final String RENDITION_UUID = "renditionUuid";
    public static final String DOCUMENT_UUID = "documentUuid";
    public static final String MULTIPLE_DUPLICATES = "multipleFlag";
    public static final String ERROR_FLAG= "//source-nav-renditions//div[@col-id='flag']//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String RENDITION_STATUS = "renditionStatus";
    public static final String SECTION_NUMBER = " sectionNumber";
    public static final String CONTENT_TYPE  ="contentType";
    public static final String BILL_ID = "billId";
    public static final String ASSIGNED_TO = "assignedTo";
    public static final String SELECTED_RENDITION_ROW_COLOR = "rgba(2, 67, 128, 1)"; //"rgba(183, 228, 255, 1)";

    @FindBy(how = How.XPATH, using = "//source-nav-rendition-counter/span")
    public static WebElement totalRenditionsNumber;

    @FindBy(how = How.XPATH, using = "(//button[@ngbtooltip='Reload table data']/em[@class='bento-icon-refresh'])[6]")
    public static WebElement deltarefreshtable;

    public static final String homePageLabel = "//h2[text()='Source Navigate']";

    public static final String WORKFLOW_POP_UP ="//span[text()='workflow was started']";

    public static final String VIEW_VALIDATIONS_TITLE = "View Validations";

    public static final String CREATE_BOOKMARK_TITLE = "//div/span[@id='title' and contains(text(),'Create bookmark for a C2012 Source Document')]";

    public static final String CONFIRMATION_TITLE = "//source-nav-confirmation//div/h5";

    public static final String CLEAR_WARNING_FLAGS = "//div[contains(@class, 'ag-menu-option')]//span[contains(text(),'Clear Warning Flags')]";

    public static final String CLEAR_VALIDATION_FLAGS = "//div[contains(@class, 'ag-menu-option')]/*[contains(text(),'Clear Validation Flags')]";

    public static final String VALIDATE_LINKS = "//div[contains(@class,'ag-menu-option')]//span[contains(text(),'Validate Links')]";

    public static final String SOURCE_NAV_VIEW_VALIDATION = "//source-nav-view-validations";

    public static final String VIEW_VALIDATION_ROW_PATTERN = "(" + SOURCE_NAV_VIEW_VALIDATION + AG_GRID_ANGULAR+ "//div[@row-id='%s'])[2]";

    public static final String COLUMN_NAMES = "//source-nav-view-validations//span[contains(text(),'%s')]";

    public static final String VIEW_VALIDATION_CLOSE_BTN = "//source-nav-view-validations//button[@class='close-btn']";
    public static final String TAX_TYPE_ADD_TITLE = "//source-nav-tax-type-add//h5";
    public static final String REMOVE_BUTTON = "//span[contains(text(),'Remove')]/parent::button";
    public static final String ADD_BUTTON = "//span[contains(text(),'Add')]/parent::button";
    public static final String AVAILABLE_TAX_TYPE = "//p[contains(text(),'Available Tax Type Adds')]";
    public static final String SELECTED_TAX_TYPE = "//p[contains(text(),'Selected Tax Type Adds')]";
    public static final String AVAILABLE_TAX_TYPE_NUM = "(//div[@class='transferlist-header-info']/div[@class='transferlist-select-info'])[1]";
    public static final String TOTAL_AVAILABLE_ROW_COUNT = "//div[@class = 'bento-table-row']";
    public static final String SELECTED_TAX_TYPE_NUM = "(//div[@class='transferlist-header-info']/div[@class='transferlist-select-info'])[2]";
    public static final String TAX_TYPE = "//span[@ref='eText' and contains(text(),'Tax Type Add')]";
    public static final String TAX_TYPE_COULMN_ID ="(//div[@row-id='0'])[2]//div[@col-id='taxTypes']//span";
    public static final String MULTIPLE_DUPLICATE_COLUMN_HEADER="(//span[text()='Multiple / Duplicates'])[4]";
    public static final String INDEX_ENTRY_COMPLETED_DATE_COLUMN_HEADER="(//span[text()='Index Entry Completed Date'])[3]";
    public static final String LOCK_COLUMN_HEADER= "(//span[text()='Lock'])[5]";
    public static final String RELOAD_DATA = "(//button[@class='btn btn-outline-secondary btn-sm mr-1' and @ngbtooltip='Reload table data'])[4]";
    public static final String RENDITION_SYSTEM_PROPERTIES_INPUT_FIELD = "//source-nav-Rendition-properties" + SECTION_PROPERTIES_INPUT_FIELD;
    public static final String CALENDAR_OPTION = SECTION_PROPERTIES_BOX_LABEL + "/following::button[@aria-label='Open calendar'][1]";
    public static final String PRINT_PREVIEW="//span[text()='Rendition Print Preview']";
}
