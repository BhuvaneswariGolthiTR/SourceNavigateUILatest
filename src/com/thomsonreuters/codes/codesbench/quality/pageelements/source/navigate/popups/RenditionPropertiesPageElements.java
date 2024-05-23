package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

public class RenditionPropertiesPageElements 
{
	public static final String RENDITION_PROPERTIES_TITLE = "Rendition Properties";
	public static final String RENDITION_PROPERTIES_CALENDAR = "//button[@id='%s']";
	public static final String CALCONTAINER_BLOCK = "//div[contains(@class,'calcontainer') and contains(@style,'block')]";
	public static final String CALENDAR_DAY = "//div[contains(@style,'block')]//table//tbody//tr//td//a[contains(text(),'%s')]";
	public static final String PAGE_HEADER = "//div[@id='pageHeader']";
	public static final String EARLIEST_EFFECTIVE_DATE_INPUT = "//span[text()='Earliest Effective Date']/ancestor::td/following-sibling::td/input";
	public static final String EFFECTIVE_DATE_INPUT = "//span[text()='Effective Date']/ancestor::td/following-sibling::td/input";
}
