package com.epam.lab.view;

import com.epam.lab.db.DbManager;
import com.epam.lab.thread.FileUpdater;

public class ShowMenu implements SubMenu {

	@Override
	public void show() {
		this.displayDataBase();
		System.out.println("========================================");
		new MainMenu().show();
	}

	private void displayDataBase() {
		DbManager dbm = new DbManager();
		dbm.displayTableClient();
		dbm.displayTableDeposit();
		
	}

}
