package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InsertTargetCiteReferencePageElements
{
        public static final String PAGE_TITLE = "Insert Target Cite Reference";
        public static final String LOCATE_TARGET_BUTTON = "//input[@id='pageForm:targetLocateButton']";
        public static final String SUBSECTION = "//input[@id='pageForm:subsectionIds']";
        public static final String REFERENCE_TYPE = "//select[@id='pageForm:referenceType']";
        public static final String URL_INPUT = "//input[@id='pageForm:url']";
        public static final String DOC_FAMILY_ID_INPUT = "//input[@id='pageForm:dfId']";
        public static final String EMAIL_INPUT = "//input[@id='pageForm:email']";
        public static final String CONTENT_SET = "//select[@id='pageForm:contentSetId']";

        public static final String CONTENT_SET_ID = "pageForm:contentSetId";

        public static final String REFERENCE_TYPE_ID = "pageForm:referenceType";
        public static final String TARGET_INPUT = "//input[@id='pageForm:targetDisplayName']";
        public static final String ADDITIONAL_CONTENT_SETS_CHECKBOX = "//input[@id='pageForm:additionalRulebooksCheckbox']";

        @FindBy(how = How.XPATH, using = "//span[@id='pageForm:errorMessage']")
        public static WebElement errorMessage;

        public enum ReferenceType
        {
                EXTERNAL_URL("external"),
                NON_MIGRATED("nonmigrated"),
                EMAIL("email"),
                TARGET("rulebook"),
                GLOSSARY("glossary");

                private final String name;

                ReferenceType(String name)
                {
                        this.name = name;
                }

                public String getName()
                {
                        return name;
                }
        }
}
