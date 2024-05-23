package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails;

public class PropertiesElements
{
        public static final String WORKFLOW_PROPERTIES_BUTTON = "//img[@id='workflow_icon']";
        public static final String PROPERTY_VALUE = "//span[text()='%s']/../following-sibling::td/span";

        public enum Property
        {
                PDF_REPORT_LINK("pdfReportLink"),
                LOG("_log"),
                CONTENT_SET("contentSet"),
                QUERY_NOTE_REPORT_LINK("queryNoteReportUrl");

                private String name;

                Property(String name)
                {
                        this.name = name;
                }

                public String getName()
                {
                        return this.name;
                }
        }

}
