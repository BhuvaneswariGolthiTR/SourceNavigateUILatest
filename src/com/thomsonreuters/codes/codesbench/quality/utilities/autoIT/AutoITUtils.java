package com.thomsonreuters.codes.codesbench.quality.utilities.autoIT;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AutoITUtils extends TestSetupEdge
{
	public static String getPingIdDesktopPasscodeWithAutoIT(String pingIdUnlockPIN)
	{
		String passcode = null;
		try
		{
			//copy to buffer PingID desktop passcode:
			String scriptFilePath =
					new File("commonFiles\\AutoITScripts\\CopyToClipboardPingIDDesktopPasscode.exe").getAbsolutePath();
			Process process = new ProcessBuilder(scriptFilePath, pingIdUnlockPIN).start();
			process.waitFor();

			//get copied passcode from clipboard:
			Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
			String bufferContent = c.getData(DataFlavor.stringFlavor).toString();
			passcode = bufferContent.matches("\\d{6}") ? bufferContent : null;
		}
		catch (Exception e)
		{
			logger.warning("Something went wrong while getting PingID passcode with AutoIT script:\n"
					+ e.getMessage() + "\n"
					+ "Please verify the following:\n"
					+ "1) desktop PingID app installed on Windows desktop where tests run\n"
					+ "2) the PingID installation directory added to PATH environment variable\n"
					+ "3) Windows desktop where tests run is paired and set as primary authentication device in PingID settings for the test user)");
		}
		return passcode;
	}

	public static void handleChooseFileToUploadWindowWithAutoIT(String filename)
	{
		try
		{
			String filePath = new File("commonFiles\\TestFiles\\" + filename).getAbsolutePath();
			String scriptFilePath;
			/*
			The Edge browser opens the select file window with an 'Open' title, not the 'Choose File to Upload' we see with Edge IE Mode
			 */
			if(TestSetupEdge.getBrowserTag().equals(CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE))
			{
				scriptFilePath = new File("commonFiles\\AutoITScripts\\SelectFileInChooseFileToUploadWindow.exe").getAbsolutePath();
			}
			else
			{
				scriptFilePath = new File("commonFiles\\AutoITScripts\\SelectFileInChooseFileToUploadWindowEdge.exe").getAbsolutePath();
			}
			Process process = new ProcessBuilder(scriptFilePath, filePath).start();
			process.waitFor();
		}
		catch (IOException e)
		{
			logger.warning(e.getMessage());
		}
		catch (InterruptedException e)
		{
			logger.warning(e.getMessage());
		}
	}
	
	public static void clickSaveOnIEPopupWithAutoIT()
	{
		String scriptFilePath = new File("commonFiles\\AutoITScripts\\ClickSaveOnIEPopup.exe").getAbsolutePath();
		Process process;
		try
		{
			process = new ProcessBuilder(scriptFilePath).start();
			process.waitFor();
		}
		catch (IOException e)
		{
			logger.warning(e.getMessage());
		}
		catch (InterruptedException e)
		{
			logger.warning(e.getMessage());
		}
	}

	public static void clickSaveOnIEPopupRawXmlEditorWithAutoIT()
	{
		String scriptFilePath = new File("commonFiles\\AutoITScripts\\ClickSaveOnIEPopupRawXmlEditor.exe").getAbsolutePath();
		Process process;
		try
		{
			process = new ProcessBuilder(scriptFilePath).start();
			process.waitFor();
		}
		catch (IOException e)
		{
			logger.warning(e.getMessage());
		}
		catch (InterruptedException e)
		{
			logger.warning(e.getMessage());
		}
	}

	public static void clickSaveOnIERibbonWithAutoIT()
	{
		String scriptFilePath = new File("commonFiles\\AutoITScripts\\ClickSaveOnIERibbon.exe").getAbsolutePath();
		try
		{
			Process process = new ProcessBuilder(scriptFilePath).start();
			process.waitFor();
		}
		catch (IOException e)
		{
			logger.warning(e.getMessage());
		}
		catch (InterruptedException e)
		{
			logger.warning(e.getMessage());
		}
	}

	public static void clickSaveOnIEToolboxWithAutoIT()
	{
		String scriptFilePath = new File("commonFiles\\AutoITScripts\\ClickSaveOnIEToolbox.exe").getAbsolutePath();
		try
		{
			Process process = new ProcessBuilder(scriptFilePath).start();
			process.waitFor();
		}
		catch (IOException e)
		{
			logger.warning(e.getMessage());
		}
		catch (InterruptedException e)
		{
			logger.warning(e.getMessage());
		}
	}

	public static boolean verifyAlertTextContainsAndAccept(boolean alertExpected, String... alertExpectedMessage)
	{
		String stringAlertExpected = String.valueOf(alertExpected);
		String scriptFilePath = new File("commonFiles\\AutoITScripts\\ValidateFindAlertContains.exe").getAbsolutePath();
		ProcessBuilder processBuilder;
		int exitValue = 0;
		if(alertExpectedMessage.length > 0)
		{
			processBuilder = new ProcessBuilder(scriptFilePath, stringAlertExpected, alertExpectedMessage[0]);
		}
		else
		{
			processBuilder = new ProcessBuilder(scriptFilePath, stringAlertExpected);
		}
		try
		{
			Process process = processBuilder.start();
			InputStream responseInputStream = process.getInputStream();
			StringBuffer stringBuffer = new StringBuffer();
			int character;

			while((character = responseInputStream.read()) != -1)
			{
				stringBuffer.append((char) character);
			}
			System.out.println("String: " + stringBuffer.toString());
			process.waitFor();
			exitValue = process.exitValue();
		}
		catch (IOException e)
		{
			logger.warning(e.getMessage());
		}
		catch (InterruptedException e)
		{
			logger.warning(e.getMessage());
		}
		// exitValue = 1 when the script returns a valid alert response
		// exitValue = 0 when the script returns an invalid alert response
		return exitValue == 1;
	}

	public static String getWorkflowIdFromSourceNavigateAlert(boolean alertExpected, String... alertExpectedMessage)
	{
		String workflowID = "";
		String stringAlertExpected = String.valueOf(alertExpected);
		String scriptFilePath = new File("commonFiles\\AutoITScripts\\SourceNavigateWorkflowAlert.exe").getAbsolutePath();
		ProcessBuilder processBuilder;
		int exitValue = 0;
		if(alertExpectedMessage.length > 0)
		{
			processBuilder = new ProcessBuilder(scriptFilePath, stringAlertExpected, alertExpectedMessage[0]);
		}
		else
		{
			processBuilder = new ProcessBuilder(scriptFilePath, stringAlertExpected);
		}
		try
		{
			Process process = processBuilder.start();
			InputStream responseInputStream = process.getInputStream();
			StringBuffer stringBuffer = new StringBuffer();
			int character;

			while((character = responseInputStream.read()) != -1)
			{
				stringBuffer.append((char) character);
			}
			String workflowMessage = stringBuffer.toString();
			System.out.println("String: " + workflowMessage);
			process.waitFor();
			exitValue = process.exitValue();
			Assertions.assertTrue(exitValue == 0, "Process completed successfully. Check alert matches expected alert.");
			workflowID = workflowMessage.substring(stringBuffer.indexOf(":") + 1);
		}
		catch (IOException e)
		{
			logger.warning(e.getMessage());
		}
		catch (InterruptedException e)
		{
			logger.warning(e.getMessage());
		}
		return workflowID;
	}

	public static boolean verifyAlertTextAndAccept(boolean alertExpected, String... alertExpectedMessage)
	{
		return verifyAlertTextWithScript(
				"commonFiles\\AutoITScripts\\ValidateFindAlert.exe",
				alertExpected,
				alertExpectedMessage);
	}

	public static boolean verifyAlertTextAndCancel(boolean alertExpected, String... alertExpectedMessage)
	{
		return verifyAlertTextWithScript(
				"commonFiles\\AutoITScripts\\ValidateAlertTextAndCancel.exe",
				alertExpected,
				alertExpectedMessage);
	}

	private static boolean verifyAlertTextWithScript(String pathToScript, boolean alertExpected, String... alertExpectedMessage)
	{
		String stringAlertExpected = String.valueOf(alertExpected);
		String scriptFilePath = new File(pathToScript).getAbsolutePath();
		ProcessBuilder processBuilder;
		int exitValue = 0;
		if(alertExpectedMessage.length > 0)
		{
			processBuilder = new ProcessBuilder(scriptFilePath, stringAlertExpected, alertExpectedMessage[0]);
		}
		else
		{
			processBuilder = new ProcessBuilder(scriptFilePath, stringAlertExpected);
		}
		try
		{
			Process process = processBuilder.start();
			InputStream responseInputStream = process.getInputStream();
			StringBuffer stringBuffer = new StringBuffer();
			int character;

			while((character = responseInputStream.read()) != -1)
			{
				stringBuffer.append((char) character);
			}
			System.out.println("String: " + stringBuffer.toString());
			process.waitFor();
			exitValue = process.exitValue();
		}
		catch (IOException e)
		{
			logger.warning(e.getMessage());
		}
		catch (InterruptedException e)
		{
			logger.warning(e.getMessage());
		}
		// exitValue = 1 when the script returns a valid alert response
		// exitValue = 0 when the script returns an invalid alert response
		return exitValue == 1;
	}
	
	public static void sendHotKey(Keys... keys)
	{
		// Create a string with all of our Keys, separated by a space
		String stringKeys = keyStringBuilder(keys);

		// Create a process builder and pass our values to the script, then start it and wait for it to finish
		String scriptFilePath = new File("commonFiles\\AutoITScripts\\SendHotkeys.exe").getAbsolutePath();
		ProcessBuilder processBuilder = new ProcessBuilder(scriptFilePath, stringKeys);
		runScriptDebug(processBuilder);
	}

	public static void sendHotKey(String numberOrLetter, Keys... keys)
	{
		// Create a string with all of our Keys, separated by a space
		String stringKeys = keyStringBuilder(keys);

		// Create a process builder and pass our values to the script, then start it and wait for it to finish
		String scriptFilePath = new File("commonFiles\\AutoITScripts\\SendHotkeys.exe").getAbsolutePath();
		ProcessBuilder processBuilder = new ProcessBuilder(scriptFilePath, stringKeys, numberOrLetter);
		runScriptDebug(processBuilder);
	}

	private static String keyStringBuilder(Keys... keys)
	{
		// Create a string with all of our Keys, separated by a space so that we can split the string into an array within the script.
		Map<String, String> keyMap = hotKeyMap();
		String stringKeys = "";
		for (Keys key : keys)
		{
			if(keyMap.containsKey(key.name()))
			{
				stringKeys += " " + keyMap.get(key.name());
			}
			else
			{
				stringKeys += " " + "{"+key.name()+"}";
			}
		}
		// remove leading and trailing whitespace
		stringKeys = stringKeys.trim();

		return stringKeys;
	}
	
	private static boolean runScript(ProcessBuilder processBuilder)
	{
		int exitValue = 0;
		try
		{
			Process process = processBuilder.start();
			process.waitFor();
			exitValue = process.exitValue();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		// exitValue = 1 when the script returns a valid alert response
		// exitValue = 0 when the script returns an invalid alert response
		return exitValue == 1;
	}
	
	private static boolean runScriptDebug(ProcessBuilder processBuilder)
	{
		int exitValue = 0;
		try
		{
			Process process = processBuilder.start();
			InputStream responseInputStream = process.getInputStream();
			StringBuffer stringBuffer = new StringBuffer();
			int character;

			while((character = responseInputStream.read()) != -1)
			{
				stringBuffer.append((char) character);
			}
//			System.out.println("String: " + stringBuffer.toString());
			process.waitFor();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		// exitValue = 1 when the script returns a valid alert response
		// exitValue = 0 when the script returns an invalid alert response
		return exitValue == 1;
	}

	public static void clickKeepOnEdgePopupWithAutoIT()
	{
		String scriptFilePath = new File("commonFiles\\AutoITScripts\\ClickKeepEdgeRibbon.exe").getAbsolutePath();
		try
		{
			Process process = new ProcessBuilder(scriptFilePath).start();
			process.waitFor();
		}
		catch (IOException e)
		{
			logger.warning(e.getMessage());
		}
		catch (InterruptedException e)
		{
			logger.warning(e.getMessage());
		}
	}

	private static Map<String, String> hotKeyMap()
	{
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("CONTROL", "^");
		// You cannot send a raw '!' through the cmd line, so we surround with quotation marks.
		keyMap.put("ALT", "\"!\"");
		keyMap.put("SHIFT", "+");
		return keyMap;
	}

	/**
	 * This method highlights and copies text wherever the cursor currently is.
	 */
	public static void highlightAndCopyText()
	{
		AutoITUtils.sendHotKey(Keys.SHIFT, Keys.HOME);
		AutoITUtils.sendHotKey("c", Keys.CONTROL);
	}
}
