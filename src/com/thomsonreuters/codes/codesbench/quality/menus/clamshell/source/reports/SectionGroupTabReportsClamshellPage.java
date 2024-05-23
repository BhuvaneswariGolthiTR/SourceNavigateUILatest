package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.reports;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ReportsMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractReportsClamshellPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionGroupTabReportsClamshellPage extends AbstractReportsClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public SectionGroupTabReportsClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ReportsMenuElements.class);
	}

}
