package com.epam.lab.thread;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.epam.lab.db.DbManager;
import com.epam.lab.db.XmlDataBaseConvertor;

public class FileUpdater implements Runnable {
	private static final Logger LOG = Logger.getLogger(FileUpdater.class);
	private long modifiedTime;
	private List<File> fileList = new ArrayList<File>();
	private File[] bufer = new File[100];
	private XmlDataBaseConvertor xmlDbConvertor = new XmlDataBaseConvertor();
	private DbManager dbm = new DbManager();

	public void run() {
		this.modifiedTime = new Date().getTime();
		while (true) {
			this.synchronizeFolder(new File("resources\\for_update"));
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				System.out.println("Thread was interrupted!");
			}
		}
	}

	private void synchronizeFolder(File folderName) {
		List<File> folderFiles = this.getFolderFiles(folderName);
		List<File> dbFiles = this.getDbFiles();
		this.compareFileLists(folderFiles, dbFiles);
		// and now for deposits
		List<File> folderFilesDeposits = this.getFolderFilesDeposit(folderName);
		List<File> dbFilesDeposits = this.getDbFilesDeposits();
		this.compareFileListsDeposits(folderFilesDeposits, dbFilesDeposits);
		
	}
	//===========================================================================================
	//===================================CLIENT PROCESSING PART=================================
	//===========================================================================================
	
	private List<File> getFolderFiles(File folderName) {
		List<File> clientList = new ArrayList<File>();
		bufer = folderName.listFiles();
		fileList = Arrays.asList(bufer);
		for (File i : fileList) {
			if (i.getPath().contains("client")) {
				clientList.add(i);
			}
		}
		return clientList;
	}

	private List<File> getDbFiles() {
		DbManager dbm = new DbManager();
		List<File> filesInDb = new ArrayList<File>();
		ResultSet rs = dbm
				.select("SELECT DISTINCT FileName FROM Clients");
		try {
			while (rs.next()) {
				String fName = rs.getString("FileName");
				File temp = new File(
						XmlDataBaseConvertor.convertFileName(fName));
				filesInDb.add(temp);
			}
		} catch (SQLException e) {
			LOG.error("SQL Exception while getting new client file list", e);
		} catch (NullPointerException e){
			//no modifications to the database occured
			//exception ignored
		}
		Set<File> filesInDbSet = new TreeSet<File>();
		filesInDbSet.addAll(filesInDb);
		List<File> result = new ArrayList<File>();
		result.addAll(filesInDbSet);
		return result;
	}

	private void compareFileLists(List<File> folderFiles, List<File> dbFiles) {
		List<File> bufer = new ArrayList<File>();
		if (folderFiles.size() > dbFiles.size()) {
			bufer = folderFiles;
			bufer.removeAll(dbFiles);
			this.storeNewFiles(bufer);
		} else if (folderFiles.size() < dbFiles.size()) {
			bufer = dbFiles;
			bufer.removeAll(folderFiles);
			this.removeMissingFiles(bufer);
		} else {
			this.checkForModifications();
		}
	}

	private void storeNewFiles(List<File> newFiles) {
		for (File i : newFiles) {
			xmlDbConvertor.parseAndStoreClient(i);
		}
	}

	private void removeMissingFiles(List<File> missingFiles) {
		DbManager dbm = new DbManager();
		for (File i : missingFiles) {
			String fName = i.getPath();
			fName = XmlDataBaseConvertor.convertDbName(fName);
			dbm.delete("DELETE FROM Clients WHERE FileName LIKE '" + fName
					+ "'");
		}
	}

	private void checkForModifications() {
		List<File> folderFiles = this.getFolderFiles(new File(
				"resources\\for_update"));
		for (int i = 0; i < folderFiles.size(); i++) {
			if (folderFiles.get(i).lastModified() > this.modifiedTime) {
				this.storeModifiedFile(folderFiles.get(i));
				this.modifiedTime = new Date().getTime();
			}
		}
	}
	private void storeModifiedFile(File file) {
		String fName = XmlDataBaseConvertor.convertDbName(file.getPath());
		dbm.delete("DELETE FROM Clients WHERE FileName LIKE '"+fName+"'");
		xmlDbConvertor.parseAndStoreClient(file);
	}
	//===========================================================================================
	//===================================DEPOSIT PROCESSING PART=================================
	//===========================================================================================
	
	private List<File> getFolderFilesDeposit(File folderName) {
		List<File> depositList = new ArrayList<File>();
		bufer = folderName.listFiles();
		fileList = Arrays.asList(bufer);
		for (File i : fileList) {
			if (i.getPath().contains("deposit")) {
				depositList.add(i);
			}
		}
		return depositList;
	}
	private List<File> getDbFilesDeposits() {
		DbManager dbm = new DbManager();
		List<File> filesInDb = new ArrayList<File>();
		ResultSet rs = dbm
				.select("SELECT DISTINCT FileName FROM Deposits");
		try {
			while (rs.next()) {
				String fName = rs.getString("FileName");
				File temp = new File(
						XmlDataBaseConvertor.convertFileName(fName));
				filesInDb.add(temp);
			}
		} catch (SQLException e) {
			LOG.error("SQL Exception while getting new client file list", e);
		}
		Set<File> filesInDbSet = new TreeSet<File>();
		filesInDbSet.addAll(filesInDb);
		List<File> result = new ArrayList<File>();
		result.addAll(filesInDbSet);
		return result;
	}
	private void compareFileListsDeposits(List<File> folderFiles, List<File> dbFiles) {
		List<File> bufer = new ArrayList<File>();
		if (folderFiles.size() > dbFiles.size()) {
			bufer = folderFiles;
			bufer.removeAll(dbFiles);
			this.storeNewFilesDeposits(bufer);
		} else if (folderFiles.size() < dbFiles.size()) {
			bufer = dbFiles;
			bufer.removeAll(folderFiles);
			this.removeMissingFilesDeposits(bufer);
		} else {
			this.checkForModificationsDeposit();
		}
	}
	
	private void storeNewFilesDeposits(List<File> newFiles) {
		for (File i : newFiles) {
			xmlDbConvertor.parseAndStoreDeposit(i);
		}
	}
	private void removeMissingFilesDeposits(List<File> missingFiles) {
		DbManager dbm = new DbManager();
		for (File i : missingFiles) {
			String fName = i.getPath();
			fName = XmlDataBaseConvertor.convertDbName(fName);
			dbm.delete("DELETE FROM Deposits WHERE FileName LIKE '"+fName+"'");
		}
	}
	private void checkForModificationsDeposit() {
		List<File> folderFiles = this.getFolderFilesDeposit(new File(
				"resources\\for_update"));
		for (int i = 0; i < folderFiles.size(); i++) {
			if (folderFiles.get(i).lastModified() > this.modifiedTime) {
				this.storeModifiedFileDeposit(folderFiles.get(i));
				this.modifiedTime = new Date().getTime();
			}
		}
	}
	private void storeModifiedFileDeposit(File file) {
		String fName = XmlDataBaseConvertor.convertDbName(file.getPath());
		dbm.delete("DELETE FROM Deposits WHERE FileName LIKE '"+fName+"'");
		xmlDbConvertor.parseAndStoreDeposit(file);
	}
}