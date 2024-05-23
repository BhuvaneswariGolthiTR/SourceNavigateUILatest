package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditAdminPageElementsAngular {

    public static final String RIGHT_PANE = "//bento-splitter-group-main";
    public static final String CASE_TITLE = RIGHT_PANE + "//h5[contains(text(),'Edit Admin')]";

    public static final String CLASSIFICATION_INFORMATION_TABLE = "//table[@class='table']";
    public static final String CLASSIFICATION_INFORMATION_TABLE_BY_ROW = CLASSIFICATION_INFORMATION_TABLE + "/tbody/tr[%d]";
    public static final String STATUTE_SHORT_CITE_BY_ROW = CLASSIFICATION_INFORMATION_TABLE_BY_ROW + "/td[1]//a";
    public static final String BLUE_LINE_INFORMATION_BY_ROW = CLASSIFICATION_INFORMATION_TABLE_BY_ROW + "/td[2]";
    public static final String REMOVE_CLASSIFICATION_BY_ROW = CLASSIFICATION_INFORMATION_TABLE_BY_ROW + "//span[@title='Remove Classification']";

    @FindBy(how = How.XPATH, using = "//button[text()='Classify']")
    public static WebElement classify;

}
