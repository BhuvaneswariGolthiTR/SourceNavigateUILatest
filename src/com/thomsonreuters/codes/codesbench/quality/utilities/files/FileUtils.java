package com.thomsonreuters.codes.codesbench.quality.utilities.files;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.io.FileUtils.copyURLToFile;

public class FileUtils extends TestSetupEdge
{
	public static final String USER_HOME_PATH = System.getProperty("user.home");
	public static final String DOWNLOAD_FOLDER_PATH = USER_HOME_PATH + "\\Downloads";
	public static final String DOWNLOAD_FOLDER_DIR = DOWNLOAD_FOLDER_PATH + File.separator;
	public static final String DOWNLOAD_FOLDER = "Downloads";
	public static final String PDF_REPORT_FILE_NAME = "report.pdf";
	public static final String PDF_REPORT_FILE_PATH = System.getProperty(USER_HOME_PATH) + File.separator +
					DOWNLOAD_FOLDER + File.separator + PDF_REPORT_FILE_NAME;
	
	public static File getFile(String directoryPath, String fileName)
	{
		File fileDirectory = new File(directoryPath);
		File importantFile = null;
		if(fileDirectory.isDirectory())
		{
			File[] files = fileDirectory.listFiles();
			for (File file : files)
			{
				if(file.getName().contains(fileName))
				{
					importantFile = file;
					break;
				}
			}
		}
		return importantFile;
	}
	
	private static File getFile(String filePath)
	{
		return new File(filePath);
	}
	
	public static boolean updateNodeContentInFile(String filePath, String nodeToUpdate, String nodeToUpdateTo)
	{
		boolean fileUpdateCompleted = false;
		try
		{
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			FileInputStream fileInputStream = new FileInputStream(filePath);
			InputSource inputSource = new InputSource(fileInputStream);
			Document doc = docBuilder.parse(inputSource);
			
			Node secpNode = doc.getElementsByTagName("SECP").item(0);
			String content = secpNode.getTextContent() + "TEST";
			secpNode.setTextContent(content);
			
			DOMImplementationLS domImplementationLS = (DOMImplementationLS) doc.getImplementation().getFeature("LS", "3.0");
			LSOutput lsOutput = domImplementationLS.createLSOutput();
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			lsOutput.setByteStream((OutputStream) fileOutputStream);
			LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
			lsSerializer.write(doc, lsOutput);
			fileOutputStream.close();
			
			fileUpdateCompleted = true;
		}
		catch (Exception e)
		{
			logger.warning(e.getMessage());
		}
		return fileUpdateCompleted;
	}

	public static File loadFileFromUrl(String link)
	{
		File file = null;
		try
		{
			URL url = new URL(link);
			file = new File(FileUtils.PDF_REPORT_FILE_PATH);
			copyURLToFile(url, file);
		}
		catch(IOException e)
		{
			logger.warning("Unable to copy URL to file");
			e.printStackTrace();
		}
		return file;
	}
	
	public static List<File> getFilesInFolder(String folderPath)
	{
		File downloadFolder = new File(folderPath);
		
		return Arrays.asList(downloadFolder.listFiles());
	}
	
	public static List<String> getFileNamesInFolder(String folderPath)
	{
		return getFilesInFolder(folderPath).stream().map(e -> e.getName()).collect(Collectors.toList());
	}

	public static boolean doesFileExist(String directory, String fileName)
	{
		File file = getFile(directory, fileName);
		return file.exists();
	}

	public static boolean doesFileExist(String filePath)
	{
		return getFile(filePath).exists();
	}
	/**
	 * Deletes the file at the specified path. Returns true if it is successsfully deleted.
	 *
	 * @param filePath the file path
	 * @return boolean of whether the file is deleted
	 */
	public static boolean deleteFile(String filePath)
	{
		File file = getFile(filePath);
		return file.delete();
	}

	public static boolean deleteFilesByFileNameRegex(String dirPath, String fileNameRegex)
	{
		List<File> filesToDelete = getFilesByFileNameRegex(dirPath, fileNameRegex);
		boolean allFilesSuccessfullyDeleted = true;

		for (File file : filesToDelete)
		{
			if (!file.delete())
			{
				allFilesSuccessfullyDeleted = false;
				logger.warning("File " + file.getName() + " wasn't deleted!");
			} else
			{
				logger.information("File " + file.getName() + " was successfully deleted");
			}

		}
		return allFilesSuccessfullyDeleted;
	}

	public static boolean waitForFileToExist(String filePath, int milliseconds)
	{
		File file = getFile(filePath);
		long startTime = DateAndTimeUtils.getCurrentDateTimeInMilliseconds();
		
		while (!file.exists())
		{
			long currentTime =  DateAndTimeUtils.getCurrentDateTimeInMilliseconds();
			if (!DateAndTimeUtils.isDateTimesInMillisecondsEqual(startTime, currentTime, milliseconds))
			{
				return file.exists();
			}
		}
		return file.exists();
	}

	public static List<File> getFilesByFileNameRegex(String dirPath, String fileNameRegex)
	{
		List<File> filesByFileNameRegex = new ArrayList<>();
		List<File> dirFiles = getFilesInFolder(dirPath);

		if (!dirFiles.isEmpty())
		{
			for (File file : dirFiles)
			{
				if (file.isFile() && file.getName().matches(fileNameRegex))
				{
					filesByFileNameRegex.add(file);
				}
			}
		}
		return filesByFileNameRegex;
	}

	public static File getFileByFileNameRegex(String dirPath, String fileNameRegex)
	{
		List<File> downloadedFilesList = getFilesByFileNameRegex(dirPath, fileNameRegex);
		if (downloadedFilesList.size() == 1)
		{
			return downloadedFilesList.get(0);
		} else if (downloadedFilesList.size() > 1)
		{
			logger.warning("More than one file was found");
			return null;
		}
		logger.warning("No files were found");
		return null;
	}

	public static boolean waitForFileToExistByFileNameRegex(String dirPath, String fileNameRegex, int milliseconds)
	{
		long startTime = DateAndTimeUtils.getCurrentDateTimeInMilliseconds();
		while (getFilesByFileNameRegex(dirPath, fileNameRegex).isEmpty())
		{
			long currentTime = DateAndTimeUtils.getCurrentDateTimeInMilliseconds();
			if (!DateAndTimeUtils.isDateTimesInMillisecondsEqual(startTime, currentTime, milliseconds))
			{
				return false;
			}
		}
		return true;
	}

	public static String getAbsoluteFilePath(String shortFilePath)
	{
		return new File(shortFilePath).getAbsolutePath();
	}

	public static void deleteIfExists(File file)
	{
		try
		{
			Files.deleteIfExists(file.toPath());
		}
		catch (IOException e)
		{
			logger.information("File was not deleted");
			throw new RuntimeException(e);
		}
	}

	public static void deleteAllFilesContains(String dirPath, String fileName)
	{
		List<File> dirFiles = getFilesInFolder(dirPath);

		for (File file : dirFiles)
		{
			if (file.getName().contains(fileName))
			{
				deleteFile(dirPath + file.getName());
			}
		}
	}
}
