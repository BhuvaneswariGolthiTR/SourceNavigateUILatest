package com.thomsonreuters.codes.codesbench.quality.tests.example;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.RestAnnotations.REST;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class ExampleTest extends TestService
{
//	@RepeatedTest(value = 1, name = "{displayName}, repetition {currentRepetition} of {totalRepetitions}")
//	@CARSWELL
//	void devRiskRepeatedTest()
//	{
//		homePage().goToHomePage();
//		loginPage().logIn();
//		Assertions.assertTrue(loginPage().checkWindowIsPresented("Home Page"), "Switching to Home Page");
//		
//		//JUnit 5 multiple asserts
////		Assertions.assertAll
////		(
////			() -> Assertions.assertTrue(true, "Mocked assertion 1."),
////			() -> Assertions.assertFalse(true, "Mocked assertion 2."),
////			() -> Assertions.assertTrue(false, "Mocked assertion 3.")
////		);
//	}
//	
//	@Test
//	@LEGAL
//	void devLegalFailingTest()
//	{
//		homePage().goToHomePage();
//		loginPage().logIn();
//		Assertions.assertFalse(loginPage().checkWindowIsPresented("Home Page"), "Switching to Home Page");
//	}
//	
//	@ParameterizedTest
//	@MethodSource("stringArrayProvider")
//	@RISK
//	void uatRiskParameterizedTest(String[] array)
//	{
//		Stream<String> stream1 = Stream.of(array);
//        stream1.forEach(System.out::println);
//        
//		homePage().goToHomePage();
//		loginPage().logIn();
//		Assertions.assertTrue(loginPage().getTitle().equals("Home Page"), "We didn't navigate to the Home Page as expected");
//	}
//	
//	static Stream<Arguments> stringArrayProvider()
//	{
//	    return Stream.of(
//	    		 Arguments.of((Object) new String[]{"HI THERE"}),
//	    		 Arguments.of((Object) new String[]{"OH", " ", "HELLO"})
//	    );
//	}
	
	/*
	 * all correct annotations
	 * 
	 * expect this test to pass the setup, and sign on
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	void allCorrectAnnotationsTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		Assertions.assertTrue(loginPage().checkWindowIsPresented("Home Page"), "Switching to Home Page");
	}
	
	/*
	 * all correct annotations with rest
	 * 
	 * expect this test to pass the setup, and assert successfully
	 */
	@Test
	@REST
	@LOG
	void allCorrectAnnotationsWithRestTest()
	{
		Assertions.assertTrue(true, "Rest test passed setup");
	}
	
	/*
	 * missing log annotation with rest
	 * 
	 * expect this test to pass the setup, and assert successfully
	 */
	@Test
	@REST
	void missingLogAnnotationWithRest()
	{
		Assertions.assertTrue(true, "Rest test passed setup");
	}
	
	/*
	 * missing all annotations
	 * 
	 * expect this test to fail setup and not sign on with the following message:
	 * You didn't set the correct number of Annotations for this test...  Good job.
	 * Annotation presence and order should be: @Test, @EnvironmentAnnotation, @UserAnnotation if this is a non-rest test. @LogAnnotation is optional for tests.
	 * For rest tests, annotation presence and order should be: @Test, @RestAnnotation. @LogAnnotation is optional for tests.
	 * The CustomAnnotations class contains the available Environment, User, and Rest annotations.
	 */
	@Test
	void missingAllAnnotationsTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		Assertions.assertTrue(loginPage().checkWindowIsPresented("Home Page"), "Switching to Home Page");
	}
	
	/*
	 * missing all annotations except log
	 * 
	 * expect this test to fail setup and not sign on with the following message:
	 * You didn't set the correct number of Annotations for this test...  Good job.
	 * Annotation presence and order should be: @Test, @EnvironmentAnnotation, @UserAnnotation if this is a non-rest test. @LogAnnotation is optional for tests.
	 * For rest tests, annotation presence and order should be: @Test, @RestAnnotation. @LogAnnotation is optional for tests.
	 * The CustomAnnotations class contains the available Environment, User, and Rest annotations.
	 */
	@Test
	@LOG
	void missingAllAnnotationsExceptLogTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		Assertions.assertTrue(loginPage().checkWindowIsPresented("Home Page"), "Switching to Home Page");
	}
	
	/*
	 * incorrect order of annotations
	 * 
	 * expect this test to fail setup and not sign on with the following message:
	 * You didn't set the correct order/value of Annotations for this test...  Good job.
	 * Annotation presence and order should be: @Test, @EnvironmentAnnotation, @UserAnnotation if this is a non-rest test. @LogAnnotation is optional for tests.
	 * For rest tests, annotation presence and order should be: @Test, @RestAnnotation. @LogAnnotation is optional for tests.
	 * The CustomAnnotations class contains the available Environment, User, and Rest annotations.
	 */
	@Test
	@LOG
	@LEGAL
	@IE_EDGE_MODE
	void incorrectOrderOfAnnotationsTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		Assertions.assertTrue(loginPage().checkWindowIsPresented("Home Page"), "Switching to Home Page");
	}
	
	
}
