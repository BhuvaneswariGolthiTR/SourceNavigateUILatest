package com.thomsonreuters.codes.codesbench.quality.pageelements.home;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePageElements
{
    public static final String HOME_PAGE_TITLE = "Home Page";
    public static final String CODESBENCH_NAVIGATOR_HEADER_XPATH = "//div[@id='codesNavigatorHeader']";
    public static final String CONTENT_SET_FROM_INFO_BAR_XPATH = CODESBENCH_NAVIGATOR_HEADER_XPATH + "//div[@class='infoBar']";

    public static final String CONTENT_SET_DROPDOWN_XPATH = "//select[@id='pageForm:selectedContentSet']";
    public static final String SELECTED_CONTENT_SET_OPTION_DROPDOWN_XPATH = CONTENT_SET_DROPDOWN_XPATH + "//option[@selected]";
    public static final String ADD_NEW_TEAM_LINK_BUTTON_XPATH = "//*[@id='pageForm:addLinkButton']";
    public static final String ADD_NEW_TEAM_NOTE_BUTTON_XPATH = "//*[@id='pageForm:addNoteButton']";

    public static final String ERROR_SPAN = "//div[@id='codesBenchNavigatorPageContent']//span[@class='error']";
    public static final String MY_BOOKMARKS = "//table[@id='pageForm:c2012_SourceDocument_Bookmarks']/tbody//span[contains(text(),'%s')]";
    public static final String TARGET_NOTE_BOOKMARKS = "//table[@id='pageForm:wipNode_Bookmarks']/tbody//span[contains(text(),'%s')]";
    public static final String BOOKMARK_DELETE = "//img[contains(@onclick, '%s' )  and (contains(@title, 'Delete Bookmark'))]";

    @FindBy(how = How.ID, using = "pageForm:selectedContentSet")
    public static WebElement contentSelect;

    @FindBy(how = How.XPATH, using = "//div[@class='infoBar']")
    public static WebElement currentContentSet;
}
