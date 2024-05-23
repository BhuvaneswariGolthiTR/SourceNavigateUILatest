package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

public class InsertSourceCiteReferencePageElements
{
    public static final String PAGE_TITLE = "Insert Source Cite Reference";
    public static final String SOURCE_LOCATE_BUTTON = "//input[@id='pageForm:sourceLocateButton']";
    public static final String REFERENCE_TYPE = "//select[@id='pageForm:referenceType']";
    public static final String CONTENT_SET = "//select[@id='pageForm:contentSetName']";

    public enum ReferenceType
    {
        CITE_REFERENCE("cite reference");

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
