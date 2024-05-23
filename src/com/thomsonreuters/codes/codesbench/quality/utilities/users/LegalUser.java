package com.thomsonreuters.codes.codesbench.quality.utilities.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LegalUser extends User
{
	public LegalUser(@Value("${legalUser}") String username, @Value("${legalPassword}") String password,
					 @Value("${legalFirstname}") String firstname, @Value("${legalLastname}") String lastname)
	{
		super(username, password, firstname, lastname);
	}
}
