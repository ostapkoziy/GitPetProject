package com.epam.lab.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.epam.lab.db.DbManager;

public class SearchMenu implements SubMenu {
	private static final Logger LOG = Logger.getLogger(SearchMenu.class);
	private BufferedReader in = new BufferedReader(new InputStreamReader(
			System.in));
	private Map<Integer, String> depositMap = new HashMap<Integer, String>();

	@Override
	public void show() {
		System.out.println("\nType '1' if you want to search for a client");
		System.out.println("Type '2' if you want to search for a credit");
		System.out.println("Type '3' to go back to main menu");
		int i = 0;
		try {
			i = Integer.valueOf(in.readLine());
		} catch (NumberFormatException e) {
			LOG.info("Incorrect value entered!", e);
			System.out.println("\nNot a valid number value. Please try again.");
			this.show();
		} catch (IOException e) {
			LOG.error("\nInput exception while reading key", e);
			System.out.println("Error while reading! Sorry!");
			System.exit(1);
		}
		if (i == 1) {
			this.searchClients();
		}
		if (i == 2) {
			this.searchDeposits();
		} else if (i == 3) {
			new MainMenu().show();
		} else {
			System.out.println("\nInvalid number entered. Please try again.");
			this.show();
		}
	}

	private void searchClients() {
		Map<Integer, String> clientMap = new HashMap<Integer, String>();
		clientMap.put(1, "Name");
		clientMap.put(2, "Adress");
		clientMap.put(3, "Phone");
		System.out.println("\nType '1' if you want to search by "
				+ clientMap.get(1));
		System.out.println("Type '2' if you want to search by "
				+ clientMap.get(2));
		System.out.println("Type '3' if you want to search by "
				+ clientMap.get(3));
		int i = 0;
		try {
			i = Integer.valueOf(in.readLine());
		} catch (NumberFormatException e) {
			LOG.info("Incorrect value entered!", e);
			System.out.println("\nNot a valid number value. Please try again.");
			this.show();
		} catch (IOException e) {
			LOG.error("\nInput exception while reading key", e);
			System.out.println("Error while reading! Sorry!");
			System.exit(1);
		}
		String value = "";
		System.out.println("\nEnter the value to search for. ");
		try {
			value = in.readLine();
		} catch (NumberFormatException e) {
			LOG.info("Incorrect value entered!", e);
			System.out.println("\nNot a valid number value. Please try again.");
			this.show();
		} catch (IOException e) {
			LOG.error("\nInput exception while reading key", e);
			System.out.println("Error while reading! Sorry!");
			System.exit(1);
		}
		if ((i <= 0) && (i >= 4)) {
			System.out.println("\nInvalid number entered. Please try again.");
			this.show();
		}
		if ((i > 0) && (i < 4)) {
			DbManager dbm = new DbManager();
			String userName = "";
			ResultSet rs = dbm
					.select("SELECT Name, Adress, Phone FROM Clients "
							+ "WHERE " + clientMap.get(i) + " = '" + value
							+ "'");
			try {
				System.out.println("The search results are: \n");
				while (rs.next()) {
					String name = rs.getString("Name");
					String adress = rs.getString("Adress");
					String phone = rs.getString("Phone");
					userName = name;
					System.out.format("%-20s\t\t%30s\t%20s\n", name, adress,
							phone);
				}
				rs.close();
				System.out.println("\n" + userName + " has next deposits: \n");
				this.findDeposit(1, userName);
			} catch (SQLException e) {
				LOG.error("SQL Error while searching");
			} catch (NullPointerException e) {
				System.out.println("No results to display!");
			}
			this.show();
		}

	}

	private void searchDeposits() {
		depositMap.put(1, "UserName");
		depositMap.put(2, "Cash");
		depositMap.put(3, "Percent");
		System.out.println("\nType '1' if you want to search by "
				+ depositMap.get(1));
		System.out.println("Type '2' if you want to search by "
				+ depositMap.get(2));
		System.out.println("Type '3' if you want to search by "
				+ depositMap.get(3));
		int i = 0;
		try {
			i = Integer.valueOf(in.readLine());
		} catch (NumberFormatException e) {
			LOG.info("Incorrect value entered!", e);
			System.out.println("\nNot a valid number value. Please try again.");
			this.show();
		} catch (IOException e) {
			LOG.error("\nInput exception while reading key", e);
			System.out.println("Error while reading! Sorry!");
			System.exit(1);
		}
		String value = "";
		System.out.println("\nEnter the value to search for. ");
		try {
			value = in.readLine();
		} catch (NumberFormatException e) {
			LOG.info("Incorrect value entered!", e);
			System.out.println("\nNot a valid number value. Please try again.");
			this.show();
		} catch (IOException e) {
			LOG.error("\nInput exception while reading key", e);
			System.out.println("Error while reading! Sorry!");
			System.exit(1);
		}
		if ((i <= 0) && (i >= 4)) {
			System.out.println("\nInvalid number entered. Please try again.");
			this.show();
		}
		if ((i > 0) && (i < 4)) {
			this.findDeposit(i, value);
		}
		this.show();
	}

	private void findDeposit(int i, String value) {
		DbManager dbm = new DbManager();
		ResultSet rs = dbm
				.select("SELECT UserName, Percent, Cash FROM Deposits "
						+ "WHERE UserName LIKE '" + value + "'");
		try {
			while (rs.next()) {
				String name = rs.getString("UserName");
				String cash = rs.getString("Cash");
				String percent = rs.getString("Percent");
				System.out.format("%-20s\t\t%30s\t%20s\n", name, cash, percent);
			}
			rs.close();
		} catch (SQLException e) {
			LOG.error("SQL Error while searching");
		} catch (NullPointerException e) {
			System.out.println("No results to display!");

		}
	}
}
