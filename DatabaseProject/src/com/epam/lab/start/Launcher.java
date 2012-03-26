package com.epam.lab.start;
import java.sql.SQLException;

import org.apache.log4j.PropertyConfigurator;

import com.epam.lab.db.DbManager;
import com.epam.lab.db.XmlDataBaseConvertor;
import com.epam.lab.thread.FileUpdater;
import com.epam.lab.view.MainMenu;

public class Launcher {
	public static void main(String[] args) throws SQLException {
		PropertyConfigurator.configure("log4j.properties");

		DbManager dbm = new DbManager();
		XmlDataBaseConvertor xmldbc = new XmlDataBaseConvertor();
		//DomLauncher dom = new DomLauncher();	
		//dom.parse(new File("resources\\for_update\\deposit_1.xml"));
		//dbm.connect();
		//dbm.insert("DROP TABLE Clients");
		//dbm.insert("DROP TABLE Deposits");
		
		FileUpdater fUpd = new FileUpdater();
		Thread updater = new Thread(fUpd);
		updater.start();
		new MainMenu().show();
	}
}
