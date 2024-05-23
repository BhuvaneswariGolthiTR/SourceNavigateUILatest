package com.thomsonreuters.codes.codesbench.quality.pages.table;

import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.pageelements.table.TableTestingServicePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigate.SourceNavigateGroupingPageElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TableTestingPage extends BasePage
{	
	WebDriver driver;

    @Autowired
	public TableTestingPage(final WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, TableTestingServicePageElements.class);
	}
	
    //work with table
    
    /**
     * Returns count of rows in the table
     * @return int
     */
    public int getRowCount()
    {
        List<WebElement> rows = getElements(tableBody.entrySet().iterator().next().getValue());
        return rows.size();
    }
    public int getRowCount(final Object col)
    {
        List<WebElement> rows = getElements(tableBody.get(col));
        return rows.size();
    }
    
    public int getExpectedRowCountAfterFilteringBy(final Object col, final String value)
    {
        List<String> cells = getColumnValues(col).stream().filter((t) -> t.contains(value)).collect(Collectors.toList());
        return cells.size();
    }
    
    public WebElement getCell(Object col, int row)
    {
        List<WebElement> rows = getElements(tableBody.get(col));
        WebElement cell = rows.get(row);
        return cell;
    }
    
    public boolean doesCellContainElement(Object col, int row, String xpath)
    {
    	return doesElementExist( String.format("(%s)[%d]", tableBody.get(col) + xpath, row) );
    }
    
    /**
     * Returns value in the specified column and specified row
     * @param col - Column
     * @param row - number of row
     * @return
     */
    public String getCellValue(Object col, int row)//TODO: the numbering of rows should begin with 1 (now with 0)
    {
        //List<WebElement> rows = getElements(tableBody.get(col));
        //WebElement cell = rows.get(row);
    	String cellText = "";
    	cellText = getCell(col, row).getText();
        return cellText; //.getAttribute("textContent"); // getting NullPointer sometimes trying to get this attr
    }
    
    public String getCellInnerHTML(Object col, int row)
    {
        //List<WebElement> rows = getElements(tableBody.get(col));
        //WebElement cell = rows.get(row);
        return getCell(col, row).getAttribute("innerHTML");
    }
    
    /**
     * Returns all cell values in specified column 
     * @param col - Column
     * @return List<String>
     */
    public List<String> getColumnValues(Object col)
    {
        List<WebElement> rows = getElements(tableBody.get(col));
        List<String> resultList = rows.stream().map( (e) -> e.getAttribute("textContent")).collect(Collectors.toList());
        return resultList;
    }
    
    public int getRowByText(Object col, String text)
    {
        List<String> colValues = getColumnValues(col);
        return colValues.indexOf(text) + 1;
    }
    
    public int[] getRowsByText(Object col, String text)
    {
        List<String> colValues = getColumnValues(col);
        return IntStream.range(0, colValues.size()-1)
        		.filter(i -> text.equals(colValues.get(i)))
        		.toArray();
    }
    
    public boolean doesCellWithTextExist(final Object col, String text)
    {
        return doesElementExist(tableBody.get(col) + "[text()='" + text + "']");
    }
    
    public boolean checkCellValues(final Object col, final String expectedValue)
    {
        if (expectedValue == null)
        {
            throw new IllegalArgumentException("expectedValue is null");
        }
        
        boolean res = getColumnValues(col).stream().map( (av) -> av.contains(expectedValue) ).reduce( (r1, r2) -> r1 && r2).orElse(false);
        return res;
    }
    
    public boolean checkCellValue(final Object col, int row, final String expectedValue)
    {
        if (expectedValue == null)
        {
            throw new IllegalArgumentException("expectedValue is null");
        }
        
        boolean res = getColumnValues(col).get(row-1).equals(expectedValue);
        return res;
    }
    
    /**
     * Sorts the specified column by the specified order
     * @param col - sorting column
     * @param sortOrder - order of sorting
     * @return boolean - Whether sorting is successful
     * @throws  
     */
    public boolean sortBy(Object col, SortOrder sortOrder)  
    {
        click(tableHeaders.get(col));
        waitForTableRefresh();
        SortOrder currentSort = getCurrentSortOrder(col);
        if (sortOrder.equals(currentSort))
        {
            return true;
        }
        click(tableHeaders.get(col));
        currentSort = getCurrentSortOrder(col);
        waitForTableRefresh();
        if (sortOrder.equals(currentSort))
        {
            return true;
        }
        return false;
    }
    
    /**
     * Checks whether the elements in the column are sorted by the specified sort order
     * @param col - Column
     * @param sortOrder Sort Order
     * @return boolean - elements are sorted correctly
     */
    public boolean checkSort(Columns col, SortOrder sortOrder)
    {
        
//		if (Number.class.equals(col.getType()))
//		{
//		    return checkSort( col,
//		            (String s1, String s2) -> SortOrder.ASC.equals(sortOrder) ? Double.compare(Double.valueOf(s1), Double.valueOf(s2)) : Double.compare(Double.valueOf(s2), Double.valueOf(s1))//Determine the rule for comparing rows for ASC/DESC sort
//		                    );
//		}
        
        //default - as strings
        return checkSort( col,
                    (String s1, String s2) -> SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1)//Determine the rule for comparing rows for ASC/DESC sort
                            );
        
    }
    
    protected SortOrder getCurrentSortOrder(Object col)
    {
        String classes = getElement(tableHeaders.get(col)).getAttribute("class");
        if (classes.contains(SORT_ASC_CLASS))
        {
            return SortOrder.ASC; 
        }
        if (classes.contains(SORT_DESC_CLASS))
        {
            return SortOrder.DESC; 
        }
        return null;
    }
    
    /**
     * Checks whether the elements in the column are sorted by the specified compare rule
     * @param col - Column
     * @param comparator - compare rul
     * @return boolean - elements are sorted correctly
     */
    protected boolean checkSort(Object col, Comparator<String> comparator)
    {
        List<String> values = getColumnValues(col);
        for (int i = 0; i < values.size() - 1; i++)
        {
            if (comparator.compare(values.get(i), values.get(i+1)) > 0)
            {
                return false;
            }
        }
        return true;
    }
    
    //help methods
    public boolean waitForTableRefresh()
    {
        if(doesElementExist(TableTestingServicePageElements.FILTER_LOADING_BAR))
        {
            waitForElementGone(TableTestingServicePageElements.FILTER_LOADING_BAR);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //work with filter
    
    public void setFilter(final Object col, final String value)
    {
        if (!TableTestingPage.FILTER_INPUTS_MAP.containsKey(col))
        {
            throw new IllegalArgumentException("Name of Filter field - " + col + " does not exist");
        }
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(col)).click();
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(col)).clear();
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(col)).sendKeys(value);
        waitForPageLoaded();
        waitForTableRefresh();
        waitForPageLoaded();
    }
    
    public void setFilterAndEnter(final Object col, final String value)  
    {
        if (!TableTestingPage.FILTER_INPUTS_MAP.containsKey(col))
        {
            throw new IllegalArgumentException("Name of Filter field - " + col + " does not exist");
        }
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(col)).click();
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(col)).clear();
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(col)).sendKeys(value);
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(col)).sendKeys(Keys.ENTER);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(col)).sendKeys(Keys.ENTER);
        waitForPageLoaded();
        waitForTableRefresh();
        waitForPageLoaded();
    }
    
    public void clearFilter(Object col, String value)
    {
        if (!TableTestingPage.FILTER_INPUTS_MAP.containsKey(col))
        {
            throw new IllegalArgumentException("Name of Filter field - " + col + " does not exist");
        }
        
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(col)).clear();
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(col)).sendKeys("\b");
        waitForTableRefresh();
    }
    
    public void clearDeltaGroupingFilter()
    {
        getElement(SourceNavigateGroupingPageElements.FILTER_CLEAR_BUTTON).click();
        getElement(SourceNavigateGroupingPageElements.FILTER_CLEAR_DROPDOWN_CLEAR_FILTERS).click();
        waitForTableRefresh();
    }
    
    public void selectRow(int rowIndex)  
    {//TODO
    	click( TableTestingServicePageElements.TABLE_BODY + String.format("//tr[%s]", rowIndex) );
    }
    
    public void selectRows(int firstLine, int lastLine)
    {//TODO
        try
        {
            List<WebElement> rows = getElements(TableTestingServicePageElements.TABLE_BODY + "//tr");
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            RobotUtils.getRobot().delay(500);
            for(int i = firstLine-1; i <= lastLine-1; i++)
            {
                rows.get(i).click();
            }
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
    }
    
    public void rightClickByCell(Object col, int row)
    {
    	rightClick( getCell(col, row) );
    }
    
    public enum SortOrder
    {
        ASC,
        DESC;
    }
    
    
    static private Map<Object, String> tableHeaders = new HashMap<>();
    static private Map<Object, String> tableBody = new HashMap<>();
    
    /**Grouping columns definition*/
    static public class GroupingColumns extends Columns
    {
        static public GroupingColumns SECTION_NUMBER = new GroupingColumns("Section Number", "sectionNumber", Number.class);
        static public GroupingColumns DELTA_LEVEL = new GroupingColumns("Delta Level", "deltaLevel", String.class);
        static public GroupingColumns DELTA_COUNT = new GroupingColumns("Delta Count", "deltaCount", String.class);
        static public GroupingColumns GROUP_NAME = new GroupingColumns("Group Name", "groupName ", String.class);
        static public GroupingColumns CPD_DATE = new GroupingColumns("Delta Count", "isCpdDateProvided ", String.class);
        
        private GroupingColumns(String columnHeader, String id, Class<?> type)
        {
            super(columnHeader, id, type);
            
            tableHeaders.put(this, String.format(TableTestingServicePageElements.TABLE+"[@id='sectionGrid']" + TableTestingServicePageElements.TABLE_HEAD + TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id));
            tableBody.put(this, String.format(TableTestingServicePageElements.TABLE+"[@id='sectionGrid']" + TableTestingServicePageElements.TABLE_BODY + TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id));
        };
    }
    
    /**Delta columns definition*/
    static public class DeltaColumns extends Columns
    {
        static public DeltaColumns NOTES = new DeltaColumns("Notes", "notes", Number.class);
        
        private DeltaColumns(String columnHeader, String id, Class<?> type)
        {
            super(columnHeader, id, type);
            
            tableHeaders.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_HEAD + TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id));
            tableBody.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_BODY + TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id));
        };
    }
    
    /**Delta columns definition*/
    static public class SectionGroupingColumns extends Columns
    {
    	static public SectionGroupingColumns SECTION_GROUP_NAME = new SectionGroupingColumns("Section Group Name", "groupName", Number.class);
        static public SectionGroupingColumns CPD_DATE_TIME = new SectionGroupingColumns("CPD Date/Time", "cpdDate", Number.class);
        
        private SectionGroupingColumns(String columnHeader, String id, Class<?> type)
        {
            super(columnHeader, id, type);
            
            //tableHeaders.put( this, String.format(TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id) );
            //tableBody.put( this, String.format(TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id) );
            tableHeaders.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_HEAD + TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id));
            tableBody.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_BODY + TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id));
        };
    }
    
    /**Rendition columns definition*/
    static public class RenditionColumns extends Columns
    {
    	static public RenditionColumns ID = new RenditionColumns("", "id", String.class);
    	
        static public RenditionColumns CONTENT_SET = new RenditionColumns("Content Set", "contentSetViewable", String.class);
        static public RenditionColumns YEAR = new RenditionColumns("Year", "year", String.class);
        static public RenditionColumns SESSION = new RenditionColumns("Session", "session", String.class);
        static public RenditionColumns RENDITION_STATUS = new RenditionColumns("Rendition Status", "renditionStatus", String.class);
        static public RenditionColumns INSTRUCTION_NOTES = new RenditionColumns("", "instructionNotes", String.class);
        static public RenditionColumns NOTES = new RenditionColumns("Notes", "notes", String.class);
        static public RenditionColumns LOCK = new RenditionColumns("", "lockViewable", String.class);
        static public RenditionColumns TARGET_CODE = new RenditionColumns("", "targetCode", String.class);
        static public RenditionColumns TARGET_NODE = new RenditionColumns("", "targetNode", String.class);
        static public RenditionColumns TARGET_SUB_NODE = new RenditionColumns("", "TargetSubNode", String.class);
        static public RenditionColumns EFF_DATE = new RenditionColumns("", "EffectiveDate", String.class);
        static public RenditionColumns TARGET_VOL = new RenditionColumns("", "targetVol", String.class);
        static public RenditionColumns CLASS_NAME = new RenditionColumns("className", "className", String.class);
        static public RenditionColumns CLASS_NUMBER = new RenditionColumns("", "classNum", String.class);
        static public RenditionColumns WEST_ID = new RenditionColumns("", "westId", String.class);
        static public RenditionColumns DOC_TYPE = new RenditionColumns("", "docType", String.class);
        static public RenditionColumns DOC_NUMBER = new RenditionColumns("", "docNumber", String.class);
        static public RenditionColumns RENDITION_SECTIONS = new RenditionColumns("", "renditionSections", String.class);
        static public RenditionColumns SECTION_NUMBER = new RenditionColumns("", "sectionNumber", String.class);
        static public RenditionColumns SUB_SECTION_NUMBER = new RenditionColumns("", "subSectionNumber", String.class);
        static public RenditionColumns DELTA_LEVEL = new RenditionColumns("", "deltaLevel", String.class);
        static public RenditionColumns ACTION = new RenditionColumns("", "action", String.class);
        static public RenditionColumns DELETE = new RenditionColumns("", "deleteFlagViewable", String.class);
        static public RenditionColumns FILE_SIZE = new RenditionColumns("", "sourceFileSize", String.class);
        
        private RenditionColumns(String columnHeader, String id, Class<?> type)
        {
            super(columnHeader, id, type);
            
            //tableHeaders.put(this, String.format(TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id));
            //tableBody.put(this, String.format(TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id));
            tableHeaders.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_HEAD + TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id));
            tableBody.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_BODY + TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id));
        };
    }
    
    /**Rendition columns definition*/
    static public class QueryNoteColumns extends Columns
    {
        static public QueryNoteColumns ID = new QueryNoteColumns("", "id", String.class);
        static public QueryNoteColumns STATUS = new QueryNoteColumns("Status", "status", String.class);
        static public QueryNoteColumns TARGET_VALUE = new QueryNoteColumns("Target Value", "targetValue", Number.class);
        static public QueryNoteColumns TYPE = new QueryNoteColumns("", "type", String.class);
        static public QueryNoteColumns KEYWORD = new QueryNoteColumns("", "targetKeyword", String.class);
        static public QueryNoteColumns TEXT = new QueryNoteColumns("", "queryText", String.class);
        
        private QueryNoteColumns(String columnHeader, String id, Class<?> type)
        {
            super(columnHeader, id, type);
            
            tableHeaders.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_HEAD + TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id));
            tableBody.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_BODY + TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id));
        };
    }
    
    static public class StageCheckReportsColumns extends Columns
    {
        static public StageCheckReportsColumns CONTENT_SET = new StageCheckReportsColumns("", "contentSetLongName", String.class);
        static public StageCheckReportsColumns SESSION = new StageCheckReportsColumns("", "shortName", String.class);
        static public StageCheckReportsColumns DOC_TYPE = new StageCheckReportsColumns("", "docType", String.class);
        static public StageCheckReportsColumns DOC_NUMBER = new StageCheckReportsColumns("", "docNumber", String.class);
        static public StageCheckReportsColumns CONTENT_TYPE = new StageCheckReportsColumns("", "contentTypeShortName", String.class);
        
        private StageCheckReportsColumns(String columnHeader, String id, Class<?> type)
        {
            super(columnHeader, id, type);
            
            tableHeaders.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_HEAD + TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id));
            tableBody.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_BODY + TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id));
        };
    }
    
    static public class ViewBaselines extends Columns
    {
        static public ViewBaselines NUMBER = new ViewBaselines("", "baselineNumber", String.class);
        static public ViewBaselines DESCRIPTION = new ViewBaselines("", "description", String.class);
        
        private ViewBaselines(String columnHeader, String id, Class<?> type)
        {
            super(columnHeader, id, type);
            
            tableHeaders.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_HEAD + TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id));
            tableBody.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_BODY + TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id));
        };
    }
    
    /**Lock Report columns definition*/
    static public class LockReportColumns extends Columns
    {
        static public LockReportColumns LOCK_DATE = new LockReportColumns("Lock Date", "lockDate", Number.class);
        
        private LockReportColumns(String columnHeader, String id, Class<?> type)
        {
            super(columnHeader, id, type);
            
            tableHeaders.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_HEAD + TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id));
            tableBody.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_BODY + TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id));
        };
    }
    
    
     static public class SubscribedCasesColumns extends Columns
     {
        public static SubscribedCasesColumns CASE_SERIAL_NUMBER = new SubscribedCasesColumns("Case Serial #", "caseSerialNumber", Number.class);
        public static SubscribedCasesColumns SIGNED_OUT_BY = new SubscribedCasesColumns("Signed Out By", "signedOutBy", Number.class);
        public static SubscribedCasesColumns COMPLETED_BY = new SubscribedCasesColumns("Completed By", "completedBy", Number.class);
        public static SubscribedCasesColumns COMPLETED_DATE = new SubscribedCasesColumns("Completed Date", "completedDate", Number.class);
        public static SubscribedCasesColumns LOADED_DATE = new SubscribedCasesColumns("", "loadedDate", String.class);
        public static SubscribedCasesColumns RU = new SubscribedCasesColumns("", "RU", String.class);
        public static SubscribedCasesColumns WESTLAW_NUMBER = new SubscribedCasesColumns("", "westlawNumber", String.class);
        public static SubscribedCasesColumns REPORTER_CITE = new SubscribedCasesColumns("", "reporterCite", String.class);
        public static SubscribedCasesColumns COURT = new SubscribedCasesColumns("", "court", String.class);
        public static SubscribedCasesColumns RELOADED = new SubscribedCasesColumns("", "reloaded", String.class);
        public static SubscribedCasesColumns HN = new SubscribedCasesColumns("", "HN", String.class);
        public static SubscribedCasesColumns TITLE = new SubscribedCasesColumns("", "title", String.class);
        public static SubscribedCasesColumns CITE_INFORMATION = new SubscribedCasesColumns("", "citeInformation", String.class);
//        static public SubscribedCasesColumns NOTES = new SubscribedCasesColumns("", "citeInformation", String.class);
        
        private SubscribedCasesColumns(String columnHeader, String id, Class<?> type)
        {
            super(columnHeader, id, type);
            
            tableHeaders.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_HEAD + TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id));
            tableBody.put(this, String.format(TableTestingServicePageElements.TABLE + TableTestingServicePageElements.TABLE_BODY + TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id));
        };
    }
    
    static public class GroupNavigateColumns extends Columns
    {
        static public GroupNavigateColumns GROUP_NAME = new GroupNavigateColumns("", "groupDisplayName", String.class);
        static public GroupNavigateColumns SECTION_COUNT = new GroupNavigateColumns("", "sectionCount", String.class);
        static public GroupNavigateColumns DELTA_COUNT = new GroupNavigateColumns("", "deltaCount", String.class);
        static public GroupNavigateColumns CPD_DATE = new GroupNavigateColumns("", "isCpdDateProvided ", String.class);
        
        private GroupNavigateColumns(String columnHeader, String id, Class<?> type)
        {
            super(columnHeader, id, type);
            
            //tableHeaders.put( this, String.format(TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id) );
            //tableBody.put( this, String.format(TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id) );
            tableHeaders.put(this, String.format(TableTestingServicePageElements.TABLE+"[@id='groups']" + TableTestingServicePageElements.TABLE_HEAD + TableTestingServicePageElements.TABLE_HEADER_TEMPLATE, id));
            tableBody.put(this, String.format(TableTestingServicePageElements.TABLE+"[@id='groups']" + TableTestingServicePageElements.TABLE_BODY + TableTestingServicePageElements.TABLE_CELL_TEMPLATE, id));
        };
    }
    
    /**
     * Filter mapping
     * Create a mapping of table headers inputs and their filter input locators(xpath)
     */
    static public final Map<Object, String> FILTER_INPUTS_MAP;
    static
    {
        FILTER_INPUTS_MAP = new HashMap<>();
        FILTER_INPUTS_MAP.put(GroupingColumns.SECTION_NUMBER, TableTestingServicePageElements.FILTER_SECTION_NUMBER_INPUT);
        FILTER_INPUTS_MAP.put(GroupingColumns.DELTA_LEVEL, TableTestingServicePageElements.FILTER_DELTA_LEVEL_INPUT);
        FILTER_INPUTS_MAP.put(GroupingColumns.DELTA_COUNT, TableTestingServicePageElements.FILTER_DELTA_COUNT_INPUT); //SourceNavigateGroupingPageElements.FILTER_DELTA_COUNT_INPUT);
        
        FILTER_INPUTS_MAP.put(QueryNoteColumns.STATUS, TableTestingServicePageElements.FILTER_QUERY_NOTE_STATUS_INPUT);
        FILTER_INPUTS_MAP.put(QueryNoteColumns.TARGET_VALUE, TableTestingServicePageElements.FILTER_QUERY_NOTE_TARGET_VALUE_INPUT);
        FILTER_INPUTS_MAP.put(QueryNoteColumns.KEYWORD, TableTestingServicePageElements.FILTER_QUERY_NOTE_KEYWORD_INPUT);
        FILTER_INPUTS_MAP.put(QueryNoteColumns.TYPE, TableTestingServicePageElements.FILTER_QUERY_NOTE_TYPE_INPUT);
        
        FILTER_INPUTS_MAP.put(StageCheckReportsColumns.CONTENT_SET, TableTestingServicePageElements.FILTER_STAGE_CHECK_REPORTS_CONTENT_SET_INPUT);
        FILTER_INPUTS_MAP.put(StageCheckReportsColumns.SESSION, TableTestingServicePageElements.FILTER_STAGE_CHECK_REPORTS_SHORT_NAME_INPUT);
        FILTER_INPUTS_MAP.put(StageCheckReportsColumns.DOC_TYPE, TableTestingServicePageElements.FILTER_STAGE_CHECK_REPORTS_DOC_TYPE_INPUT);
        FILTER_INPUTS_MAP.put(StageCheckReportsColumns.DOC_NUMBER, TableTestingServicePageElements.FILTER_STAGE_CHECK_REPORTS_DOC_NUMBER_INPUT);
        FILTER_INPUTS_MAP.put(StageCheckReportsColumns.CONTENT_TYPE, TableTestingServicePageElements.FILTER_STAGE_CHECK_REPORTS_CONTENT_TYPE_INPUT);
        
        FILTER_INPUTS_MAP.put(SubscribedCasesColumns.CASE_SERIAL_NUMBER, TableTestingServicePageElements.FILTER_SUBSCRIBED_CASES_CASE_SERIAL_NUMBER_INPUT);
    }
    
    /** classes for sorting */
    static private final String SORT_DESC_CLASS = "yui-dt-desc";
    static private final String SORT_ASC_CLASS = "yui-dt-asc";
    
    static class Columns
    {
        
        protected Columns(String columnHeader, String id, Class<?> type)
        {
            this.columnHeader = columnHeader;
            this.id = id;
            this.type = type;
        }
        
        /*public String getColumnHeader()
        {
            return columnHeader;
        }*/
        
        public String getId()
        {
            return id;
        }
        
        public Class<?> getType()
        {
            return type;
        }
        
        /**Name of the column */
        protected String columnHeader;
        /** id of the column, Is taken from the column html code (th/td/so on)  */
        protected String id;
        
        protected Class<?> type;
    }
	

}
