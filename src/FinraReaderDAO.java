package com.finra.ShortData;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class FinraReaderDAO {
    String fileURL;
    LinkScrapper ls;
    ConcurrentHashMap<String, String> dataMap = new ConcurrentHashMap<String, String>();
    public FinraReaderDAO(String url)
    {
    	fileURL = url;
    	ls = new LinkScrapper(fileURL);
    	
    }
	/**
	 *  
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String readShortData() throws IOException, InterruptedException
	{
		ArrayList<String> links = ls.processLinks();
		ThreadManager tm = new ThreadManager();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < links.size(); i++)
		{
			System.out.println("loading link " + links.get(i));
			LoaderThread lt = new LoaderThread();
			Thread loader = new Thread(lt);
			tm.addThread(lt);
			lt.setLink(links.get(i));
			loader.start();
		}
		for (int i = 0; i < tm.getThreads().size(); i++)
		{
			while(!tm.getThreads().get(i).isDone)
			{
				Thread.sleep(500);
				continue;
			}
			tm.removeThread(tm.getThreads().get(i));
			System.out.println("threads remaining : " + tm.getThreads().size());
		}
		long timeTaken = System.currentTimeMillis() - startTime;
		return "the time taken in ms to complete this load is " + timeTaken;
	}
	
	class ThreadManager 
	{
		ArrayList<LoaderThread> threads = new ArrayList<LoaderThread>();
		void addThread(LoaderThread lt)
		{
			threads.add(lt);
			try {
			Thread.sleep(100);
			}
			catch(InterruptedException ie)
			{}
		}
		ArrayList<LoaderThread> getThreads()
		{
			return threads;
		}
		void removeThread(LoaderThread lt)
		{
			threads.remove(lt);
		}
	}

	class LoaderThread implements Runnable
	{
		String linkUrl;
		String jsonData;
		boolean isDone = false;
		public void setLink(String url)
		{
			this.linkUrl = url;
			
		}
		public void run()
		{
			try {
				dataMap.put(linkUrl,loadFile(linkUrl));
				System.out.println(linkUrl + " loaded string data of length " + dataMap.get(linkUrl).length());
				isDone = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private String loadFile(String url) throws IOException
		   {
			   System.out.println("Processing file " + url);
			   BufferedReader fs = null;
			   URL shortDataURL = new URL( url);
			   try 
			   {
				  fs  = new BufferedReader(
				   new InputStreamReader(shortDataURL.openStream()));
			   }
			   catch (java.net.ConnectException ce)
			   {
				   try 
				   {
					   Thread.sleep(10);
					   fs  = new BufferedReader(
							   new InputStreamReader(shortDataURL.openStream()));
				   }
				   catch (InterruptedException ie)
				   {
					   
				   }
				   
			   }
				String s  = fs.readLine();
				ArrayList<String> fields = new ArrayList<String>();
				
				StringTokenizer st  = new StringTokenizer(s,"|");
				while (st.hasMoreTokens())
				{
					fields.add(st.nextToken());
				}
				String shortDataJSON = "[";
				while ((s = fs.readLine()) != null)
				{
					jsonData += shortDataJSON;
					shortDataJSON = "";
					st  = new StringTokenizer(s,"|");
					int j = 0;
					while (st.hasMoreTokens())
					{

						if (j == 0)
						    shortDataJSON += "{";
						if (j < 3)
							shortDataJSON += "\"" + fields.get(j) + "\": \"" + st.nextToken() + "\",";
						if (j > 2 && j < 8) // numeric data
						{	
							String data = st.nextToken();
							if (data.startsWith("."))
								data = "0" + data;
							if (data.startsWith("-."))
								data = data.replace("-", "-0");
							shortDataJSON += "\"" + fields.get(j) + "\": " + data + ",";
						}
						if (j == 8)
							shortDataJSON += "\"" + fields.get(j) + "\": " + st.nextToken() + "},";
						j++;
					}
					
				}
				int jsonDataSize = shortDataJSON.length();
				shortDataJSON = shortDataJSON.substring(0, jsonDataSize - 1 );
				shortDataJSON += "]";
				jsonData += shortDataJSON;
				fs.close();
				return jsonData;

		   }
	}
	
   
	/**
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws IOException
	 */
	public String getSymbols() throws JsonParseException, IOException
	{
		Enumeration<String> keys = this.dataMap.keys();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// just need one data set to get unique symbols
		if(keys.hasMoreElements())
		{
		JsonFactory factory = new JsonFactory();
		JsonParser  parser  = factory.createParser(dataMap.get(keys.nextElement()).getBytes());
	    JsonGenerator generator = factory.createGenerator(outputStream);
	    generator.writeStartArray();
		while (!parser.isClosed())
		{
			JsonToken jsonToken = parser.nextToken();
			String fieldName = parser.getCurrentName();
			String value = parser.getValueAsString();
			if (fieldName != null && fieldName.equalsIgnoreCase("Security Symbol"))
			{
				if (value != null ) {
					if (!value.equalsIgnoreCase("Security Symbol")) {
						generator.writeStartObject();
						generator.writeObjectField("Security Symbol", value);
						generator.writeEndObject();
					}
				}
			}
			
		}
		generator.writeEndArray();
        generator.close();
        outputStream.close();
//        System.out.println(outputStream.toString());
		}
		return outputStream.toString();
	}
}
