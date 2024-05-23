package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowsearch;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WorkflowFiltersPageElements {

    // Filter Headers
    public static final String WORKFLOW_ID_FILTER_XPATH = "//input[@id='pageForm:workflowGrid:id17']";
    public static final String WORKFLOW_TYPE_FILTER_XPATH = "//select[@id='pageForm:workflowGrid:id25']";
    public static final String CONTENT_SET_FILTER_XPATH = "//select[@id='pageForm:workflowGrid:id32']";
    public static final String DESCRIPTION_FILTER_XPATH = "//input[@id='pageForm:workflowGrid:id39']";
    public static final String STEP_NAME_FILTER_XPATH = "//input[@id='pageForm:workflowGrid:id45']";
    public static final String START_TIME_FROM_FILTER_XPATH = "//input[@id='pageForm:workflowGrid:id54']";
    public static final String START_TIME_TO_FILTER_XPATH = "//input[@id='pageForm:workflowGrid:id55']";
    public static final String USER_FILTER_XPATH = "//select[@id='pageForm:workflowGrid:id61']";
    public static final String STATUS_FILTER_XPATH = "//select[@id='pageForm:workflowGrid:id68']";

    public static final String WORKFLOW_START_TIME_XPATH = "//tbody/tr[@class='grid-row1']/td[7]/span";

}
