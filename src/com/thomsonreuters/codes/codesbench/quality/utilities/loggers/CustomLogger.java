package com.thomsonreuters.codes.codesbench.quality.utilities.loggers;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.logging.*;


public class CustomLogger
{
	Logger logger;
	ConsoleHandler consoleHandler;
//	FileHandler fileHandler;
	
	public CustomLogger(Set<String> tags) throws IOException
	{
		logger = Logger.getLogger(this.getClass().getName());
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.FINE);

		/*
		 * to console
		 */
		if (tags.contains(CustomAnnotations.LogAnnotations.LOG))
		{
			consoleHandler = new ConsoleHandler();
			logger.addHandler(consoleHandler);
			consoleHandler.setFormatter(new LoggingFormatter());
			consoleHandler.setLevel(Level.ALL);
		}
		
		/*
		 * to file
		 */
//		fileHandler = new FileHandler(tempLog);
//		logger.addHandler(fileHandler);
//		fileHandler.setFormatter(new LoggingFormatter());
//		fileHandler.setLevel(Level.FINE);
	}
	
	public void information(String message)
	{
		logger.info(message);
	}
	
	public void warning(String message)
	{
		logger.warning(message);
	}
	
	public void step(String message)
	{
		logger.fine(message);
	}

	public void severe(String message)
	{
		logger.severe(message);
	}
	
	public void closeTempLog()
	{
//		fileHandler.close();
//		new File(tempLog).delete();
		
		if (consoleHandler != null)
		{
			logger.removeHandler(consoleHandler);
		}
		
//		logger.removeHandler(fileHandler);
	}
	
	class LoggingFormatter extends Formatter
	{
	    @Override
	    public String format(LogRecord record)
	    {
	        StringBuilder builder = new StringBuilder();
	        
	        LocalDateTime currentTime = LocalDateTime.now(/*ZoneId.of("America/Chicago")*/);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			builder.append("\u001b[36m");//Cyan
	        builder.append("[" + formatter.format(currentTime) + "] ");
	        
	        if (record.getLevel().equals(Level.FINE))
	        {
	        	builder.append("\u001b[32m");//Green
	        	builder.append("STEP: ");
	        }
	        else if (record.getLevel().equals(Level.WARNING))
	        {
	        	builder.append("\u001b[33;1m");//Bright Yellow
	        	builder.append("WARN: ");
	        }
			else if (record.getLevel().equals(Level.SEVERE))
			{
				builder.append("\u001b[31m");//Red
				builder.append("SEVERE: ");
			}
			else if (record.getLevel().equals(Level.INFO))
			{
				builder.append("\u001b[36m");//Cyan
				builder.append("INFO: ");
			}
	        else
	        {
	        	builder.append(record.getLevel() + ": ");
	        }
	        
	        builder.append(formatMessage(record));
	        builder.append("\u001b[0m");//Reset
	        builder.append(System.lineSeparator());
	        
	        return builder.toString();
	    }
	}
}
