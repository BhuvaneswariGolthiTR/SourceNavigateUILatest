package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.track;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.TrackMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractTrackClamshellPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaGroupTabTrackClamshellPage extends AbstractTrackClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public DeltaGroupTabTrackClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, TrackMenuElements.class);
	}

}
