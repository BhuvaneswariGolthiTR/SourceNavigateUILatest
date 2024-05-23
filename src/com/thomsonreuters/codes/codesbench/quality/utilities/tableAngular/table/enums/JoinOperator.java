package com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;

public enum JoinOperator
{
    AND(FilterPopupElementsAngular.AND),
    OR(FilterPopupElementsAngular.OR);

    private final String xpath;

    JoinOperator(String xpath)
    {
        this.xpath = xpath;
    }

    public String getXpath()
    {
        return this.xpath;
    }
}
