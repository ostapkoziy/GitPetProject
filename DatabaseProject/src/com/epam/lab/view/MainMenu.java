package com.epam.lab.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class MainMenu implements SubMenu {
	private static final Logger LOG = Logger.getLogger(MainMenu.class);
	private Map<Integer,SubMenu> mainMenuMap = new HashMap<Integer, SubMenu>();
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	private void init(){
		mainMenuMap.put(1, new ShowMenu());
		mainMenuMap.put(2, new SearchMenu());
	}
	@Override
	public void show() {
		this.init();
		System.out.println("\n========================================");
		System.out.println("Type '1' to view the database");
		System.out.println("Type '2' to search in the database");
		System.out.println("Type '3' to exit");
		
		int i = 0;
		
		try {
			i = Integer.valueOf(in.readLine());
		} catch (NumberFormatException e) {
			LOG.info("Incorrect value entered!",e);
			System.out.println("\nNot a valid number value. Please try again.");
			this.show();
		} catch (IOException e) {
			LOG.error("\nInput exception while reading key", e);
			System.out.println("Error while reading! Sorry!");
			System.exit(1);
		}
		if((i>0) && (i<=mainMenuMap.size())){
			mainMenuMap.get(i).show();
		}
		else if(i == mainMenuMap.size()+1){
			System.out.println("\nBye!");
			System.exit(0);
		}
		else{
			System.out.println("\nInvalid number entered. Please try again.");
			this.show();
		}
	}

}
