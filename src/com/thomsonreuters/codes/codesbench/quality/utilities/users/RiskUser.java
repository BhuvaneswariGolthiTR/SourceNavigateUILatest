package com.thomsonreuters.codes.codesbench.quality.utilities.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RiskUser extends User
{
	public RiskUser(@Value("${riskUser}") String username, @Value("${riskPassword}") String password,
					@Value("${riskFirstname}") String firstname, @Value("${riskLastname}") String lastname)
	{
		super(username, password, firstname, lastname);
	}
}
