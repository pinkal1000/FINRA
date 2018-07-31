package com.finra.ShortData;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.finra.interfaces.FinraReader;


public class FinraShortInterestReader implements FinraReader{
	
	String jsonData = "";
	AppConfig app;
	FinraReaderDAO dao;
	public FinraShortInterestReader(AppConfig app)
	{
		this.app = app;
		dao = new FinraReaderDAO(app.getShortURLFile());
	}
	/**
	 * 
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException
	 */
	public String getShortJSON() throws IOException, InterruptedException 
	{
		
		
		
		return dao.readShortData();
	}
	
//	public void insertToElasticSearch() throws IOException
//	{
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
// 		//read json file data to String
//		byte[] jsonData = Files.readAllBytes(Paths.get("jsonData.json"));
//		String jsonString = new String(jsonData);
//		new TransportClient()
//        .addTransportAddress(new InetSocketTransportAddress("192.168.0.198",9300));
//	}
//	
	/**
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws IOException
	 */
	public String getSymbols() throws JsonParseException, IOException
	{
		return dao.getSymbols();
	}
	
}
