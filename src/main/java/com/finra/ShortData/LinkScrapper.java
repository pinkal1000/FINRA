package com.finra.ShortData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkScrapper {
	String archiveURL;
	ArrayList<String> links = new ArrayList<String>();
	
	public LinkScrapper(String url)
	{
		archiveURL = url;
	}
	
	public ArrayList<String> processLinks() throws MalformedURLException, IOException
	{
		String HTMLPage = getPageSource();
		//Pattern linkPattern = Pattern.compile("(<a[^>]+>.+?</a>)",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Pattern linkPattern = Pattern.compile("<a[^>]+href=[\"']?([\"'>]+)[\"']?[^>]*>(.+?)</a>",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher pageMatcher = linkPattern.matcher(HTMLPage);
		
		while(pageMatcher.find()){
		    filter(pageMatcher.group());
		}
		return links;
	}
	
	private void filter(String link)
	{
		StringTokenizer st = new StringTokenizer(link, "\"");
		if (!link.contains("ESI"))
			return;
		while (st.hasMoreTokens())
		{
			String alink = st.nextToken();
			if (alink.contains("ESI"))
			{
				alink = archiveURL.replace("/ESI/Archives", "") + alink;
				System.out.println(alink);
				links.add(alink);
				return;
			}
		}
	}
	
	private String getPageSource() throws MalformedURLException, IOException
	{
		InputStream is = new URL(archiveURL).openStream(); //archiveURL can be one of the links above
		BufferedReader buffer = null;
		buffer = new BufferedReader(new InputStreamReader(is, "iso-8859-9"));
		StringBuilder builder = new StringBuilder();
		int byteRead;
		while ((byteRead = buffer.read()) != -1) {
		    builder.append((char) byteRead);
		}
		buffer.close();
		return builder.toString();
	}

}
