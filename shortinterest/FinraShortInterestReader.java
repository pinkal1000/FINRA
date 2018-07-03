package com.stocks.shortinterest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.jws.WebService;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;


public class FinraShortInterestReader {
	
	FileReader shortDataFile;
	BufferedReader fs;
	String shortDataJSON = "";
	URL shortDataURL;
	/**
	 *  
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void readShortData() throws IOException, InterruptedException
	{
		//shortDataFile = new FileReader("shortdata.txt"); 
		//fs = new BufferedReader(shortDataFile);
		
		shortDataURL = new URL("http://otce.finra.org/ESI/DownloadFileStream?fileId=90");
        fs = new BufferedReader(
        new InputStreamReader(shortDataURL.openStream()));
		
		String s  = fs.readLine();
		System.out.println(s);
		ArrayList<String> fields = new ArrayList<String>();
		
		StringTokenizer st  = new StringTokenizer(s,"|");
		while (st.hasMoreTokens())
		{
			fields.add(st.nextToken());
		}
		for (int i = 0; i < fields.size(); i++) {
			System.out.println(fields.get(i));
		}
		FileWriter fw = new FileWriter("jsonData.json");
		BufferedWriter bw = new BufferedWriter(fw);
		shortDataJSON = "[";
		while ((s = fs.readLine()) != null)
		{
			bw.write(shortDataJSON);
			bw.newLine();
			shortDataJSON = "";
			st  = new StringTokenizer(s,"|");
			int j = 0;
			while (st.hasMoreTokens())
			{

				if (j == 0)
					shortDataJSON = shortDataJSON.concat("{");
				if (j < 3)
					shortDataJSON = shortDataJSON.concat("\"" + fields.get(j) + "\": \"" + st.nextToken() + "\",");
				if (j > 2 && j < 8) // numeric data
				{	
					String data = st.nextToken();
					if (data.startsWith("."))
						data = "0" + data;
					if (data.startsWith("-."))
						data = data.replace("-", "-0");
					shortDataJSON = shortDataJSON.concat("\"" + fields.get(j) + "\": " + data + ",");
				}
				if (j == 8)
					shortDataJSON = shortDataJSON.concat("\"" + fields.get(j) + "\": " + st.nextToken() + "},");
				j++;
			}
			
		}
		int jsonDataSize = shortDataJSON.length();
		shortDataJSON = shortDataJSON.substring(0, jsonDataSize - 1 );
		shortDataJSON = shortDataJSON.concat("]");
		
		bw.write(shortDataJSON);
		bw.newLine();
		bw.flush();
		bw.close();
		fs.close();
		Thread.sleep(2000);
	}
	
	/**
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws IOException
	 */
	public String getSymbols() throws JsonParseException, IOException
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
 		//read json file data to String
		byte[] jsonData = Files.readAllBytes(Paths.get("jsonData.json"));

		
		JsonFactory factory = new JsonFactory();
		JsonParser  parser  = factory.createParser(jsonData);
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
//			System.out.println("Field name is " + fieldName);
//			System.out.println("Value is " + value);
			
		}
		generator.writeEndArray();
        generator.close();
        outputStream.close();
        System.out.println(outputStream.toString());
		return outputStream.toString();
	}
}
