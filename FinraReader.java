package com.finra.interfaces;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;

public interface FinraReader
{
	public String getShortJSON() throws IOException, InterruptedException;
	public String getSymbols() throws JsonParseException, IOException;
}