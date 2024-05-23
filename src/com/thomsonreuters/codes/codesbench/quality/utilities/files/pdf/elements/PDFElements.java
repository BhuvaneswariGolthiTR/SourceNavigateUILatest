package com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements;

import org.sikuli.script.Region;
import org.w3c.dom.css.Rect;

import java.awt.geom.Rectangle2D;

public class PDFElements
{
    //This comes out from the codes bench server
    public static final String AUDIT_LANDING_STRIP_PDF = "/apps/Codes/MP2/LandingStrips/Reports/Audits/diff/%s.pdf";
    //This is where audits that have been run will be saved for testing
    public static final String AUDIT_SAVING_LOCATION_PDF ="./commonFiles/TestFiles/AuditRedesignFiles/TempFiles/%s.pdf";

    public static final String AUDIT_SINGLE_PAGE_LOCATION_PDF = "./commonFiles/TestFiles/AuditRedesignFiles/TempFiles/OnePageOfAudit.pdf";

    //We will want to look into a more dynamic solution
    public static final String AUDIT_SINGLE_PAGE_LOCATION_PDF_2 = "./commonFiles/TestFiles/AuditRedesignFiles/TempFiles/TwoPageOfAudit.pdf";

    public static final double PAGE_HEIGHT = 612.0;
    public static final double PAGE_WIDTH = 792.0;

    //This refers to the center header that appears on each page of an audited node
    //The content of this area will vary depending on the node
    public static final Rectangle2D PAGE_HEADER = new Rectangle2D.Double(180, 0, 432, 70);

    //This is the left top cornerpiece of each page, this should stay the same for each page and only the time will change
    public static final Rectangle2D LEFT_CORNERPIECE = new Rectangle2D.Double(15, 0, 150, 45);

    //This is the right top cornerpiece of each page, this should stay the same for each page and current page number will change
    public static final Rectangle2D RIGHT_CORNERPIECE = new Rectangle2D.Double(657 ,0, 130, 50);

    //This is only on the first page of an audit and can vary in size if the audit identifier is long
    //This is the Shortest Possible Summary Header
    public static final Rectangle2D SUMMARY_HEADER = new Rectangle2D.Double(30 ,70, 732, 150);
    public static final Rectangle2D NODE_VALUE_IN_CONTENT_SUMMARY = new Rectangle2D.Double(0, 250, 1030, 75);

    public static final Rectangle2D VALIDATION_REPORT_TEXT_AREA = new Rectangle2D.Double(10,225, 800,175); // h=120

    public static final Rectangle2D CLEAN_VALIDATION_REPORT_TEXT_AREA = new Rectangle2D.Double(10, 225, 800, 120);

    public static final Rectangle2D DELETED_VALIDATION_REPORT_TEXT_AREA = new Rectangle2D.Double(10, 225, 800, 100);

    //All rectangles here are subject to change because audit size for these sections can vary
    public static final Rectangle2D SINGLE_NODE_FROM_SUMMARY_INFO_LISTING = new Rectangle2D.Double(35,350,710,25);
    public static final Rectangle2D FIRST_NODE_FROM_SUMMARY_INFO_LISTING = new Rectangle2D.Double(35,254,710,22);//Selecting second node add with y+25

    public static final Rectangle2D END_OF_CONTENT_NODE_SUMMARY_LINE = new Rectangle2D.Double(35, 300, 700, 22);

    public static final Rectangle2D TAX_TYPE_REPORT_SECTION = new Rectangle2D.Double(35, 375, 700, 100);
    public static final Rectangle2D FIRST_NODE_VALUE_FROM_SUMMARY_INFO = new Rectangle2D.Double(10,254,600,22);
    public static final Rectangle2D FULL_NODE_LIST = new Rectangle2D.Double(15,230,755,350);

    public static final Rectangle2D NODE_SUMMARY_START_PAGE = new Rectangle2D.Double(35, 119, 721, 75);//Selects the summary on the start page of a node

