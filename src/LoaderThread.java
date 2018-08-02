package com.finra.ShortData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

public class LoaderThread implements Runnable
{
	private static ConcurrentHashMap<String, String> dataMap = new ConcurrentHashMap<String, String>();
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
		} catch (Exception e) {
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
		    String line;
		    StringBuilder dataFile = new StringBuilder();
		    // Extract Link contents into a file with a line dividing token.
			while ((line = fs.readLine()) != null)
			{
				dataFile.append(line + "^");
			}
		    fs.close();
		    StringTokenizer lineTokenizer = new StringTokenizer(dataFile.toString(), "^");
		    StringTokenizer st;
		    ArrayList<String> fields = new ArrayList<String>();
	        if(lineTokenizer.hasMoreTokens() ) {
			    String s  = lineTokenizer.nextToken();
				st  = new StringTokenizer(s,"|");
				while (st.hasMoreTokens())
				{
					fields.add(st.nextToken().replaceAll(" ", ""));
				}
	        }
			String shortDataJSON = "[";
			while (lineTokenizer.hasMoreElements())
			{
			    String s = lineTokenizer.nextToken();
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
			return jsonData;

	   }
	   public boolean isDone()
	   {
		   return isDone;
	   }
	   public static ConcurrentHashMap<String,String> getDataMap()
	   {
		   return dataMap;
	   }
}