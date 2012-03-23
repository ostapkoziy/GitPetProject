package com.epam.lab.db;

import java.sql.*;

import org.apache.log4j.Logger;

import com.epam.lab.model.Client;

public class DbManager {
	private static final Logger LOG = Logger.getLogger(DbManager.class);
	private Connection con = null;

	private final String url = "jdbc:mysql://localhost:3306/";
	private final String dbName = "MySQL";
	private final String driverName = "com.mysql.jdbc.Driver";
	private final String userName = "root";
	private final String password = "ostap1993";

	public void connect() {
		try {
			Class.forName(driverName).newInstance();
			con = DriverManager.getConnection(url + dbName, userName, password);
			try {
				Statement st = con.createStatement();
				String tableClient = "CREATE TABLE Clients "
						+ "(Name VARCHAR(30), " 
						+ "Adress VARCHAR(30), " 
						+ "Phone VARCHAR(30), " 
						+ "FileName VARCHAR(90), "
						+ "LastModified DOUBLE)"; 
				st.executeUpdate(tableClient);
				System.out.println("Table CLIENT successfully created!");
				st.close();
				Statement stmt = con.createStatement();
				String tableDeposit = "CREATE TABLE Deposits "
						+ "(Cash INTEGER, "
						+ "Percent DOUBLE, "
						+ "UserName VARCHAR(30), "
						+ "FileName VARCHAR(90), "
						+ "LastModified DOUBLE)"; 
				stmt.executeUpdate(tableDeposit);
				System.out.println("Table DEPOSIT successfully created!");
				stmt.close();
			} catch (SQLException s) {
				System.out.println("Table allready exists!");
				LOG.error("Error in creating!",s);
			}
		} catch (Exception e) {
			LOG.error("Error in connecting to database. ", e);
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				LOG.error("Error while closing connection");
			}
		}
	}

	public void insert(String query) {
		try {
			con = DriverManager.getConnection(url + dbName, userName, password);
			Statement st = con.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			LOG.error("SQL Error while inserting", e);
			System.out.println("Error while inserting\n"+e);
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				LOG.error("Error while closing connection");
			}
		}
	}
	public void delete(String query) {
		try {
			con = DriverManager.getConnection(url + dbName, userName, password);
			Statement st = con.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			LOG.error("SQL Error while deleting", e);
			System.out.println("Error while deleting");
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				LOG.error("Error while closing connection");
			}
		}
	}

	public ResultSet select(String query) {
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(url + dbName, userName, password);
			Statement st = con.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			LOG.error("SQL Error while selecting", e);
			System.out.println("Error while selecting");
		}
		return rs;
	}

	public void displayTableClient() {
		System.out.println("\n\n================================\n\t\tClients\n" +
				"================================");
		System.out.format("%-20s\t\t%30s\t%20s\n","Name","Adress","Phone");
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(url + dbName, userName, password);
			Statement st = con.createStatement();
			rs = st.executeQuery("SELECT Name,Adress,Phone FROM Clients");
			while (rs.next()) {
				String name = rs.getString("Name");
				String adress = rs.getString("Adress");
				String phone = rs.getString("Phone");
				System.out.format("\n%-20s\t\t%30s\t%20s", name, adress, phone);
			}
		} catch (SQLException e) {
			LOG.error("SQL Error while displaying", e);
			System.out.println("Display error!");
		}
		finally{
			try {
				rs.close();
				con.close();
			} catch (SQLException e) {
				LOG.error("Error while closing connection");
			}
		}
	}
	public void displayTableDeposit() {
		System.out.println("\n\n================================\n\t\tDeposits\n" +
				"================================");
		System.out.format("%-20s\t\t%30s\t%20s\n","Client","Cash","Percent");
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(url + dbName, userName, password);
			Statement st = con.createStatement();
			rs = st.executeQuery("SELECT UserName, Cash, Percent FROM Deposits");
			while (rs.next()) {
				String client = rs.getString("UserName");
				int cash = rs.getInt("Cash");
				double percent = rs.getDouble("Percent");
				System.out.format("\n%-20s\t\t%30s\t%20s",client, cash, percent);
			}
			System.out.println("\n");
		} catch (SQLException e) {
			LOG.error("SQL Error while displaying", e);
			System.out.println("Display error!");
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				con.close();
			} catch (SQLException e) {
				LOG.error("Error while closing connection");
			}
		}
	}

}
