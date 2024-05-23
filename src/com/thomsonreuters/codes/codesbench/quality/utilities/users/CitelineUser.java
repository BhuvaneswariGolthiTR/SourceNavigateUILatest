package com.thomsonreuters.codes.codesbench.quality.utilities.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CitelineUser extends User
{
    public CitelineUser(@Value("${citelineUser}") String username, @Value("${citelinePassword}") String password,
                        @Value("${citelineFirstname}") String firstname, @Value("${citelineLastname}") String lastname)
    {
        super(username, password, firstname, lastname);
    }
}
