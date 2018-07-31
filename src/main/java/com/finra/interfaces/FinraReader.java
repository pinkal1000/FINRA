package com.finra.interfaces;
import java.io.IOException;

public interface FinraReader
{
	public String getShortJSON() throws IOException, InterruptedException;
}