package com.thomsonreuters.codes.codesbench.quality.utilities.urls;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge.environmentTag;

@Component
public class Urls
{
	@Value("${homePageUrl}")
	public String homePageUrl;

	@Value("${dynamicScrollingHomePageUrl}")
	public String dynamicScrollingHomePageUrl;

	@Value("${hierarchyPageUrl}")
	public String hierarchyPageUrl;

	@Value("${sourcePageUrl}")
	public String sourcePageUrl;

	@Value("${sourceAngularPageUrl}")
	public String sourceAngularPageUrl;

	@Value("${renditionUuid}")
	public String renditionUuid;

	@Value("${nodPageUrl}")
	public String nodPageUrl;

	public String getHomePageUrl()
	{
		return homePageUrl;
	}
	public void setHomePageUrl(String homepageUrl)
	{
		this.homePageUrl = homepageUrl;
	}

	public String getDynamicScrollingHomePageUrl()
	{
		return dynamicScrollingHomePageUrl;
	}
	public void setDynamicScrollingHomePageUrl(String dynamicScrollingHomePageUrl) {
		this.dynamicScrollingHomePageUrl = dynamicScrollingHomePageUrl;
	}
	
	public String getHierarchyPageUrl()
	{
		return hierarchyPageUrl;
	}
	public void setHierarchyPageUrl(String hierarchyPageUrl)
	{
		this.hierarchyPageUrl = hierarchyPageUrl;
	}
	
	public String getSourcePageUrl()
	{
		return sourcePageUrl;
	}

	public String getSourceAngularPageUrl()
	{
		return sourceAngularPageUrl;
	}
	public void setSourcePageUrl(String sourcePageUrl)
	{
		this.sourcePageUrl = sourcePageUrl;
	}

	public void setSourceAngularPageUrl(String sourceAngularPageUrl)
	{
		this.sourceAngularPageUrl = sourceAngularPageUrl;
	}

	public String getNodPageUrl()
	{
		return nodPageUrl;
	}
	public void setNodPageUrl(String nodPageUrl)
	{
		this.nodPageUrl = nodPageUrl;
	}

	public String getRenditionUuid()
	{
		return renditionUuid;
	}
	public void setRenditionUuid(String renditionUuid)
	{
		this.renditionUuid = renditionUuid;
	}

	public String getSourcePageUrlWithRenditionUuids(String... uuids)
	{
		String url = String.format(sourcePageUrl, environmentTag);
		if (uuids.length != 0)
		{
			url += renditionUuid + String.join(",", uuids);
		}
		return url;
	}
}
