package com.epam.lab.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.lab.dom.DomLauncher;
import com.epam.lab.model.Client;
import com.epam.lab.model.Deposit;
import com.epam.lab.sax.SaxLauncher;

public class XmlDataBaseConvertor {
	private static final Logger LOG = Logger
			.getLogger(XmlDataBaseConvertor.class);
	private DbManager dbManager = new DbManager();

	public void parseAndStoreClient(File file) {
		SaxLauncher saxLauncher = new SaxLauncher();
		saxLauncher.parseClient(file);

		List<Client> clientList = new ArrayList<Client>();
		clientList = SaxLauncher.clients;
		this.storeClientList(clientList);
	}

	private void storeClientList(List<Client> clientList) {
		String query = null;
		for (Client i : clientList) {
			query = convertClientToSqlString(i);
			dbManager.insert("INSERT INTO Clients VALUES " + query);
		}
	}

	private String convertClientToSqlString(Client i) {
		String result = "";
		result += "(";
		result += "'" + i.getName() + "', ";
		result += "'" + i.getAdress() + "', ";
		result += "'" + i.getPhoneNumber() + "', ";
		result += "'" + i.getFileName() + "', ";
		result += "'" + (double) i.getLastModified() + "')";
		return result;
	}
	
	public void parseAndStoreDeposit(File file) {
		SaxLauncher saxLauncher = new SaxLauncher();
		DomLauncher domLauncher = new DomLauncher();
		List<Deposit> depositList = domLauncher.parse(file);
		this.storeDepositList(depositList);
	}
	
	private void storeDepositList(List<Deposit> arg) {
		String query = null;
		for (Deposit i : arg) {
			query = convertDepositToSqlString(i);
			dbManager.insert("INSERT INTO Deposits VALUES " + query);
		}
	}

	private String convertDepositToSqlString(Deposit i) {
		//TODO bound ClientID to ClientName
		String result = "";
		result += "(";
		result += "'" + i.getCash() + "', ";
		result += "'" + i.getPercent() + "', ";
		result += "'" + i.getClient() + "', ";
		result += "'" + i.getFileName() + "',";
		result += "'" + (double) i.getLastModified() + "')";
		return result;
	}
	
	public void removeDeposit(Deposit arg){
		dbManager.insert("DELETE FROM Deposits WHERE Cash = "+arg.getCash()+
				"AND UserName = "+arg.getClient()+"");
	}
	public static String convertFileName(String arg){
		//adds all slashes
		String result,end;
		end = arg.substring(19);
		result = arg.substring(0,9);
		result+="\\";
		result+=arg.substring(9,19);
		result+="\\";
		result+=end;
		return result;
	}
	public static String convertDbName(String arg){
		//removes all slashes
		String result;
		result = arg.substring(0,9);
		result += arg.substring(10,20);
		result += arg.substring(21);
		return result;
	}
}
