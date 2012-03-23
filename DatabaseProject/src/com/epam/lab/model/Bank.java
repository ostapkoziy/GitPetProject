package com.epam.lab.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bank {
	private List<File> fileList= new ArrayList<File>();
	private List<Client> clientList = new ArrayList<Client>();
	private static final Bank INSTANCE = new Bank();
	
	private Bank(){
	}
	
	public static Bank getInstance(){
		return INSTANCE;
	}
	
	public void addFileToList(File arg){
		fileList.add(arg);
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}

	
}
