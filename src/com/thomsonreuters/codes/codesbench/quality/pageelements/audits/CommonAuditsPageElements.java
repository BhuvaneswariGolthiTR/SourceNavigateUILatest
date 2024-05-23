package com.thomsonreuters.codes.codesbench.quality.pageelements.audits;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CommonAuditsPageElements
{
    public static final String LABEL_CONTENT_AUDIT_XPATH = "//label[@for='pageForm:contentAudit']";

    public static final String LABEL_HIERARCHY_CONTEXT_XPATH = "//label[@for='pageForm:hierarchyContext']";

    public static final String LABEL_VERSIONS_AUDIT_STARTING_XPATH = "//label[@for='pageForm:containsVersions']";

    public static final String LABEL_VALIDATIONS_XPATH = "//label[@for='pageForm:containsValidationReport']";

    public static final String LABEL_SOURCES_XPATH = "//label[@for='pageForm:containsShowOtherSourcesReport']";

    public static final String LABEL_NOD_REPORT_XPATH = "//label[@for='pageForm:containsNODAuditReport']";

    public static final String FROM_DATES_CHANGED_TEXTBOX_XPATH = "//input[@name='pageForm:fromDate']";

    public static final String TO_DATES_CHANGED_TEXTBOX_XPATH = "//input[@name='pageForm:toDate']";

    public static final String LABEL_SHORT_SAME_PARAGRAPHS_XPATH = "//label[@for='pageForm:reportFormat:0']";

    public static final String LABEL_NOT_SAME_PARAGRAPHS_XPATH = "//label[@for='pageForm:reportFormat:1']";

    public static final String LABEL_ALL_TEXT_XPATH = "//label[@for='pageForm:reportFormat:2']";

    public static final String LABEL_IGNORE_PUBTAG_CHANGE_XPATH = "//label[@for='pageForm:reportFormat:3']";

    public static final String LABEL_CLASSIFICATION_XPATH = "//label[contains(text(), 'Classification')]";

    public static final String LABEL_DATE_XPATH = "//label[contains(text(), 'Date')]";

    @FindBy(how = How.ID, using = "pageForm:contentAudit")
    public static WebElement contentAuditCheckbox;

    @FindBy(how = How.ID, using = "pageForm:hierarchyContext")
    public static WebElement hierarchyContextCheckbox;

    @FindBy(how = How.ID, using = "pageForm:containsVersions")
    public static WebElement versionsAuditStartingCheckbox;

    @FindBy(how = How.ID, using = "pageForm:containsValidationReport")
    public static WebElement validationsCheckbox;

    @FindBy(how = How.ID, using = "pageForm:containsShowOtherSourcesReport")
    public static WebElement sourcesCheckbox;

    @FindBy(how = How.ID, using = "pageForm:containsNODAuditReport")
    public static WebElement nodReportCheckbox;

    @FindBy(how = How.ID, using = "pageForm:containsTaxTypeReport")
    public static WebElement taxTypeReportCheckbox;

    @FindBy(how = How.XPATH, using = "//input[@value='SHORT_SAME']")
    public static WebElement shortSameParagraphsRadioButton;

    @FindBy(how = How.XPATH, using = "//input[@value='NO_SAME']")
    public static WebElement notShortSameParagraphsRadioButton;

    @FindBy(how = How.XPATH, using = "//input[@value='ALL_TEXT']")
    public static WebElement allTextRadioButton;

    @FindBy(how = How.XPATH, using = "//input[@value='IGNORE_PUBTAG_CHANGE']")
    public static WebElement ignorePubtagChangeRadioButton;

    @FindBy(how = How.XPATH, using = "//input[@value='target']")
    public static WebElement classificationRadioButton;

    @FindBy(how = How.XPATH,using = "//input[@value='date' and @type='radio']")
    public static WebElement dateRadioButton;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:addOneVolsButton']")
    public static WebElement addOneDocumentButton;
}
