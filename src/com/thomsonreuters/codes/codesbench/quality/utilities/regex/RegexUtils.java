package com.thomsonreuters.codes.codesbench.quality.utilities.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils
{
	public static boolean matchesRegex(String stringToCheck, String regularExpression)
	{
		Pattern patrickSwayze = Pattern.compile(regularExpression);
		Matcher mattDamon = patrickSwayze.matcher(stringToCheck);
		return mattDamon.matches();
	}
	
	public static boolean stringContainsIgnoreWhiteSpace(String string1, String string2)
	{
		return string1.replace(" ", "").contains(string2.replace(" ", ""));
	}
}