    //----------------------------------------------------------------------------------------
    //This is a temporary rectangle, Just something I put in here that can be manipulated for when the dynamic drawing gets figured out
    //It is a base rectangle that is 25 pixels tall and takes up the whole right to left content of the page
    public static final Rectangle2D DYNAMIC_RECTANGLE = new Rectangle2D.Double(35, 15, 721, 25);

    //The next 5 integers are used to navigate through the bookmarks tab
    //The first two are the X and Y values of the bookmark expand button
    public static final int X_VALUE_OF_PDF_BOOKMARK_EXPANDER = 20;

    public static final int Y_VALUE_OF_PDF_BOOKMARK_EXPANDER = 90;
    //The next 3 are used to click on the separate bookmarks, with the y value being 200 + (32 * i) where i is the index of the node within the audit
    public static final int X_VALUE_OF_PDF_BOOKMARKS = 100;

    public static final int Y_VALUE_OF_PDF_BOOKMARKS = 200;

    public static final int Y_VALUE_OF_SPACE_IN_BETWEEN_BOOKMARKS = 32;

    public static final Region REGION_OF_NODE_VALUE_ON_FIRST_PAGE_OF_NODE_SECTION = new Region(494, 147, 589, 48);

    public static final Region REGION_OF_CONTENT_COMPARE_WITH_MARKUPS = new Region(1008, 326, 429, 435);

    public static final Rectangle2D MODIFIED_UNDESIGNATED_REGION_FROM_CONTENT_COMPARE = new Rectangle2D.Double(355, 100, 75, 75);

    public static final Rectangle2D ADDED_UNDESIGNATED_REGION_FROM_CONTENT_COMPARE = new Rectangle2D.Double(355, 205, 75, 200);

    public static final Rectangle2D SAME_REGION_FROM_CONTENT_COMPARE = new Rectangle2D.Double(355, 465, 75, 50);

    public static final Rectangle2D MODIFIED_REGION_FROM_CONTENT_COMPARE = new Rectangle2D.Double(355, 50, 75, 50);

    public static final Rectangle2D DELETED_REGION_FROM_CONTENT_COMPARE = new Rectangle2D.Double(355, 200, 75, 40);

    public static final Rectangle2D ADDED_REGION_FROM_CONTENT_COMPARE = new Rectangle2D.Double(355, 415, 75, 40);

    public static final Rectangle2D DELETED_REGION_FOR_FOOTNOTE_DELETED = new Rectangle2D.Double(355,175,75,40);
    public static final Region REGION_OF_NODE_VALUE_OF_FIRST_PAGE_OF_NODE_SECTION_FOR_LINK_TEST = new Region(497, 292, 571, 36);

    public static final int Y_VALUE_OF_NODE_LINK_IN_CONTENT_SUMMARY = 475;

    public static final int X_VALUE_OF_NODE_LINK_IN_CONTENT_SUMMARY = 750;

    public static final Rectangle2D TEXT_FROM_NOD_REPORT_SHARED = new Rectangle2D.Double(100,50,589,25);
    public static final Rectangle2D TEXT_FROM_NOD_REPORT_CURRENT_NOT_PRIOR_DOES = new Rectangle2D.Double(150,50,589,25);

    public static final Rectangle2D TEXT_FROM_NOD_REPORT_NOT_SHARED = new Rectangle2D.Double(150,100,589,48);

    public static final Rectangle2D TEXT_FROM_NOD_REPORT_NOD_RETAINED_AT_PRIOR = new Rectangle2D.Double(325,550,589,48);

    public static final Rectangle2D TEXT_FROM_NOD_REPORT_REPEALED_HAS_NOD = new Rectangle2D.Double(375,50,589,48);

    public static final Rectangle2D TEXT_FROM_NOD_REPORT_CURRENT_DOES_PRIOR_DOES_NOT = new Rectangle2D.Double(115,125,589,48);
}
