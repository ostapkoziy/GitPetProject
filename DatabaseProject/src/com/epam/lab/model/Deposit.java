package com.epam.lab.model;

public class Deposit {
	private int cash;
	private double percent;
	private String client;
	private String fileName;
	private long lastModified;
	
	public Deposit() {
		this.client = null;
		this.cash = 0;
		this.percent = 0.0;
		this.setLastModified(0);
	}
	public Deposit(String client, int cash, double percent, long lastModified) {
		this.client = client;
		this.cash = cash;
		this.percent = percent;
		this.setLastModified(lastModified);
	}
	
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

}
