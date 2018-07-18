package com.finra.ShortData;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value= {"/shortinterest","/short"})
public class ShortDataController { 
   @RequestMapping(method = RequestMethod.GET)
   public String printQuote(@RequestParam("symbol") String symbol)  {
	   String s = "";
	   try
	   {
	   FinraShortInterestReader fsir = new FinraShortInterestReader();
	   fsir.readShortData();
	   s = fsir.getShortJSON();
	   }
	   catch (Exception exc)
	   {
		   exc.printStackTrace();
	   }
	   
//      model.addAttribute("message", "Hello Spring MVC Framework!");
      return s;
   }
}