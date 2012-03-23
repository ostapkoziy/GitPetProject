package com.epam.lab.model;

public class Client {
	private String name;
	private String phoneNumber;
	private String adress;
	private String fileName;
	private long lastModified;
	
	public Client(String name, String phoneNumber, String adress, String fileName, long lastModified) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.adress = adress;
		this.fileName = fileName;
		this.lastModified = lastModified;
	}
	
	
	public Client() {
		this.name = null;
		this.phoneNumber = null;
		this.adress = null;
		this.fileName = null;
		this.lastModified = 0;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
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
