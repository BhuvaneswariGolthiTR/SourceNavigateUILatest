package com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums;

public enum FilterOperator
{
    CONTAINS("Contains"),
    NOT_CONTAINS("Not contains"),
    EQUALS("Equals"),
    NOT_EQUAL("Not equal"),
    STARTS_WITH("Starts with"),
    ENDS_WITH("Ends with"),
    GREATER_THAN("Greater than"),
    LESS_THAN("Less than"),
    GREATER_THAN_OR_EQUALS("Greater than or equals"),
    LESS_THAN_OR_EQUALS("Less than or equals"),
    IN_RANGE("In range"),
    BLANKS("Blanks");

    private final String text;

    FilterOperator(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return this.text;
    }
}
