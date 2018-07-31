package com.finra.ShortData;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:configprops.properties")
@ConfigurationProperties

public class AppConfig {
	private String shortURL;
	private String shortURLFile;
	
	public String getShortURL() {
		return shortURL;
	}
	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}
	public String getShortURLFile() {
		return shortURLFile;
	}
	public void setShortURLFile(String shortURLFile) {
		this.shortURLFile = shortURLFile;
	}

}
