package com.thomsonreuters.codes.codesbench.quality.utilities.annotations;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

public class CustomAnnotations
{
	public static class LogAnnotations
	{
		public static final String LOG = "log";
		public static final List<String> logAnnotations = Arrays.asList(LOG);
		
		@Target({ElementType.TYPE, ElementType.METHOD})
		@Retention(RetentionPolicy.RUNTIME)
		@Tag(LOG)
		public @interface LOG {}
	}
	
	public static class RestAnnotations
	{
		public static final String REST = "rest";
		public static final List<String> restAnnotations = Arrays.asList(REST);
		
		@Target({ElementType.TYPE, ElementType.METHOD})
		@Retention(RetentionPolicy.RUNTIME)
		@Tag(REST)
		public @interface REST {}
	}
	
	public static class UserAnnotations
	{
		public static final String CARSWELL = "carswell";
		public static final String CITELINE = "citeline";
		public static final String LEGAL = "legal";
		public static final String RISK = "risk";
		public static final String PRODLEGAL = "prodlegal";
		public static final List<String> userAnnotations = Arrays.asList(CARSWELL, CITELINE, LEGAL, RISK, PRODLEGAL);
		
		//CARSWELL
		@Target({ElementType.TYPE, ElementType.METHOD})
		@Retention(RetentionPolicy.RUNTIME)
		@Tag(CARSWELL)
		public @interface CARSWELL {}

		//CITELINE
		@Target({ElementType.TYPE, ElementType.METHOD})
		@Retention(RetentionPolicy.RUNTIME)
		@Tag(CITELINE)
		public @interface CITELINE {}
		
		//LEGAL
		@Target({ElementType.TYPE, ElementType.METHOD})
		@Retention(RetentionPolicy.RUNTIME)
		@Tag(LEGAL)
		public @interface LEGAL {}
		
		//RISK
		@Target({ElementType.TYPE, ElementType.METHOD})
		@Retention(RetentionPolicy.RUNTIME)
		@Tag(RISK)
		public @interface RISK {}
		
		//PRODLEGAL
		@Target({ElementType.TYPE, ElementType.METHOD})
		@Retention(RetentionPolicy.RUNTIME)
		@Tag(PRODLEGAL)
		public @interface PRODLEGAL {}
	}

	public static class BrowserAnnotations
	{
		public static final String IE_EDGE_MODE = "ie_edge_mode";
		public static final String EDGE = "edge";
		public static final String CHROME = "chrome";
		public static final List<String> browserAnnotations = Arrays.asList(IE_EDGE_MODE, EDGE, CHROME);

		//IE_EDGE_MODE
		@Target({ElementType.TYPE, ElementType.METHOD})
		@Retention(RetentionPolicy.RUNTIME)
		@Tag(IE_EDGE_MODE)
		public @interface IE_EDGE_MODE {}

		//EDGE
		@Target({ElementType.TYPE, ElementType.METHOD})
		@Retention(RetentionPolicy.RUNTIME)
		@Tag(EDGE)
		public @interface EDGE {}

		//CHROME
		@Target({ElementType.TYPE, ElementType.METHOD})
		@Retention(RetentionPolicy.RUNTIME)
		@Tag(CHROME)
		public @interface CHROME {}
	}
}
