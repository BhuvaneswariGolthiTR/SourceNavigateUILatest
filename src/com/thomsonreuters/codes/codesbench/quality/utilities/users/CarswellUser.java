package com.thomsonreuters.codes.codesbench.quality.utilities.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CarswellUser extends User
{
	public CarswellUser(@Value("${carswellUser}") String username, @Value("${carswellPassword}") String password,
						@Value("${carswellFirstname}") String firstname, @Value("${carswellLastname}") String lastname)
	{
		super(username, password, firstname, lastname);
	}
}
