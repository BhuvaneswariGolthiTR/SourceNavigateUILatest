package com.thomsonreuters.codes.codesbench.quality.utilities.aspects;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspectService extends TestSetupEdge
{
	/*
		<aop:pointcut expression="execution(* com.thomsonreuters.codes.codesbench.quality.pages..*(..))" id="stepPointcutPages" />
		<aop:pointcut expression="execution(* com.thomsonreuters.codes.codesbench.quality.menus..*(..))" id="stepPointcutMenus" />
		<aop:pointcut expression="execution(* com.thomsonreuters.codes.codesbench.quality.utilities..*(..))" id="stepPointcutUtilities" />
	*/
//	private static final String PAGES_POINTCUT = "execution(* com.thomsonreuters.codes.codesbench.quality.pages..*(..))";
//	private static final String MENUS_POINTCUT = "execution(* com.thomsonreuters.codes.codesbench.quality.menus..*(..))";
//	private static final String UTILITIES_POINTCUT = "execution(* com.thomsonreuters.codes.codesbench.quality.utilities..*(..))";

	private static final String MEGA_POINTCUT = "execution(* com.thomsonreuters.codes.codesbench.quality.pages..*(..)) || execution(* com.thomsonreuters.codes.codesbench.quality.menus..*(..)) || execution(* com.thomsonreuters.codes.codesbench.quality.utilities..*(..))";

	/*
		<aop:around method="stepLoggerAdvice" pointcut-ref="stepPointcutPages" arg-names="proceedingJoinPoint" />
		<aop:around method="stepLoggerAdvice" pointcut-ref="stepPointcutMenus" arg-names="proceedingJoinPoint" />
		<aop:around method="stepLoggerAdvice" pointcut-ref="stepPointcutUtilities" arg-names="proceedingJoinPoint" />
	*/
//	@Around(PAGES_POINTCUT)
//	public Object logAroundPages(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
//	{
//		String message = composeMessage(proceedingJoinPoint);
//		logger.step(message);
//		Object value;
//		try
//		{
//			value = proceedingJoinPoint.proceed();
//		}
//		catch (Throwable e)
//		{
//			logger.warning(message + " did not finish!");
//			//storeFailedTestInfo(proceedingJoinPoint.getSignature().getName());
//			throw e;
//		}
//		return value;
//	}
//
//	@Around(MENUS_POINTCUT)
//	public Object logAroundMenus(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
//	{
//		String message = composeMessage(proceedingJoinPoint);
//		logger.step(message);
//		Object value;
//		try
//		{
//			value = proceedingJoinPoint.proceed();
//		}
//		catch (Throwable e)
//		{
//			logger.warning(message + " did not finish!");
//			//storeFailedTestInfo(proceedingJoinPoint.getSignature().getName());
//			throw e;
//		}
//		return value;
//	}
//
//	@Around(UTILITIES_POINTCUT)
//	public Object logAroundUtilities(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
//	{
//		String message = composeMessage(proceedingJoinPoint);
//		logger.step(message);
//		Object value;
//		try
//		{
//			value = proceedingJoinPoint.proceed();
//		}
//		catch (Throwable e)
//		{
//			logger.warning(message + " did not finish!");
//			//storeFailedTestInfo(proceedingJoinPoint.getSignature().getName());
//			throw e;
//		}
//		return value;
//	}

	@Around(MEGA_POINTCUT)
	public Object logAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
	{
		String message = composeMessage(proceedingJoinPoint);
		logger.step(message);
		Object value;
		try
		{
			value = proceedingJoinPoint.proceed();
		}
		catch (Throwable e)
		{
			logger.warning(message + " did not finish!");
			//storeFailedTestInfo(proceedingJoinPoint.getSignature().getName());
			throw e;
		}
		return value;
	}

	private String composeMessage(ProceedingJoinPoint proceedingJoinPoint)
	{
		StringBuilder message = new StringBuilder();
		Object[] arguments = proceedingJoinPoint.getArgs();
		int argumentsNum = arguments.length;

		// name of executed method
		message.append(proceedingJoinPoint.getSignature().getName()).append("(");
		// if any args were passed, they would be listed
		if(argumentsNum != 0)
		{
			for (int index = 0; index < argumentsNum; index++)
			{
				message.append(arguments[index]);
				if(index != argumentsNum - 1)
				{
					message.append(", ");
				}
			}
		}
		message.append("): ");
		
		// page of executed method, and strip the object address
		String page = proceedingJoinPoint.getTarget().toString();
		page = page.substring(0, page.indexOf("@"));
		message.append(page);
		
		return message.toString();
	}

//	String infoFolderPath = null;
//	private void storeFailedTestInfo(String stepName) throws IOException, AWTException
//	{
//		createFolderForTheTest();
//
//		String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
//		takeScreenshot(stepName, currentTime);
//		saveLog(currentTime);
//	}
	
//	private void createFolderForTheTest()
//	{
//		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//		infoFolderPath = "failed_test_info" + File.separator + currentDate + "_" + testName;
//		new File(infoFolderPath).mkdirs();
//	}

//	private byte[] takeScreenshot(String stepName, String currentTime) throws IOException, AWTException
//	{
//		String screenshotPath = infoFolderPath + File.separator + currentTime + "_" + stepName + ".png";
//
//		// Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//		// BufferedImage capture = new Robot().createScreenCapture(screenRect);
//		// ImageIO.write(capture, "png", new File(screenshotPath));
//
//		TakesScreenshot screenshooter = (TakesScreenshot) driver();
//		File screenshot = new File(screenshotPath);
//		FileUtils.moveFile(screenshooter.getScreenshotAs(OutputType.FILE), screenshot);
//		return screenshooter.getScreenshotAs(OutputType.BYTES);
//	}
	
//	public void saveLog(String currentTime) throws IOException
//	{
//		String logPath = infoFolderPath + File.separator + currentTime + ".log";
//
//		FileUtils.copyFile(new File(tempLog), new File(logPath));
//	}
}
