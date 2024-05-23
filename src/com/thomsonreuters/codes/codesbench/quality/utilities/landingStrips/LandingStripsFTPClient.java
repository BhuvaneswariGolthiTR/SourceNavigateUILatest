package com.thomsonreuters.codes.codesbench.quality.utilities.landingStrips;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.jupiter.api.Assertions;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * This class provides functionality to connect to the UAT landing strips and work with files
 * in the landing strip.  It is an FTP server.
 *
 * Please see the associated Example.java class for an example of how to use the class.
 */
@CustomAnnotations.LogAnnotations.LOG
public class LandingStripsFTPClient
{
    private String hostname = "C778APSCWB1.int.thomsonreuters.com";

    private String popnamesHostname = "C677VTFCWB2.int.thomsonreuters.com";
    private String userName = "asadmin";
    private String passWord = "east";
    private FTPClient client;

    
    public void connectToServer()
    {
        connectToServerHelper(hostname);
    }

    public void connectToPopnamesServer()
    {
        connectToServerHelper(popnamesHostname);
    }

    private void connectToServerHelper(String serverHostname)
    {
        client = new FTPClient();
        try
        {
            client.connect(serverHostname);
            client.login(userName, passWord);
            client.enterLocalPassiveMode();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    
    public void disconnectFromServer()
    {
        if(isConnected())
        {
            try
            {
                client.logout();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    client.disconnect();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isConnected()
    {
        return client.isConnected();
    }

    
    public boolean checkDirectoryExistence(String directoryPath)
    {
        int code = 0;
        try
        {
            //250 exists
            //550 doesn't exist
            code = client.cwd(directoryPath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return (code == 250);
    }

    
    public boolean checkFileExistence(String directoryPath, String file)
    {
        boolean found = false;
        try
        {
            FTPFile[] files = listFiles(directoryPath);
            List<FTPFile> fileList = Arrays.asList(files);
            found = fileList.stream().anyMatch(fileListItem -> fileListItem.getName().contains(file));
//			The line above is basically doing this
//			if(files.length > 0)
//			{
//				for (int i = 0; i < files.length; i++)
//				{
//					if(files[i].getName().contains(file))
//					{
//						found = true;
//						break;
//					}
//				}
//			}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return found;
    }

    public boolean checkFileExtensionExistence(String directoryPath, String fileExtension)
    {
        boolean found = false;
        try
        {
            FTPFile[] files = client.listFiles(directoryPath);
            List<FTPFile> fileList = Arrays.asList(files);
            found = fileList.stream().anyMatch(fileListItem -> fileListItem.getName().contains(fileExtension));
//			The line above is basically doing this
//			if(files.length > 0)
//			{
//				for (int i = 0; i < files.length; i++)
//				{
//					if(files[i].getName().contains(fileExtension))
//					{
//						found = true;
//						break;
//					}
//				}
//			}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return found;
    }

    public FTPFile[] listFiles(String directoryPath) throws IOException
    {
        return client.listFiles(directoryPath);
    }

    public File download(String source, String target) throws IOException
    {
        File f = new File(target);

        client.setFileType(FTP.BINARY_FILE_TYPE);
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(target, true));
        client.retrieveFile(source, outputStream);
        outputStream.close();

        return f;
    }

    public void upload(String source, String target)
    {
        try
        {
            client.setFileType(FTP.BINARY_FILE_TYPE);
        }
        catch (IOException e)
        {
            Assertions.fail("There was an exception setting the file type to FTP.BINARY_FILE_TYPE (2)");
        }

        File file = new File(source);
        InputStream inputStream = null;

        try
        {
            inputStream = new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            Assertions.fail("There was a File Not Found Exception trying to create an input stream with the file: " + file.getName());
        }
        try
        {
            client.storeFile(target, inputStream);
        }
        catch (IOException e)
        {
            Assertions.fail("There was an exception trying to store the file: " + inputStream.toString() + " in the target: " + target);
        }
        try
        {
            inputStream.close();
        }
        catch (IOException e)
        {
            Assertions.fail("There was an exception trying to close the input stream: " + inputStream.toString());
        }
    }

    public void makeDirectory(String directory)
    {
        try
        {
            client.makeDirectory(directory);
        }
        catch(IOException e)
        {
            Assertions.fail("There was an IOException when making the directory: " + directory);
        }
    }

    public void deleteDirectory(String directory)
    {
        FTPFile[] files = null;
        try
        {
            files = listFiles(directory);
        }
        catch (IOException e)
        {
            Assertions.fail("Failed to list files from given directory: " + directory);
        }
        if (files != null)
        {
            for (FTPFile file : files)
            {
                try
                {
                    client.deleteFile(directory + "/" + file.getName());
                }
                catch (IOException e)
                {
                    Assertions.fail("Failed to delete given file: " + file.getName());
                }
            }
        }
        try
        {
            client.removeDirectory(directory);
        }
        catch (IOException e)
        {
            Assertions.fail("Failed to remove directory: " + directory);
        }
    }

    public List<String> getNodeContentFromFile(String directoryPath, String file, String node)
    {
        List<String> nodeContent = new ArrayList<>();
        String source = directoryPath + "/" + file;
        InputStream inputStream = null;
        if(checkFileExistence(directoryPath, file))
        {
            try
            {
                client.setFileType(FTP.BINARY_FILE_TYPE);
                inputStream = client.retrieveFileStream(source);
                client.completePendingCommand();
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(inputStream);
                NodeList nodesOfInterest = doc.getElementsByTagName(node);
                for(int i = 0; i < nodesOfInterest.getLength(); i++)
                {
                    nodeContent.add(nodesOfInterest.item(i).getTextContent());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    inputStream.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return nodeContent;
    }

    public List<String> getNodeContentFromFileByDownloading(String directoryPath, String file, String target, String node)
    {
        List<String> nodeContent = new ArrayList<>();
        String source = directoryPath + "/" + file;
        FileInputStream inputStream = null;

        if(checkFileExistence(directoryPath, file))
        {
            try
            {
                download(source, target);

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder;
                Document doc;

                inputStream = new FileInputStream(target);

                docBuilder = docFactory.newDocumentBuilder();
                doc = docBuilder.parse(inputStream);
                NodeList nodesOfInterest = doc.getElementsByTagName(node);

                for(int i = 0; i < nodesOfInterest.getLength(); i++)
                {
                    nodeContent.add(nodesOfInterest.item(i).getTextContent());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    inputStream.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }


        }

        return nodeContent;
    }

    public boolean checkFileForString(String directoryPath, String file, String contains)
    {
        boolean found = false;
        String source = directoryPath + "/" + file;
        InputStream inputStream = null;
        if(checkFileExistence(directoryPath, file))
        {
            try
            {
                client.setFileType(FTP.BINARY_FILE_TYPE);
                inputStream = client.retrieveFileStream(source);
                client.completePendingCommand();
                BufferedReader veryBuffReader = new BufferedReader(new InputStreamReader(inputStream));
                String fileLine = "";
                while((fileLine = veryBuffReader.readLine()) != null)
                {
                    if(fileLine.contains(contains))
                    {
                        found = true;
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    inputStream.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return found;
    }

    public void changeWorkingDirectory(String directoryPath)
    {
        try
        {
            client.changeWorkingDirectory(directoryPath);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getFileCountInDirectory(String directory, String extension)
    {

        int fileCount = 0;
        FTPFile[] ftpFiles = null;
        try
        {
            changeWorkingDirectory(directory);
            ftpFiles = listFiles(directory);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if((ftpFiles != null) && (ftpFiles.length > 0))
        {
            for (FTPFile file : ftpFiles)
            {
                if(file.getName().endsWith(extension))
                {
                    fileCount++;
                }
            }
        }
        return fileCount;
    }
}
