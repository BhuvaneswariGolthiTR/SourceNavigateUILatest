package com.thomsonreuters.codes.codesbench.quality.utilities.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProdLegalUser extends User
{
	public ProdLegalUser(@Value("${prodLegalUser}") String username, @Value("${prodLegalPassword}") String password,
						 @Value("${prodFirstname}") String firstname, @Value("${prodLastname}") String lastname)
	{
		super(username, password, firstname, lastname);
	}
}