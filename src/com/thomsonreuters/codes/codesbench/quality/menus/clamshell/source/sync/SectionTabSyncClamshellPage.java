package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.sync;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.SyncMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractSyncClamshellPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionTabSyncClamshellPage extends AbstractSyncClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public SectionTabSyncClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SyncMenuElements.class);
	}

}