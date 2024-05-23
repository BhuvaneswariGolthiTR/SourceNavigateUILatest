package com.thomsonreuters.codes.codesbench.quality.utilities.users;

public class User
{
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	
	public User(String username, String password, String firstname, String lastname)
	{
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public String getWorkflowUsername()
	{
		return username;
	}

	public String getEditorModifiedByUsername()
	{
		return String.format("%s %s ", firstname, lastname);
	}

	public String getSubscribedCasesInitials()
	{
		return firstname.substring(0,1) + lastname.substring(0,1);
	}

	public String getReportCentralRequestersUsername()
	{
		return String.format("%s, %s", lastname, firstname);
	}

	public String getReportCentralPDFRequestersUsername()
	{
		return String.format("%s %s", firstname, lastname);
	}

	public String getNodEditorUsername()
	{
		return String.format("%s %s", firstname, lastname);
	}

	public String getRenditionSectionDeltaPropertiesUsername()
	{
		return String.format("%s %s", firstname, lastname);
	}
	
	public String getUsernameWithoutULowerCaseC()
	{
		return username.substring(1);
	}

	public String getUsernameWithUpperCaseUC()
	{
		return username.toUpperCase();
	}

	public String getUsernameWithoutUUpperCaseC()
	{
		return username.replace("u", "").replace("U", "").toUpperCase();
	}

	public String getPublishingToolboxUsername()
	{
		return lastname + ", " + firstname.substring(0, 1);
	}
}
