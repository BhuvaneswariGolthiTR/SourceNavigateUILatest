package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ManageAssignmentsPageElements
{

    public static final String SELECTED_DOCUMENT = "//span[@id='pageForm:documentsGroup']/div/ul/li";

    public static final String SELECTED_PRODUCT_VIEW_TAG = "//tbody/tr[td[contains(text(), 'View/Tag:')]]/td/span";

    public static final String SELECTED_PRODUCT_TYPE =  "//tbody/tr[td[contains(text(), 'Product Type:')]]/td/span";

    public static final String SELECTED_PRODUCT_NAME = "//tbody/tr[td[contains(text(), 'Product Name:')]]/td/span";

    public static final  String SELECTED_PRODUCT_CURRENT_ASSIGNMENT_STATUS = "//tbody/tr[td[contains(text(), 'Current Assignment Status:')]]/td/span";

    public static final String ADD_TAG = "//input[@value='add']";

    public static final String REMOVE_TAG = "//input[@value='remove']";

    public static final String SINGLE_NODE_ONLY = "//input[@value='single']";

    public static final String WITH_ANCESTORS_AND_DESCENDANTS ="//input[@value='ancestors']";

    public static final String WITH_DESCENDANTS_ONLY = "//input[@value='descendants']";

    public static final String ALL_VERSIONS = "//input[@value='all']";

    public static final String CURRENT_AND_PROSPECTIVE_VERSIONS_ONLY = "//input[@value='current']";

}
