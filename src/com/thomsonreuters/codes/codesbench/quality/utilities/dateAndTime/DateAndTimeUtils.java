package com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateAndTimeUtils
{
	private static final String WORKFLOW_DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	private static final String ddMMMyyyy_DATE_FORMAT = "dd MMM yyyy";
	private static final String MMMddyyyy_DATE_FORMAT = "MMM d, yyyy";
	private static final String MMMMddyyyy_DATE_FORMAT = "MMMM d, yyyy";
	private static final String yyyyMMddHHmmss_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
	private static final String yyyyMMddHHmm_DATE_TIME_FORMAT = "yyyyMMddHHmm";
	private static final String yyyyMMdd_DATE_TIME_FORMAT = "yyyyMMdd";
	private static final String ddHHmmss_DATE_TIME_FORMAT = "ddHHmmss";
	private static final String HHmmss_TIME_FORMAT = "HHmmss";
	private static final String yyyyMMdd_DATE_FORMAT = "yyyy.MM.dd";
	private static final String yyyyMMdd_DATE_FORMAT_WITH_HYPHEN = "yyyy-MM-dd";
	private static final String yyyy_DATE_FORMAT = "yyyy";
	private static final String dd_DATE_FORMAT = "dd";
	private static final String d_DATE_FORMAT = "d";  // M/d/yyyy
	private static final String MdYYYY_FORMAT = "M/d/yyyy";
	private static final String MM_DATE_FORMAT = "MM";
	private static final String AMERICA_CHICAGO_TIMEZONE_ID = "America/Chicago";

	public static final String MMddyyyy_DATE_FORMAT = "MM/dd/yyyy";
	public static final String MMddyyyy_DATE_FORMAT_NO_LEADING_ZEROS = "M/d/yyyy";
	public static final String MMddyyyy_NO_DELIMETERS_DATE_FORMAT = "MMddyyyy";
	public static final String yyyyDashMMDashdd_HHmmss_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyDashMMDashdd_HHmm_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String yyyyDashMMDashdd_HH_DATE_TIME_FORMAT = "yyyy-MM-dd HH";
	public static final String ddDashMMMdashYY_DATE_TIME_FORMAT = "dd-MMM-yy";

	public static final int QUARTER_SECOND = 250;
	public static final int HALF_SECOND = 500;
	public static final int ONE_SECOND = 1000;
	public static final int ONE_AND_A_HALF_SECONDS = 1500;
	public static final int TWO_SECONDS = 2000;
	public static final int THREE_SECONDS = 3000;
	public static final int FOUR_SECONDS = 4000;
	public static final int FIVE_SECONDS = 5000;
	public static final int TEN_SECONDS = 10000;
	public static final int FIFTEEN_SECONDS = 15000;
	public static final int TWENTY_SECONDS = 20000;
	public static final int THIRTY_SECONDS = 30000;
	public static final int ONE_MINUTE = 60000;
	public static final int FIVE_MINUTES = 300000;
	public static final int TEN_MINUTES = 600000;
	public static final int THIRTY_MINTUES = 1800000;
	public static final int ONE_HOUR = 3600000;
	public static final int IMPLICIT_ELEMENT_WAIT_SECONDS = 45;
	public static final int PAGE_LOAD_TIMEOUT_SECONDS = 90;
	
	private static String getDateTimeInFormat(String format)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.now().atZone(ZoneId.of(AMERICA_CHICAGO_TIMEZONE_ID)).format(formatter);
	}

	private static String getTomorrowDateTimeInFormat(String format)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.now().plusDays(1).atZone(ZoneId.of(AMERICA_CHICAGO_TIMEZONE_ID)).format(formatter);
	}

	/**
	 * @return current date - 1 day in 'MM/dd/yyyy' format
	 * Example: 03/20/1992
	 */
	public static String getYesterdayDateMMddyyyy()
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MMddyyyy_DATE_FORMAT);
		return LocalDateTime.now().minus(Period.ofDays(1)).atZone(ZoneId.of(AMERICA_CHICAGO_TIMEZONE_ID)).format(formatter);
	}

	public static String getXdaysFromCurrentDateMMddyyy(int x)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MMddyyyy_DATE_FORMAT);
		return LocalDateTime.now().minus(Period.ofDays(x)).atZone(ZoneId.of(AMERICA_CHICAGO_TIMEZONE_ID)).format(formatter);
	}

	/**
	 * @return the current date in 'MM/dd/YYYY' format,
	 * Example: 3/2/1992
	 */
	public static String getCurrentDateMMddyyyyNoLeadingZeros()
    {
		return getDateTimeInFormat(MMddyyyy_DATE_FORMAT_NO_LEADING_ZEROS);
    }

	/**
	 * @return the current date in 'MM/dd/YYYY' format,
	 * Example: 03/20/1992
	 */
	public static String getCurrentDateMMddyyyy()
	{
		return getDateTimeInFormat(MMddyyyy_DATE_FORMAT);
	}

	/**
	 * @return the current date in 'MMddYYYY' format,
	 * Example: 03/20/1992
	 */
	public static String getCurrentDateMMddyyyyNoDelimeters()
	{
		return getDateTimeInFormat(MMddyyyy_NO_DELIMETERS_DATE_FORMAT);
	}

    public static String getTomorrowDateMMddyyyy()
    {
		return getTomorrowDateTimeInFormat(MMddyyyy_DATE_FORMAT);
    }

	/**
	 * @return the current date in 'dd MMM YYYY' format,
	 * Example: 20 Mar 1992
	 */
    public static String getCurrentDateddMMMYYYY()
    {
    	return getDateTimeInFormat(ddMMMyyyy_DATE_FORMAT);
	}

	public static String getCurrentDateMMMddyyyy()
	{
		return getDateTimeInFormat(MMMddyyyy_DATE_FORMAT);
	}

	/**
	 * @return the current date in 'dd-MMM-yy' format,
	 * Example: 05-Jan-20
	 */
	public static String getCurentDateDDDashMMMDashYY()
	{
		return getDateTimeInFormat(ddDashMMMdashYY_DATE_TIME_FORMAT);
	}

	/**
	 * @return the current date in 'M/d/yyyy' format,
	 * Example: M/D/YYY
	 */
	public static String getCurentDateMDYYYY()
	{
		return getDateTimeInFormat(MdYYYY_FORMAT);
	}



	/**
     * @return the current date and time in 'ddHHmmss' format,
     * Example: 20125959  
     */
    public static String getCurrentDateAndTimeddHHmmss()
    {
		return getDateTimeInFormat(ddHHmmss_DATE_TIME_FORMAT);
    }
    
    /**
	 * @return the current date and time in 'yyyyMMddHHmmss' format,
	 * Example: 19920320125959
	 */
    public static String getCurrentDateAndTimeyyyyMMddHHmmss()
    {
    	return getDateTimeInFormat(yyyyMMddHHmmss_DATE_TIME_FORMAT);
    }
    
    /**
	 * @return the current date and time in 'yyyyMMddHHmm' format,
	 * Example: 199203201259
	 */
    public static String getCurrentDateAndTimeyyyyMMddHHmm()
    {
    	return getDateTimeInFormat(yyyyMMddHHmm_DATE_TIME_FORMAT);
    }

	/**
	 * @return the current date and time in 'yyyy-mm-dd hh-mm,
	 * Example: 2020-09-30 9:39
	 */
	public static String getCurrentDateInTimeYYYYMMDDHHMM()
	{
		return getDateTimeInFormat(yyyyDashMMDashdd_HHmm_DATE_TIME_FORMAT);
	}

	/**
	 * @return the current date and time in 'yyyy-mm-dd hh,
	 * Example: 2020-09-30 9
	 */
	public static String getCurrentDateInTimeYYYYMMDDHH()
	{
		return getDateTimeInFormat(yyyyDashMMDashdd_HH_DATE_TIME_FORMAT);
	}

    /**
	 * @return the current year,
	 * Example: 1992
	 */
    public static String getCurrentYearyyyy()
    {
    	return getDateTimeInFormat(yyyy_DATE_FORMAT);
    }
    
    /**
	 * @return the current month,
	 * Example: 03
	 */
    public static String getCurrentMonthMM()
    {
    	return getDateTimeInFormat(MM_DATE_FORMAT);
    }
    
    /**
	 * @return the current day,
	 * Example: 20
	 */
    public static String getCurrentDayDD()
    {	
    	return getDateTimeInFormat(dd_DATE_FORMAT);
    }

	/**
	 * @return current date - 1 day in 'D' format
	 * Example: 9
	 */
	public static String getYesterdayDayDWithoutLeadingZero()
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(d_DATE_FORMAT);
		return LocalDateTime.now().minus(Period.ofDays(1)).atZone(ZoneId.of(AMERICA_CHICAGO_TIMEZONE_ID)).format(formatter);
	}

	/**
	 * @return current date in 'D' format
	 * Example: 9
	 */
	public static String getCurrentDayDWithoutLeadingZero()
	{
		return getDateTimeInFormat(d_DATE_FORMAT);
	}
	
    /**
	 * @return the current year,
	 * Example: 2020.01.16
	 */
    public static String getCurrrentDateyyyyMMdd()
    {
    	return getDateTimeInFormat(yyyyMMdd_DATE_FORMAT);
    }

	/**
	 * @return the current year,
	 * Example: 1992
	 */
	public static String getCurrentDateyyyyMMddWithHyphen()
	{
		return getDateTimeInFormat(yyyyMMdd_DATE_FORMAT_WITH_HYPHEN);
	}

    /**
	 * @return the current date time in milliseconds,
	 * Example: 1543956398626
	 */
    public static long getCurrentDateTimeInMilliseconds()
	{
    	return LocalDateTime.now().atZone(ZoneId.of(AMERICA_CHICAGO_TIMEZONE_ID)).toInstant().toEpochMilli();
	}
	
    /**
     * This is the generic version of getSpecificDateTimeInMilliseconds. There is a second version that assumes the WORKFLOW_DATE_FORMAT and AMERICA_CHICAGO_TIMEZONE_ID
     * @param date - the date you want to convert to milliseconds
     * @param dateFormat - the format the date is in for the parser
     * @param timeZoneID - the time zone ID for the time zone you're in
     * @return the milliseconds representation of the date,
     * Example: 1543956398626
     */
	public static long getSpecificDateTimeInMilliseconds(String date, String dateFormat, String timeZoneID)
	{
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
	    return LocalDateTime.parse(date, formatter).atZone(ZoneId.of(timeZoneID)).toInstant().toEpochMilli();
	}
	
	/**
     * This is the second version of getSpecificDateTimeInMilliseconds that assumes the WORKFLOW_DATE_FORMAT and AMERICA_CHICAGO_TIMEZONE_ID. Please use the generic version if the assumptions do not work for your situation.
     * @param date - the date you want to convert to milliseconds
     * @return the milliseconds representation of the date,
     * Example: 1543956398626
     */
	public static long getSpecificDateTimeInMilliseconds(String date)
	{
		return getSpecificDateTimeInMilliseconds(date, WORKFLOW_DATE_TIME_FORMAT, AMERICA_CHICAGO_TIMEZONE_ID);
	}
	
	/**
	 * This compares if the current date time and the date time passed in are equal.  It is currently deprecated as I'm not sure there is a use for it.
	 * @param date
	 * @param dateFormat
	 * @param timeZoneID
	 * @param accuracy
	 * @return
	 */
	@Deprecated
    public static boolean timeEqual(String date, String dateFormat, String timeZoneID,
    		int accuracy)
	{
		long firstDateTimeInMilliseconds = getCurrentDateTimeInMilliseconds();
		long secondDateTimeInMilliseconds = getSpecificDateTimeInMilliseconds(date, dateFormat, timeZoneID);
		return isDateTimesInMillisecondsEqual(firstDateTimeInMilliseconds, secondDateTimeInMilliseconds, accuracy);
	}
	
	/**
	 * This compares two date times within a specific accuracy to determine if they are equal.
	 * @param firstDate - the first date time you want to compare
	 * @param firstDateFormat - the format of the first date time you want to compare
	 * @param firstTimeZoneID - the time zone ID of the first date time you want to compare
	 * @param secondDate - the second date time you want to compare
	 * @param secondDateFormat - the format of the second date time you want to compare
	 * @param secondTimeZoneID - the time zone ID of the second date time you want to compare
	 * @param accuracy - the accuracy of the comparison
	 * @return a true or false value for if the date times being compared are equal within accuracy
	 */
	public static boolean isDateTimesEqual(String firstDate, String firstDateFormat, String firstTimeZoneID,
				 String secondDate, String secondDateFormat, String secondTimeZoneID,
				 int accuracy)
	{
		long firstDateTimeInMilliseconds = getSpecificDateTimeInMilliseconds(firstDate, firstDateFormat, firstTimeZoneID);
		long secondDateTimeInMilliseconds = getSpecificDateTimeInMilliseconds(secondDate, secondDateFormat, secondTimeZoneID);
		return isDateTimesInMillisecondsEqual(firstDateTimeInMilliseconds, secondDateTimeInMilliseconds, accuracy);
	}
	
	/**
	 * This compares two date times represented in milliseconds within a specific accuracy to determine if they are equal.
	 * @param firstDateTimeInMilliseconds
	 * @param secondDateTimeInMilliseconds
	 * @param accuracy
	 * @return a true or false value for if the date times being compared are equal within accuracy
	 */
	public static boolean isDateTimesInMillisecondsEqual(long firstDateTimeInMilliseconds, long secondDateTimeInMilliseconds, long accuracy)
	{
		return Math.abs(firstDateTimeInMilliseconds - secondDateTimeInMilliseconds) < accuracy;
	}
	
	public static String getCurrentTimeHHmmss()
	{
		return getDateTimeInFormat(HHmmss_TIME_FORMAT);
	}

	public static String getCurrentdateYYYYMMdd()
	{
		return getDateTimeInFormat(yyyyMMdd_DATE_TIME_FORMAT);
	}
	
	public static long getHHmmssTimeInMilliseconds(String timeHHmmss)
	{
		int hours = Integer.parseInt(timeHHmmss.substring(0, 2)) * 3600;
		int minutes = Integer.parseInt(timeHHmmss.substring(2, 4)) * 60;
		int seconds = Integer.parseInt(timeHHmmss.substring(4, 6));
		return (hours + minutes + seconds) * 1000;
	}
	
	/**
	 * Compares two dates
	 * @param firstDate
	 * @param secondDate
	 * @return a boolean indicating if firstDate is before secondDate
	 * @throws ParseException
	 */
	public static boolean compareDates(String firstDate, String secondDate) throws ParseException
	{
		return convertStringToDateObject(firstDate,"MM/d/yyyy").before(convertStringToDateObject(secondDate,"MM/d/yyyy"));
	}
	
	/**
	 * Converts a string date to a Date object
	 * @param date
	 * @param format
	 * @return returns a converted Date object
	 * @throws ParseException
	 */
	public static Date convertStringToDateObject(String date, String format)
	{
		Date returnDate = null;

		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			returnDate = formatter.parse(date);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return returnDate;
	}
	
	/**
	 * Gets the first date of the year.
	 * @param year
	 * @return a LocalDate object
	 * Example: 2020-01-01
	 */
	public static LocalDate getFirstDateOfYear(int year)
	{
		return LocalDate.of(year, 1, 1);
	}

	/**
	 * Converts a LocalDate object into MM/dd/yyyy format
	 * @param localDate
	 * @return a String of the converted LocalDate
	 * Example: 2020-01-01 to 01/01/2020
	 */
	public static String formatLocalDateMMddyyy(LocalDate localDate)
	{
		return DateTimeFormatter.ofPattern(MMddyyyy_DATE_FORMAT).format(localDate);
	}
	
	/**
	 * Example:<br>
	 * Current year 2019, it'll return 2019<br>
	 * Current year 2020, it'll return 2020<br>
	 * @return the most recent odd year
	 */
	public static String getMostRecentOddYearyyyy()
	{
		int integerYar = Integer.parseInt(getCurrentYearyyyy());
		String oddYear = integerYar % 2 == 0 ? (integerYar - 1) + "" : integerYar + "";
		return oddYear;
	}

	public static String convertDateToFormat(Date date, String format)
	{
		DateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(date);
	}

	public static String monthNameToNumber(String month)
	{
		return String.valueOf(Month.valueOf(month.toUpperCase()).getValue());
	}

	public static long getDateInMs(String date)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(WORKFLOW_DATE_TIME_FORMAT);
		return LocalDateTime.parse(date, formatter).atZone(ZoneId.of("America/Chicago")).toInstant().toEpochMilli();
	}

	public static String getCurrentDatePlusOneMonthMMddyyyy()
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MMddyyyy_DATE_FORMAT);
		return LocalDateTime.now().plusMonths(1).atZone(ZoneId.of(AMERICA_CHICAGO_TIMEZONE_ID)).format(formatter);
	}

	public static String getYesterdaysDateWithoutLeadingZeros()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/YYYY");
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return sdf.format(cal.getTime());
	}

	public static String getTomorrowsDateWithoutLeadingZeros()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/YYYY");
		cal.add(Calendar.DAY_OF_YEAR, + 1);
		return sdf.format(cal.getTime());
	}

	public static LocalDate getLocalDateFromStringDate(String dateAsString)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MMMMddyyyy_DATE_FORMAT, Locale.ENGLISH);
		return LocalDate.parse(dateAsString, formatter);
	}

	public static String getFormattedDate(String date)
	{
		return LocalDate.parse(date, DateTimeFormatter.ofPattern(MMddyyyy_DATE_FORMAT))
				.format(DateTimeFormatter.ofPattern("MMM. dd, yyyy"));
	}

	/**
	 * Verifies that the inputted time matches the correct format of HH:MM:SS A/PM
	 * @param timeOfAudit
	 * @return boolean if the input matches HH:MM:SS A/PM
	 */
	public static boolean timeFormatValidation(String timeOfAudit)
	{
		String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]([AP]M)";
		return Pattern.compile(regex).matcher(timeOfAudit).matches();
	}

	/**
	 * Replaces all calls to Thread.sleep and handles the
	 *
	 * @param timeToSleepInMilliSeconds
	 */
	public static void takeNap(int timeToSleepInMilliSeconds)
	{
		try
		{
			Thread.sleep(timeToSleepInMilliSeconds);
		}
		catch ( InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Replaces all calls to Thread.sleep and handles the
	 *
	 * @param timeToSleepInMilliSeconds
	 */
	public static void takeNap(long timeToSleepInMilliSeconds)
	{
		try
		{
			Thread.sleep(timeToSleepInMilliSeconds);
		}
		catch ( InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * reformat effective date from yyyy-mm-dd hh:mm:ss to mm/dd/yyyy
	 * @param dateInput
	 * @return dateOutput
	 */
	public static String reformatEffectiveDate(String dateInput)
	{
		if(dateInput == null)
		{
			return "";
		}
		String year = dateInput.substring(0,4);
		String month = dateInput.substring(5,7);
		String day = dateInput.substring(8,10);
		String dateOutput = month + "/" + day + "/" + year;
		return dateOutput;
	}

	/**
	 * reformat  date from MM/DD/YYYY to m/d/yyyy depending on the first char of mm and dd
	 * @param dateInput
	 * @return dateOutput
	 */
	public static String reformatModifiedDate(String dateInput)
	{
		String dateOutput = "";

		if(dateInput.charAt(0) == '0')
		{
			dateOutput = dateOutput + dateInput.substring(1,3);
		}
		else
		{
			dateOutput = dateOutput + dateInput.substring(0,3);
		}
		if(dateInput.charAt(3) == '0')
		{
			dateOutput = dateOutput + dateInput.substring(4,6);
		}
		else
		{
			dateOutput = dateOutput + dateInput.substring(3,6);
		}
		dateOutput = dateOutput + dateInput.substring(dateInput.length()-2);
		return dateOutput;
	}

}