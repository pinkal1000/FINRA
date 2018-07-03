package com.stocks.shortinterest;

public class FinraShortObject {
	private String secName;
	private String secSymbol;
	private String otcMarket;
	private double sharesShort;
	private double prevShsShort;
	private double changeFromPrev;
	private double pctChgFromPrev;
	private double avgShareVol;
	private double daysToCover;
	
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public String getSecSymbol() {
		return secSymbol;
	}
	public void setSecSymbol(String secSymbol) {
		this.secSymbol = secSymbol;
	}
	public String getOtcMarket() {
		return otcMarket;
	}
	public void setOtcMarket(String otcMarket) {
		this.otcMarket = otcMarket;
	}
	public double getSharesShort() {
		return sharesShort;
	}
	public void setSharesShort(double sharesShort) {
		this.sharesShort = sharesShort;
	}
	public double getPrevShsShort() {
		return prevShsShort;
	}
	public void setPrevShsShort(double prevShsShort) {
		this.prevShsShort = prevShsShort;
	}
	public double getChangeFromPrev() {
		return changeFromPrev;
	}
	public void setChangeFromPrev(double changeFromPrev) {
		this.changeFromPrev = changeFromPrev;
	}
	public double getAvgShareVol() {
		return avgShareVol;
	}
	public void setAvgShareVol(double avgShareVol) {
		this.avgShareVol = avgShareVol;
	}
	public double getDaysToCover() {
		return daysToCover;
	}
	public void setDaysToCover(double daysToCover) {
		this.daysToCover = daysToCover;
	}
	public double getPctChgFromPrev() {
		return pctChgFromPrev;
	}
	public void setPctChgFromPrev(double pctChgFromPrev) {
		this.pctChgFromPrev = pctChgFromPrev;
	}
	

}
