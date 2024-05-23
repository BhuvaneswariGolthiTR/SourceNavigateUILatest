package com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums;

public enum TableColumns
{
    LOADED_DATE("Loaded Date", "loadedDate"),
    NOTES("Notes", "noteId"),
    CASE_SERIAL_SUBSCRIBED_CASES("Case serial", "caseSerial"),
    CASE_SERIAL_CASES("Case serial", "caseSerial"),
    CCDB("CCDB", "ccdb"),
    WESTLAW("Westlaw", "westlaw"),
    REPORTER_CITE("Reporter Cite", "reporterCite"),
    NEUTRAL_CITE("Neutral Cite", "neutralCite"),
    COURT("Court", "court"),
    RU("R/U", "ru"),
    RELOADED("Reloaded", "reloaded"),
    //Subscribed cases table naming for headnotes is HN
    HN("HN", "headnotes"),
    //Cases table naming for headnotes is Headnotes
    HEADNOTES("Headnotes", "headnotes"),
    TITLE("Title", "title"),
    SIGNED_OUT_BY("Signed Out By", "signedOutBy"),
    COMPLETED_DATE("Completed Date", "completedDate"),
    COMPLETED_BY("Completed By", "completedBy"),
    CITE_INFORMATION("Cite Information", "citeInformation"),
    SUBSCRIPTIONS("Subscriptions", "subscriptions"),
    DISPLAY_NAME("Display Name", "displayName"),
    LONG_NAME("Long Name", "longName"),
    COURT_LINE_STYLE("Court Line Style", "courtLineStyle"),

    // O'Connors Processing tables columns:
    PUBLICATION_NAME("Publication Name", "name"),
    YEAR("Year", "ag-Grid-AutoColumn"),
    CONTENT_SET("Content Set", "contentSet"),
    REQUESTER("Requester's Name", "requester"),
    PUB_TAG("Pub Tag", "pubTag"),
    FILE_NAME("File Name", "fileName"),
    CREATED_DATE("Created Date", "dateCreated"),
    STATUS("Status", "status"),

    // Query Note Report table columns
    QUERY_NOTE_REPORT_TYPE("Type", "type"),
    QUERY_NOTE_REPORT_STATUS("Status", "status"),
    QUERY_NOTE_REPORT_VOLS("Vols", "vols"),
    QUERY_NOTE_REPORT_CODE("Code", "targetCode"),
    QUERY_NOTE_HIERARCHY_BREADCRUMB("Hierarchy Breadcrumb", "hierarchyText"),
    QUERY_NOTE_REPORT_KEYWORD("Keyword", "targetKeyword"),
    QUERY_NOTE_REPORT_VALUE("Value", "targetValue"),
    QUERY_NOTE_REPORT_CREATED_DATE("Created Date", "createdDate"),
    QUERY_NOTE_REPORT_ACTION_DATE("Action Date", "actionDate"),
    QUERY_NOTE_REPORT_START_DATE("Start Date", "startDate"),
    QUERY_NOTE_REPORT_END_DATE("End Date", "endDate"),
    QUERY_NOTE_REPORT_PRODUCT_TYPE("Product Type", "productType"),
    QUERY_NOTE_REPORT_VIEW_TAG("View Tag", "viewTag"),
    QUERY_NOTE_REPORT_PRODUCT_NAME("Product Name", "productName"),
    QUERY_NOTE_REPORT_QUERY_TEXT("Query Text", "queryText"),
    QUERY_NOTE_REPORT_RESOLVED_DATE("Resolved Date", "resolvedDate"),
    QUERY_NOTE_REPORT_RESOLVED_BY("Resolved By", "resolvedBy"),
    QUERY_NOTE_REPORT_RESOLVED_COMMENT("Resolved Comment", "resolvedComment"),
    QUERY_NOTE_REPORT_PUBTAG_INFORMATION("PubTag Information", "pubtags");

    private final String columnHeaderName;
    private final String columnId;

    TableColumns(String columnHeaderName, String columnId)
    {
        this.columnHeaderName = columnHeaderName;
        this.columnId = columnId;
    }

    public String getColumnHeaderName()
    {
        return columnHeaderName;
    }

    public String getColumnId()
    {
        return columnId;
    }
}

