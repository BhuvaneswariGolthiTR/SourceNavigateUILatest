package com.thomsonreuters.codes.codesbench.quality.utilities.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PingIdUnlockPIN
{
	private String pingIdUnlockPIN;

	public PingIdUnlockPIN(@Value("${pingIdUnlockPIN}") String pingIdUnlockPIN)
	{
		this.pingIdUnlockPIN = pingIdUnlockPIN;
	}

	public String getPingIdUnlockPIN()
	{
		return pingIdUnlockPIN;
	}
}
