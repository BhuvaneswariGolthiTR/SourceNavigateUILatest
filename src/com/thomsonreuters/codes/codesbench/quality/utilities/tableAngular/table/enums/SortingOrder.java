package com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums;

public enum SortingOrder
{
    UNSORTED("Unsorted"),
    ASCENDING("Ascending"),
    DESCENDING("Descending");

    private final String sortingOrderString;

    SortingOrder(String sortingOrderString)
    {
        this.sortingOrderString = sortingOrderString;
    }

    public String getSortingOrderAsString()
    {
        return this.sortingOrderString;
    }
}
