package com.finra.ShortData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finra.interfaces.FinraReader;



@RestController

public class ShortDataController { 
	
	AppConfig app;
	
	@Autowired
    public void setApp(AppConfig app) {
        this.app = app;
    }
	
   @RequestMapping(value = {"/json"}, method = RequestMethod.GET)
   public String load()  {
	   String s = "";
	   try
	   {
		   FinraReader fsir = new FinraShortInterestReader(app);
		   return fsir.getShortJSON();
	   }
	   catch (Exception exc)
	   {
		   exc.printStackTrace();
	   }
      return s;
   }
   @RequestMapping(value= {"/quote"}, method = RequestMethod.GET)
   public String getQuote(@RequestParam("symbol") String symbol)
   {
	   //to do for rich UI.
	   return "";
   }
}