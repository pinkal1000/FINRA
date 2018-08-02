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
		dao = new FinraReaderDAO(app.getShortURL());
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
