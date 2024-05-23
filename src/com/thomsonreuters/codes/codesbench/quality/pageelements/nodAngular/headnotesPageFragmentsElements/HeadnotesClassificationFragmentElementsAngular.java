package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements;

public class HeadnotesClassificationFragmentElementsAngular
{
//    CLASSIFICATION_AREA
    public static final String APP_HEADNOTES_TABLE = "//app-headnotes-table";
    public static final String APP_HEADNOTES_TEXT = "//app-headnote-text";

    public static final String HEADNOTE_ROW = "//app-headnotes-table/table/tbody/tr";
    public static final String TABLE = APP_HEADNOTES_TABLE + "/table/tbody";
    public static final String CLASSIFICATION_COLUMN = APP_HEADNOTES_TABLE + "//th[@scope='col' and text()='%s']";

    public static final String COLUMN_VALUES_BY_ROW_AND_COLUMN = TABLE + "//tr[%s]/td[%s]";
    public static final String HN_COLUMN_VALUE_BY_ROW = TABLE + "//tr[%s]/td[2]/a";
    public static final String RL_COLUMN_VALUE_BY_ROW = TABLE + "//tr[%s]/td[3]";
    public static final String SN_COLUMN_VALUE_BY_ROW = TABLE + "//tr[%s]/td[4]";
    public static final String HEADNOTE_TEXT_COLUMN_VALUE_BY_ROW = TABLE + "//tr[%s]//app-headnote-text/span";
    public static final String TOPIC_KEY_COLUMN_VALUE_BY_ROW = TABLE + "//tr[%s]/td[6]/div";
    public static final String CLASSIFICATION_BLOCK_BY_ROW = TABLE + "//tr[%s]/td[7]";
    public static final String CLASSIFICATION_COLUMN_NY_NUMBER = "./td[%s]";
    public static final String HN_COLUMN_VALUE = "./td[2]/a";
    public static final String RL_COLUMN_VALUE = "./td[3]";
    public static final String SN_COLUMN_VALUE = "./td[4]";
    public static final String HEADNOTE_TEXT_COLUMN_VALUE = "./td[5]/app-headnote-text/span";
    public static final String CLASSIFICATION_BLOCK = "./td[6]";
    public static final String CLASSIFICATION_INNER_TABLE = "/table[@class='table classification-table']";
    public static final String ALL_INNER_CLASSIFICATION_TABLES = APP_HEADNOTES_TABLE + "/" + CLASSIFICATION_INNER_TABLE;
    public static final String ALL_HEADNOTES_CLASSIFICATIONS = APP_HEADNOTES_TABLE + "/" + CLASSIFICATION_INNER_TABLE + "//tr";
    public static final String ALL_UNIGNORE_BUTTONS = APP_HEADNOTES_TABLE + "//table//tr/td/button";
    public static final String CLASSIFICATION_LINK = CLASSIFICATION_INNER_TABLE + "/a";
    public static final String CLASSIFICATION_DELETE_BUTTON = CLASSIFICATION_INNER_TABLE + "//span[%s]";
    public static final String CLASSIFY_BUTTON_BY_ROW = APP_HEADNOTES_TABLE + "//tr[%s]/td[1]/div/button[text() = 'Classify']";
    public static final String IGNORE_BUTTON_BY_ROW = APP_HEADNOTES_TABLE + "//tr[%s]/td[1]/div/button[text() = 'Ignore']";
    public static final String UNIGNORE_BUTTON = "/button[text()='Unignore']";
    public static final String UNIGNORE_BUTTON_BY_ROW = APP_HEADNOTES_TABLE + "//tr[%s]/" + UNIGNORE_BUTTON;
    public static final String CLASSIFICATION_FOR_ONE_HEADNOTE_BY_ORDER = CLASSIFICATION_INNER_TABLE + "//tr[%s]/td[1]";
    public static final String CLASSIFICATION_TABLE_BY_ROW = TABLE + "//tr[%s]/td[7]" + CLASSIFICATION_INNER_TABLE;
    public static final String CLASSIFICATION_TEXT = TABLE + "//tr[%s]/td[7]" + CLASSIFICATION_INNER_TABLE + "//a[%s]";
    public static final String HEADNOTE_TEXT_CITE = APP_HEADNOTES_TEXT + "//em[text()='%s']";


}
