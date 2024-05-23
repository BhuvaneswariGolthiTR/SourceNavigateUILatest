package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InsertNewNodesPageElements
{
    public static final String INSERT_NEW_NODES_PAGE_TITLE = "Insert New Nodes";
    private static final String GENERIC_NODE_TABLE_XPATH = "//table[@id='nodeTable']/tbody/";
    public static final String VALUE_TEXTBOX = "//input[@id='pageForm:userEnteredData:0:value']";
    public static final String VALUE_TEXTBOX_OF_GIVEN_ROW = "//input[@id='pageForm:userEnteredData:%d:value']";
    public static final String DEPTH_TEXTBOX = "//input[@id='pageForm:userEnteredData:0:depth']";
    public static final String DEPTH_TEXTBOX_OF_GIVEN_ROW = "//input[@id='pageForm:userEnteredData:%d:depth']";
    public static final String EFFECTIVE_START_DATE_TEXTBOX = "//input[@name='pageForm:userEnteredData:0:effStartDate']";
    public static final String EFFECTIVE_START_DATE_TEXTBOX_OF_GIVEN_ROW = "//input[@name='pageForm:userEnteredData:%d:effStartDate']";
    public static final String EFFECTIVE_END_DATE_TEXTBOX_OF_GIVEN_ROW = "//input[@id='pageForm:userEnteredData:%d:effEndDate']";
    public static final String NODE_TYPE_TEXTBOX_OF_GIVEN_ROW = "//select[@name='pageForm:userEnteredData:%d:j_idt50']/option[@value='%s']";
    public static final String KEYWORD_TEXTBOX_OF_GIVEN_ROW = "//select[@name='pageForm:userEnteredData:%d:j_idt53']/option[@value='%s']";
    public static final String EFFECTIVE_END_DATE_TEXTBOX = "//input[@id='pageForm:userEnteredData:0:effEndDate']";
    public static final String KEYWORD_SELECT = "//table[@id='nodeTable']/tbody/tr[contains(@class,'userEnteredRow')]/td[count(//tr/th[contains(text(), 'Keyword')]/preceding-sibling::*)+1]/select";
    public static final String STATUS_SELECT = "//table[@id='nodeTable']/tbody/tr[contains(@class,'userEnteredRow')]/td[count(//tr/th[contains(text(), 'Westlaw')]/preceding-sibling::*)+1]/select";
    public static final String NODE_TYPE_SELECT = "//table[@id='nodeTable']/tbody/tr[contains(@class,'userEnteredRow')]/td[count(//tr/th[contains(text(), 'Node Type')]/preceding-sibling::*)+1]/select";
    public static final String DEFAULT_USER_ADDED_NODE_DEPTH_XPATH = GENERIC_NODE_TABLE_XPATH + "tr[3]/td[1]";
    public static final String DEFAULT_USER_ADDED_NODE_WESTLAW_FORMAT_STATUS_XPATH = GENERIC_NODE_TABLE_XPATH + "tr[3]/td[2]";
    public static final String DEFAULT_USER_ADDED_NODE_NODE_TYPE_XPATH = GENERIC_NODE_TABLE_XPATH + "tr[3]/td[3]";
    public static final String DEFAULT_USER_ADDED_NODE_KEYWORD_XPATH = GENERIC_NODE_TABLE_XPATH + "tr[3]/td[4]";
    public static final String DEFAULT_USER_ADDED_NODE_VALUE_XPATH = GENERIC_NODE_TABLE_XPATH + "tr[3]/td[5]";
    public static final String DEFAULT_USER_ADDED_NODE_EFFECTIVE_START_DATE_XPATH = GENERIC_NODE_TABLE_XPATH + "tr[3]/td[6]";
    public static final String DEFAULT_USER_ADDED_NODE_EFFECTIVE_END_DATE_XPATH = GENERIC_NODE_TABLE_XPATH + "tr[3]/td[7]";

    /**
     * Use in addition to the endings, with the appropriate row.
     */
    public static final String ADDITION_COLUMN_BEGINNING_XPATH = GENERIC_NODE_TABLE_XPATH + "tr[";
    public static final String ADDITIONAL_COLUMN_ENDING_NODE_DEPTH_XPATH = "]/td[1]";
    public static final String ADDITIONAL_COLUMN_ENDING_NODE_WESTLAW_FORMAT_STATUS_XPATH = "]/td[2]";
    public static final String ADDITIONAL_COLUMN_ENDING_NODE_NODE_TYPE_XPATH = "]/td[3]";
    public static final String ADDITIONAL_COLUMN_ENDING_NODE_KEYWORD_XPATH = "]/td[4]";
    public static final String ADDITIONAL_COLUMN_ENDING_NODE_VALUE_XPATH = "]/td[5]";
    public static final String ADDITIONAL_COLUMN_ENDING_NODE_EFFECTIVE_START_DATE_XPATH = "]/td[6]";
    public static final String ADDITIONAL_COLUMN_ENDING_NODE_EFFECTIVE_END_DATE_XPATH = "]/td[7]";

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement quickLoadButton;

}
