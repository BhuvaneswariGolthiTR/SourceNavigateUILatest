package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class QueryNoteReportAngularEditActionDatesPageElements
{
    public static final String QUERY_NOTE_EDIT_ACTION_DATES_MODAL_HEADER = "Edit Action Dates";
    public static final String QUERY_NOTE_EDIT_ACTION_DATES_PAGE_XPATH = "//app-edit-action-dates";

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'OK')]")
    public static WebElement editAndSaveButton;

    @FindBy(how = How.ID, using = "actionDate")
    public static WebElement newActionDateTextbox;
}
