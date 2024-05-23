package com.thomsonreuters.codes.codesbench.quality.utilities.rest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class RestUtils
{
	public static final String POST = "POST";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String APPLICATION_XML = "application/xml";
	public static final String TEXT_PLAIN = "text/plain";
	public static final String GET = "GET";

	public static HttpURLConnection connectToURLWithMethod(String url, String method)
    {
		HttpURLConnection connection = openUrlConnection(url);
		try
		{
			connection.setRequestMethod(method);
			connection.connect();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
			
		return connection;
    }
    
    public static int getResponseCode(HttpURLConnection connection) 
    {
    	int responseCode = 0;
    	try 
    	{
			responseCode = connection.getResponseCode();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	
    	return responseCode;
    }
    
    public static String getResponseBody(HttpURLConnection connection) 
    {
    	BufferedReader br;
    	try
    	{
    		br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
    	}
    	catch (IOException e)
    	{
    		br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
    	}
	    StringBuilder sb = new StringBuilder();
	    String output;
	    
	    try 
	    {
			while ((output = br.readLine()) != null)
			{
				sb.append(output);
			}
		} 
	    catch (IOException e) 
	    {
			e.printStackTrace();
		}
	    
	    return sb.toString();
    }
    
    private static HttpURLConnection openUrlConnection(String url)
    {
    	HttpURLConnection connection = null;
		try 
		{
			URL newUrlConnection = new URL(url);
			connection = (HttpURLConnection) newUrlConnection.openConnection();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	    
	    
    	return connection;
    }
    
    public static HttpURLConnection connectToURLWithMethodAndBody(String url, String method, String body)
    {
    	HttpURLConnection connection = openUrlConnection(url);
    	
    	try
    	{
		    connection.setRequestMethod(method);
		    connection.setDoOutput(true);
		    byte[] outputInBytes = body.getBytes("UTF-8");
		    OutputStream os = connection.getOutputStream();
		    os.write( outputInBytes );    
		    os.close();
		    connection.connect();
    	}
    	catch (IOException e) 
		{
			e.printStackTrace();
		}
    	
    	return connection;
    }
    
    public static HttpURLConnection connectToRestConnection(String url, String requestMethod, String requestPropertyKey, String requestPropertyValue)
	{
    	HttpURLConnection connection = openUrlConnection(url);
		
    	try
    	{
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod(requestMethod);
			connection.setRequestProperty(requestPropertyKey, requestPropertyValue);
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    	
		return connection;
	}
    
    public static HttpURLConnection connectToRestConnectionWithNoRequestProperty(String url, String requestMethod) 
	{
    	HttpURLConnection connection = openUrlConnection(url);

    	connection.setDoInput(true);
    	connection.setDoOutput(true);
    	try 
    	{
			connection.setRequestMethod(requestMethod);
		} 
    	catch (ProtocolException e) 
    	{
			e.printStackTrace();
		}
	
		return connection;
	}
    
	public static String writeToRestServiceAndGetStringResponseFromInputStream(HttpURLConnection connection, String xml) 
	{		
		InputStream responseInputStream = null;
		
		try 
		{
			connection.getOutputStream().write(xml.getBytes());
			responseInputStream = connection.getInputStream();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return getStringFromInputStream(responseInputStream);
	}
	
	public static void writeToRestService(HttpURLConnection connection, String xml) 
	{		
		try 
		{
			connection.getOutputStream().write(xml.getBytes());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static String getStringFromInputStream(InputStream inputStream)
	{
		StringBuffer stringBuffer = new StringBuffer();
		int character;

		try 
		{
			while((character = inputStream.read()) != -1)
			{
				stringBuffer.append((char) character);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return stringBuffer.toString();
	}
	
	public static String getRestServiceResponseFromInputStreamWithoutWrite(HttpURLConnection connection) 
	{
		InputStream responseInputStream = null;
		try 
		{
			responseInputStream = connection.getInputStream();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	
		return getStringFromInputStream(responseInputStream);
	}
	
	public static boolean isResponseCodeOk(HttpURLConnection restConnection)
	{
		boolean result = false;
		
		try 
		{
			result = restConnection.getResponseCode() == 200;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
    public static void disconnectFromRestConnection(HttpURLConnection connection)
	{
		connection.disconnect();
	}
}
